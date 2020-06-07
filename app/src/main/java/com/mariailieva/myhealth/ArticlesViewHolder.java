package com.mariailieva.myhealth;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mariailieva.myhealth.activities.ArticleActivity;

public class ArticlesViewHolder extends RecyclerView.ViewHolder{

    TextView titleText;
    //private ArticleAdapter.OnListItemClick onListItemClick;

    public ArticlesViewHolder(@NonNull final View itemView) {
        super(itemView);
        LinearLayout linearLayout = (LinearLayout) itemView;
        titleText = linearLayout.findViewById(R.id.list_title);
        //itemView.setOnClickListener(this);
//        titleText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(itemView.getContext(), ArticleActivity.class);
//                //intent.putExtra()
//
//                itemView.getContext().startActivity(intent);
//            }
//        });

    }
}
