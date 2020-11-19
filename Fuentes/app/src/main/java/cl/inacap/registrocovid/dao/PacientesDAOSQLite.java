package cl.inacap.registrocovid.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.registrocovid.dto.Paciente;
import cl.inacap.registrocovid.helpers.PacientesDBOpenHelper;

public class PacientesDAOSQLite implements PacientesDAO{

    private PacientesDBOpenHelper db;

    public PacientesDAOSQLite(Context context){
        this.db = new PacientesDBOpenHelper(context, "DBPacientes", null, 1);
    }


    @Override
    public Paciente save(Paciente p) {
        SQLiteDatabase writer = this.db.getWritableDatabase();

        String sql = String.format("INSERT INTO pacientes(" +
                "rut,nombre,apellido,fecha,areaTrabajo,sintomas,temperatura,tos,presionArterial)" +
                " VALUES('%s', '%s', '%s', '%s', '%s', '%s', %f, '%s', %d)"
                ,p.getRut(),p.getNombre(),p.getApellido(),p.getFecha(),p.getAreaTrabajo(),p.getSintomas()
                ,p.getTemperatura(),p.getTos(),p.getPresionArterial());

        writer.execSQL(sql);
        writer.close();
        return p;
    }

    @Override
    public List<Paciente> getAll() {
        SQLiteDatabase reader = this.db.getReadableDatabase();
        List<Paciente> pacientes =  new ArrayList<>();
        try{
            if (reader != null){
                Cursor c = reader.rawQuery("SELECT rut,nombre,apellido,fecha,areaTrabajo,sintomas," +
                        "temperatura,tos,presionArterial FROM pacientes", null);
                if (c.moveToFirst()){
                    do {
                        Paciente p = new Paciente();
                        p.setRut(c.getString(0));
                        p.setNombre(c.getString(1));
                        p.setApellido(c.getString(2));
                        p.setFecha(c.getString(3));
                        p.setAreaTrabajo(c.getString(4));
                        if (c.getString(5).equals("true")){
                            p.setSintomas(true);
                        }else{
                            p.setSintomas(false);
                        }
                        p.setTemperatura(c.getFloat(6));

                        if (c.getString(7).equals("true")){
                            p.setTos(true);
                        }else{
                            p.setTos(false);
                        }
                        p.setPresionArterial(c.getInt(8));
                        pacientes.add(p);
                    }while (c.moveToNext());
                }
                reader.close();
            }

        }catch (Exception ex){

        }
        return pacientes;
    }

}
