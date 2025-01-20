package es.thatapps.scatterbrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    //Inicia Firebase
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar firebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Login con google
        findViewById(R.id.btnGoogle).setOnClickListener(v -> signInWithGoogle());

        // Botones
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        // Acciones para los botones
        btnLogin.setOnClickListener(view -> {
            // Acción de Iniciar Sesión
            Toast.makeText(MainActivity.this, "Iniciar Sesión", Toast.LENGTH_SHORT).show();
        });

        btnRegister.setOnClickListener(view -> {
            // Acción de Registrarse
            Toast.makeText(MainActivity.this, "Registrarse", Toast.LENGTH_SHORT).show();
        });
    }

    private void signInWithGoogle() {
        // Construir el objeto de configuración para GoogleSignIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1037173707313-31o34n49p52opu58uqd67s88ekrdaarp.apps.googleusercontent.com")
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Iniciar el intent de inicio de sesión
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // Obtener el token de autenticación y realizar el sign-in con Firebase
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, task1 -> {
                            if (task1.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (ApiException e) {
                Log.w("Google SignIn", "SignInResult:failed code=" + e.getStatusCode());
            }
        }
    }
}
