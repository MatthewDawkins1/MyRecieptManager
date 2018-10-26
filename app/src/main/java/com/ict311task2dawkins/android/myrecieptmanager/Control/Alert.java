package com.ict311task2dawkins.android.myrecieptmanager.Control;


import java.util.Observable;

public class Alert extends Observable {

    private ExecutionCode executionCode;
    private Object refObj;

    public interface ExecutionCode{
        void execute(Object refObj);
    }

    public Alert(ExecutionCode executionCode, Alertable alertable, Object refObj){
        this.executionCode = executionCode;
        addObserver(alertable);
        this.refObj = refObj;
        execute();
    }

    private void execute(){
        setChanged();
        notifyObservers(this);
    }

    public ExecutionCode getExecutionCode() {
        return executionCode;
    }

    public Object getRefObj() {
        return refObj;
    }
}
