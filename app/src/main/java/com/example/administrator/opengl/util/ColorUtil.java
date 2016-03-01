package com.example.administrator.opengl.util;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/2/29.
 */
public class ColorUtil {
    public static void parseColor(String color) {
        int parsedColor = Color.parseColor(color);
        float red = Color.red(parsedColor) / 255f;
        float green = Color.green(parsedColor) / 255f;
        float blue = Color.blue(parsedColor) / 255f;
    }
}
