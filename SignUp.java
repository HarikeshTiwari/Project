package com.example.techapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;

public class SignUp extends AppCompatActivity {

    EditText etUsername,etPassword,etRetypePassword;

    Button btnSign;

    private RadioButton rdoGen;

    private RadioGroup rgGender;

    private Spinner spinCity;

    private CheckBox chkAgree;

    private String str="";

    private String saher="";

    private int counter=0;

    String[] city={"Mumbai","Pune","Nashik"};

    String[] Interest={"Watch","Phones","Laptop","Weareables","TV","Alexa"};

    String user,pwd,rePwd;

    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db=new DataBase(this);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,Interest);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        etRetypePassword=findViewById(R.id.etRetypePassword);
        btnSign=findViewById(R.id.btnSign);
        rgGender=findViewById(R.id.rgGender);
        spinCity=findViewById(R.id.spinCity);
        chkAgree=findViewById(R.id.chkAgree);

        user=etUsername.getText().toString();
        pwd=etPassword.getText().toString();
        rePwd=etRetypePassword.getText().toString();

        try {
            FileOutputStream fout = openFileOutput("myfile.txt",0);
            fout.write(user.getBytes());
            fout.close();
        }
        catch(Exception e){

        }

        ArrayAdapter adapter1=new ArrayAdapter(this, android.R.layout.simple_spinner_item,city);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCity.setAdapter(adapter1);
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saher=city[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        register(user,pwd,rePwd);
    }
    public void check(String rePass,String pass){
        if(pass!=rePass){
            Toast.makeText(getApplicationContext(),"password not matched",Toast.LENGTH_LONG).show();
        }
    }
    public void register(String user,String pwd,String rePwd){
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedid=rgGender.getCheckedRadioButtonId();
                rdoGen=findViewById(selectedid);
                Intent intent=new Intent(getApplicationContext(),Admin.class);
                intent.putExtra("radio",rdoGen.getText());

                if(user.equals("") && pwd.equals("")){
                    Toast.makeText(getApplicationContext(),"Invalid username/password",Toast.LENGTH_LONG).show();
                }
                else{
                    intent.putExtra("username",user);
                    intent.putExtra("password",pwd);

                }
                startActivity(intent);
            }
        });
        //handle after register
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Admin.class);
                if(chkAgree.isChecked()){
                    Toast.makeText(getApplicationContext(),"You agreed our terms and conditions",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please agree our terms and conditions",Toast.LENGTH_LONG).show();
                }
                Boolean value=db.insertuserdata(etUsername.getText().toString(),etPassword.getText().toString());
                if(value){
                    Toast.makeText(getApplicationContext(),"Data inserted",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error occured while inserting",Toast.LENGTH_LONG).show();
                }

                EditText etUsername=findViewById(R.id.etUsername);
                EditText etPassword=findViewById(R.id.etPassword);
                String us=etUsername.getText().toString();
                String pw=etPassword.getText().toString();

                SharedPreferences sp1= getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
                SharedPreferences.Editor editor=sp1.edit();
                editor.putString("username",us);
                editor.putString("password",pw);
                editor.commit();

                check(rePwd,pwd);
                intent.putExtra("hobby",str);
                intent.putExtra("city",saher);
                startActivity(intent);
            }
        });
    }
}