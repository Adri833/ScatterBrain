package es.thatapps.scatterbrain;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.thatapps.scatterbrain.repository.AuthRepository;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Clases
        AuthRepository authRepository = new AuthRepository(this);

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
                authRepository.createNewUser(email, password, this::navigateToHome);
            }
        });
    }

    // Navegaciones
    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    // Método de validación de los inputs
    private boolean validateInputs(String email, String password) {
        // Validar email
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, R.string.camp_required, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, R.string.password_length, Toast.LENGTH_SHORT).show();
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
}
