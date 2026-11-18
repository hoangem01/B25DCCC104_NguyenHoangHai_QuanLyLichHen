package com.example.datlich.pages;
import com.example.datlich.sqlite.DatabaseHelper;
import com.example.datlich.models.Appointment;
import com.example.datlich.adapters.AppointmentAdapter;
import com.example.datlich.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    TextView tvTotal, tvReminders;
    ListView listView;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        tvTotal = findViewById(R.id.tvTotal);
        tvReminders = findViewById(R.id.tvReminders);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddAppointmentActivity.class)));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Appointment appt = (Appointment) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, AppointmentDetailActivity.class);
            intent.putExtra("id", appt.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        tvTotal.setText(String.valueOf(dbHelper.countAll()));
        tvReminders.setText(String.valueOf(dbHelper.countReminders()));

        List<Appointment> list = dbHelper.getAllAppointments();
        AppointmentAdapter adapter = new AppointmentAdapter(this, list);
        listView.setAdapter(adapter);
    }
}