package com.example.jsonparsingdemoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
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

import com.example.jsonparsingdemoapplication.model.CategoryData;
import com.example.jsonparsingdemoapplication.model.CategoryName;
import com.example.jsonparsingdemoapplication.model.GroupCategoryModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class AddCategoryDataActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtCatNameEn, edtCatNameHi, edtCatDes;
    TextView txtAddData,txtSelectImage;
    RelativeLayout rlImage;
    ImageView imgGallery;
    CheckBox chkFeatured;
    int SELECT_PICTURE = 200;
    Bitmap bitmap;
    private ArrayList<CategoryData> categoryListData = new ArrayList<CategoryData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_data);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_cat));
        initViews();
    }

    private void initViews() {
        edtCatNameEn = findViewById(R.id.edtCatNameEn);
        edtCatNameHi = findViewById(R.id.edtCatNameHi);
        edtCatDes = findViewById(R.id.edtCatDes);
        txtAddData = findViewById(R.id.txtAddData);
        rlImage = findViewById(R.id.rlImage);
        imgGallery = findViewById(R.id.imgGallery);
        chkFeatured = findViewById(R.id.chkFeatured);
        txtSelectImage = findViewById(R.id.txtSelectPhoto);

        rlImage.setOnClickListener(this::onClick);
        txtAddData.setOnClickListener(this::onClick);
    }

    void imageChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_PICTURE) {
                    Uri selectedImageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    Log.i("BITMAP",bitmap.toString());
                    // Get the path from the Uri
//                    final String path = getPathFromURI(selectedImageUri);
//                    if (path != null) {
//                        File f = new File(path);
//                        selectedImageUri = Uri.fromFile(f);
//                    }
//                    // Set the image in ImageView
//                    imgGallery.setImageURI(selectedImageUri);
                    imgGallery.setImageBitmap(bitmap);
                    txtSelectImage.setVisibility(View.GONE);
                    Log.i("IMAGEURL",selectedImageUri.toString());
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rlImage:
                imageChooser();
                break;
            case R.id.txtAddData:
                addCategoryData();
                break;
        }
    }

    private void addCategoryData(){
        if(edtCatNameEn.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please Add Category Name in English ", Toast.LENGTH_SHORT).show();
        }else if(edtCatNameHi.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please Add Category Name in Hindi ", Toast.LENGTH_SHORT).show();
        }else if(edtCatDes.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please Add Category Description ", Toast.LENGTH_SHORT).show();
        }else if(edtCatNameEn.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please Add Category Name in English ", Toast.LENGTH_SHORT).show();
        }else if(bitmap == null){
            Toast.makeText(this, "Please Select Photo ", Toast.LENGTH_SHORT).show();
        }else {
            CategoryData categoryData = new CategoryData();
            categoryData.slug = edtCatNameEn.getText().toString();
            categoryData.description = edtCatDes.getText().toString();
            CategoryName categoryName = new CategoryName();
//            categoryName.value = edtCatNameEn.getText().toString();
//            categoryName.language = "en";
//            categoryName._id = UUID.randomUUID().toString();
//            categoryData.name.add(categoryName);
//            CategoryName categoryNameHi = new CategoryName();
//            categoryNameHi.value = edtCatNameHi.getText().toString();
//            categoryNameHi.language = "hi";
//            categoryNameHi._id = UUID.randomUUID().toString();
//            categoryData.name.add(categoryNameHi);
            if(chkFeatured.isChecked()) {
                categoryData.featured = true;
            }else {
                categoryData.featured = false;
            }
            categoryData.categoryId = UUID.randomUUID().toString();
            categoryData.icon = bitmap.toString();
            /*Gson gson = new Gson();//create Gson object
            GroupCategoryModel modelData = gson.fromJson(loadJSONFromAsset(),GroupCategoryModel.class);
            categoryListData = modelData.data;
            JSONArray employeeList = new JSONArray(categoryListData);
            employeeList.put(categoryData);
            try {
                JSONObject employeeObject = new JSONObject();
                employeeObject.put("category", categoryData);
                try (FileWriter file = new FileWriter("TestCategoryData.json")) {
                    //We can write any JSONArray or JSONObject instance to the file
                    file.write(employeeList.toString());
                    file.flush();
                    Toast.makeText(this, "ADD Data Successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("TestCategoryData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

//    private void CopyAssets() {
//        AssetManager assetManager = getAssets();
//        String[] files = null;
//
//
//
//        System.out.println("File name => "+filename);
//        InputStream in = null;
//        OutputStream out = null;
//        try {
//            in = assetManager.open(YOUR_ASSETS_FILE);   // if files resides inside the "Files" directory itself
//            out = new FileOutputStream(STORAGE_PATH).toString() +"/" + filename);
//            copyFile(in, out);
//            in.close();
//            in = null;
//            out.flush();
//            out.close();
//            out = null;
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}