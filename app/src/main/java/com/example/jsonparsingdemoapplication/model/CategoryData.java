package com.example.jsonparsingdemoapplication.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryData implements Serializable {

    public int type,categoryNumber,level;
    public String categoryId,slug,description,parentID,attributeSet,icon,create_date;
    public boolean featured,status;
    public ArrayList<CategoryName> name;
    public ArrayList<CategoryData> categoryData ;
}
