package Datos;
import Clases.Farmaceuta.logic.Farmaceuta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutaDAO {

    Database db;

    public FarmaceutaDAO() {
        db = Database.instance();
    }

    public void guardar(Farmaceuta f) throws Exception {
        String sql = "INSERT INTO Farmaceuta (id, nombre, clave) VALUES (?,?,?)";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, f.getId());
        ps.setString(2, f.getNombre());
        ps.setString(3, f.getClave());
        int count = db.executeUpdate(ps);

        if (count == 0) {
            throw new Exception("Farmaceuta ya existe");
        }
    }

    public void actualizar(Farmaceuta f) throws Exception {
        String sql = "UPDATE Farmaceuta SET nombre=?, clave=? WHERE id=?";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, f.getNombre());
        ps.setString(2, f.getClave());
        int count = db.executeUpdate(ps);

        if (count == 0) {
            throw new Exception("Farmaceuta ya existe");
        }
        if (count == 0) {
            throw new Exception("Farmaceuta no existe");
        }
    }

    public Farmaceuta buscarPorId(String id) throws Exception {
        try {
            String sql = "SELECT * FROM Farmaceuta WHERE id=?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Farmaceuta(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Farmaceuta buscarPorNombre(String nombre) throws Exception {
        try {
            String sql = "SELECT * FROM Medico WHERE nombre=?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Farmaceuta(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void borrar(Farmaceuta o) throws Exception {
        String sql = "DELETE FROM Farmaceuta WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, o.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Farmaceuta no existe");
        }
    }

    public List<Farmaceuta> listar() throws Exception {
        List<Farmaceuta> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Farmaceuta";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                lista.add(new Farmaceuta(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
