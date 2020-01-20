package com.example.grut;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        credit1();
        credit2();
    }

    private void credit1() {
        TextView name = findViewById(R.id.credit1_name);
        name.setText("Freepik");
        name.setPaintFlags(name.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLink("https://www.flaticon.com/authors/freepik");
            }
        });
        TextView from = findViewById(R.id.credit1);
       // from.setText("click here");
        from.setPaintFlags(from.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLink("https://www.flaticon.com");
            }
        });
    }

    private void credit2() {
        TextView name = findViewById(R.id.credit2_name);
        name.setText("Pixelmeetup");
        name.setPaintFlags(name.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLink("https://www.flaticon.com/authors/pixelmeetup");
            }
        });
        TextView from = findViewById(R.id.credit2);
        from.setPaintFlags(from.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLink("https://www.flaticon.com");
            }
        });
    }

    private void clickLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
