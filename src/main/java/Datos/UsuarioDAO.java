package Datos;

import Datos.Database;
import Clases.Usuario.logic.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static UsuarioDAO instance;
    private final Database db;

    private UsuarioDAO() {
        db = Database.instance();
    }

    public static UsuarioDAO instance() {
        if (instance == null) instance = new UsuarioDAO();
        return instance;
    }

    public void create(Usuario u) throws Exception {
        String sql = "INSERT INTO Usuario (id, nombre, clave, rol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, u.getId());
            stmt.setString(2, u.getNombre());
            stmt.setString(3, u.getClave());
            stmt.setString(4, u.getRol());
            int rows = db.executeUpdate(stmt);
            if (rows == 0) throw new Exception("Error al crear usuario.");
        }
    }

    public Usuario readById(String id) {
        String sql = "SELECT * FROM Usuario WHERE id=?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = db.executeQuery(stmt);
            if (rs.next()) {
                return new Usuario(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave"),
                        rs.getString("rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Usuario u) {
        String sql = "UPDATE Usuario SET nombre=?, clave=?, rol=? WHERE id=?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getClave());
            stmt.setString(3, u.getRol());
            stmt.setString(4, u.getId());
            db.executeUpdate(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM Usuario WHERE id=?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, id);
            db.executeUpdate(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            ResultSet rs = db.executeQuery(stmt);
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("clave"),
                        rs.getString("rol")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}