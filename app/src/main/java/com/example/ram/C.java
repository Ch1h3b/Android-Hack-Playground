package com.example.ram;

import android.content.Context;

public class C {
    static {
        System.loadLibrary("native-lib");
    }

    public native String keym(String i);

    private String key;
    public C(Context context){



    }

    public String getKey() {
        this.key = keym("xor");
        return this.key;
    }
}
