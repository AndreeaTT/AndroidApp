package colocviu.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewActivity extends AppCompatActivity {
    private String[] titles = {"Barcelona, 3 nights", "Maldive, 7 nights", "Thailand, 10 nights"};
    private String[] descriptions = {"Barcelona is a nice city", "Maldive is a nice city", "Thailand is a nice city"};
    private String[] images = {"offer_1", "offer_2", "offer_3"};
    private String[] prices = {"300 EUR", "1050 EUR", "1250 EUR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewmain);

        ArrayList<Excursie> excursions = new ArrayList<Excursie>();
        MyAdapter adapter = new MyAdapter(this, excursions);
        ListView listView = (ListView) findViewById(R.id.excursionsList);
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
}
