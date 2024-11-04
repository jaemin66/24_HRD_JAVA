package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.BabyViewHolder> {

    private List<Baby> babyList;
    private OnItemClickListener listener;

    public BabyAdapter(List<Baby> babyList) {
        this.babyList = babyList;
    }

    public interface OnItemClickListener {
        void onItemClick(Baby baby);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BabyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baby_profile, parent, false);
        return new BabyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BabyViewHolder holder, int position) {
        Baby baby = babyList.get(position);
        holder.babyNum.setText(baby.getBabyNum());
        holder.babyName.setText(baby.getBabyName());
        holder.babyBirth.setText(baby.getBabyBirth() + "일 생");
        holder.babyBlood.setText(baby.getBabyBlood());
        holder.babyGender.setText(baby.getBabyGender());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(baby);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (babyList != null) ? babyList.size() : 0;
    }

    public static class BabyViewHolder extends RecyclerView.ViewHolder {
        TextView babyNum, babyName, babyBirth, babyBlood, babyGender;

        public BabyViewHolder(@NonNull View itemView) {
            super(itemView);
            babyNum = itemView.findViewById(R.id.babyNum);
            babyName = itemView.findViewById(R.id.babyName);
            babyBirth = itemView.findViewById(R.id.babyBirth);
            babyBlood = itemView.findViewById(R.id.babyBlood);
            babyGender = itemView.findViewById(R.id.babyGender);
        }
    }
}

