package com.example.apptv.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptv.Prevalent.Prevalent;
import com.example.apptv.models.User;
import com.example.apptv.repositories.PeopleRepository;
import com.example.apptv.R;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView btnRegister;
    private PeopleRepository peopleRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        peopleRepository = new PeopleRepository();
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (TextView) findViewById(R.id.textRegister);

        Paper.init(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        String userPhone = Paper.book().read(Prevalent.userPhoneKey);
        String userPassword = Paper.book().read(Prevalent.userPasswordKey);

        Log.v("Проверка", "Начало проверки");
        if(userPhone == null) Log.v("userPhone", "null");
        if(userPassword == null) Log.v("userPassword", "null");
        if(userPhone != null && userPassword != null) {
            if (!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPassword)){
                Log.v("Проверка", "Конец проверки");
                validateUser(userPhone, userPassword);
            }
        }
    }

    private void validateUser(String phone, String password) {
        User user = peopleRepository.selectUserByPhone(phone);
        if (user != null) {
            Log.v("User", user.toString());
            if (user.getPassword().equals(password)) {
                Toast.makeText(MainActivity.this, "Вход выполнен успешно!", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(loginIntent);
            } else {
                Toast.makeText(MainActivity.this, "Не получилось осуществить вход", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Не получилось осуществить вход", Toast.LENGTH_SHORT).show();
        }
    }
}