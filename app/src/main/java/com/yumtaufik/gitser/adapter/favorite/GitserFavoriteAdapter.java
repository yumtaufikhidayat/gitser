package com.yumtaufik.gitser.adapter.favorite;

import android.app.Activity;
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
import com.yumtaufik.gitser.helper.CustomOnClickListener;
import com.yumtaufik.gitser.model.favorite.GitserFavorite;

import java.util.ArrayList;

public class GitserFavoriteAdapter extends RecyclerView.Adapter<GitserFavoriteAdapter.MyViewHolder> {

    ArrayList<GitserFavorite> gitserFavoriteList = new ArrayList<>();
    Activity activity;

    public GitserFavoriteAdapter(ArrayList<GitserFavorite> gitserFavoriteList) {
        this.gitserFavoriteList = gitserFavoriteList;
    }

    public GitserFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<GitserFavorite> getGitserFavoriteList() {
        return gitserFavoriteList;
    }

    public void setGitserFavoriteList(ArrayList<GitserFavorite> gitserFavoriteList) {

        if (gitserFavoriteList.size() > 0) {
            this.gitserFavoriteList.clear();
        }

        this.gitserFavoriteList.addAll(gitserFavoriteList);
        notifyDataSetChanged();
    }

    public void addItem(GitserFavorite gitserFavorite) {
        this.gitserFavoriteList.add(gitserFavorite);
        notifyItemInserted(gitserFavoriteList.size() - 1);
    }

    public void updateItem(int position, GitserFavorite gitserFavorite) {
        this.gitserFavoriteList.set(position, gitserFavorite);
        notifyItemChanged(position, gitserFavorite);
    }

    public void removeItem(int position) {
        this.gitserFavoriteList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, gitserFavoriteList.size());
    }

    @NonNull
    @Override
    public GitserFavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GitserFavoriteAdapter.MyViewHolder holder, int position) {

        GitserFavorite favorite = gitserFavoriteList.get(position);

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(favorite.getFavoriteAvatarUrl())
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.imgFavUser);

        holder.tvFavName.setText(favorite.getFavoriteName());
        holder.tvFavUsername.setText(favorite.getFavoriteUsername());
        holder.tvFavFollowing.setText(favorite.getFavoriteFollowing());
        holder.tvFavFollowers.setText(favorite.getFavoriteFollowers());
        holder.tvFavRepository.setText(favorite.getFavoriteRepository());
        holder.tvFavLocation.setText(favorite.getFavoriteLocation());
        holder.tvFavCompany.setText(favorite.getFavoriteCompany());
        holder.tvFavLink.setText(favorite.getFavoriteLink());

        holder.cardFavorite.setOnClickListener(new CustomOnClickListener(position, new CustomOnClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

            }
        }));
    }

    @Override
    public int getItemCount() {
        return gitserFavoriteList.size();
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
