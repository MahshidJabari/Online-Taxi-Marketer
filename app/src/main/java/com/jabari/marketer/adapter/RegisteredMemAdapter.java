package com.jabari.marketer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jabari.marketer.R;

import java.util.List;

public class RegisteredMemAdapter extends RecyclerView.Adapter<RegisteredMemAdapter.RegisteredMarketerViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private List<String> list;


    public RegisteredMemAdapter(Context context, RecyclerView recyclerView,List<String> list){
        this.context = context;
        this.recyclerView = recyclerView;
        this.list = list;

    }
    @NonNull
    @Override
    public RegisteredMarketerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_registered_marketer, parent, false);

        return new RegisteredMemAdapter.RegisteredMarketerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredMarketerViewHolder holder, int i) {

        holder.tv_name.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RegisteredMarketerViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        public RegisteredMarketerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }

    }
}
