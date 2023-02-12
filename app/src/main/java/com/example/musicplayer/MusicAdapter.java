package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder>{

    ArrayList<String> list;
    Context mContext;

    public MusicAdapter(ArrayList<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing the object to the musicviewholder constructor
        //parent object represent the activity that contains the recyclerView that is the main activity
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_music,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        /*passing the path of the audio file to the variable of the string type and also going transfer
        all the file path to the file path variable  */
        String filePath=list.get(position);
        Log.e("filepath",filePath);
        String title=filePath.substring(filePath.lastIndexOf("/")+1);
        // writing the audio file name in textView component
        holder.textViewFileName.setText(title);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext,MusicAdapter.class);
                intent.putExtra( "title",title);
                intent.putExtra("filepath",filePath);
                intent.putExtra("position",position);
                intent.putExtra("list",list);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFileName;
        private CardView cardView;
        public MusicViewHolder (@NonNull View itemView) {
            super (itemView);

            textViewFileName = itemView.findViewById(R.id. textViewFileName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


}
