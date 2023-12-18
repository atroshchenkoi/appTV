package com.example.apptv.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.apptv.Prevalent.Prevalent;
import com.example.apptv.R;

import com.example.apptv.models.User;
import com.example.apptv.repositories.PeopleRepository;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText loginPhoneInput;
    private EditText loginPasswordInput;
    private CheckBox rememberMe;

    private PeopleRepository peopleRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        peopleRepository = new PeopleRepository();
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loginPhoneInput = (EditText) findViewById(R.id.login_phone_input);
        loginPasswordInput = (EditText) findViewById(R.id.login_password_input);
        rememberMe = (CheckBox) findViewById(R.id.login_check_box);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loginUser();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void loginUser() throws InterruptedException {
        String phone = loginPhoneInput.getText().toString();
        String password = loginPasswordInput.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Введите телефон", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            }
        } else {
            validateUser(phone, password);
        }
    }

    private void validateUser(String phone, String password) {
        if (rememberMe.isChecked()){
            Paper.book().write(Prevalent.userPhoneKey, phone);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }
        User user = peopleRepository.selectUserByPhone(phone);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                Toast.makeText(LoginActivity.this, "Вход выполнен успешно!", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(loginIntent);
            } else {
                Toast.makeText(LoginActivity.this, "Данные введены неверно", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(LoginActivity.this, "Аккаунта с этим номером не существует", Toast.LENGTH_SHORT).show();
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        }

    }
}
