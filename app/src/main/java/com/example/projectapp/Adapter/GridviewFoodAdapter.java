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

import com.example.projectapp.ObjectClass.Product;
import com.example.projectapp.R;
import com.example.projectapp.ui.Home.FoodActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridviewFoodAdapter extends RecyclerView.Adapter<GridviewFoodAdapter.FoodHolder> {

    private Context context;
    List<Product> products;

    public GridviewFoodAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public GridviewFoodAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_gridview, parent, false);
        return new GridviewFoodAdapter.FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridviewFoodAdapter.FoodHolder holder, int position) {
        final Product product = products.get(position);
        if(product == null){
            return;
        }
        holder.name.setText(product.getProductName());
        holder.price.setText((int)product.getPrice()+"");
        Picasso.get().load(product.getAvatarUrl()).into(holder.img);
        holder.LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj_food", product);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(products != null){
            return products.size();
        }
        return 0;
    }

    public class FoodHolder extends RecyclerView.ViewHolder {
        private TextView name,price;
        private ImageView img ;
        private CardView LinearLayout;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_food);
            img = itemView.findViewById(R.id.imv_food);
            price = itemView.findViewById(R.id.tv_price);
            LinearLayout = itemView.findViewById(R.id.cardView);
        }
    }

//    Context context;
//    int layout;
//    List<Product> arraylist;
//
//    public GridviewFoodAdapter(Context context, int layout, List<Product> arraylist) {
//        this.context = context;
//        this.layout = layout;
//        this.arraylist = arraylist;
//    }
//
//    @Override
//    public int getCount() {
//        return arraylist.size();
//    }
//
//    private class ViewHolder {
//        TextView name,price;
//        ImageView img ;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder;
//        if(view == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(layout, null);
//            viewHolder =new ViewHolder();
//            viewHolder.img = (ImageView) view.findViewById(R.id.imv_food);
//            viewHolder.name = (TextView) view.findViewById(R.id.tv_name_food);
//            viewHolder.price = (TextView) view.findViewById(R.id.tv_price);
//            view.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//        viewHolder.name.setText(arraylist.get(i).getProductName());
//        viewHolder.price.setText((int)arraylist.get(i).getPrice()+"");
//        Picasso.get().load(arraylist.get(i).getAvatarUrl()).into(viewHolder.img);
//        return view;
//    }
}
