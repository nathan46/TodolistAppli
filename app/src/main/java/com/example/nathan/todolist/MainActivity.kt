package com.example.nathan.todolist

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var textInput: String = ""

    // var pour listeAffiche
    var textLine: String = ""
    var isCoche: Boolean = false
    var id: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RequestHTTP(this,null).execute("GET")


        val enterText = findViewById<EditText>(R.id.enter)
        val buttonAdd = findViewById<FloatingActionButton>(R.id.buttonAdd)

        enterText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty())
                    buttonAdd.visibility = View.VISIBLE
                else
                    buttonAdd.visibility = View.INVISIBLE
            }
        })


        buttonAdd.setOnClickListener({
            textInput = enterText.text.toString()
            RequestHTTP(this,null).execute("POST")
            enterText.text = Editable.Factory.getInstance().newEditable("")
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.action_history -> {
                startActivity(Intent(this@MainActivity,HistoryActivity::class.java))
                //Toast.makeText(this,"genre historique",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun affichage(liste: ArrayList<Line>) {
        val affichage = listeAffiche(this, liste,false)
        line_liste.adapter = affichage
    }


    /*  inner class AsyncTaskHandleJsonGet : AsyncTask<String, String, String>() {
          override fun doInBackground(vararg a: String?): String? {

              return getJSON(url, null, 20000, "GET")

          }

          override fun onPostExecute(result: String?) {
              super.onPostExecute(result)
              handleJson(result)
          }
      }

      inner class AsyncTaskHandleJsonPost : AsyncTask<String, String, String>() {

          override fun doInBackground(vararg a: String?): String? {

              var json = "{\"text\": \"$textInput\", \"coche\":\"false\"}"
              return getJSON(url, json, 20000, "POST")

          }

          override fun onPostExecute(result: String?) {
              super.onPostExecute(result)

              AsyncTaskHandleJsonGet().execute()


          }
      }

      inner class AsyncTaskHandleJsonPut : AsyncTask<String, String, String>() {
          override fun doInBackground(vararg a: String?): String? {

              var json = "{\"text\": \"Nouveau\", \"coche\": \"true\"}"
              return getJSON("http://todolistapi58.herokuapp.com/api/2", json, 20000, "PUT")

          }

          override fun onPostExecute(result: String?) {
              super.onPostExecute(result)

              handleJson(result)


          }
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

      */

    /*inner class AsyncTaskHandleJsonGet : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {

            val text: String
            var connection = URL(url[0]).openConnection() as HttpURLConnection

            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }

            } finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }*/

}
