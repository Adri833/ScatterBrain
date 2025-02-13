package es.thatapps.scatterbrain;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    private boolean isVisibvle = false; // Controla la visibilidad de los elementos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener elementos de la UI
        Button bttnCreateRoom = findViewById(R.id.bttnCreateRoom);
        Button bttnJoinRoom = findViewById(R.id.bttnJoinRoom);
        Button bttnPlay = findViewById(R.id.bttnPlay);
        EditText edtName = findViewById(R.id.edtName);
        EditText edtCode = findViewById(R.id.edtCode);

        // Obtener los colores para el boton
        int enabledColor = ContextCompat.getColor(this, R.color.dark_pink45);
        int disabledColor = ContextCompat.getColor(this, R.color.dark_pink);

        // Inicialmente deshabilitar el botón Create Room
        bttnCreateRoom.setEnabled(false);
        bttnCreateRoom.setBackgroundTintList(ColorStateList.valueOf(disabledColor));

        // Listener para el botón Join Room (muestra/oculta elementos)
        bttnJoinRoom.setOnClickListener(v -> {
            if (isVisibvle) {
                edtCode.setVisibility(View.GONE);
                bttnPlay.setVisibility(View.GONE);
            } else {
                edtCode.setVisibility(View.VISIBLE);
                bttnPlay.setVisibility(View.VISIBLE);
            }
            isVisibvle = !isVisibvle;
        });

        // Listener para el botón Create Room (disable/enable)
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Habilita el boton solo si hay texto
                boolean hasText = charSequence.length() > 0;
                bttnCreateRoom.setEnabled(hasText);
                bttnCreateRoom.setBackgroundTintList(ColorStateList.valueOf(hasText ? enabledColor : disabledColor));
            }

            @Override public void afterTextChanged(Editable editable) {}
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        });

        // Boton Create Room
        bttnCreateRoom.setOnClickListener(v -> navigateToSettings());
    }

    // Navegacion hacia la pantalla ajustes
    public void navigateToSettings() {
        // Obtiene el nombre de usuario desde el EditText
        EditText edtName = findViewById(R.id.edtName);
        String userName = edtName.getText().toString();

        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
    }
}
