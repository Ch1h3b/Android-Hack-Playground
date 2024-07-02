package com.example.ram;

import android.content.Context;

public class B {
    static {
        System.loadLibrary("native-lib");
    }

    public native String keym(String i);

    private String key;
    public B(Context context){

        this.key = keym("des");

    }

    public String getKey() {
        return this.key;
    }
}
