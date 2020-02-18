package com.example.abozyigit.homework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {
    RadioButton r1;
    RadioButton r2;
    EditText username;
    EditText password;
    Button giris;
    TextView isim;
    TextView sifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        r1 = findViewById(R.id.radioButton);
        r2 = findViewById(R.id.radioButton2);
        username = findViewById(R.id.editText3);
        password = findViewById(R.id.editText2);
        giris = findViewById(R.id.button2);
        isim = findViewById(R.id.textView);
        sifre = findViewById(R.id.textView3);
    }

    public void buttonOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        if (r1.isChecked()) {
            if ((username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))) {
                intent.putExtra("kullanıcı", true);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Yanlış şifre ya da kullanıcı adı girdiniz!", Toast.LENGTH_SHORT).show();
            }
        }
        if (r2.isChecked()) {
            intent.putExtra("kullanıcı", false);
            giris.setVisibility(View.VISIBLE);
            startActivity(intent);
        }
    }

    public void onRadioButtonClicked(View view) {
        if(r1.isChecked()){
            username.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            isim.setVisibility(View.VISIBLE);
            sifre.setVisibility(View.VISIBLE);
            giris.setVisibility(View.VISIBLE);
        }
    }

    public void onRadioButton2Clicked(View view) {
        if(r2.isChecked()){
            username.setVisibility(View.INVISIBLE);
            password.setVisibility(View.INVISIBLE);
            isim.setVisibility(View.INVISIBLE);
            sifre.setVisibility(View.INVISIBLE);
        }
    }
}