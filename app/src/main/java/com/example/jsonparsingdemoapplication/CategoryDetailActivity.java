package com.example.jsonparsingdemoapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsonparsingdemoapplication.R;
import com.example.jsonparsingdemoapplication.model.CategoryData;
import com.example.jsonparsingdemoapplication.model.CategoryName;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class CategoryDetailActivity extends AppCompatActivity{

    TextView txtCatNameEnValue, txtCatNameHiValue, txtCatDesValue;
    CategoryData categoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.cat_detail));
        initViews();

        if(getIntent().getExtras() != null){
            categoryData = (CategoryData) getIntent().getSerializableExtra("CATEGORY_DATA");
            txtCatNameEnValue.setText(categoryData.name.get(0).value);
            txtCatNameHiValue.setText(categoryData.name.get(1).value);
            txtCatDesValue.setText(categoryData.description);


        }
    }

    private void initViews() {
        txtCatNameEnValue = findViewById(R.id.txtCatNameEnValue);
        txtCatNameHiValue = findViewById(R.id.txtCatNameHiValue);
        txtCatDesValue = findViewById(R.id.txtCatDesValue);

    }


}