package com.e.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {


    private Context context;
    private ArrayList<File> videoArrayList;

    public VideoAdapter(Context context, ArrayList<File> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_layout, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        File file = videoArrayList.get(position);
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(file.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.thumbnail_iv.setImageBitmap(bitmap);
        holder.tittle_tv.setText(file.getName());

    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail_iv;
        TextView tittle_tv;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail_iv = itemView.findViewById(R.id.thumbnail_iv);
            tittle_tv = itemView.findViewById(R.id.tittle_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoPlayerActivity.class).putExtra("position",getAdapterPosition());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
