package com.ict311task2dawkins.android.myrecieptmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class SingleFragmentActivty extends AppCompatActivity{

    protected abstract Fragment createFragment ();

    private int contentView;
    private int fragment;

    SingleFragmentActivty(int contentView, int fragment){
        this.contentView = contentView;
        this.fragment = fragment;

    }

    SingleFragmentActivty(){
        this(R.layout.activity_fragment, R.id.fragment_container);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.contentView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(this.fragment);

        if (fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction().add(this.fragment, fragment).commit();
        }

    }
}
