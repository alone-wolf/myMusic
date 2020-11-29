package com.wh.mymusic.util;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class ViewUtils {

    public static void setViewCornerRadius(View v,int r){
        v.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(),   view.getHeight(), r);
            }
        });
        v.setClipToOutline(true);
    }

    public static void setViewCornerRadius(View v){
        setViewCornerRadius(v,30);
    }
}
