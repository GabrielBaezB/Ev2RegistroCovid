package cl.inacap.registrocovid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import cl.inacap.registrocovid.dao.PacientesDAO;
import cl.inacap.registrocovid.dao.PacientesDAOSQLite;
import cl.inacap.registrocovid.dto.Paciente;


public class CrearPacienteActivity extends AppCompatActivity {

    private PacientesDAO pacientesDAO = new PacientesDAOSQLite(this);
    private EditText rutTxt, nombreTxt, apellidoTxt, fechaTxt, temperaturaTxt
            , presionArterialTxt;
    private Spinner areaTrabajoTxt;
    private Switch sintomasTxt, tosTxt ;
    private Button btnRegistrar;

    ArrayAdapter<String> nameAreaTrabajo;
    private String[] areaTrabajo = {"Seleccione","Atencion a Publico", "Otro"};

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_paciente);

        this.rutTxt = findViewById(R.id.crearRutTxt);
        this.nombreTxt = findViewById(R.id.crearNameTxt);
        this.apellidoTxt = findViewById(R.id.crearLastTxt);
        this.fechaTxt = findViewById(R.id.crearDateTxt);
        this.areaTrabajoTxt = findViewById(R.id.crearAreaTxt);
        this.sintomasTxt = findViewById(R.id.crearSintomasTxt);
        this.temperaturaTxt = findViewById(R.id.crearTemperaturaTxt);
        this.tosTxt = findViewById(R.id.crearTosTxt);
        this.presionArterialTxt = findViewById(R.id.crearPresionTxt);

        ///Calendario///
        fechaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                setListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mes, int day) {
                        mes = mes+1;
                        Log.d("CrearPacienteActivity", "onDateSet: dd/mm/yyyy" + day + "/" + mes + "/" + year);
                        String date = day + "/" + mes + "/" + year;
                        fechaTxt.setText(date);
                    }};
                DatePickerDialog dialog = new DatePickerDialog(CrearPacienteActivity.this, android.R.style.Theme_Holo_Dialog
                ,setListener, year, mes, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }});
        /////////////////////

        ////Area de Trabajo///
       nameAreaTrabajo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, areaTrabajo);
       areaTrabajoTxt.setAdapter(nameAreaTrabajo);
        /////////////////////
        ///Sintomas y Tos///verifica el estado actual de un Switch (true o false).
        if (sintomasTxt  != null){
            sintomasTxt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String msg = isChecked ? "Si" : "No";
                    Toast.makeText(CrearPacienteActivity.this, msg, Toast.LENGTH_SHORT).show();
                    sintomasTxt.setText(msg);
                }
            });
        }
        if (tosTxt != null){
            tosTxt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String msgTos = isChecked ? "Si" : "No";
                    Toast.makeText(CrearPacienteActivity.this, msgTos, Toast.LENGTH_SHORT).show();
                    tosTxt.setText(msgTos);
                }
            });

        }
        /////////////
        this.btnRegistrar = findViewById(R.id.btnRegistrar);
        this.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> errores = new ArrayList<>();

                String rutValidado = rutTxt.getText().toString().trim();
                if (validarRut(rutValidado) == false){
                    errores.add("Ingrese rut Valido");
                }

                if (nombreTxt.getText().toString().trim().isEmpty()){
                    errores.add("Ingresa Nombre");
                }

                if(apellidoTxt.getText().toString().trim().isEmpty()){
                    errores.add("Ingresa Apellido");
                }

                if (fechaTxt.getText().toString().trim().isEmpty()){
                    errores.add("Seleccione Fecha");
                }

               if (areaTrabajoTxt.getSelectedItemPosition() == 0){
                    TextView errorTxt = (TextView) areaTrabajoTxt.getSelectedView();
                    errorTxt.setText("");
                    errores.add("Seleccione un Area de trabajo");
                }

                float temperatura = 0;
               try {
                   temperatura = Float.parseFloat(temperaturaTxt.getText().toString().trim());
                   if (temperatura < 20){
                       throw new NumberFormatException();
                   }
               }catch (Exception e){
                   errores.add("Temperatura debe ser mayor que 20");
               }

               int presion = 0;
               try {
                  presion = Integer.parseInt(presionArterialTxt.getText().toString().trim());
                   if (presion < 0){
                       throw new NumberFormatException();
                   }
               }catch (Exception e){
                   errores.add("Ingrese la Presion arterial");
               }

                //crear los pacientes
                if(errores.isEmpty()){
                Paciente p = new Paciente();
                p.setRut(rutTxt.getText().toString());
                p.setNombre(nombreTxt.getText().toString());
                p.setApellido(apellidoTxt.getText().toString());
                p.setFecha(fechaTxt.getText().toString());
                p.setAreaTrabajo(areaTrabajoTxt.getSelectedItem().toString());
              if (sintomasTxt.isChecked()){
                    p.setSintomas(true);
                }else{
                    p.setSintomas(false);
                }
               p.setTemperatura(Float.parseFloat(temperaturaTxt.getText().toString()));
               if (tosTxt.isChecked()){
                    p.setTos(true);
                }else {
                    p.setTos(false);
                }
                p.setPresionArterial(Integer.parseInt(presionArterialTxt.getText().toString()));
                Log.d("Paciente guardado",p.toString());

                pacientesDAO.save(p);

                startActivity(new Intent(CrearPacienteActivity.this, PrincipalActivity.class));
            }else{
                    mostrarErrores(errores);
                }
            }
        });
    }


    public void mostrarErrores(List<String> errores){
        String mensaje = "";
        for (String e: errores){
            mensaje+= "*" + e + "\n";
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CrearPacienteActivity.this);
        alertBuilder.setTitle("Error de Validacion")
                .setMessage(mensaje)
                .setPositiveButton("Ingresar", null)
                .create()
                .show();
    }

    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

 }