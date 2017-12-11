package com.example.student.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private String[] titles = {"Barcelona, 3 nights", "Maldive, 7 nights", "Thailand, 10 nights"};
    private String[] descriptions = {"Barcelona is a nice city", "Maldive is a nice city", "Thailand is a nice city"};
    private String[] images = {"offer_1", "offer_2", "offer_3"};
    private String[] prices = {"300 EUR", "1050 EUR", "1250 EUR"};
    Integer counter;
    MyAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<Excursie> excursions = new ArrayList<Excursie>();
        counter = 1;
        adapter = new MyAdapter(this, excursions);
        listView = (ListView) findViewById(R.id.excursionsList);
        listView.setAdapter(adapter);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(myToolbar);

        adapter.add(new Excursie(titles[0], descriptions[0], images[0], prices[0]));
        adapter.add(new Excursie(titles[1], descriptions[1], images[1], prices[1]));
        adapter.add(new Excursie(titles[2], descriptions[2], images[2], prices[2]));
        adapter.notifyDataSetChanged();
        this.registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Excursie trip = adapter.getItem(position);
                Intent intent	=	new	Intent(ListViewActivity.this,	DetailActivity.class);
                intent.putExtra("trip",	trip);
                intent.putExtra("favourite",	trip.getFavourite());
                intent.putExtra("counter", counter);
                intent.putExtra("id", position);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return true;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
// verificăm dacă meniul este creat pentru lista vizată
        if (v.getId()==R.id.excursionsList)
        {
// identificăm elementul selectat din listă
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            View targetView = info.targetView;
            TextView exTtile = (TextView) targetView.findViewById(R.id.exTitle);
            menu.setHeaderTitle(exTtile.getText());
            getMenuInflater().inflate(R.menu.content_menu, menu);
        }
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getItemId() == R.id.addOfferId)
        {
            Excursie trip = (Excursie)adapter.getItem(info.position);
            adapter.add(new Excursie("Add a new offer", "Description for the added item", trip.getImage(), "0 EUR"));
            adapter.notifyDataSetChanged();
            return	true;
        }
        else if(item.getItemId() == R.id.removeOfferId)
        {
            Object id_view = adapter.getItem(info.position);
            adapter.remove((Excursie)id_view);
            adapter.notifyDataSetChanged();
            return	true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public	boolean	onOptionsItemSelected(MenuItem	item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int	id	=	item.getItemId();
        if (id == R.id.clear){
            clearItemList();
            counter = 1;
            return true;
        }
        if (id == R.id.favourite){
            adapter.clearFavourite();
            adapter.notifyDataSetChanged();
            Toast.makeText(this,	"Remove favourite elements",	Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.chat){
           SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String username = pref.getString("username", "");

            Intent intent	=	new	Intent(ListViewActivity.this,	ChatActivity.class);
            intent.putExtra("username",	username);
            startActivityForResult(intent, 1);
        }
        return	super.onOptionsItemSelected(item);
    }

    public void logoutConfirmation(View view){
        final AlertDialog.Builder	myDialog =	new	AlertDialog.Builder(this);
        myDialog.setTitle("Confirmation")
                .setMessage("Please confirm logout intention")
                .setPositiveButton("Confirrm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.remove("username");
                        edit.commit();
                        Intent intent = new Intent(ListViewActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder	myDialog =	new	AlertDialog.Builder(this);
        myDialog.setTitle("Confirmation")
                .setMessage("Please confirm logout intention")
                .setPositiveButton("Confirrm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ListViewActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public void clearItemList(){
        adapter.clear();
        adapter.add(new Excursie(titles[0], descriptions[0], images[0], prices[0]));
        adapter.add(new Excursie(titles[1], descriptions[1], images[1], prices[1]));
        adapter.add(new Excursie(titles[2], descriptions[2], images[2], prices[2]));
        Toast.makeText(this,	"List has been reset",	Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                Boolean status_favorite = data.getBooleanExtra("favourite", false);
                Integer id = data.getIntExtra("id", 0);
                adapter.setFavorite(id, status_favorite);
                counter++;
            }
        }
    }
}