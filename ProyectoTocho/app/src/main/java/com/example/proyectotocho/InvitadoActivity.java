package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

public class InvitadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
    }
}