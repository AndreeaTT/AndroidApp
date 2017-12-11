package colocviu.com.myapplication;

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

import java.util.List;

/**
 * Created by AndreeaT on 11/26/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private final List<Message> items;

    public MessageAdapter(Context context, List<Message> items) {
        super(context, 0, items);
        this.items = items;
    }

    public List<Message> getItems() {
        return items;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //get element
        Message msg = (Message) getItem(i);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, viewGroup, false);
        }

        // Lookup view for data population
        TextView userField = (TextView) view.findViewById(R.id.textView6);
        TextView messageField = (TextView) view.findViewById(R.id.textView7);
        TextView dateField = (TextView) view.findViewById(R.id.textView8);

        // Populate the data into the template view using the data object
        userField.setText(msg.getUsername());
        userField.setTypeface(null, Typeface.BOLD);
        messageField.setText(msg.getMsg());
        dateField.setText(msg.getDate());
        dateField.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        return view;
    }
}
