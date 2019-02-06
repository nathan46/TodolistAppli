package com.example.nathan.todolist

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class RequestHTTP(val main: MainActivity?, val histo: HistoryActivity?) : AsyncTask<String, String, String>() {

    val url = "http://todolistapi58.herokuapp.com/api/"
    var b: String = ""


    override fun doInBackground(vararg a: String?): String? {
        var textJson: String? = ""
        b = a[0].toString()

        if (main == null && histo != null) {

            if (b == "GET") {
                return getJSON(url + "true", null, 20000, "GET")
            }
        } else if (main != null) {

            when {
                a[0] == "GET" -> {
                    textJson = getJSON(url + "false", null, 20000, "GET")
                }
                a[0] == "POST" -> {
                    val json = "{\"text\": \"" + main.textInput.toString() + "\", \"coche\":\"false\"}"
                    textJson = getJSON(url, json, 20000, "POST")
                }
                a[0] == "PUT" -> {
                    val str = if (main.isCoche) "true"
                    else "false"

                    val json = "{\"text\": \"" + main.textLine.toString() + "\", \"coche\": \"" + str + "\"}"
                    return getJSON(url + "" + main.id.toString(), json, 20000, "PUT")
                }
            }
        }

        return textJson
    }


    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        if (histo != null) {
            histo.affichage(handleJson(result))
        } else if (b != "PUT" && main != null)
            main.affichage(handleJson(result))

    }

    fun getJSON(url: String, json: String?, timeout: Int, method: String): String? {
        var connection: HttpURLConnection? = null
        try {

            val u = URL(url)
            connection = u.openConnection() as HttpURLConnection
            connection.requestMethod = method

            //set the sending type and receiving type to json
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")

            connection.allowUserInteraction = false
            connection.connectTimeout = timeout
            connection.readTimeout = timeout

            if (json != null) {
                //set the content length of the body
                connection.setRequestProperty("Content-length", json.toByteArray().size.toString() + "")
                connection.doInput = true
                connection.doOutput = true
                connection.useCaches = false

                //send the json as body of the request
                val outputStream = connection.outputStream
                outputStream.write(json.toByteArray(Charsets.UTF_8))
                outputStream.close()
            }

            //Connect to the server
            connection.connect()

            val status = connection.responseCode
            Log.i("HTTP Client", "HTTP status code : " + status)
            Log.i("HTTP Client", "HTTP json : " + json)
            Log.i("HTTP Client", "HTTP method " + method)
            Log.i("HTTP Client", "HTTP url " + url)
            when (status) {
                200, 201 -> {
                    val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                    val sb = StringBuilder()
                    bufferedReader.forEachLine { line ->
                        sb.append(line + "\n")
                    }
                    bufferedReader.close()
                    Log.i("HTTP Client", "Received String : " + sb.toString())
                    //return received string
                    return sb.toString()
                }
            }

        } catch (ex: MalformedURLException) {
            Log.e("HTTP Client", "Error in http connection" + ex.toString())
        } catch (ex: IOException) {
            Log.e("HTTP Client", "Error in http connection" + ex.toString())
        } catch (ex: Exception) {
            Log.e("HTTP Client", "Error in http connection" + ex.toString())
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect()
                } catch (ex: Exception) {
                    Log.e("HTTP Client", "Error in http connection" + ex.toString())
                }

            }
        }
        return null

    }

    fun handleJson(jsonString: String?): ArrayList<Line> {
        var jsonArray: JSONArray? = null
        var jsonObject: JSONObject? = null

        try {
            jsonArray = JSONArray(jsonString)

        } catch (e: Exception) {
            Log.i("TAG", e.toString())
            jsonObject = JSONObject(jsonString)
        }


        val liste = ArrayList<Line>()

        if (jsonArray != null) {
            var x = 0

            while (x < jsonArray.length()) {

                val jsonObject = jsonArray.getJSONObject(x)


                liste.add(Line(
                        jsonObject.getInt("id"),
                        jsonObject.getString("text"),
                        jsonObject.getString("coche")

                ))
                ++x
            }
        } else {
            val line = Line(jsonObject!!.getInt("id"), jsonObject.getString("text"), jsonObject.getString("coche"))
            liste.add(line)
        }
        /*if (listeDebut == null) {
            listeDebut = liste
            listeFin = liste
        }*/

        /*val affichage = listeAffiche(this, liste)
        line_liste.adapter = affichage*/

        return liste

    }
}