package com.example.student.adapter;

/**
 * Created by Student on 05.12.2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.model.Message;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by AndreeaT on 11/26/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private List<Message> items;
    private int type;

    public static Comparator<Message> sortedMessage = new Comparator<Message>() {

        public int compare(Message app1, Message app2) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Message msg1 = app1;
            Message msg2 = app2;
            try {
                Date date1 = df.parse(msg1.getDate());
                Date date2 = df.parse(msg2.getDate());

                return date1.compareTo(date2) == 0 ? 0 : -date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0;
        }
    };

    public MessageAdapter(Context context, List<Message> items) {
        super(context, 0, items);
        this.items = items;
        this.type = 1;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return this.type;
    }

    public List<Message> getItems() {
        return items;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public void add(Message message){
        this.items.add(0, message);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;

        if (type == 1)
            viewType = 1;
        if (type == 2)
            viewType = 2;
        if (type == 3)
            viewType = (position %2 == 0)? 1 : 2;

        return viewType;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //get element
        Message msg = (Message)getItem(i);
        int type = getItemViewType(i);

        // Lookup view for data population
        TextView userField;
        TextView messageField;
        TextView dateField;
        TextView fromField;
        ImageView favouriteImg;

        switch(type) {
                case 1:
                    view = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, viewGroup, false);
                    userField = (TextView) view.findViewById(R.id.textView6);
                    messageField = (TextView) view.findViewById(R.id.textView7);
                    dateField = (TextView) view.findViewById(R.id.textView8);
                    favouriteImg = (ImageView)view.findViewById(R.id.imageView2);

                    // Populate the data into the template view using the data object
                    userField.setText(msg.getUsername());
                    userField.setTypeface(null, Typeface.BOLD);
                    messageField.setText(msg.getMsg());
                    dateField.setText(msg.getDate());
                    dateField.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    if (msg.getFavourite())
                        favouriteImg.setVisibility(View.VISIBLE);
                    else
                        favouriteImg.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    view = LayoutInflater.from(getContext()).inflate(R.layout.chat_item2, viewGroup, false);
                    userField = (TextView) view.findViewById(R.id.textView9);
                    messageField = (TextView) view.findViewById(R.id.textView10);
                    dateField = (TextView) view.findViewById(R.id.textView12);
                    fromField = (TextView) view.findViewById(R.id.textView11);
                    favouriteImg = (ImageView)view.findViewById(R.id.imageView3);

                    // Populate the data into the template view using the data object
                    userField.setText(msg.getUsername());
                    userField.setTypeface(null, Typeface.BOLD);
                    messageField.setText(msg.getMsg());
                    dateField.setText("on" + msg.getDate());
                    fromField.setText("Text");
                    if (msg.getFavourite())
                        favouriteImg.setVisibility(View.VISIBLE);
                    else
                        favouriteImg.setVisibility(View.INVISIBLE);
                    break;
            }
            return view;
    }
}
