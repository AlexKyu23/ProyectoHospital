package data;

import logic.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class UsuarioDAO {
    Database db;

    public UsuarioDAO() {
        db = Database.instance();
    }

    public void guardar(Usuario u) throws Exception {
        String sql = "INSERT INTO Usuario (id, nombre, clave, rol) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, u.getId());
        ps.setString(2, u.getNombre());
        ps.setString(3, u.getClave());
        ps.setString(4, u.getRol());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Usuario ya existe");
        }
    }

    public void actualizar(Usuario u) throws Exception {
        String sql = "UPDATE Usuario SET nombre = ?, clave = ?, rol = ? WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, u.getNombre());
        ps.setString(2, u.getClave());
        ps.setString(3, u.getRol());
        ps.setString(4, u.getId());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Usuario no existe");
        }
    }

    public Usuario buscarPorId(String id) throws Exception {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            return from(rs, "");
        }
        return null;
    }

    public void borrar(String id) throws Exception {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Usuario no existe");
        }
    }

    public List<Usuario> listar() throws Exception {
        return search(new Usuario("", "", "", ""));
    }

    public List<Usuario> search(Usuario e) throws Exception {
        List<Usuario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Usuario WHERE nombre LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + e.getNombre() + "%");
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) {
            resultado.add(from(rs, ""));
        }
        return resultado;
    }

    public Usuario from(ResultSet rs, String alias) throws Exception {
        Usuario e = new Usuario(
                rs.getString(alias + "id"),
                rs.getString(alias + "nombre"),
                rs.getString(alias + "clave"),
                rs.getString(alias + "rol")
        );
        return e;
    }
}