package com.example.shakil.kotlinpdfviewwithindicator

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener

class MainActivity : AppCompatActivity(), OnLoadCompleteListener, OnPageChangeListener {
    var pdfView: PDFView? = null
    var layoutDot: LinearLayout? = null
    lateinit var dot: Array<TextView?>
    var dotCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pdfView = findViewById(R.id.pdfView)
        layoutDot = findViewById(R.id.layoutDot)

        pdfView!!.fromAsset("englishgrammarbook.pdf")
                .enableSwipe(true)
                .swipeHorizontal(true)
                .spacing(0)
                .autoSpacing(true)
                .pageSnap(true)
                .pageFling(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .onLoad(this)
                .onPageChange(this)
                .load()
    }

    override fun loadComplete(nbPages: Int) {
        dotCount = nbPages
        pdfView!!.visibility = View.VISIBLE
        addDot(0)
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        addDot(page)
    }

    private fun addDot(page: Int) {
        dot = arrayOfNulls(dotCount)
        layoutDot!!.removeAllViews()
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(10, 10, 10, 10)
        for (i in dot.indices) {
            dot[i] = TextView(this)
            //dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i]!!.background = resources.getDrawable(R.drawable.blank_circle)
            dot[i]!!.textSize = 15f
            dot[i]!!.layoutParams = params
            dot[i]!!.gravity = Gravity.CENTER
            //dot[i].setTextColor(getResources().getColor(R.color.black));
            layoutDot!!.addView(dot[i])
        }
        //active dot
        dot[page]!!.setTextColor(resources.getColor(R.color.white))
        dot[page]!!.text = (page + 1).toString()
        dot[page]!!.background = resources.getDrawable(R.drawable.full_circle)
    }
}