package com.ict311task2dawkins.android.myrecieptmanager;

import android.support.v4.app.Fragment;

public class ListUiActivity extends SingleFragmentActivty{

    ListUiActivity(){

    }

    @Override
    protected Fragment createFragment() {
        return new ListUiFragment();
    }
}
