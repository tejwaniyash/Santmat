package com.example.itskh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class videolistAdapter extends RecyclerView.Adapter<videolistAdapter.videolistAdapterViewHolder> {
    private static final String TAG = "videolistAdapter";
    private List<Videolist> Videos;
    private Context context;

    public videolistAdapter(List<Videolist> videos, Context context) {
        Videos = videos;
        this.context = context;
    }


    @NonNull
    @Override
    public videolistAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videolist, parent, false);
        return new videolistAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull videolistAdapterViewHolder holder, int position) {
        final Videolist videolist = Videos.get(position);

        holder.VideoName.setText(videolist.getVideoTitle());
        Picasso.get()
                .load(videolist.getVideoImage())
                .into(holder.videothumb);

        holder.videolayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,player.class);
                intent.putExtra("Video_Id",videolist.getVideoID());
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Videos.size();
    }

    public class videolistAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView videothumb;
        private TextView VideoName;
        private RelativeLayout videolayout;

        public videolistAdapterViewHolder(View itemView) {
            super(itemView);
            videothumb = (ImageView) itemView.findViewById(R.id.VideoThumb);
            VideoName = (TextView) itemView.findViewById(R.id.VideoName);
            videolayout = (RelativeLayout) itemView.findViewById(R.id.VideoListRelativeLayout);
        }
    }

}
