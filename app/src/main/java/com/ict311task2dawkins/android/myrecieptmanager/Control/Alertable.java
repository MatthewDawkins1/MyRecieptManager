package com.ict311task2dawkins.android.myrecieptmanager.Control;

import java.util.Observable;
import java.util.Observer;

public class Alertable implements Observer {

    @Override
    public void update(Observable observable, Object o) {
        Alert newAlert = (Alert)o;
        newAlert.getExecutionCode().execute(newAlert.getRefObj());
    }
}