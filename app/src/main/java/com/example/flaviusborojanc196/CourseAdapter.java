package com.example.flaviusborojanc196;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.textViewTitle.setText(currentCourse.getTitle());
        holder.textViewDescription.setText(currentCourse.getDescription());
        holder.textViewId.setText(String.valueOf(currentCourse.getId()));
        holder.datePickerStart.setText(String.valueOf(currentCourse.getStart()));
        holder.datePickerEnd.setText(String.valueOf(currentCourse.getEnd()));
        holder.textViewStatus.setText(String.valueOf(currentCourse.getStatus()));
        holder.textViewMentor.setText(String.valueOf(currentCourse.getMentor()));
        holder.textViewPhone.setText(String.valueOf(currentCourse.getPhone()));
        holder.textViewEmail.setText(String.valueOf(currentCourse.getEmail()));
        holder.textViewNote.setText(String.valueOf(currentCourse.getNote()));
        holder.courseCardView.setCardBackgroundColor(Color.WHITE);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewId;
        private TextView datePickerStart;
        private TextView datePickerEnd;
        private TextView textViewStatus;
        private TextView textViewMentor;
        private TextView textViewPhone;
        private TextView textViewEmail;
        private TextView textViewNote;
        private CardView courseCardView;


        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewId = itemView.findViewById(R.id.text_view_ID);
            datePickerStart = itemView.findViewById(R.id.text_course_start_date);
            datePickerEnd = itemView.findViewById(R.id.text_course_end_date);
            courseCardView = itemView.findViewById(R.id.course_card);
            textViewStatus = itemView.findViewById(R.id.text_course_status);
            textViewMentor = itemView.findViewById(R.id.text_course_mentor);
            textViewPhone = itemView.findViewById(R.id.text_course_mentor_phone);
            textViewEmail = itemView.findViewById(R.id.text_course_mentor_email);
            textViewNote = itemView.findViewById(R.id.text_course_notes);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(position));
                    }
                }
            });
        }

    }

    public Course getCourseAt(int position){
        return courses.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
