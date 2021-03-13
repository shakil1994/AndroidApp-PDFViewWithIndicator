package com.example.shakil.pdfviewwithindicator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

public class MainActivity extends AppCompatActivity implements OnLoadCompleteListener, OnPageChangeListener {

    PDFView pdfView;
    LinearLayout layoutDot;
    TextView[] dot;
    int dotCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfView = findViewById(R.id.pdfView);
        layoutDot = findViewById(R.id.layoutDot);

        pdfView.fromAsset("englishgrammarbook.pdf")
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
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {
        dotCount = nbPages;
        pdfView.setVisibility(View.VISIBLE);
        addDot(0);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        addDot(page);
    }

    private void addDot(int page) {
        dot = new TextView[dotCount];
        layoutDot.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);

        for (int i = 0; i < dot.length; i++) {
            ;
            dot[i] = new TextView(this);
            //dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i].setBackground(getResources().getDrawable(R.drawable.blank_circle));
            dot[i].setTextSize(15);
            dot[i].setLayoutParams(params);
            dot[i].setGravity(Gravity.CENTER);
            //dot[i].setTextColor(getResources().getColor(R.color.black));
            layoutDot.addView(dot[i]);
        }
        //active dot
        dot[page].setTextColor(getResources().getColor(R.color.white));
        dot[page].setText(String.valueOf(page + 1));
        dot[page].setBackground(getResources().getDrawable(R.drawable.full_circle));
    }
}