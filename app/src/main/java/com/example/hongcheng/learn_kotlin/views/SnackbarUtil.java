package com.example.hongcheng.learn_kotlin.views;

import android.content.res.ColorStateList;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by hongcheng on 16/9/6.
 */
public class SnackbarUtil {

    private static SnackbarUtil instance;
    private static Snackbar snackbar;

    private SnackbarUtil() {

    }

    public static SnackbarUtil getInstance() {
        if (instance == null) {
            instance = new SnackbarUtil();
        }
        return instance;
    }

    public SnackbarUtil create(View view, String content) {

        if (snackbar == null) {
            snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        } else {
            snackbar.setText(content);
        }

        return instance;
    }

    public SnackbarUtil create(View view, int contentId) {

        if (snackbar == null) {
            snackbar = Snackbar.make(view, contentId, Snackbar.LENGTH_LONG);
        } else {
            snackbar.setText(contentId);
        }

        return instance;
    }

    public SnackbarUtil addAction(String action, View.OnClickListener listener) {
        if (snackbar != null) {
            snackbar.setAction(action, listener);
        }

        return instance;
    }

    public SnackbarUtil addAction(int actionId, View.OnClickListener listener) {
        if (snackbar != null) {
            snackbar.setAction(actionId, listener);
        }

        return instance;
    }

    public SnackbarUtil setActionColor(ColorStateList color) {
        if (snackbar != null) {
            snackbar.setActionTextColor(color);
        }

        return instance;
    }

    public SnackbarUtil setActionColor(int color) {
        if (snackbar != null) {
            snackbar.setActionTextColor(color);
        }

        return instance;
    }

    public void show() {
        if (snackbar != null) {
            snackbar.show();
        }
    }

    public static void show(View view, String content) {

        if (snackbar == null) {
            snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        } else {
            snackbar.setText(content);
        }

        snackbar.show();
    }

    public static void show(View view, int contentId) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, contentId, Snackbar.LENGTH_LONG);
        } else {
            snackbar.setText(contentId);
        }

        snackbar.show();
    }

}
