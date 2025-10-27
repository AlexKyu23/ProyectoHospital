package data;

import logic.Medicamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {
    Database db;

    public MedicamentoDAO() {
        db = Database.instance();
    }

    public void guardar(Medicamento m) throws Exception {
        String sql = "INSERT INTO Medicamento (codigo, nombre, descripcion) VALUES (?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, m.getCodigo());
        ps.setString(2, m.getNombre());
        ps.setString(3, m.getDescripcion());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Medicamento ya existe");
        }
    }

    public void actualizar(Medicamento m) throws Exception {
        String sql = "UPDATE Medicamento SET nombre = ?, descripcion = ? WHERE codigo = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, m.getNombre());
        ps.setString(2, m.getDescripcion());
        ps.setInt(3, m.getCodigo());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Medicamento no existe");
        }
    }

    public Medicamento buscarPorCodigo(int codigo) throws Exception {
        String sql = "SELECT * FROM Medicamento WHERE codigo = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, codigo);
        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            return from(rs, "");
        }
        return null;
    }

    public void borrar(int codigo) throws Exception {
        String sql = "DELETE FROM Medicamento WHERE codigo = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, codigo);
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Medicamento no existe");
        }
    }

    public List<Medicamento> listar() throws Exception {
        return search(new Medicamento("", "", 0)); // Búsqueda sin filtro para listar todos
    }

    public List<Medicamento> search(Medicamento e) throws Exception {
        List<Medicamento> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Medicamento WHERE descripcion LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + e.getDescripcion() + "%");
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) {
            resultado.add(from(rs, ""));
        }
        return resultado;
    }

    public Medicamento from(ResultSet rs, String alias) throws Exception {
        Medicamento e = new Medicamento(
                rs.getString(alias + "nombre"),
                rs.getString(alias + "descripcion"),
                rs.getInt(alias + "codigo")
        );
        return e;
    }
}