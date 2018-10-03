package com.example.crplayer.takenotesnew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by CR Sohan on 6/6/2018.
 */

public class NewViewHolder extends RecyclerView.ViewHolder{

    View mView;
    TextView textTitle, textTime, textContent;
    CardView noteCard;

    public NewViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        textTitle = (TextView) mView.findViewById(R.id.note_title);
        textTime = (TextView) mView.findViewById(R.id.note_time);
        textContent = (TextView) mView.findViewById(R.id.note_content);
        noteCard = (CardView)mView.findViewById(R.id.note_card);
    }

    public void setNoteContent(String content){
        textContent.setText(content);
    }

    public void setNoteTitle(String title){
        textTitle.setText(title);
    }

    public void setNoteTime(String time){
        textTime.setText(time);
    }
}

