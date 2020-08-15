package com.yumtaufik.gitser.adapter.followingfollowers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.model.followingfollowers.FollowingFollowersResponse;

import java.util.ArrayList;
import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.MyViewHolder> {

    private List<FollowingFollowersResponse> followingFollowersResponseList = new ArrayList<>();

    public void setDataFollowing(List<FollowingFollowersResponse> followingFollowersResponseList) {
        this.followingFollowersResponseList.clear();
        this.followingFollowersResponseList.addAll(followingFollowersResponseList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final FollowingFollowersResponse followingFollowersResponse = followingFollowersResponseList.get(position);

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(followingFollowersResponse.getAvatar_url())
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.imgUser);

        holder.tvUsername.setText(followingFollowersResponse.getLogin());
    }

    @Override
    public int getItemCount() {
        return followingFollowersResponseList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgUser;
        private final TextView tvUsername;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUser = itemView.findViewById(R.id.imgUserSearch);
            tvUsername = itemView.findViewById(R.id.tvUsernameSearch);
        }
    }
}
