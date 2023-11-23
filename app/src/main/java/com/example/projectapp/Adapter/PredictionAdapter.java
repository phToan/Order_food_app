package com.example.projectapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.R;
import com.example.projectapp.ObjectClass.Prediction;
import com.example.projectapp.ui.Address.UpdateEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PredictionAdapter extends RecyclerView.Adapter<PredictionAdapter.PredictionViewHolder> {
    private List<Prediction> predictions;
    private OnCloseActivity mListener;
    private Context context;

    private SharedPreferences sharedPreferences;

    public PredictionAdapter(List<Prediction> predictions,OnCloseActivity mListener, Context context) {
        this.predictions = predictions;
        this.context = context;
        this.mListener = mListener;
        sharedPreferences = context.getSharedPreferences("MyPrefs", context.MODE_PRIVATE);
    }

    public interface OnCloseActivity {
        void onCloseActivity();
    }


    @NonNull
    @Override
    public PredictionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prediction, parent, false);
        return new PredictionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionViewHolder holder, int position) {
        Prediction prediction = predictions.get(position);
        holder.predictionTextView.setText(prediction.getDescription());
        holder.item_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String myString = prediction.getDescription();
                editor.putString("address", myString);
                editor.apply();
                if(mListener != null){
                    EventBus.getDefault().post(new UpdateEvent());
                    mListener.onCloseActivity();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }



    public class PredictionViewHolder extends RecyclerView.ViewHolder  {
        private TextView predictionTextView;
        private ConstraintLayout item_address;

        public PredictionViewHolder(View itemView) {
            super(itemView);
            predictionTextView = itemView.findViewById(R.id.textView85);
            item_address = itemView.findViewById(R.id.item_address);
        }

    }
}

