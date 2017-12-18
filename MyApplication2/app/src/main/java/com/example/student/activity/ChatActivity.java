package com.example.student.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student.model.Message;
import com.example.student.adapter.MessageAdapter;
import com.example.student.service.MessageInterface;
import com.example.student.adapter.R;
import com.example.student.model.RequestMessage;
import com.example.student.model.ResponseMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Student on 05.12.2017.
 */

public class ChatActivity extends AppCompatActivity {

    MessageAdapter adapter;
    String username;
    Handler handler;
    static Integer counter = 1;
    MessageInterface messageInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        adapter = new MessageAdapter(this, new ArrayList<Message>());


        TextView userField = (TextView)findViewById(R.id.textView5);
        Intent myIntent	= getIntent();
        username =  getIntent().getStringExtra("username");
        userField.setText("Username: " + username);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_chat);
        setSupportActionBar(myToolbar);

        final ListView listView = (ListView) findViewById(R.id.messageList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        this.registerForContextMenu(listView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cgisdev.utcluj.ro/moodle/chat-piu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messageInterface = retrofit.create(MessageInterface.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = adapter.getItem(position);
                ImageView favouriteImage;
                if (msg.getFavourite()) {
                    int type = adapter.getItemViewType(position);
                    if (type == 1 || (type == 3 && position%2==0))
                        favouriteImage = (ImageView)view.findViewById(R.id.imageView2);
                    else
                        favouriteImage = (ImageView)view.findViewById(R.id.imageView3);
                    favouriteImage.setVisibility(View.INVISIBLE);
                    msg.setFavourite(false);
                }
                else {
                    int type = adapter.getItemViewType(position);
                    if (type == 1 || (type == 3 && position%2==0))
                        favouriteImage = (ImageView)view.findViewById(R.id.imageView2);
                    else
                        favouriteImage = (ImageView)view.findViewById(R.id.imageView3);
                    favouriteImage.setVisibility(View.VISIBLE);
                    msg.setFavourite(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

       handler = new Handler();
       handler.postDelayed(updateTimerThread, 1000);
    }

    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            final String token = pref.getString("token", "");

            messageInterface.receiveMessage("Bearer "+ token).enqueue(new Callback<List<ResponseMessage>>() {
                @Override
                public void onResponse(Call<List<ResponseMessage>> call, Response<List<ResponseMessage>> response) {
                    if (response.isSuccessful()) {
                        List<ResponseMessage> messageList = response.body();
                        if (messageList != null) {
                            for (ResponseMessage responseMessage: messageList) {
                                Message message = new Message(responseMessage.getSender(), responseMessage.getText(), responseMessage.getTimestamp());
                                adapter.add(message);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    else{
                        System.out.println(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseMessage>> call, Throwable t) {
                    System.out.println("Receive: Failed connection!");
                }
            });
            handler.postDelayed(updateTimerThread, 1000);
        }
    };

    public void addMessage(View view){
        final TextView addMessage = (TextView)findViewById(R.id.editText3);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        final Message message = new Message(username, addMessage.getText().toString(), df.format(new Date()));
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = pref.getString("token", "");

        messageInterface.sendMessage("Bearer "+ token, new RequestMessage(addMessage.getText().toString())).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Message sended.");
                    adapter.add(message);
                    adapter.notifyDataSetChanged();
                }
                else{
                    System.out.println("Eroare: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Send: Failed connection!");
            }
        });
    }

    @Override
    public	boolean	onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.chat_menu,	menu);
        return	true;
    }

    @Override
    public	boolean	onOptionsItemSelected(MenuItem	item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int	id	=	item.getItemId();
        if	(id	==	R.id.design1)
        {
            adapter.setType(1);
            adapter.notifyDataSetChanged();
            return	true;
        }
        if(id	==	R.id.design2)
        {
            adapter.setType(2);
            adapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.bothdesigns){
            adapter.setType(3);
            adapter.notifyDataSetChanged();
            return true;
        }
        return	super.onOptionsItemSelected(item);
    }

    public void onBackPressed()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
