package com.example.projectapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.ItemDrawer;
import com.example.projectapp.R;
import com.example.projectapp.ui.DrawerMenu.EditUserActivity;
import com.example.projectapp.ui.DrawerMenu.OrderActivity;
import com.example.projectapp.ui.DrawerMenu.RePassActivity;

import java.util.List;

public class ListItemMenuAdapter extends RecyclerView.Adapter<ListItemMenuAdapter.ListItemHolder> {
    private Context context;
    private List<ItemDrawer> list;
    private OnCloseActivity mListener;

    public ListItemMenuAdapter(Context context, List<ItemDrawer> list, OnCloseActivity mListener) {
        this.context = context;
        this.list = list;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ListItemMenuAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
        return new ListItemMenuAdapter.ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemMenuAdapter.ListItemHolder holder, int position) {
        final ItemDrawer item = list.get(position);
        if(item == null){
            return;
        }
        holder.title.setText(item.getTitle());
        holder.image.setImageResource(item.getImage());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getId() == 4){
                    showAlertDialog();
                } else if (item.getId() == 2) {
                    Intent intent = new Intent(context, RePassActivity.class);
                    context.startActivity(intent);
                } else if (item.getId() == 1) {
                    Intent intent = new Intent(context, OrderActivity.class);
                    context.startActivity(intent);
                } else if (item.getId() == 0) {
                    Intent intent = new Intent(context, EditUserActivity.class);
                    context.startActivity(intent);
                }
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

    public interface OnCloseActivity {
        void onCloseActivity();
    }


    public class ListItemHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;
        private ConstraintLayout constraintLayout;

        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView13);
            title = itemView.findViewById(R.id.textView51);
            constraintLayout =  itemView.findViewById(R.id.item_menu);
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo"); // Tiêu đề của AlertDialog
        builder.setMessage("Bạn có muốn đăng xuất khỏi app không?"); // Nội dung của AlertDialog
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(mListener != null){
                    mListener.onCloseActivity();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
