package es.thatapps.scatterbrain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Referencias a los elementos
        TextView tvWinnerName = findViewById(R.id.tvWinnerName);
        Button btnMainMenu = findViewById(R.id.btnMainMenu);
        Button btnNewGame = findViewById(R.id.btnNewGame);

        // Establecer el nombre del ganador
        tvWinnerName.setText("Berto Antimoros");

        // Configurar el botón Menú Principal
        btnMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });

       
    }
}
