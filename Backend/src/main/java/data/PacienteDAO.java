package data;
import logic.Paciente;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    Database db;

    public PacienteDAO() {
        db = Database.instance();
    }

    public void guardar(Paciente p) throws Exception {
        String sql = "INSERT INTO Paciente (id, nombre, telefono, fechaNacimiento) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, p.getId());
        ps.setString(2, p.getNombre());
        ps.setString(3, p.getTelefono());
        ps.setDate(4, Date.valueOf(p.getFechaNacimiento()));
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Paciente ya existe");
        }
    }

    public void actualizar(Paciente p) throws Exception {
        String sql = "UPDATE Paciente SET nombre = ?, telefono = ?, fechaNacimiento = ? WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getTelefono());
        ps.setDate(3, Date.valueOf(p.getFechaNacimiento()));
        ps.setString(4, p.getId());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Paciente no existe");
        }
    }

    public Paciente buscarPorId(String id) throws Exception {
        String sql = "SELECT * FROM Paciente WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            return from(rs, "");
        }
        return null;
    }

    public void borrar(String id) throws Exception {
        String sql = "DELETE FROM Paciente WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Paciente no existe");
        }
    }

    public List<Paciente> listar() throws Exception {
        return search(new Paciente("", "", "", null));
    }

    public List<Paciente> search(Paciente e) throws Exception {
        List<Paciente> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Paciente WHERE nombre LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + e.getNombre() + "%");
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) {
            resultado.add(from(rs, ""));
        }
        return resultado;
    }

    public Paciente from(ResultSet rs, String alias) throws Exception {
        Paciente e = new Paciente(
                rs.getString(alias + "id"),
                rs.getString(alias + "nombre"),
                rs.getString(alias + "telefono"),
                rs.getDate(alias + "fechaNacimiento").toLocalDate()
        );
        return e;
    }
}