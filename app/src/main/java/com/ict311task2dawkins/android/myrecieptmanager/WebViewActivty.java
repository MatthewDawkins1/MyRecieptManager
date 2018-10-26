package com.ict311task2dawkins.android.myrecieptmanager;

import android.support.v4.app.Fragment;

public class WebViewActivty extends SingleFragmentActivty {
    @Override
    protected Fragment createFragment() {
        return new WebViewFragment();
    }
}
