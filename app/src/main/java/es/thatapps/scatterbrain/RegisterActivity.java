package es.thatapps.scatterbrain;

import static android.content.ContentValues.TAG;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Elementos de a UI
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
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

        // Acción del boton
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validar los inputs
            if (validateInputs(email, password)) {
                loginWithEmailPassword(email, password);
            }
        });
    }

    // Método de validación de los inputs
    private boolean validateInputs(String email, String password) {
        // Validar email
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "El email es necesario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "Ingresa un email válido por favor", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar contraseña
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "La contraseña es necesaria", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Método para validar el formato del email
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void loginWithEmailPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Registro exitoso! Bienvenido, " + Objects.requireNonNull(user).getEmail(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Error en el inicio de sesión
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Error al iniciar sesión. " + Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
