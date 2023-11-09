package com.ahsan.soundplayer;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder>{

    Context context;


    private ArrayList<SoundItem> soundItems;

    public SoundAdapter(ArrayList<SoundItem> soundItems,Context context){
        this.soundItems = soundItems;

        this.context = context;
    }
    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_item,parent,false);

        SoundViewHolder svh = new SoundViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position) {

        SoundItem currentItem = soundItems.get(position);

        holder.imgView1.setImageResource(currentItem.getImageSource());
        holder.txtView1.setText(currentItem.getText1());
        holder.txtView2.setText(currentItem.getText2());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerBox.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return soundItems.size();
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout cardView;

        ImageView imgView1;
        TextView txtView1;
        TextView txtView2;
        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView1 = itemView.findViewById(R.id.imgView);
            txtView1  = itemView.findViewById(R.id.soundName);
            txtView2  = itemView.findViewById(R.id.soundMaker);
            cardView = itemView.findViewById(R.id.singleCardView);
        }
    }
}