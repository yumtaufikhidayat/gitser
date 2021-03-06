package com.yumtaufik.gitser.adapter.favorite;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.activity.DetailFavoriteActivity;
import com.yumtaufik.gitser.database.GitserHelper;
import com.yumtaufik.gitser.helper.Utils;
import com.yumtaufik.gitser.model.detail.DetailProfileResponse;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class GitserFavoriteAdapter extends RecyclerView.Adapter<GitserFavoriteAdapter.MyViewHolder> {

    ArrayList<DetailProfileResponse> profileResponsesList;
    Activity activity;

    GitserHelper gitserHelper;

    public void setGitserFavoriteList(ArrayList<DetailProfileResponse> profileResponsesList) {

        if (profileResponsesList.size() > 0) {
            this.profileResponsesList.clear();
        }

        this.profileResponsesList.addAll(profileResponsesList);
        notifyDataSetChanged();
    }

    public GitserFavoriteAdapter(ArrayList<DetailProfileResponse> profileResponse, Activity activity) {
        this.profileResponsesList = profileResponse;
        this.activity = activity;
    }

    public void addItem(DetailProfileResponse favorite) {
        this.profileResponsesList.add(favorite);
        notifyItemInserted(profileResponsesList.size() - 1);
    }

    public void updateItem(int position, DetailProfileResponse favorite) {
        this.profileResponsesList.set(position, favorite);
        notifyItemChanged(position, favorite);
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
    public void onBindViewHolder(@NonNull final GitserFavoriteAdapter.MyViewHolder holder, final int position) {

        DetailProfileResponse favorite = profileResponsesList.get(position);

        gitserHelper = new GitserHelper(activity);
        byte[] data = gitserHelper.getBitmapAvatarUrl(favorite);

        if (data != null) {

            Bitmap bitmap = Utils.getImage(data);
            holder.imgFavUser.setImageBitmap(bitmap);

        } else {
            Toasty.error(activity, R.string.tvFailedToShowAvatar, Toast.LENGTH_SHORT, true).show();
        }

        holder.tvFavName.setText(favorite.getName());
        holder.tvFavUsername.setText(favorite.getLogin());
        holder.tvFavFollowing.setText(String.valueOf(favorite.getFollowing()));
        holder.tvFavFollowers.setText(String.valueOf(favorite.getFollowers()));
        holder.tvFavRepository.setText(String.valueOf(favorite.getPublicRepos()));
        holder.tvFavLocation.setText(favorite.getLocation());
        holder.tvFavCompany.setText(favorite.getCompany());
        holder.tvFavLink.setText(favorite.getBio());

        final String link = holder.tvFavLink.getText().toString().trim();

        SpannableString spannableLink = new SpannableString(link);
        ClickableSpan spanLink = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intentLink = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

                PackageManager packageManager = holder.itemView.getContext().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intentLink, PackageManager.MATCH_DEFAULT_ONLY);

                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    holder.itemView.getContext().startActivity(Intent.createChooser(intentLink, "Open with"));
                } else {
                    Toasty.warning(holder.itemView.getContext(), R.string.tvInstallBrowserApp, Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };

        spannableLink.setSpan(spanLink, 0, link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvFavLink.setText(spannableLink);
        holder.tvFavLink.setMovementMethod(LinkMovementMethod.getInstance());

        holder.cardFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DetailFavoriteActivity.class);
                intent.putExtra(DetailFavoriteActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailFavoriteActivity.EXTRA_FAVORITE, profileResponsesList.get(position));
                activity.startActivityForResult(intent, DetailFavoriteActivity.REQUEST_UPDATE);

            }
        });
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
