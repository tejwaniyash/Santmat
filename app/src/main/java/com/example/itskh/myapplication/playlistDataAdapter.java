package com.example.itskh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class playlistDataAdapter extends RecyclerView.Adapter<playlistDataAdapter.playlistDataViewHolder> {

    private List<playlistData> YtPlaylist;
    private Context context;

    playlistDataAdapter(List<playlistData> ytPlaylist, Context context) {
        YtPlaylist = ytPlaylist;
        this.context = context;
    }

    @NonNull
    @Override
    public playlistDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item_data, parent, false);
        return new playlistDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull playlistDataViewHolder holder, int position) {
        final playlistData playlistdata = YtPlaylist.get(position);

        holder.PlaylistName.setText(playlistdata.getName());
        holder.PlaylistDesc.setText(playlistdata.getDesc());
        Picasso.get()
                .load(playlistdata.getImage())
                .into(holder.PlaylistThumb);


        holder.playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if(activeNetwork!=null) {
                    Intent intent = new Intent(context, VideorecyclerActivity.class);
                    intent.putExtra("Plylist-id", playlistdata.getPlaylistID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context,"Please Check your Internet Access",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return YtPlaylist.size();
    }

    class playlistDataViewHolder extends RecyclerView.ViewHolder {
        private ImageView PlaylistThumb;
        private TextView PlaylistName, PlaylistDesc;
        RelativeLayout playlist;

        playlistDataViewHolder(View itemView) {
            super(itemView);
            PlaylistThumb =  itemView.findViewById(R.id.playlist_item_thumb);
            PlaylistName =  itemView.findViewById(R.id.playlist_name);
            PlaylistDesc =  itemView.findViewById(R.id.playlist_Desc);
            playlist = itemView.findViewById(R.id.play);
        }
    }
}
