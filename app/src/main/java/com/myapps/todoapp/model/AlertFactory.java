package com.myapps.todoapp.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AlertFactory {

    public static void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.create().show();
    }

    public static AlertDialog showProgressDialog(Context context) {
        ProgressBar progressBar = new ProgressBar(context);

        progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.parseColor("#DC143C")));

        TextView loadingText = new TextView(context);

        loadingText.setText("Loading...");
        loadingText.setTextSize(16);
        loadingText.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.leftMargin = 30;

        loadingText.setLayoutParams(textParams);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);  // Set horizontal orientation

        int padding = 50;
        layout.setPadding(padding, padding, padding, padding);
        layout.addView(progressBar);
        layout.addView(loadingText);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#333333"));

        AlertDialog progressDialog = new AlertDialog.Builder(context)
                .setView(layout)
                .setCancelable(false)
                .create();

        progressDialog.show();

        return progressDialog;
    }

}
