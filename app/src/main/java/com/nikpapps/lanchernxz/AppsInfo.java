package com.nikpapps.lanchernxz;

import android.graphics.drawable.Drawable;

import java.util.Comparator;

public class AppsInfo {
    CharSequence label;
    CharSequence packageName;
    Drawable icon;
    public static Comparator<AppsInfo> sort = new Comparator<AppsInfo>() {

        public int compare(AppsInfo s1, AppsInfo s2) {
            String App1 = s1.label.toString().toUpperCase();
            String App2 = s2.label.toString().toUpperCase();

            //ascending order
            return App1.compareTo(App2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
