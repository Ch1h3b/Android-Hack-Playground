package com.example.ram;

import android.content.Context;

public class A {
    static {
        System.loadLibrary("native-lib");
    }

    public native String keym(String i);

    private String key;
    public A(Context context){

        this.key = keym(context.getResources().getString(R.string.param));


    }

    public String getKey() {
        return this.key;
    }
}
