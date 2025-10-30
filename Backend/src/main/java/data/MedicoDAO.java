package data;

import logic.Medico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    Database db;

    public MedicoDAO() {
        db = Database.instance();
    }

    public void guardar(Medico m) throws Exception {
        String sql = "INSERT INTO Medico (id, nombre, clave, especialidad) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, m.getId());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getClave());
            ps.setString(4, m.getEspecialidad());
            int count = db.executeUpdate(ps);
            if (count == 0) {
                throw new Exception("Medico ya existe");
            }
        } catch (SQLException e) {
            throw new Exception("Error al guardar Medico: " + e.getMessage());
        }
    }

    public void actualizar(Medico m) throws Exception {
        String sql = "UPDATE Medico SET nombre = ?, clave = ?, especialidad = ? WHERE id = ?";
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getClave());
            ps.setString(3, m.getEspecialidad());
            ps.setString(4, m.getId());
            int count = db.executeUpdate(ps);
            if (count == 0) {
                throw new Exception("Medico no existe");
            }
        } catch (SQLException e) {
            throw new Exception("Error al actualizar Medico: " + e.getMessage());
        }
    }

    public Medico buscarPorId(String id) throws Exception {
        String sql = "SELECT * FROM Medico WHERE id = ?";
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = db.executeQuery(ps);
            if (rs.next()) {
                return from(rs, "");
            }
        } catch (SQLException e) {
            throw new Exception("Error al buscar Medico por ID: " + e.getMessage());
        }
        return null;
    }

    public Medico buscarPorNombre(String nombre) throws Exception {
        List<Medico> resultado = search(new Medico("", nombre, "", ""));
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void borrar(String id) throws Exception {
        String sql = "DELETE FROM Medico WHERE id = ?";
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, id);
            int count = db.executeUpdate(ps);
            if (count == 0) {
                throw new Exception("Medico no existe");
            }
        } catch (SQLException e) {
            throw new Exception("Error al borrar Medico: " + e.getMessage());
        }
    }

    public List<Medico> listar() throws Exception {
        return search(new Medico("", "", "", ""));
    }

    public List<Medico> search(Medico e) throws Exception {
        List<Medico> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Medico WHERE nombre LIKE ?";
        try (PreparedStatement ps = db.prepareStatement(sql)) {
            ps.setString(1, "%" + e.getNombre() + "%");
            ResultSet rs = db.executeQuery(ps);
            while (rs.next()) {
                resultado.add(from(rs, ""));
            }
            System.out.println("🔍 Médicos encontrados: " + resultado.size());
        } catch (SQLException ex) {
            throw new Exception("Error al buscar Medico: " + ex.getMessage());
        }
        return resultado;
    }

    public Medico from(ResultSet rs, String alias) throws Exception {
        Medico e = new Medico(
                rs.getString(alias + "id"),
                rs.getString(alias + "nombre"),
                rs.getString(alias + "clave"),
                rs.getString(alias + "especialidad")
        );
        return e;
    }
}