package cl.inacap.registrocovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;


import cl.inacap.registrocovid.dto.Paciente;

public class PacienteViewActivity extends AppCompatActivity {


    TextView rutView, nombreView, fechaView, areaView, sintomasView, tosView,temperaturaView, presionView;
    Paciente paciente;
    Toolbar toolbar;

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_view);

        this.toolbar = findViewById(R.id.toolbar);

        this.rutView = findViewById(R.id.rut_paciente_view);
        this.nombreView = findViewById(R.id.nombre_paciente_view);
        this.fechaView = findViewById(R.id.fecha_paciente_view);
        this.areaView = findViewById(R.id.area_paciente_view);
        this.sintomasView = findViewById(R.id.sintoma_paciente_view);
        this.tosView = findViewById(R.id.tos_paciente_view);
        this.temperaturaView = findViewById(R.id.temperatura_paciente_view);
        this.presionView = findViewById(R.id.presion_paciente_view);

        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        if (getIntent().getExtras() != null){
            this.paciente = (Paciente) getIntent().getSerializableExtra("paciente");
            this.rutView.setText(this.paciente.getRut());
            this.nombreView.setText(this.paciente.getNombre()+" "+this.paciente.getApellido());
            this.fechaView.setText(this.paciente.getFecha());
            this.areaView.setText(this.paciente.getAreaTrabajo());
            if (paciente.getSintomas()){
                this.sintomasView.setText("si");
            }else {
                this.sintomasView.setText("no");
            }

            this.temperaturaView.setText(String.format("%.02f", paciente.getTemperatura()));

            if (paciente.getTos()){
                this.tosView.setText("si");
            }else {
                this.tosView.setText("no");
            }

           this.presionView.setText(" "+paciente.getPresionArterial());
        }

            }



}