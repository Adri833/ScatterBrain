package es.thatapps.scatterbrain;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Referencias a los campos de texto y los íconos de borrar
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        ImageView iconDeleteEmail = findViewById(R.id.iconDeleteEmail);
        ImageView iconDeletePassword = findViewById(R.id.iconDeletePassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Configurar acción para borrar el campo de correo
        iconDeleteEmail.setOnClickListener(v -> etEmail.setText(""));

        // Configurar acción para borrar el campo de contraseña
        iconDeletePassword.setOnClickListener(v -> etPassword.setText(""));

        // Configurar botón de inicio de sesión
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validar entradas antes de iniciar sesión
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Iniciar sesión con Firebase Authentication
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            // TODO navegacion hacia la pantalla home
                        } else {
                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión. " + Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}