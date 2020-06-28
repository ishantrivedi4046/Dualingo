package com.example.dualingo;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class word_adapter extends ArrayAdapter<word> {

    private int colorres;
    public word_adapter(Activity context, List<word> wordList,int r) {
        super(context, 0, wordList);
        colorres=r;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.number_list, parent, false);
        }

        word current = getItem(position);

        LinearLayout ll=(LinearLayout)listView.findViewById(R.id.text_layout);
        ll.setBackgroundColor(ContextCompat.getColor(getContext(),colorres));

        TextView t1 = (TextView) listView.findViewById(R.id.text1);
        t1.setText(current.getMiwoktranslation());

        TextView t2 = (TextView) listView.findViewById(R.id.text2);
        t2.setText(current.getDefaultTranslation());

        ImageView i = (ImageView) listView.findViewById(R.id.images);
        if (current.hasImage())
        {
            i.setImageResource(current.getImageresource());
        }
        else
        {
            i.setVisibility(View.GONE);
        }

        return listView;
    }
}
