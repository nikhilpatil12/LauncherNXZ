package com.nikpapps.lanchernxz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.LinearLayout.*;

public class MainActivity extends AppCompatActivity {
    ImageView chbutt;
    AppsInfo appsInfo;
    public List<AppsInfo> listOfApps;
    public RAdapter radapter;
    public static Context baseContext;
    ViewPager mPager;
    TextView appname;
    ImageView settings;
    PagerAdapter mPagerAdapter;
    ConstraintLayout applist,first,second,mainlayout;
    SlidingUpPanelLayout slidingUpPanelLayout;
    LinearLayout dock,apps,newdock,linearLayout;
    public boolean isexpanded=false,changed1=false,changed2=false;
    private DrawView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RView);
        radapter = new RAdapter(this);
        new myThread().execute();
        slidingUpPanelLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        recyclerView.setAdapter(radapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setScrollBarDefaultDelayBeforeFade(6000);
        drawView = (DrawView) findViewById(R.id.paintView);
        applist=(ConstraintLayout)findViewById(R.id.applist);
        mainlayout = (ConstraintLayout)findViewById(R.id.mainlay);
        listOfApps = new ArrayList<>();
        baseContext = getBaseContext();
        dock = (LinearLayout)findViewById(R.id.dock);
        first = (ConstraintLayout) findViewById(R.id.first);
        second = (ConstraintLayout) findViewById(R.id.second);
        radapter.setDock(mainlayout);
        apps = (LinearLayout)findViewById(R.id.searchBar);
        linearLayout= (LinearLayout)findViewById(R.id.linearLayout);
        settings = (ImageView)findViewById(R.id.settings_icon);
        final String DEBUG_TAG="MSG";
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        drawView.hideee(applist);
        drawView.init(dm);
        mPager = (ViewPager) findViewById(R.id.homeScreenPager);
        mPagerAdapter = new HomescreenAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);
        chbutt = (ImageView) findViewById(R.id.chromebut);
        chbutt.setImageDrawable(getActivityIcon(this, "com.android.chrome", "com.google.android.apps.chrome.Main"));
        chbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(launchIntent);
            }
        });
        SharedPreferences sp = getSharedPreferences("accentColor",0);
        String str = sp.getString("appDrawer","#ffffff");
        if(str!=null)
        {
            dock.setBackgroundColor(Color.parseColor(str));
            apps.setBackgroundColor(Color.parseColor(str));
            recyclerView.setBackgroundColor(Color.parseColor(str));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            ColorDrawable viewColor = (ColorDrawable) apps.getBackground();
            int blk = Color.parseColor("#7a7a7a");
            int colorId = viewColor.getColor();
            //if(apps.getSolidColor()==)
            Log.d("MSGC",colorId+" apps");
            Log.d("MSGC",blk+" blk");
            if(colorId>blk) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            else{
            }
        }
        slidingUpPanelLayout.setOverlayed(true);
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                dock.setAlpha(1-1.3f*slideOffset);
                /*if(slideOffset>0.5){
                    dock.setVisibility(GONE);
                }
                else{
                    dock.setVisibility(VISIBLE);
                }*/
                //linearLayout.getChildAt(0).setScaleY(1-slideOffset);
                apps.setAlpha(slideOffset);
                recyclerView.setAlpha(slideOffset);

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.d("MSG",newState.toString());
                if(newState.toString().equals("EXPANDED")){

                }
            }
        });
        settings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
    public static Drawable getActivityIcon(Context context, String packageName, String activityName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityName));
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
        return resolveInfo.loadIcon(pm);
    }

    public class myThread extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... Params) {
            PackageManager pm = getPackageManager();
            Intent i = new Intent(Intent.ACTION_MAIN, null);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
            for(ResolveInfo ri:allApps) {
                AppsInfo app = new AppsInfo();
                app.label = ri.loadLabel(pm);
                app.packageName = ri.activityInfo.packageName;
                app.icon = ri.activityInfo.loadIcon(pm);
                //radapter.appsList.add(app);
                listOfApps.add(app);
            }
            Collections.sort(listOfApps,appsInfo.sort);
            radapter.appsList=listOfApps;

            return "Success";
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            updateStuff();
        }
        public void updateStuff() {
            radapter.notifyItemInserted(radapter.getItemCount()-1);
        }
    }

    public SharedPreferences getSharedResources(String a,int b) {
        return super.getSharedPreferences(a,b);
    }
}
