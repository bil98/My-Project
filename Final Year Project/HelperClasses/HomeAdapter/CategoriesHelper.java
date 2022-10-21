package com.example.cuba2.HelperClasses.HomeAdapter;

import android.graphics.drawable.Drawable;

public class CategoriesHelper {

  int image;
  Drawable gradient;
  String title;

    public CategoriesHelper(int image, Drawable gradient, String title) {
        this.image = image;
        this.gradient = gradient;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public Drawable getGradient() {
        return gradient;
    }

    public String getTitle() {
        return title;
    }
}
