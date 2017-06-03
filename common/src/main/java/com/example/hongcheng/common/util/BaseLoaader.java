package com.example.hongcheng.common.util;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by hongcheng on 16/9/16.
 */
public class BaseLoaader extends AsyncTaskLoader<String> {

    public BaseLoaader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {
        return null;
    }
}
