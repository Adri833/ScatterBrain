package es.thatapps.scatterbrain;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Referencias a los campos de texto y los íconos de borrar
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        ImageView iconDeleteEmail = findViewById(R.id.iconDeleteEmail);
        ImageView iconDeletePassword = findViewById(R.id.iconDeletePassword);

        // Configurar acción para borrar el campo de correo
        iconDeleteEmail.setOnClickListener(v -> {
            etEmail.setText(""); // Borra el texto del campo de correo
        });

        // Configurar acción para borrar el campo de contraseña
        iconDeletePassword.setOnClickListener(v -> {
            etPassword.setText(""); // Borra el texto del campo de contraseña
        });
    }
}
