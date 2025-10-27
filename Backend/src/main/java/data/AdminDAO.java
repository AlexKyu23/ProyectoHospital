package data;

import logic.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    Database db;

    public AdminDAO() {
        db = Database.instance();
    }

    public void guardar(Admin admin) throws Exception {
        String sql = "INSERT INTO Admin (id, nombre, clave) VALUES (?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, admin.getId());
        ps.setString(2, admin.getNombre());
        ps.setString(3, admin.getClave());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Admin ya existe");
        }
    }

    public void actualizar(Admin admin) throws Exception {
        String sql = "UPDATE Admin SET nombre = ?, clave = ? WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, admin.getNombre());
        ps.setString(2, admin.getClave());
        ps.setString(3, admin.getId());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Admin no existe");
        }
    }

    public Admin buscarPorId(String id) throws Exception {
        String sql = "SELECT * FROM Admin WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            return from(rs, "");
        }
        return null;
    }

    public void borrar(String id) throws Exception {
        String sql = "DELETE FROM Admin WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Admin no existe");
        }
    }

    public List<Admin> listar() throws Exception {
        return search(new Admin("", "", ""));
    }

    public List<Admin> search(Admin e) throws Exception {
        List<Admin> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Admin WHERE nombre LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + e.getNombre() + "%");
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) {
            resultado.add(from(rs, ""));
        }
        return resultado;
    }

    public Admin from(ResultSet rs, String alias) throws Exception {
        Admin e = new Admin(
                rs.getString(alias + "id"),
                rs.getString(alias + "nombre"),
                rs.getString(alias + "clave")
        );
        return e;
    }
}