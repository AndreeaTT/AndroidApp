package com.example.student.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.student.adapter.R;
import com.example.student.model.ResponseLogin;
import com.example.student.model.User;
import com.example.student.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cgisdev.utcluj.ro/moodle/chat-piu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    public void SignInClick(View view){
        EditText username = (EditText) findViewById(R.id.editText2);
        EditText password = (EditText) findViewById(R.id.editText);
        TextInputLayout name_error = (TextInputLayout) findViewById(R.id.text_input_layout);
        TextInputLayout password_error = (TextInputLayout) findViewById(R.id.password_input_layout);
        TextView button_message = (TextView) findViewById(R.id.button_input_layout);
        Button signInBtn = (Button) findViewById(R.id.button);
        TextView nameLabel  = (TextView)findViewById(R.id.textView);
        TextView passwordLabel  = (TextView)findViewById(R.id.textView2);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar_login);
        progressBar.setVisibility(View.GONE);
        boolean correct = true;

        name_error.setError(null);
        password_error.setError(null);
        button_message.setText("");

        if (username.getText().toString().isEmpty()){
            name_error.setError("Username cannot be empty");
            correct = false;
        }
        else{
            if (username.getText().toString().length() < 3){
                name_error.setError("Username is too short");
                correct = false;
            }
        }

        if (password.getText().toString().isEmpty()){
            password_error.setError("Password cannot be empty");
            correct = false;
        }
        else{
            if (password.getText().toString().length() < 6){
                password_error.setError("Password is too short");
                correct = false;
            }
        }

        if (correct) {

            User logedUser = new User(username.getText().toString(), password.getText().toString());
            userService.loginUser(logedUser).enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response){
                    EditText username = (EditText) findViewById(R.id.editText2);
                    EditText password = (EditText) findViewById(R.id.editText);
                    TextInputLayout name_error = (TextInputLayout) findViewById(R.id.text_input_layout);
                    TextInputLayout password_error = (TextInputLayout) findViewById(R.id.password_input_layout);
                    TextView button_message = (TextView) findViewById(R.id.button_input_layout);
                    Button signInBtn = (Button) findViewById(R.id.button);
                    TextView nameLabel  = (TextView)findViewById(R.id.textView);
                    TextView passwordLabel  = (TextView)findViewById(R.id.textView2);

                    ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar_login);
                    progressBar.setVisibility(View.GONE);
                    boolean correct = true;

                    name_error.setError(null);
                    password_error.setError(null);
                    button_message.setText("");

                    if (response.isSuccessful()){
                        button_message.setText("Login successfull!");
                        button_message.setTextColor(Color.GREEN);

                        progressBar.setVisibility(View.VISIBLE);
                        username.setVisibility(View.GONE);
                        password.setVisibility(View.GONE);
                        name_error.setVisibility(View.GONE);
                        password_error.setVisibility(View.GONE);
                        button_message.setVisibility(View.GONE);
                        signInBtn.setVisibility(View.GONE);
                        nameLabel.setVisibility(View.GONE);
                        passwordLabel.setVisibility(View.GONE);

                        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        ResponseLogin responseLogin = response.body();
                        edit.putString("username", responseLogin.getDisplay());
                        edit.putString("token", responseLogin.getToken());
                        edit.putString("name", username.getText().toString());
                        edit.putString("password", password.getText().toString());
                        edit.commit();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                                startActivity(intent);
                            }
                        }, 500);
                    }
                    else
                    {
                        button_message.setText("Login failed. Username or password are inccorect!");
                        button_message.setTextColor(Color.RED);
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t){
                    System.out.println("Failed connection!");
                }
            });
        }
    }
}
