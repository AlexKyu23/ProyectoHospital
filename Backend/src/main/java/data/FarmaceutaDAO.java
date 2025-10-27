package data;

import logic.Farmaceuta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutaDAO {
    Database db;

    public FarmaceutaDAO() {
        db = Database.instance();
    }

    public void guardar(Farmaceuta f) throws Exception {
        String sql = "INSERT INTO Farmaceuta (id, nombre, clave) VALUES (?, ?, ?)";
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
        String sql = "UPDATE Farmaceuta SET nombre = ?, clave = ? WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, f.getNombre());
        ps.setString(2, f.getClave());
        ps.setString(3, f.getId());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Farmaceuta no existe");
        }
    }

    public Farmaceuta buscarPorId(String id) throws Exception {
        String sql = "SELECT * FROM Farmaceuta WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            return from(rs, "");
        }
        return null;
    }

    public void borrar(String id) throws Exception {
        String sql = "DELETE FROM Farmaceuta WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Farmaceuta no existe");
        }
    }

    public List<Farmaceuta> listar() throws Exception {
        return search(new Farmaceuta("", "", ""));
    }

    public List<Farmaceuta> search(Farmaceuta e) throws Exception {
        List<Farmaceuta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Farmaceuta WHERE nombre LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + e.getNombre() + "%");
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) {
            resultado.add(from(rs, ""));
        }
        return resultado;
    }

    public Farmaceuta from(ResultSet rs, String alias) throws Exception {
        Farmaceuta e = new Farmaceuta(
                rs.getString(alias + "id"),
                rs.getString(alias + "nombre"),
                rs.getString(alias + "clave")
        );
        return e;
    }
}