package data;

import logic.Medicamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        ps.setString(1, m.getCodigo());
        ps.setString(2, m.getNombre());
        ps.setString(3, m.getDescripcion());

        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Error al guardar medicamento");
        }
    }

    public void actualizar(Medicamento m) throws Exception {
        String sql = "UPDATE Medicamento SET nombre = ?, descripcion = ? WHERE codigo = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, m.getNombre());
        ps.setString(2, m.getDescripcion());
        ps.setString(3, m.getCodigo());

        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Medicamento no existe");
        }
    }

    public Medicamento buscarPorCodigo(String codigo) throws Exception {
        String sql = "SELECT * FROM Medicamento WHERE codigo = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, codigo);

        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            return from(rs, "");
        }
        return null;
    }

    public void borrar(String codigo) throws Exception {
        String sql = "DELETE FROM Medicamento WHERE codigo = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, codigo);

        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Medicamento no existe");
        }
    }

    public List<Medicamento> listar() throws Exception {
        return search(new Medicamento("", "", ""));
    }

    public List<Medicamento> search(Medicamento e) throws Exception {
        List<Medicamento> resultado = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM Medicamento WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (e.getNombre() != null && !e.getNombre().isEmpty()) {
            sql.append(" AND nombre LIKE ?");
            params.add("%" + e.getNombre() + "%");
        }

        if (e.getDescripcion() != null && !e.getDescripcion().isEmpty()) {
            sql.append(" AND descripcion LIKE ?");
            params.add("%" + e.getDescripcion() + "%");
        }

        if (e.getCodigo() != null && !e.getCodigo().isEmpty()) {
            sql.append(" AND codigo = ?");
            params.add(e.getCodigo());
        }

        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) {
            ps.setString(i + 1, params.get(i));
        }

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
                rs.getString(alias + "codigo") // ← ya no se usa getInt
        );
        return e;
    }
}
