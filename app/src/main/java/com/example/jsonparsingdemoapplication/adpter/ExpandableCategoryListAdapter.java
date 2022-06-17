package com.example.jsonparsingdemoapplication.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jsonparsingdemoapplication.R;
import com.example.jsonparsingdemoapplication.model.CategoryData;
import com.example.jsonparsingdemoapplication.model.CategoryName;
import com.example.jsonparsingdemoapplication.model.GroupCategoryModel;

import java.util.ArrayList;

public class ExpandableCategoryListAdapter extends BaseExpandableListAdapter {

    private Context context;
    public ArrayList<CategoryData> categoryList;
    private ArrayList<CategoryData> originalCategoryList;
    public ArrayList<CategoryData> childList;
    public ExpandableCategoryListAdapter(Context context, ArrayList<CategoryData> categoryList,ArrayList<CategoryData> childList){
        this.context = context;
        this.categoryList=categoryList;
        this.childList = childList;
    }
    @Override
    public int getGroupCount() {
        return categoryList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        ArrayList<CategoryName> countryList = categoryList.get(i).name;
        return childList.size();
    }

    @Override
    public Object getGroup(int i) {
        if(categoryList.size()>0) {
            return categoryList.get(i);
        }
        return 0;
    }


    @Override
    public Object getChild(int i, int i1) {
        ArrayList<CategoryData> countryList = categoryList.get(i).categoryData;
        return countryList.size();
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        CategoryData categoryData = (CategoryData) getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.group_row_layout, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);

        heading.setText(categoryData.name.get(0).value);


        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        CategoryData categoryData = childList.get(i1);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.child_row_layout, null);
        }

        TextView lName = (TextView) view.findViewById(R.id.lName);

        lName.setText(categoryData.name.get(0).value);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void filterData(String query){

//        query = query.toLowerCase();
//        Log.v("MyListAdapter", String.valueOf(categoryList.size()));
//        categoryList.clear();
        ArrayList<CategoryData> newList = new ArrayList<CategoryData>();
        if(query.isEmpty()){
            newList.addAll(categoryList);
        }
        else {

            ArrayList<CategoryData> categoryListData = categoryList;


            for (int i = 0; i < categoryListData.size(); i++) {
                if (categoryListData.get(i).slug.toLowerCase().contains(query)) {
                    newList.add(categoryListData.get(i));
                }

//                for(CategoryData category: categoryList){
//                    if(category.slug.toLowerCase().contains(query)){
//                        newList.add(category);
//                    }
//                }
//                if(newList.size() > 0){
//                    CategoryData cData = new CategoryData(continent.getName(),newList);
//                    categoryList.add(nContinent);
//                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(categoryList.size()));
        notifyDataSetChanged();

    }

}
