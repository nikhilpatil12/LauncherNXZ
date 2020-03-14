package com.nikpapps.lanchernxz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;

public class SettingsActivity extends AppCompatActivity {
    Button btnColorPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Context context = SettingsActivity.this;
        btnColorPicker = (Button)findViewById(R.id.colorPicker);
        SharedPreferences sp = getSharedPreferences("accentColor",0);
        String str = sp.getString("myStore","#ffffff");
        if(str!=null)
        {
            Log.d("MSGN",str);
            btnColorPicker.setBackgroundColor(Color.parseColor(str));
        }
        btnColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog colorPickerDialog= ColorPickerDialog.createColorPickerDialog(SettingsActivity.this);
                colorPickerDialog.setHexaDecimalTextColor(Color.parseColor("#ffffff")); //There are many functions like this
                colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                    @Override
                    public void onColorPicked(int color, String hexVal) {
                        btnColorPicker.setBackgroundColor(Color.parseColor(hexVal));
                        SharedPreferences.Editor editor = getSharedPreferences("accentColor",0).edit();
                        editor.putString("appDrawer", hexVal);
                        editor.commit();
                        finish();
                        System.exit(0);
                    }
                });
                colorPickerDialog.show();
            }
        });

    }
}
