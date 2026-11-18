package com.example.datlich.pages;
import com.example.datlich.sqlite.DatabaseHelper;
import com.example.datlich.models.Appointment;
import com.example.datlich.adapters.AppointmentAdapter;
import com.example.datlich.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class AddAppointmentActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText edtTitle, edtLocation;
    DatePicker datePicker;
    TimePicker timePicker;
    Switch switchReminder;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        dbHelper = new DatabaseHelper(this);
        edtTitle = findViewById(R.id.edtTitle);
        edtLocation = findViewById(R.id.edtLocation);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        switchReminder = findViewById(R.id.switchReminder);
        btnSave = findViewById(R.id.btnSave);
        
        timePicker.setIs24HourView(true);

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String location = edtLocation.getText().toString().trim();

            if (title.isEmpty()) {
                edtTitle.setError("Không được để trống");
                return;
            }
            if (location.isEmpty()) {
                edtLocation.setError("Không được để trống");
                return;
            }

            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            Calendar selectedTime = Calendar.getInstance();
            selectedTime.set(year, month, day, hour, minute, 0);

            if (selectedTime.getTimeInMillis() <= System.currentTimeMillis()) {
                Toast.makeText(this, "Thời gian hẹn phải lớn hơn hiện tại", Toast.LENGTH_SHORT).show();
                return;
            }

            String dateStr = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
            String timeStr = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            int reminder = switchReminder.isChecked() ? 1 : 0;

            dbHelper.insertAppointment(title, location, dateStr, timeStr, reminder);
            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}