package Clases.Admin.logic;

import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Farmaceuta.logic.FarmaceutaService;
import Clases.Medico.logic.Medico;
import Clases.Medico.logic.MedicoService;
import Clases.Usuario.logic.Usuario;
import Clases.Usuario.logic.UsuarioService;

public class AdminService {
    public void registrarMedico(Medico m) throws Exception {
        MedicoService.instance().create(m);
        Usuario u = new Usuario(m.getId(), m.getNombre(), m.getId(), "MED");
        UsuarioService.instance().create(u);
    }

    public void registrarFarmaceuta(Farmaceuta f) throws Exception {
        FarmaceutaService.instance().create(f);
        Usuario u = new Usuario(f.getId(), f.getNombre(), f.getId(), "FAR");
        UsuarioService.instance().create(u);
    }
}
