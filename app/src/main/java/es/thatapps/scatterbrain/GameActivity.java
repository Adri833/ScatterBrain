package es.thatapps.scatterbrain;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.thatapps.scatterbrain.cliente.Jugador;

public class GameActivity extends AppCompatActivity {

    private Handler handler;
    private TextView playersList;
    private Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            // Recuperar el codigo de la sala
            String codigoSala = getIntent().getStringExtra("CODIGO_SALA");

            TextView roomCode = findViewById(R.id.roomCode);
            if (codigoSala != null) {
                roomCode.setText("Codigo de la sala -> " + codigoSala);
            }

            playersList = findViewById(R.id.playersList);
    }
}
