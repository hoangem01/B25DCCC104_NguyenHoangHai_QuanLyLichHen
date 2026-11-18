package com.example.datlich.pages;
import com.example.datlich.sqlite.DatabaseHelper;
import com.example.datlich.models.Appointment;
import com.example.datlich.adapters.AppointmentAdapter;
import com.example.datlich.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetailActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    TextView tvDetailTitle, tvDetailLocation, tvDetailTime;
    Switch switchDetailReminder;
    Button btnBack, btnDelete;
    int appointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        dbHelper = new DatabaseHelper(this);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailLocation = findViewById(R.id.tvDetailLocation);
        tvDetailTime = findViewById(R.id.tvDetailTime);
        switchDetailReminder = findViewById(R.id.switchDetailReminder);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        appointmentId = getIntent().getIntExtra("id", -1);
        if (appointmentId != -1) {
            loadDetail();
        }

        switchDetailReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dbHelper.updateReminder(appointmentId, isChecked);
            switchDetailReminder.setText(isChecked ? "Đang bật" : "Đang tắt");
        });

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteAppointment(appointmentId);
            finish();
        });
    }

    private void loadDetail() {
        Appointment appt = dbHelper.getAppointmentById(appointmentId);
        if (appt != null) {
            tvDetailTitle.setText(appt.getTitle());
            tvDetailLocation.setText(appt.getLocation());
            
            String[] dateParts = appt.getDate().split("-");
            String displayDate = dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0];
            tvDetailTime.setText(displayDate + " · " + appt.getTime());

            boolean isReminderOn = appt.getReminder() == 1;
            switchDetailReminder.setChecked(isReminderOn);
            switchDetailReminder.setText(isReminderOn ? "Đang bật" : "Đang tắt");
        }
    }
}