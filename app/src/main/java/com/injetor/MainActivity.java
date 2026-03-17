package com.injetor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInjetar = findViewById(R.id.btnInjetar);
        btnInjetar.setOnClickListener(v -> pedirPermissao());
    }

    private void pedirPermissao() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, 42);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42 && resultCode == RESULT_OK) {
            injetar(data.getData());
        }
    }

    private void injetar(Uri treeUri) {
        try {
            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);
            DocumentFile file = pickedDir.createFile("text/plain", "sabor_xit_v2.cfg");
            OutputStream out = getContentResolver().openOutputStream(file.getUri());

            RadioButton rbAlta = findViewById(R.id.rbAlta);
            RadioButton rbMedia = findViewById(R.id.rbMedia);
            
            String config;
            if (rbAlta.isChecked()) {
                // MODO 100% CAPA: IGNORA DISTÂNCIA E FORÇA A MIRA NA CABEÇA
                config = "Aim_Force: 1.0\nLock_On: Head\nSmooth: 0.02\nFOV: 150\nRange: Infinite\nSnap: True";
            } else if (rbMedia.isChecked()) {
                config = "Aim_Force: 0.75\nLock_On: Head\nSmooth: 0.35";
            } else {
                config = "Aim_Force: 0.50\nLock_On: Chest\nSmooth: 0.60";
            }

            out.write(config.getBytes());
            out.close();

            Toast.makeText(this, "MODO 100% CAPA ATIVADO!", Toast.LENGTH_LONG).show();
            startActivity(getPackageManager().getLaunchIntentForPackage("com.dts.freefireth"));
        } catch (Exception e) {
            Toast.makeText(this, "Erro na Injeção!", Toast.LENGTH_SHORT).show();
        }
    }
}
