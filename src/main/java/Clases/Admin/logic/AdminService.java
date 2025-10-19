package Clases.Admin.logic;

import Datos.AdminDAO;
import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Farmaceuta.logic.FarmaceutaService;
import Clases.Medico.logic.Medico;
import Clases.Medico.logic.MedicoService;
import Clases.Usuario.logic.Usuario;
import Clases.Usuario.logic.UsuarioService;

public class AdminService {
    private static AdminService instance;
    private final AdminDAO adminDAO;

    private AdminService() {
        adminDAO = AdminDAO.instance();
    }

    public static AdminService instance() {
        if (instance == null) instance = new AdminService();
        return instance;
    }

    public void create(Admin admin) throws Exception {
        if (adminDAO.read(admin.getId()) != null)
            throw new Exception("El admin ya existe");
        adminDAO.create(admin);
    }

    public Admin read(String id) {
        return adminDAO.read(id);
    }

    public void update(Admin admin) throws Exception {
        adminDAO.update(admin);
    }

    public void delete(String id) throws Exception {
        adminDAO.delete(id);
    }

    // 👉 Mantiene la misma lógica que antes
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
