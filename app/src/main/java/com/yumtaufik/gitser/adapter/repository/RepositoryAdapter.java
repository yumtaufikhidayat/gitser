package com.yumtaufik.gitser.adapter.repository;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yumtaufik.gitser.R;
import com.yumtaufik.gitser.model.repository.RepositoryResponse;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.MyViewHolder> {

    private List<RepositoryResponse> repositoryList = new ArrayList<>();

    public void setDataRepository(List<RepositoryResponse> repositoryList) {
        this.repositoryList.clear();
        this.repositoryList.addAll(repositoryList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final RepositoryResponse repository = repositoryList.get(position);

        holder.tvRepositoryName.setText(repository.getName());
        holder.tvRepositoryDesc.setText(repository.getDescription());
        holder.tvRepositoryLanguage.setText(repository.getLanguage());

        Integer reposSize = repository.getSize();
        holder.tvRepositorySize.setText(String.valueOf(reposSize));

        String sizeStr = holder.tvRepositorySize.getText().toString().trim();
        int sizeInt = Integer.parseInt(sizeStr);
        double sizeDouble = (float) sizeInt / 1000;
        int sizeIntNew = (int) Math.round(sizeDouble);

        if (sizeIntNew < 1) {
            double sizeDoubleKb = Math.round(sizeDouble * 1000);
            int sizeIntKb = (int) Math.round(sizeDoubleKb);
            String sizeStrKb = String.valueOf(sizeIntKb);
            holder.tvRepositorySize.setText(String.format("%s KB", sizeStrKb));
        } else {
            String sizeStrMb = String.valueOf(sizeIntNew);
            holder.tvRepositorySize.setText(String.format("%s MB", sizeStrMb));
        }
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView tvRepositoryName,
                tvRepositoryDesc,
                tvRepositorySize,
                tvRepositoryLanguage;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRepositoryName = itemView.findViewById(R.id.tvRepositoryName);
            tvRepositoryDesc = itemView.findViewById(R.id.tvRepositoryDesc);
            tvRepositorySize = itemView.findViewById(R.id.tvRepositorySize);
            tvRepositoryLanguage = itemView.findViewById(R.id.tvRepositoryLanguage);
        }
    }
}
