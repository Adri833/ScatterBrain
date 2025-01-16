package es.thatapps.scatterbrain;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
}