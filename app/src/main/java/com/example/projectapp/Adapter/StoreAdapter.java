package com.example.projectapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.Stores;
import com.example.projectapp.R;
import com.example.projectapp.ui.Home.StoreItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder>{

    private List<Stores> listStore;
    private Context context;

    public StoreAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Stores> liststore){
        this.listStore = liststore;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.propose_recycleview, parent, false);

        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        final Stores store = listStore.get(position);
        if(store == null) {
            return;
        }
        Picasso.get().load(store.getAvatarUrl()).into(holder.img);
        holder.name.setText(store.getStoreName());
        holder.endow.setText(store.getRate()+"");
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToItem(store);
            }
        });
    }

    private void onClickGoToItem(Stores store) {
        Intent intent = new Intent(context, StoreItem.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_store", store);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(listStore != null){
            return listStore.size();
        }
        return 0;
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView endow;
        private TextView name;
        private CardView layoutItem ;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem =  itemView.findViewById(R.id.layout_item_store);
            img = itemView.findViewById(R.id.image_propose_recycleview);
            endow = itemView.findViewById(R.id.tv_endow);
            name = itemView.findViewById(R.id.propose_nameStore);
        }
    }
}
