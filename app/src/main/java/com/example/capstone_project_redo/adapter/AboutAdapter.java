package com.example.capstone_project_redo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.model.AboutModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AboutAdapter extends FirebaseRecyclerAdapter<AboutModel, AboutAdapter.aboutViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AboutAdapter(@NonNull FirebaseRecyclerOptions<AboutModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AboutAdapter.aboutViewHolder holder, final int position, @NonNull AboutModel model) {
        holder.question.setText(model.getQuestion());
        holder.answer.setText(model.getAnswer());

        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.answer.getVisibility() == View.GONE) {
                    holder.answer.setVisibility(View.VISIBLE);
                }
                else {
                    holder.answer.setVisibility(View.GONE);
                }
            }
        });
    }

    @NonNull
    @Override
    public aboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_about, parent, false);
        return new aboutViewHolder(view);
    }


    public class aboutViewHolder extends RecyclerView.ViewHolder {

        TextView question, answer;

        public aboutViewHolder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.tv_question);
            answer = itemView.findViewById(R.id.tv_answer);
            answer.setVisibility(View.GONE);
        }
    }
}