package com.example.finalproject.student_ui.sattendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.finalproject.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GraphAttendanceAdapter extends RecyclerView.Adapter<GraphAttendanceAdapter.AttendanceViewHolder> {
    private ArrayList<GraphData> gData;
    private ArrayList<GraphData> gData1 = new ArrayList<GraphData>();
    public GraphAttendanceAdapter()
    {}
    public GraphAttendanceAdapter(ArrayList<GraphData> gData)
    {
        gData1.add(new GraphData("CLOUD COMPUTING",3,1));
        gData1.add(new GraphData("ML",4,1));
        gData1.add(new GraphData("OS",5,1));
        this.gData = gData1;
    }
    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.graph_attendance_layout,parent,false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        GraphData g = gData.get(position);

        Pie pie = AnyChart.pie();
        List<DataEntry> al = new ArrayList<>();
        String attend[] = {"Present", "Absent"};

        al.add(new ValueDataEntry(attend[0], g.getTotalPresent()));
        al.add(new ValueDataEntry(attend[1], g.getTotalAbsent()));
        pie.data(al);
        holder.chart.setChart(pie);
        holder.text.setText(g.getSubjectName());
    }

    @Override
    public int getItemCount() {
        return gData.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder{
        AnyChartView chart;
        TextView text;
        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            chart = itemView.findViewById(R.id.Chart);
            text = itemView.findViewById(R.id.text);
        }
    }

}

