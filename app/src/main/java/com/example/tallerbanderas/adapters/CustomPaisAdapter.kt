package com.example.tallerbanderas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.example.tallerbanderas.R
import com.example.tallerbanderas.data.Pais

class CustomPaisAdapter(context: Context, paisList: List<Pais>) :
    ArrayAdapter<Pais>(context, R.layout.flag_card, paisList) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.flag_card, parent, false)

        val imageViewFlag: ImageView = view.findViewById(R.id.flag)
        val textViewPais: TextView = view.findViewById(R.id.name)
        val textViewNative: TextView = view.findViewById(R.id.nativeName)
        val textViewCurrency: TextView = view.findViewById(R.id.currency)

        val pais = getItem(position)



        textViewPais.text = "${pais?.name}"
        textViewNative.text = "${pais?.nativeName}"
        textViewCurrency.text = "Currency: ${pais?.currencyName} ${pais?.currencySymbol}"

        pais?.flagUri?.let { uri ->
            try {
                Glide.with(context)
                    .load(uri)
                    .into(imageViewFlag)
            } catch (e: GlideException) {

                e.logRootCauses("Glide Loading Error")


            }
        }

        return view
    }
}
