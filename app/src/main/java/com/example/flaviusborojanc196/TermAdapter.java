package com.example.flaviusborojanc196;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {
    private List<Term> terms = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);
        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        Term currentTerm = terms.get(position);
        holder.textViewTitle.setText(currentTerm.getTitle());
        holder.textViewDescription.setText(currentTerm.getDescription());
        holder.textViewId.setText(String.valueOf(currentTerm.getId()));

    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    class TermHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewId;


        public TermHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewId = itemView.findViewById(R.id.text_view_ID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(terms.get(position));
                    }
                }
            });
        }

    }

    public Term getTermAt(int position){
        return terms.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Term term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
