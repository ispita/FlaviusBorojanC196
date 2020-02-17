package com.example.flaviusborojanc196;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {
    private List<Assessment> assessments = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_item, parent, false);
        return new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        Assessment currentAssessment = assessments.get(position);
        holder.textViewTitle.setText(currentAssessment.getTitle());
        holder.textViewDescription.setText(currentAssessment.getDescription());
        holder.textViewId.setText(String.valueOf(currentAssessment.getId()));
        holder.datePickerEnd.setText(String.valueOf(currentAssessment.getEnd()));

    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    class AssessmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewId;
        private TextView datePickerEnd;


        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_assessment_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewId = itemView.findViewById(R.id.text_view_ID);
            datePickerEnd = itemView.findViewById(R.id.text_assessment_end_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(assessments.get(position));
                    }
                }
            });
        }

    }

    public Assessment getAssessmentAt(int position){
        return assessments.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Assessment assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
