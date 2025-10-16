package Datos;

import Clases.Medico.logic.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    Database db;

    public MedicoDAO() {
        db = Database.instance();
    }

    public void guardar(Medico m) throws Exception {
        String sql = "INSERT INTO Medico (id, nombre, clave, especialidad) VALUES (?,?,?,?,?)";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, m.getId());
        ps.setString(2, m.getNombre());
        ps.setString(3, m.getClave());
        ps.setString(4, m.getEspecialidad());
        int count = db.executeUpdate(ps);

        if (count == 0) {
            throw new Exception("Medico ya existe");
        }
    }

    public void actualizar(Medico m) throws Exception {
        String sql = "UPDATE Medico SET nombre=?, clave=?, especialidad=? WHERE id=?";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, m.getNombre());
        ps.setString(2, m.getClave());
        ps.setString(3, m.getEspecialidad());
        int count = db.executeUpdate(ps);

        if (count == 0) {
            throw new Exception("Medico ya existe");
        }
        if (count == 0) {
            throw new Exception("Medico no existe");
        }
    }

    public Medico buscarPorId(String id) throws Exception {
        try {
            String sql = "SELECT * FROM Medico WHERE id=?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medico(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave"),
                        rs.getString("especialidad"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Medico buscarPorNombre(String nombre) throws Exception {
        try {
            String sql = "SELECT * FROM Medico WHERE nombre=?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medico(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave"),
                        rs.getString("especialidad"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void borrar(Medico o) throws Exception {
        String sql = "DELETE FROM Medico WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, o.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Medico no existe");
        }
    }

    public List<Medico> listar() throws Exception {
        List<Medico> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Medico";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                lista.add(new Medico(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave"),
                        rs.getString("especialidad")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
