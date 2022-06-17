package com.example.jsonparsingdemoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.example.jsonparsingdemoapplication.adpter.ExpandableCategoryListAdapter;
import com.example.jsonparsingdemoapplication.model.CategoryData;
import com.example.jsonparsingdemoapplication.model.CategoryName;
import com.example.jsonparsingdemoapplication.model.GroupCategoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener,View.OnClickListener{
    private SearchView search;
    private ExpandableCategoryListAdapter listAdapter;
    private ExpandableListView categoryList;
    private ArrayList<CategoryData> categoryListData = new ArrayList<CategoryData>();
    FloatingActionButton btnFloatAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadDataFromJson();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

//        categoryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                if(categoryListData != null&&categoryListData.size()>0){
//                    Intent detailIntent = new Intent(MainActivity.this,CategoryDetailActivity.class);
//                    detailIntent.putExtra("CATEGORY_DATA",categoryListData.get(i));
//                    startActivity(detailIntent);
//                }
//                return false;
//            }
//        });
    }

    private void initViews(){
        search = findViewById(R.id.search);
        categoryList = findViewById(R.id.expandableList);
        btnFloatAdd = findViewById(R.id.btnFloating);
        btnFloatAdd.setOnClickListener(this::onClick);
    }


    private void loadDataFromJson(){
        Gson gson = new Gson();//create Gson object
        GroupCategoryModel modelData = gson.fromJson(loadJSONFromAsset(),GroupCategoryModel.class);
        categoryListData = modelData.data;
        ArrayList<CategoryData> catData = new ArrayList<>();
        ArrayList<CategoryData> childData = new ArrayList<>();
        ArrayList<CategoryData> dataarry = null;
        if(dataarry == null){
            dataarry = new ArrayList<>();
        }
        for (int i = 0; i < categoryListData.size(); i++) {

            if(categoryListData.get(i).parentID.equalsIgnoreCase("0")){
                categoryListData.get(i).categoryData = dataarry;
                Log.i("Category",categoryListData.get(i).categoryData.toString());
                catData.add(categoryListData.get(i));
                Log.i("CategoryIDS",catData.get(0).toString());

            }
                for (int i1 = 0; i1 < catData.size(); i1++) {
                    if (categoryListData.get(i).parentID.equalsIgnoreCase(catData.get(i1).categoryId)) {

                        dataarry.add(categoryListData.get(i));


                    }
                }


          //  Log.i("SUBIDS", String.valueOf(dataarry.size()));
            listAdapter = new ExpandableCategoryListAdapter(MainActivity.this, catData,dataarry);
            categoryList.setAdapter(listAdapter);
        }
//        for (int i = 0; i < categoryListData.size(); i++) {
//            if(categoryListData.get(i).parentID.equalsIgnoreCase("0")){
//                catData.add(categoryListData.get(i));
//            }
//            for (int i1 = 0; i1 < catData.size(); i1++) {
//                if(categoryListData.get(i).parentID.equalsIgnoreCase(catData.get(i1).categoryId)){
//                    childData.add(categoryListData.get(i));
//
//                }
//            }
//        }


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

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            categoryList.expandGroup(i);
        }
    }
    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        listAdapter.notifyDataSetChanged();
        //expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        listAdapter.filterData(s);
        listAdapter.notifyDataSetChanged();
       // expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listAdapter.filterData(s);
        listAdapter.notifyDataSetChanged();
        //expandAll();
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnFloating:
                Intent addCat = new Intent(MainActivity.this,AddCategoryDataActivity.class);
                startActivity(addCat);
                break;
        }
    }
}