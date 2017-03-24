package com.project.nghicv.videochathead.screen.listdevicevideo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.project.nghicv.videochathead.databinding.ItemVideoBinding;
import com.project.nghicv.videochathead.model.Video;
import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Video> mVideos;
    private ListDeviceVideoContract.Presenter mPresenter;

    public VideosAdapter(List<Video> videos, ListDeviceVideoContract.Presenter presenter) {
        mVideos = videos;
        mPresenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVideoBinding itemVideoBinding = ItemVideoBinding.inflate(LayoutInflater.from(parent
                .getContext()), parent, false);
        return new VideoViewHolder(itemVideoBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VideoViewHolder) holder).bind(mVideos.get(position));
    }

    @Override
    public int getItemCount() {
        return mVideos == null ? 0 : mVideos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ItemVideoBinding mItemVideoBinding;

        public VideoViewHolder(ItemVideoBinding itemVideoBinding) {
            super(itemVideoBinding.getRoot());
            mItemVideoBinding = itemVideoBinding;
        }

        public void bind(Video video) {
            mItemVideoBinding.setVideo(video);
            mItemVideoBinding.setActionHandler(new VideoItemActionHandler(mPresenter));
            mItemVideoBinding.executePendingBindings();
        }
    }
}
