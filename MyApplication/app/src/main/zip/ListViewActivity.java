package com.example.student.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
    MyAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<Excursie> excursions = new ArrayList<Excursie>();
        adapter = new MyAdapter(this, excursions);
        listView = (ListView) findViewById(R.id.excursionsList);
        listView.setAdapter(adapter);

        adapter.add(new Excursie(titles[0], descriptions[0], images[0], prices[0]));
        adapter.add(new Excursie(titles[1], descriptions[1], images[1], prices[1]));
        adapter.add(new Excursie(titles[2], descriptions[2], images[2], prices[2]));
        adapter.notifyDataSetChanged();
        registerForContextMenu(listView);
    }

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
// încărcăm structura vizuală a meniului
            getMenuInflater().inflate(R.menu.content_menu, menu);
        }
    }

    public boolean onContextItemSelected(MenuItem item)
    {
// accesarea informației atașate meniului contextual
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
// identificarea elementului selectat din meniu, folosind ID-urile predefinite
        if(item.getItemId() == R.id.addOfferId)
        {
        }
        else if(item.getItemId() == R.id.removeOfferId)
        {
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public	boolean	onCreateOptionsMenu(Menu menu)
    {
//	Inflate the	menu;	this	adds	items	to	the	action	bar	if	it	is	present.
        getMenuInflater().inflate(R.menu.content_menu,	menu);
        return	true;
    }

    @Override
    public	boolean	onOptionsItemSelected(MenuItem	item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int	id	=	item.getItemId();
        if	(id	==	R.id.addOfferId)
        {
            Excursie trip = (Excursie)adapter.getItem(info.position);
            adapter.add(new Excursie("Add a new offer", "Description for the added item", trip.getImage(), "0 EUR"));
            adapter.notifyDataSetChanged();
            return	true;
        }
        if(id	==	R.id.removeOfferId)
        {
            Object id_view = adapter.getItem(info.position);
            adapter.remove(id_view);
            adapter.notifyDataSetChanged();
            return	true;
        }
        if (id == R.id.clear){
            clearItemList();
            return true;
        }
        if (id == R.id.favourite){
            List<Object> items = adapter.getItems();
            for (Object ex:items){
                Excursie trip = (Excursie)ex;
                trip.setFavourite(false);
                adapter.remove(ex);
                adapter.add(trip);

            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this,	"Remove favourite elements",	Toast.LENGTH_SHORT).show();
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

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public void onBackPressed()
    {
        final AlertDialog.Builder	myDialog =	new	AlertDialog.Builder(this);
        myDialog.setTitle("Confirmation")
                .setMessage("Please confirm logout intention")
                .setPositiveButton("Confirrm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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

    public void startDetailPage(){
        Intent intent	=	new	Intent(ListViewActivity.this, DetailActivity.class);
        //intent.putExtra();
        //startActivity(intent);

    }
}