package com.example.finalproject.student_ui.snotice;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.ViewStudentAdapter;
import com.example.finalproject.admin_ui.Notice;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder>
{
    ArrayList<Notice> al;
    public NoticeAdapter()
    {}
    public NoticeAdapter(ArrayList<Notice> al)
    {
        this.al = al;
    }
    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item_layout_view_notice,parent,false);
        return new NoticeAdapter.NoticeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoticeHolder holder, int position)
    {
        final Notice notice = al.get(position);
        holder.titlenotice.setText(notice.getTitle());
        holder.datenotice.setText(notice.getDate());
        holder.descnotice.setText(notice.getDesc());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.Theme_AppCompat_Light_Dialog_Alert);

                builder.setTitle(notice.getTitle());
                builder.setMessage(notice.getDesc());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class NoticeHolder extends RecyclerView.ViewHolder
    {
        TextView titlenotice, descnotice, datenotice;
        CardView cardView;
        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            titlenotice = itemView.findViewById(R.id.titlenotice);
            descnotice = itemView.findViewById(R.id.descnotice);
            datenotice = itemView.findViewById(R.id.datenotice);
            cardView = itemView.findViewById(R.id.noticecv);

        }
    }
}
