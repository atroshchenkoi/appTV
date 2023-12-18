package com.example.apptv.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.apptv.R;
import com.example.apptv.models.User;
import com.example.apptv.repositories.PeopleRepository;


import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText nameInput;
    private EditText phoneInput;
    private EditText passwordInput;
    private PeopleRepository peopleRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        peopleRepository = new PeopleRepository();
        btnRegister = (Button) findViewById(R.id.btnRegister);
        nameInput = (EditText) findViewById(R.id.register_name_input);
        phoneInput = (EditText) findViewById(R.id.register_phone_input);
        passwordInput = (EditText) findViewById(R.id.register_password_input);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String username = nameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Введите телефон", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            }
        } else {
            validatePhone(username, phone, password);
        }
    }

    private void validatePhone(String username, String phone, String password) {
        User check = peopleRepository.selectUserByPhone(phone);
        if (check == null) {
            User userInsert = new User(username, password, phone);
            peopleRepository.insertUser(userInsert);
        }
        else {
            Toast.makeText(RegisterActivity.this, "Номер " + phone + " уже зарегестрирован.", Toast.LENGTH_SHORT).show();
        }
        Intent homeIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(homeIntent);
    }
}
