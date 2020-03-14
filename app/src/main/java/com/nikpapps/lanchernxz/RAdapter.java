package com.nikpapps.lanchernxz;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder> {
    public List<AppsInfo> appsList;
    public LinearLayout dock;
    public ConstraintLayout second;
    public float x,y;
    public ImageView ico1,ico2,ico3,ico4,ico5;
    public ImageView imgg;
    protected Context context;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        public TextView textView;
        public ImageView img;
        public Drawable iconn;
        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.appName);
            img = (ImageView) itemView.findViewById(R.id.appIcon);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            GetSharedResources gsr = new GetSharedResources();
            SharedPreferences sp = gsr.shareResources(context.getApplicationContext(),"accentColor");
            String str = sp.getString("appDrawer","#ffffff");
            if(str!=null)
            {
                Log.d("MSGN",str);//you were here, change the color values to get desired result
                int clr = Color.parseColor(str);
                int wht = Color.argb(225,225,225,225);
                //if(apps.getSolidColor()==)
                Log.d("MSGClr",clr+" apps");
                Log.d("MSGClr",wht+" wht");
                if(clr>wht) {
                    textView.setTextColor(Color.argb(255,255,255,255));
                }
                else{
                }
            }
            itemView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch(event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            Log.d("MSG", "Action is DragEvent.ACTION_DRAG_STARTED");

                            // Do nothing
                            break;

                        case DragEvent.ACTION_DRAG_ENTERED:
                            Log.d("MSG", "Action is DragEvent.ACTION_DRAG_ENTERED");
                            int x_cord = (int) event.getX();
                            int y_cord = (int) event.getY();
                            break;

                        case DragEvent.ACTION_DRAG_EXITED :
                            Log.d("MSG", "Action is DragEvent.ACTION_DRAG_EXITED");
                            x_cord = (int) event.getX();
                            y_cord = (int) event.getY();
                            break;

                        case DragEvent.ACTION_DRAG_LOCATION  :
                            Log.d("MSG", "Action is DragEvent.ACTION_DRAG_LOCATION");
                            x_cord = (int) event.getX();
                            y_cord = (int) event.getY();
                            break;

                        case DragEvent.ACTION_DRAG_ENDED   :

                            Log.d("MSG", "Action is DragEvent.ACTION_DRAG_ENDED");
                            //ViewAnimation.showOut(second.getViewById(R.id.newdock));
                            // add hide dock code here
                            break;

                        case DragEvent.ACTION_DROP:
                            Log.d("MSG", "ACTION_DROP event");
                            x = event.getX()/10;
                            y = event.getY()/10;
                            String index = event.getClipDescription().toString();
                            Log.d("MSG!",index);
                            x=x-7;
                            y=y+105;
                            LinearLayout ll = (LinearLayout)second.getViewById(R.id.newdock);
                            final int childCount = ll.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View v1 = ll.getChildAt(i);
                                int[] p = new int[2];
                                v1.getLocationOnScreen(p);
                                Log.d("LOC View",p[0]/10+" "+p[1]/10);
                                Log.d("LOC Event",(int)x+" "+(int)y);
                                if (p[0] / 10 - (int) x < 3 && p[0] / 10 - (int) x > -2 && p[1] / 10 - (int) y < 5 && p[1] / 10 - (int) y > -5) {
                                    Log.d("LOC31", i + "");
                                    ((ImageView) v1).setImageDrawable(imgg.getDrawable());
                                }
                            }
                            // add change dock icons code here
                            break;
                        default: break;
                    }
                    return true;
                }
            });
        }

        @Override
        public void onClick (View v) {
            int pos = getAdapterPosition();
            Context context = v.getContext();

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appsList.get(pos).packageName.toString());
            context.startActivity(launchIntent);
            //Toast.makeText(v.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();

        }

        @Override
        public boolean onLongClick(View v) {
            ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes, item);
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(img);
            imgg=img;
            v.startDrag(dragData,myShadow,null,0);
            LayoutInflater inflater = LayoutInflater.from(second.getContext());
            View view = inflater.inflate(R.layout.dock, second, false);
            second.addView(view);
            dock = (LinearLayout) second.findViewById(R.id.newdock);
            ico1 = (ImageView) dock.getChildAt(0);
            ico2 = (ImageView) dock.getChildAt(1);
            ico3 = (ImageView) dock.getChildAt(2);
            ico4 = (ImageView) dock.getChildAt(3);
            ico5 = (ImageView) dock.getChildAt(4);
            return false;
        }

    }



    public RAdapter(Context c) {
        appsList = new ArrayList<>();
        context = c;
    }

        @Override
    public void onBindViewHolder(RAdapter.ViewHolder viewHolder, int i) {
        String appLabel = appsList.get(i).label.toString();
        String appPackage = appsList.get(i).packageName.toString();
        Drawable appIcon = appsList.get(i).icon;
        TextView textView = viewHolder.textView;
        textView.setText(appLabel);
        viewHolder.itemView.setTag(appPackage);
        ImageView imageView = viewHolder.img;
        imageView.setImageDrawable(appIcon);
        imageView.getLayoutParams().height = 160;
        imageView.getLayoutParams().width = 160;
    }


    @Override
    public int getItemCount() {
        //This method needs to be overridden so that Androids knows how many items
        //will be making it into the list
        return appsList.size();
    }


    @Override
    public RAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.appslist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void setDock(ConstraintLayout layout){
        second = layout;
    }
}
