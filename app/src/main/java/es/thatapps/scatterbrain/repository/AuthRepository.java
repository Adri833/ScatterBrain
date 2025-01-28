package es.thatapps.scatterbrain.repository;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepository {

    private final FirebaseAuth mAuth;
    private final Context context;

    public AuthRepository(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    // Crea un nuevo usuario y lo agrega a Firebase
    public void createNewUser(String email, String password, Runnable onSuccess) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        // Login exitoso
                        Toast.makeText(context, "Registro exitoso! Bienvenido", Toast.LENGTH_SHORT).show();
                        onSuccess.run();
                    } else {
                        // Error en el inicio de sesión
                        Log.e(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(context, "Error al registrarse. ", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
