package colocviu.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Student on 05.12.2017.
 */

public class ChatActivity extends AppCompatActivity {

    MessageAdapter adapter;
    String username;
    static Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        adapter = new MessageAdapter(this, new ArrayList<Message>());

        TextView userField = (TextView)findViewById(R.id.textView5);
        Intent myIntent	= getIntent();
        username =  getIntent().getStringExtra("username");
        userField.setText("Username: " + username);
        counter = 1;

        ListView listView = (ListView) findViewById(R.id.messageList);
        listView.setAdapter(adapter);

    }

    public void addMessage(View view){
        TextView addMessage = (TextView)findViewById(R.id.editText3);

        Message message = new Message(username, addMessage.getText().toString(), new Date().toString());
        adapter.add(message);
        adapter.notifyDataSetChanged();
    }

    public void computerMessage(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = new Message("Computer", "Automated message: "+ String.valueOf(counter), new Date().toString());
                adapter.add(message);
                adapter.notifyDataSetChanged();
                counter++;
            }
        }, 10000);
    }
}
