package com.example.student.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Student on 05.12.2017.
 */

public class ChatActivity extends AppCompatActivity {

    MessageAdapter adapter;
    String username;
    Handler handler;
    static Integer counter = 1;

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
        this.registerForContextMenu(listView);

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
        handler.postDelayed(updateTimerThread, 0);
    }

    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Message message = new Message("Computer", "Automated message: "+ String.valueOf(counter), df.format(new Date()));
            counter++;
            adapter.add(message);
            adapter.notifyDataSetChanged();
            handler.postDelayed(updateTimerThread, 10000);
        }
    };

    public void addMessage(View view){
        TextView addMessage = (TextView)findViewById(R.id.editText3);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Message message = new Message(username, addMessage.getText().toString(), df.format(new Date()));
        adapter.add(message);
        adapter.notifyDataSetChanged();
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
}
