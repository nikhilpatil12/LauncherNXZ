package com.nikpapps.lanchernxz;

import android.content.Context;
import android.content.SharedPreferences;

public class GetSharedResources {
    public SharedPreferences shareResources(Context context,String resname){
        SharedPreferences sp = context.getSharedPreferences(resname,0);
        return sp;
    }
}
