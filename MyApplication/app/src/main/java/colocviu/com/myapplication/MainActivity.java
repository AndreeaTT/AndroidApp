package colocviu.com.myapplication;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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

            if (username.getText().toString().equals("admin") && password.getText().toString().equals("password")) {
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

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                        startActivity(intent);
                    }
                }, 5000);
            } else
            {
                button_message.setText("Login failed. Username or password are inccorect!");
                button_message.setTextColor(Color.RED);
            }
        }
    }
}
