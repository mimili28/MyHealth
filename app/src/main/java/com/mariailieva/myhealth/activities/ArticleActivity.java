package com.mariailieva.myhealth.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mariailieva.myhealth.model.Article;
import com.mariailieva.myhealth.R;
import com.squareup.picasso.Picasso;


public class ArticleActivity extends AppCompatActivity {
    private TextView title;
    private TextView text;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        title = findViewById(R.id.titleView);
        text = findViewById(R.id.text);
        imageView = findViewById(R.id.image);

        Intent intent = getIntent();
        Article article= (Article) intent.getSerializableExtra("model");
        title.setText(article.getTitle());
        text.setText(article.getText());
        Picasso.get().load(article.getImageURL()).into(imageView);
    }
}
