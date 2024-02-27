package com.example.tallerbanderas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tallerbanderas.adapters.CustomPaisAdapter
import com.example.tallerbanderas.data.Pais
import com.example.tallerbanderas.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val jsonArray = loadJSONfromString(loadJSONfromAssets("data.json"))
            val paisList = parsePaisesFromJson(jsonArray)

            val customAdapter = CustomPaisAdapter(this, paisList)
            binding.listViewContainer.adapter = customAdapter



            binding.listViewContainer.setOnItemClickListener { parent, view, position, id ->
                val selectedPais = paisList[position]
                Toast.makeText(this, "Selected ${selectedPais.name}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            // Handle JSONException, log or show an error message as needed.
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle other exceptions, log or show an error message as needed.
        }


    }

    fun parsePaisesFromJson(jsonObject: JSONObject): List<Pais> {
        val paisList = mutableListOf<Pais>()

        val countriesArray = jsonObject.optJSONArray("Countries")
        if (countriesArray != null) {
            for (i in 0 until countriesArray.length()) {
                val jsonPais = countriesArray.getJSONObject(i)
                val pais = Pais(
                    jsonPais.optString("Name", ""),
                    jsonPais.optString("NativeName", ""),
                    jsonPais.optString("Alpha3Code", ""),
                    jsonPais.optString("CurrencyName", ""),
                    jsonPais.optString("CurrencySymbol", ""),
                    Uri.parse(jsonPais.optString("FlagPNG", "")),
                    jsonPais.optString("NumericCode", "")
                )
                paisList.add(pais)
            }
        }

        return paisList
    }


    fun loadJSONfromAssets(name: String): String {
        return try {
            val inputStream = assets.open(name)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            ""
        }
    }

    fun loadJSONfromString(jsonString: String): JSONObject {
        return try {
            JSONObject(jsonString)
        } catch (ex: org.json.JSONException) {
            ex.printStackTrace()
            JSONObject()
        }
    }
}