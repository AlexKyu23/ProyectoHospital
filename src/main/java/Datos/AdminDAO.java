package Datos;

import Clases.Admin.logic.Admin;
import Datos.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private static AdminDAO instance;
    private Database db;

    private AdminDAO() {
        db = Database.instance();
    }

    public static AdminDAO instance() {
        if (instance == null) instance = new AdminDAO();
        return instance;
    }

    public void create(Admin admin) throws Exception {
        String sql = "INSERT INTO admin (id, nombre, clave) VALUES (?, ?, ?)";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, admin.getId());
            stm.setString(2, admin.getNombre());
            stm.setString(3, admin.getClave());
            db.executeUpdate(stm);
        } catch (SQLException ex) {
            throw new Exception("Error al crear el admin: " + ex.getMessage());
        }
    }

    public Admin read(String id) {
        String sql = "SELECT * FROM admin WHERE id = ?";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, id);
            ResultSet rs = db.executeQuery(stm);
            if (rs.next()) {
                return new Admin(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave")
                );
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer admin: " + ex.getMessage());
        }
        return null;
    }

    public List<Admin> findAll() {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin";
        try (PreparedStatement stm = db.prepareStatement(sql);
             ResultSet rs = db.executeQuery(stm)) {
            while (rs.next()) {
                list.add(new Admin(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar admins: " + ex.getMessage());
        }
        return list;
    }

    public void update(Admin admin) throws Exception {
        String sql = "UPDATE admin SET nombre = ?, clave = ? WHERE id = ?";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, admin.getNombre());
            stm.setString(2, admin.getClave());
            stm.setString(3, admin.getId());
            db.executeUpdate(stm);
        } catch (SQLException ex) {
            throw new Exception("Error al actualizar admin: " + ex.getMessage());
        }
    }

    public void delete(String id) throws Exception {
        String sql = "DELETE FROM admin WHERE id = ?";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, id);
            db.executeUpdate(stm);
        } catch (SQLException ex) {
            throw new Exception("Error al eliminar admin: " + ex.getMessage());
        }
    }
}
