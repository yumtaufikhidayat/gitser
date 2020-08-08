package com.yumtaufik.gitser.adapter.main;

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
import com.yumtaufik.gitser.model.main.MainResponse;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private List<MainResponse> mainResponseList = new ArrayList<>();

    public void setDataMain(List<MainResponse> mainResponseList) {
        this.mainResponseList.clear();
        this.mainResponseList.addAll(mainResponseList);
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

        final MainResponse mainResponse = mainResponseList.get(position);

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(mainResponse.getAvatarUrl())
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.imgUser);

        holder.tvUsername.setText(mainResponse.getLogin());
        holder.cardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Kamu memilih " + holder.tvUsername.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return mainResponseList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        
        private final CardView cardMain;
        private final ImageView imgUser;
        private final TextView tvUsername;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardMain = itemView.findViewById(R.id.cardSearchUser);
            imgUser = itemView.findViewById(R.id.imgUserSearch);
            tvUsername = itemView.findViewById(R.id.tvUsernameSearch);
        }
    }
}
