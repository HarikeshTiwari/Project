package com.example.techapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Admin extends AppCompatActivity {

    EditText etProductNameAndPrice;

    ImageView imgProduct;

    Button btnSelectFromGallery,btnAddProduct;

    AdminDatabase adminDatabase=new AdminDatabase(this);

    final int REQUEST_CODE=999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //initilaize all views
        init();

        //handle choose button from gallery
        btnSelectFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Admin.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etProductNameAndPrice.length()!=0){
                    String prod_name=etProductNameAndPrice.getText().toString();
                    byte[] prod_img=convertImageToByte(imgProduct);
                    AddData(prod_name,prod_img);
                    Intent exploreIntent=new Intent(getApplicationContext(),DashBoard.class);
                    startActivity(exploreIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter product name",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void AddData(String prod_name,byte[] prod_img) {
        boolean result=adminDatabase.addData(prod_name,prod_img);
        if(result){
            Toast.makeText(getApplicationContext(),"Data inserted successfully",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Error while inserting",Toast.LENGTH_LONG).show();
        }

    }

    private byte[] convertImageToByte(ImageView imgProduct) {
        Bitmap bitmap=((BitmapDrawable)imgProduct.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);
            }
            else{
                Toast.makeText(getApplicationContext(),"You dont have permission",Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri uri=data.getData();
        try{
            InputStream inputStream=getContentResolver().openInputStream(uri);
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            imgProduct.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        etProductNameAndPrice=findViewById(R.id.etProductNameAndPrice);
        imgProduct=findViewById(R.id.imgProduct);
        btnSelectFromGallery=findViewById(R.id.btnSelectFromGallery);
        btnAddProduct=findViewById(R.id.btnAddProduct);
    }
}