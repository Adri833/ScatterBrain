package es.thatapps.scatterbrain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.thatapps.scatterbrain.cliente.Jugador;
import es.thatapps.scatterbrain.servidor.Server;

public class SettingsActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Elementos de la UI
        Button buttonJugar = findViewById(R.id.buttonJugar);

        buttonJugar.setOnClickListener(v -> {
            // Iniciar el servidor en un hilo
            new Thread(() -> {
                Server server = new Server();
                server.abrirServidor();
            }).start();

            // Conectar el cliente a su propio servidor
            Jugador jugador = new Jugador(handler);
            jugador.conectar();

            runOnUiThread(() -> navigateToGame());
        });

        // Configurar los spinners
        setupSpinnerIdiomas();
        setupFlag();
        setupSpinnerDificultad();
        setupDificultad();
    }

    // Configura la bandera según la opción seleccionada
    private void setupFlag() {
        ImageView imageFlag = findViewById(R.id.imageFlag);
        Spinner spinnerIdiomas = findViewById(R.id.spinnerIdiomas);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.idiomas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdiomas.setAdapter(adapter);

        // Listener del spinner
        spinnerIdiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idiomaSeleccionado = parent.getItemAtPosition(position).toString();
                switch (idiomaSeleccionado) {
                    case "Español":
                        imageFlag.setImageResource(R.drawable.flag_es);
                        break;
                    case "Inglés":
                        imageFlag.setImageResource(R.drawable.flag_en);
                        break;
                    case "Italiano":
                        imageFlag.setImageResource(R.drawable.flag_it);
                        break;
                }
            }

            // Método obligatorio
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    // Configura el icono de dificultad
    private void setupDificultad() {
        ImageView imageDificultad = findViewById(R.id.imageDificultad);
        Spinner spinnerDificultad = findViewById(R.id.spinnerDificultad);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dificultades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificultad.setAdapter(adapter);

        // Listener del spinner
        spinnerDificultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dificultadSeleccionada = parent.getItemAtPosition(position).toString();
                switch (dificultadSeleccionada) {
                    case "Fácil":
                        imageDificultad.setImageResource(R.drawable.ic_easy);
                        break;
                    case "Normal":
                        imageDificultad.setImageResource(R.drawable.ic_normal);
                        break;
                    case "Difícil":
                        imageDificultad.setImageResource(R.drawable.ic_hard);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    // Configura las opciones del spinnerIdiomas con el adapter
    private void setupSpinnerIdiomas() {
        Spinner spinnerIdiomas = findViewById(R.id.spinnerIdiomas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.idiomas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdiomas.setAdapter(adapter);
    }

    // Configura las opciones del spinnerDificultad con el adapter
    private void setupSpinnerDificultad() {
        Spinner spinnerDificultades = findViewById(R.id.spinnerDificultad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dificultades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificultades.setAdapter(adapter);
    }

    private void navigateToGame() {
        String codigoSala = Server.generarCodigoSala();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("CODIGO_SALA", codigoSala);
        startActivity(intent);
    }
}