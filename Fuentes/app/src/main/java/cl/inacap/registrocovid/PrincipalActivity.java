package cl.inacap.registrocovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cl.inacap.registrocovid.adapters.PacientesListAdapter;
import cl.inacap.registrocovid.dao.PacientesDAO;
import cl.inacap.registrocovid.dao.PacientesDAOSQLite;
import cl.inacap.registrocovid.dto.Paciente;

public class PrincipalActivity extends AppCompatActivity {

    private List<Paciente> pacientes;
    private PacientesDAO pacientesDAO= new PacientesDAOSQLite(this);
    private ListView pacientesListView;
    private FloatingActionButton addBtn;
    private PacientesListAdapter adapter;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    protected void onResume() {
        super.onResume();
        this.pacientes = this.pacientesDAO.getAll();
        this.pacientesListView =  findViewById(R.id.pacientes_lv);
        this.adapter = new PacientesListAdapter(this, R.layout.lista_pacientes, this.pacientes);
        this.pacientesListView.setAdapter(this.adapter);
        this.pacientesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Paciente paciente = pacientes.get(position);
                Intent intent = new Intent(PrincipalActivity.this, PacienteViewActivity.class);
                intent.putExtra("paciente", paciente);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        this.setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        this.addBtn = findViewById(R.id.btn_add_float);
        this.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent position = new Intent(PrincipalActivity.this, CrearPacienteActivity.class);
                startActivity(position);
            }
        });
    }
}