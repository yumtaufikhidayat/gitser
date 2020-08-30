package com.yumtaufik.gitser.adapter.favorite;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.activity.DetailFavoriteActivity;
import com.yumtaufik.gitser.helper.CustomOnClickListener;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import java.util.ArrayList;

public class GitserFavoriteAdapter extends RecyclerView.Adapter<GitserFavoriteAdapter.MyViewHolder> {

    ArrayList<DetailProfileResponse> profileResponsesList = new ArrayList<>();
    Activity activity;

    public GitserFavoriteAdapter(ArrayList<DetailProfileResponse> profileResponsesList) {
        this.profileResponsesList = profileResponsesList;
    }

    public GitserFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<DetailProfileResponse> getGitserFavoriteList() {
        return profileResponsesList;
    }

    public void setGitserFavoriteList(ArrayList<DetailProfileResponse> profileResponsesList) {

        if (profileResponsesList.size() > 0) {
            this.profileResponsesList.clear();
        }

        this.profileResponsesList.addAll(profileResponsesList);
        notifyDataSetChanged();
    }

    public void addItem(DetailProfileResponse profileResponse) {
        this.profileResponsesList.add(profileResponse);
        notifyItemInserted(profileResponsesList.size() - 1);
    }

    public void updateItem(int position, DetailProfileResponse profileResponse) {
        this.profileResponsesList.set(position, profileResponse);
        notifyItemChanged(position, profileResponse);
    }

    public void removeItem(int position) {
        this.profileResponsesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, profileResponsesList.size());
    }

    @NonNull
    @Override
    public GitserFavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GitserFavoriteAdapter.MyViewHolder holder, int position) {

        DetailProfileResponse favorite = profileResponsesList.get(position);

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(favorite.getAvatarUrl())
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.imgFavUser);

        holder.tvFavName.setText(favorite.getName());
        holder.tvFavUsername.setText(favorite.getLogin());
        holder.tvFavFollowing.setText(favorite.getFollowing());
        holder.tvFavFollowers.setText(favorite.getFollowers());
        holder.tvFavRepository.setText(favorite.getPublicRepos());
        holder.tvFavLocation.setText(favorite.getLocation());
        holder.tvFavCompany.setText(favorite.getCompany());
        holder.tvFavLink.setText(favorite.getBlog());

        holder.cardFavorite.setOnClickListener(new CustomOnClickListener(position, new CustomOnClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                Intent intent = new Intent(activity, DetailFavoriteActivity.class);
                intent.putExtra(DetailFavoriteActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailFavoriteActivity.EXTRA_FAVORITE, profileResponsesList.get(position));
                activity.startActivityForResult(intent, DetailFavoriteActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return profileResponsesList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardFavorite;
        ImageView imgFavUser;
        TextView tvFavName,
                tvFavUsername,
                tvFavFollowing,
                tvFavFollowers,
                tvFavRepository,
                tvFavLocation,
                tvFavCompany,
                tvFavLink;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardFavorite = itemView.findViewById(R.id.cardFavorite);
            imgFavUser = itemView.findViewById(R.id.imgFavUser);
            tvFavName = itemView.findViewById(R.id.tvFavName);
            tvFavUsername = itemView.findViewById(R.id.tvFavUsername);
            tvFavFollowing = itemView.findViewById(R.id.tvFavFollowing);
            tvFavFollowers = itemView.findViewById(R.id.tvFavFollowers);
            tvFavRepository = itemView.findViewById(R.id.tvFavRepository);
            tvFavLocation = itemView.findViewById(R.id.tvFavLocation);
            tvFavCompany = itemView.findViewById(R.id.tvFavCompany);
            tvFavLink = itemView.findViewById(R.id.tvFavLink);

        }
    }
}
