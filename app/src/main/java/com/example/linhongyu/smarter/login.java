package com.example.linhongyu.smarter;

import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {

    private EditText etUserName,etPassword;
    public static Resinfo resinfo;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName = (EditText) findViewById(R.id.et_userName);
        etPassword = (EditText) findViewById(R.id.et_password);
        tv = (TextView)findViewById(R.id.tv_welMessage);
        final TextView resultTextView = (TextView) findViewById(R.id.tv_password);
        Button btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask<String, Void, String>() {

                    @Override
                    protected String doInBackground(String... params) {
                        String name = params[0];
                        String password = params[1];
                        String result = "";
                        if (name.trim().isEmpty() || password.trim().isEmpty()){
                            result = "Please fill all blank";
                        }
                        else{
                            Log.i("name","print : " +params[0]);
                            result = RestClient.checkLogin(params[0], HashPassword.MD5(params[1]));
                            if(result.equals("Login successfully")) {
                                resinfo = RestClient.getResinfo(params[0]).get(0).getResid();
                                Intent homeIntent = new Intent(login.this, home.class);
                                //用Bundle携带数据
                                Bundle bundle=new Bundle();
                                //传递name参数为tinyphp
                                bundle.putString("username",params[0]);
                                homeIntent.putExtras(bundle);
                                startActivity(homeIntent);
                            }
                        }
                        return result;
                    }
                    @Override
                    protected void onPostExecute(String response) {
                        tv.setText(response);
                    }
                }.execute(etUserName.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    public void register(View view) {
        Intent registerIntent = new Intent(this,Register.class);
        startActivity(registerIntent);
    }

}
