package cl.inacap.registrocovid.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cl.inacap.registrocovid.R;
import cl.inacap.registrocovid.dto.Paciente;

public class PacientesListAdapter extends ArrayAdapter<Paciente> {

    private List<Paciente> pacientes;
    private Activity activity;

    public PacientesListAdapter(@NonNull Activity context, int resource, @NonNull List<Paciente> objects) {
        super(context, resource, objects);
        this.pacientes = objects;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View fila = inflater.inflate(R.layout.lista_pacientes, null, true);

        //Carga el contenido del layout
        TextView rutCl = fila.findViewById(R.id.rut_paciente_lp);
        TextView nombreCl = fila.findViewById(R.id.nombre_paciente_lp);
        TextView fechaCl = fila.findViewById(R.id.fecha_paciente_lp);
        ImageView icono = fila.findViewById(R.id.imgIcons);
        Paciente actual = pacientes.get(position);
        rutCl.setText("Rut: "+ actual.getRut());
        nombreCl.setText("Nombre: "+actual.getNombre()+" "+ actual.getApellido());
        fechaCl.setText("Fecha: "+actual.getFecha());

        return fila;
    }
}
