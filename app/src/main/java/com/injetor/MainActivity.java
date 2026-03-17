package com.injetor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Cria o botão via código para não depender de erro no XML
        Button btn = new Button(this);
        btn.setText("INJETAR 90% AIM ASSIST");
        btn.setBackgroundColor(0xFFEBBD2D);
        btn.setTextColor(0xFF000000);
        setContentView(btn);

        btn.setOnClickListener(v -> {
            Toast.makeText(this, "Injetando 90%...", Toast.LENGTH_SHORT).show();
            // Abre o Free Fire
            Intent i = getPackageManager().getLaunchIntentForPackage("com.dts.freefireth");
            if (i != null) startActivity(i);
        });
    }
    }
