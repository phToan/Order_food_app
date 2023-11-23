package com.example.projectapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.ObjectClass.Evaluation;
import com.example.projectapp.R;

import java.util.List;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationHolder> {

    private List<Evaluation> list ;
    private Context context;

    public EvaluationAdapter(List<Evaluation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EvaluationAdapter.EvaluationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_evaluation,parent,false);
        return new EvaluationAdapter.EvaluationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationAdapter.EvaluationHolder holder, int position) {
        Evaluation evaluation = list.get(position);
        holder.tv_name.setText(evaluation.getName());
        holder.tv_review.setText(evaluation.getEvaluation());
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class EvaluationHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_review;
        public EvaluationHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_evaluation_name);
            tv_review = itemView.findViewById(R.id.tv_review);
        }
    }
}
