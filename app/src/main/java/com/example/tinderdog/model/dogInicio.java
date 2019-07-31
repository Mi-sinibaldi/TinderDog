package com.example.tinderdog.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tinderdog.R;

public class dogInicio extends AppCompatActivity {

    private Button btnEntrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_inicio);

        btnEntrar = findViewById(R.id.btnDog);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicar();
            }
        });

    }

    public void clicar() {
        Intent intent = new Intent(dogInicio.this, MainActivity.class);
        startActivity(intent);
    }
}
