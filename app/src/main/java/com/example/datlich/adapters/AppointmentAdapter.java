package com.example.datlich.adapters;

import com.example.datlich.R;
import com.example.datlich.models.Appointment;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        super(context, 0, appointments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_appointment, parent, false);
        }

        Appointment appt = getItem(position);
        TextView tvTitle = convertView.findViewById(R.id.tvItemTitle);
        TextView tvLocation = convertView.findViewById(R.id.tvItemLocation);
        TextView tvDateTime = convertView.findViewById(R.id.tvItemDateTime);
        TextView tvReminderLabel = convertView.findViewById(R.id.tvItemReminderLabel);

        tvTitle.setText(appt.getTitle());
        tvLocation.setText("Địa điểm: " + appt.getLocation());
        // Chuyển đổi định dạng ngày yyyy-MM-dd -> dd/MM/yyyy để hiển thị
        String[] dateParts = appt.getDate().split("-");
        String displayDate = dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0];
        tvDateTime.setText(displayDate + " · " + appt.getTime());

        if (appt.getReminder() == 1) {
            tvReminderLabel.setText("Có nhắc");
            tvReminderLabel.setTextColor(Color.parseColor("#FF9800"));
        } else {
            tvReminderLabel.setText("Không nhắc");
            tvReminderLabel.setTextColor(Color.parseColor("#757575"));
        }

        return convertView;
    }
}