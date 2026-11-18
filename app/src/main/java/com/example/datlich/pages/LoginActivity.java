package com.example.datlich.pages;
import com.example.datlich.sqlite.DatabaseHelper;
import com.example.datlich.models.Appointment;
import com.example.datlich.adapters.AppointmentAdapter;
import com.example.datlich.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvError = findViewById(R.id.tvError);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                tvError.setText("Vui lòng nhập đầy đủ thông tin");
                return;
            }

            if (dbHelper.checkLogin(username, password)) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                tvError.setText("Sai tên đăng nhập hoặc mật khẩu");
            }
        });
    }
}