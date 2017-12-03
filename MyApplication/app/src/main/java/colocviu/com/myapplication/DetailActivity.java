package colocviu.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by AndreeaT on 12/1/2017.
 */

public class DetailActivity extends AppCompatActivity {

    private Boolean favourite;
    private Integer counter;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent myIntent	= getIntent();
        final Excursie trip =  (Excursie) getIntent().getSerializableExtra("trip");
        favourite = myIntent.getBooleanExtra("favourite", false);
        counter = myIntent.getIntExtra("counter", 1);
        id = myIntent.getIntExtra("id", 1);

        TextView titleDetail = (TextView)findViewById(R.id.exTitle1);
        ImageView imageDetail = (ImageView)findViewById(R.id.exImage1);
        TextView priceDetail = (TextView)findViewById(R.id.exPrice1);
        TextView descriptionDetail = (TextView)findViewById(R.id.textView3);
        TextView counterDetail = (TextView)findViewById(R.id.textView4);
        Button btn = (Button)findViewById(R.id.favouriteBtn);

        if (favourite)
            btn.setText(R.string.remove_favour);
        else
            btn.setText(R.string.add_favour);

        titleDetail.setText(trip.getTitle());
        int id = getResources().getIdentifier(trip.getImage(), "drawable", getPackageName());
        System.out.println(R.drawable.offer_1);
        imageDetail.setImageResource(id);
        priceDetail.setText(trip.getPrice());
        descriptionDetail.setText(trip.getDescription());
        counterDetail.setText("Details page displayed: "+ String.valueOf(counter)+ " times");
    }

    public void stateFavorite(View view){
        Button btn = (Button)findViewById(R.id.favouriteBtn);
        if (favourite){
            favourite = false;
            btn.setText(R.string.add_favour);
            Toast.makeText(this,	"Removed from favorites",	Toast.LENGTH_SHORT).show();
        }
        else{
            favourite = true;
            btn.setText(R.string.remove_favour);
            Toast.makeText(this,	"Added to favorites",	Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("favourite", favourite);
        returnIntent.putExtra("id", id);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}


