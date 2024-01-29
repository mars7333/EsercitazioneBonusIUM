package com.example.librettouniversitario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    private List<Exam> examList;
    private OnExamDeleteClickListener deleteClickListener;

    public ExamAdapter(List<Exam> examList, OnExamDeleteClickListener listener) {
        this.examList = examList;
        this.deleteClickListener = listener;
    }

    @Override
    public ExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_exam, parent, false);
        return new ExamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExamViewHolder holder, int position) {
        Exam exam = examList.get(position);
        holder.subject.setText(exam.getName());
        holder.cfu.setText(String.valueOf(exam.getCfu()));
        holder.grade.setText(String.valueOf(exam.getGrade()));
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }


    public class ExamViewHolder extends RecyclerView.ViewHolder {
        public TextView subject, cfu, grade;
        public ImageView deleteIcon;


        public ExamViewHolder(View view) {
            super(view);
            subject = view.findViewById(R.id.name);
            cfu = view.findViewById(R.id.cfu);
            grade = view.findViewById(R.id.grade);
            deleteIcon = view.findViewById(R.id.delete);

            deleteIcon.setOnClickListener(v -> {
                if (deleteClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Mostra un AlertDialog per chiedere conferma
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Conferma cancellazione")
                                .setMessage("Sei sicuro di voler cancellare questo esame?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Procedi con la cancellazione dell'esame
                                        deleteClickListener.onDeleteClick(position);

                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
            });
        }
    }
}
