package com.example.projectapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.R;

import java.util.List;

public class AllStoreAdapter extends RecyclerView.Adapter<AllStoreAdapter.AllStoreHolder> {

    private Context context;
    private List<String> list;

    public AllStoreAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllStoreAdapter.AllStoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggest_store, parent, false);

        return new AllStoreAdapter.AllStoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStoreAdapter.AllStoreHolder holder, int position) {
        holder.textView.setText(list.get(position));
        holder.item_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class AllStoreHolder extends RecyclerView.ViewHolder {
        private TextView textView ;
        private ConstraintLayout item_suggest;
        public AllStoreHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView44);
            item_suggest = itemView.findViewById(R.id.item_suggest);
        }
    }
}
