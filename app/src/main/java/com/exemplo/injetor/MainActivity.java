package com.injetor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
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
            Uri treeUri = data.getData();
            injetar(treeUri);
        }
    }

    private void injetar(Uri treeUri) {
        try {
            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);
            DocumentFile file = pickedDir.createFile("text/plain", "aim_config.cfg");
            OutputStream out = getContentResolver().openOutputStream(file.getUri());
            
            // CONFIGURAÇÃO 90% AIM ASSIST
            String config = "Aim_Lock_Force: 0.9\n" +
                           "Smooth_Control: 0.15\n" + 
                           "FOV_Radius: 95.0\n" +
                           "Headshot_Percent: 90\n" +
                           "Auto_Tracking: 1\n" +
                           "No_Recoil_Power: 0.8";
                           
            out.write(config.getBytes());
            out.close();

            Toast.makeText(this, "Aim Assist 90% ATIVADO! Abrindo FF...", Toast.LENGTH_LONG).show();
            
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.dts.freefireth");
            if (launchIntent != null) startActivity(launchIntent);
            
        } catch (Exception e) {
            Toast.makeText(this, "Erro na Injeção!", Toast.LENGTH_SHORT).show();
        }
    }
}
