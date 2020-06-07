package com.mariailieva.myhealth.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.mariailieva.myhealth.R;
import com.mariailieva.myhealth.activities.ArticleActivity;
import com.mariailieva.myhealth.model.Article;
import com.mariailieva.myhealth.view.ArticlesViewHolder;

public class ArticleAdapter extends FirestoreRecyclerAdapter<Article, ArticlesViewHolder> {

    public ArticleAdapter(@NonNull FirestoreRecyclerOptions<Article> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ArticlesViewHolder holder, int position, @NonNull final Article model) {
        holder.getTitleText().setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ArticleActivity.class);
                intent.putExtra("model", model);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        return new ArticlesViewHolder(v);
    }


}
