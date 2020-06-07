package com.mariailieva.myhealth.view;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mariailieva.myhealth.R;
import com.mariailieva.myhealth.activities.ArticleActivity;

public class ArticlesViewHolder extends RecyclerView.ViewHolder{

    TextView titleText;


    public ArticlesViewHolder(@NonNull final View itemView) {
        super(itemView);
        LinearLayout linearLayout = (LinearLayout) itemView;
        titleText = linearLayout.findViewById(R.id.list_title);

    }

    public TextView getTitleText() {
        return titleText;
    }
}
