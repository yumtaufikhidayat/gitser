package com.yumtaufik.gitser.adapter.search;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.activity.DetailProfileActivity;
import com.yumtaufik.gitser.model.search.SearchItems;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<SearchItems> resultItems = new ArrayList<>();

    public void setDataItems(List<SearchItems> resultItems) {
        this.resultItems.clear();
        this.resultItems.addAll(resultItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final SearchItems searchItems = resultItems.get(position);

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(searchItems.getAvatarUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgUserSearch);

        holder.tvUsernameSearch.setText(searchItems.getLogin());
        holder.cardSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailProfileActivity.class);
                intent.putExtra(DetailProfileActivity.EXTRA_DETAIL_PROFILE, searchItems);
                holder.itemView.getContext().startActivity(intent);
                Toast.makeText(holder.itemView.getContext(), "Kamu memilih " + holder.tvUsernameSearch.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardSearchUser;
        private final ImageView imgUserSearch;
        private final TextView tvUsernameSearch;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardSearchUser = itemView.findViewById(R.id.cardSearchUser);
            imgUserSearch = itemView.findViewById(R.id.imgUserSearch);
            tvUsernameSearch = itemView.findViewById(R.id.tvUsernameSearch);
        }
    }
}
