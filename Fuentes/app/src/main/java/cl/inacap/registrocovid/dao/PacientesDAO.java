package cl.inacap.registrocovid.dao;

import java.util.List;

import cl.inacap.registrocovid.dto.Paciente;

public interface PacientesDAO {

    Paciente save(Paciente p);
    List<Paciente> getAll();
}
