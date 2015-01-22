package com.example.homework;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

/**
 * Created by Arafat Hossain on 1/18/2015.
 */
public class CustomActionBar {
    Toolbar toolbar;
    public CustomActionBar(Toolbar toolbar){
        this.toolbar = toolbar;
    }
    public Toolbar getActionBar(String title,int drawable,int titleColor){
        toolbar.setTitle(title);
        toolbar.setLogo(drawable);
        toolbar.setTitleTextColor(titleColor);
        return toolbar;
    }
}
