package com.nikpapps.lanchernxz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class ViewAnimation {
    public static boolean rotateFab(final View v, boolean rotate) {
        v.animate().setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(rotate ? 135f : 0f);
        return rotate;
    }
    public static void showIn(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0f);
        v.setTranslationY(v.getHeight());
        v.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }
    public static void showOut(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1f);
        v.setTranslationY(0);
        v.animate()
                .setDuration(200)
                .translationY(v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.INVISIBLE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f)
                .start();
    }
    public static void fadeOut(final View v){
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1f);
        v.animate()
                .setDuration(600).
                alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.INVISIBLE);
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }
    public static void fadeIn(final View v){
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0f);
        v.animate()
                .setDuration(600)
                .alpha(1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.VISIBLE);
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }
    public static void init(final View v) {
        v.setVisibility(View.INVISIBLE);
        v.setTranslationY(v.getHeight());
        v.setAlpha(0f);
    }
}
