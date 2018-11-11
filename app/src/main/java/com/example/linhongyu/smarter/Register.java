package com.example.linhongyu.smarter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Register extends AppCompatActivity {

    int day,month,year;
    private Calendar currentCalender;
    private TextView tv;
    private static int record;
    private static Resinfo resinfo;
    private static Rescredential rescredential;
    private static ArrayList<Rescredential> rescredentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etFirstname = (EditText) findViewById(R.id.et_firstname);
        final EditText etSurname = (EditText)findViewById(R.id.et_surname);
        final EditText etAddress = (EditText) findViewById(R.id.et_address);
        final EditText etEmail = (EditText)findViewById(R.id.et_email);
        final EditText etPostcode = (EditText) findViewById(R.id.et_postcode);
        final EditText etUserName = (EditText)findViewById(R.id.et_user_name);
        final EditText etPassword = (EditText) findViewById(R.id.et_user_pass);
        final EditText etProvider = (EditText)findViewById(R.id.et_provider);
        final EditText etMobile = (EditText) findViewById(R.id.et_mobile);
        final Spinner spResidentno = (Spinner) findViewById(R.id.s_no);
        final TextView tvDob = (TextView) findViewById(R.id.tv_dob);
        final TextView tvPassword = (TextView) findViewById(R.id.tv_password);
        tv = (TextView) findViewById(R.id.tv_message);
        Button btnRegister = (Button)findViewById(R.id.btn_submit);
        final Date registerDate = Calendar.getInstance().getTime();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        new AsyncTask<String, Void, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                record = RestClient.getRecordOfResinfo();
                rescredentials = (ArrayList<Rescredential>) RestClient.getRescredential();
                Log.i("record: ","number : "+ record);
                return record;
            }
        }.execute();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from UI
                int resdid = record + 1;
                String firstname = etFirstname.getText().toString();
                String surname = etSurname.getText().toString();
                Date dob = Calendar.getInstance().getTime();
                try {
                    dob = formatter.parse(tvDob.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String address = etAddress.getText().toString();
                String email = etEmail.getText().toString();
                String postcode = etPostcode.getText().toString();
                String stMobile = etMobile.getText().toString();
                String provider = etProvider.getText().toString();
                int residentno = Integer.parseInt(spResidentno.getSelectedItem().toString());

                String username = etUserName.getText().toString();
                String stPassword = etPassword.getText().toString();

                String[] inputs = {firstname,surname,address,email,postcode,stMobile,provider,username,stPassword};

                Boolean ckRepeat = checkUsername(username);
                Boolean ckBlank = checkBlank(inputs);
                Validation validation = new Validation();

                if (ckBlank && validation.validPassword(stPassword) && validation.validMobile(stMobile)){
                    String password = HashPassword.MD5(etPassword.getText().toString());
                    Long mobile = Long.parseLong(etMobile.getText().toString());
                    resinfo = new Resinfo(resdid,firstname,surname,dob,address,postcode,email,
                            mobile,residentno,provider);
                    rescredential = new Rescredential(username,password,registerDate);
                    rescredential.setResid(resinfo);

                    new AsyncTask<Boolean, Void, String>() {
                        @Override
                        protected String doInBackground(Boolean... params) {
                            Boolean ckR = params[0];
                            if (ckR){
                                RestClient.createResinfo(resinfo);
                                RestClient.createRescredential(rescredential);
                                return "Register Successfully";
                            }
                            else
                                return "Username has been used";
                        }
                        protected void onPostExecute(final String response) {
                            if (response.equals("Username has been used"))
                                tv.setText(response);
                            else{
                                Intent loginIntent = new Intent(Register.this, login.class);
                                startActivity(loginIntent);
                            }
                        }
                    }.execute(ckRepeat);
                }
                if (!validation.validPassword(stPassword))
                    tv.setText("Invalid Password");

                if (!validation.validMobile(stMobile))
                    tv.setText("Invalid Phone number");

                if (!ckBlank)
                    tv.setText("Please fill all the blank");
            }
        });

        currentCalender = Calendar.getInstance();

        day = currentCalender.get(Calendar.DAY_OF_MONTH);
        month = currentCalender.get(Calendar.MONTH);
        year = currentCalender.get(Calendar.YEAR);

        month = month+1;

        tvDob.setText(day + "-" + month + "-" + year);

        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        tvDob.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }

    public Boolean checkUsername(String newUsername){
        Boolean ckRepeat = true;
        for (int i = 0; i < rescredentials.size(); i++){
            if (rescredentials.get(i).getUsername().equals(newUsername)){
                ckRepeat = false;
                break;
            }
        }
        return ckRepeat;
    }

    public Boolean checkBlank(String[] newInputs){
        Boolean ckBlank = true;
        for (int i = 0; i < newInputs.length; i++){
            if (newInputs[i].trim().isEmpty()){
                ckBlank = false;
                break;
            }
        }
        return ckBlank;
    }

}