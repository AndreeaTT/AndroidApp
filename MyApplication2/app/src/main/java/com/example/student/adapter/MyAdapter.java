package com.example.student.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.model.Excursie;

import java.util.List;

/**
 * Created by AndreeaT on 11/26/2017.
 */

public class MyAdapter extends BaseAdapter {

    private final List<Excursie> items;
    private Context context;

    public MyAdapter(Context context, List<Excursie> items) {
        this.context = context;
        this.items = items;
    }

    public List<Excursie> getItems() {
        return items;
    }

    public void clearFavourite(){
        for (Excursie ex:items) {
            ex.setFavourite(false);
        }
    }

    public void setFavorite(int id, Boolean status){
        Excursie ex = items.get(id);
        ex.setFavourite(status);
        items.set(id, ex);
    }

     public void add(Excursie excursie){
        this.items.add(excursie);
     }

    public void remove(Excursie excursie){
        this.items.remove(excursie);
    }

    public void clear(){
        this.items.clear();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Excursie getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //get element
        Excursie excursion = (Excursie) getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        }

        // Lookup view for data population
        TextView exDesciption = (TextView) view.findViewById(R.id.exDescription);
        TextView exPrice = (TextView) view.findViewById(R.id.exPrice);
        TextView exTtile = (TextView) view.findViewById(R.id.exTitle);
        ImageView exImg = (ImageView) view.findViewById(R.id.exImage);

        final View finalConvertView = view;
        exImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalConvertView.performLongClick();
            }
        });

        // Populate the data into the template view using the data object
        exDesciption.setText(excursion.getDescription());
        exTtile.setText(excursion.getTitle());
        exPrice.setText(excursion.getPrice());
        exImg.setImageResource(resolveImageView(excursion.getImage()));

        return view;
    }

    private int resolveImageView(String exImgName) {
        switch (exImgName) {
            case "offer_1":
                return R.drawable.offer_1;
            case "offer_2":
                return R.drawable.offer_2;
            case "offer_3":
                return R.drawable.offer_3;
            default:
                return 0;

        }
    }
}