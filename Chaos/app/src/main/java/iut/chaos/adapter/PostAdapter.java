package iut.chaos.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import iut.chaos.R;
import iut.chaos.activity.Cell;
import iut.chaos.activity.SignIn;
import iut.chaos.model.PostEvent;
import iut.chaos.model.PostMedia;
import iut.chaos.model.PostText;

public class PostAdapter extends ArrayAdapter {

    public PostAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        Object o = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View toReturn = inflater.inflate(R.layout.cell, parent, false);

        if (position % 2 == 0) {
            toReturn.setBackgroundColor(Color.LTGRAY);
        } else {
            toReturn.setBackgroundColor(Color.DKGRAY);
        }

        toReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Open post details
            }
        });

        if (o instanceof PostText) {
            PostText p = (PostText) o;
            ((TextView) toReturn.findViewById(R.id.textPost)).setText(p.getText());
            ((TextView) toReturn.findViewById(R.id.datePost)).setText(p.getTimestamp().toString());
            //((TextView) toReturn.findViewById(R.id.cellNote)).setText(String.valueOf(p.getScore()));
        }

        if (o instanceof PostMedia) {
            PostMedia p = (PostMedia) o;
            ((TextView) toReturn.findViewById(R.id.textPost)).setText(p.getMedia().getType().toString());
            ((TextView) toReturn.findViewById(R.id.datePost)).setText(p.getTimestamp().toString());
            //((TextView) toReturn.findViewById(R.id.cellNote)).setText(String.valueOf(p.getScore()));
        }

        if (o instanceof PostEvent) {
            PostEvent p = (PostEvent) o;
            ((TextView) toReturn.findViewById(R.id.textPost)).setText(p.getEventLocation().toString());
            ((TextView) toReturn.findViewById(R.id.datePost)).setText(p.getTimestamp().toString());
            //((TextView) toReturn.findViewById(R.id.cellNote)).setText(String.valueOf(p.getScore()));
        }

        return toReturn;
    }
}
