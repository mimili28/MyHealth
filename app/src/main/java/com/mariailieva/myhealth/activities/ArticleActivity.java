package com.mariailieva.myhealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mariailieva.myhealth.Article;
import com.mariailieva.myhealth.R;

public class ArticleActivity extends AppCompatActivity {
    private TextView title;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        title = findViewById(R.id.titleView);
        text = findViewById(R.id.text);

        Intent intent = getIntent();
        Article article= (Article) intent.getSerializableExtra("model");
        title.setText(article.getTitle());
        text.setText(article.getText());
    }
}
