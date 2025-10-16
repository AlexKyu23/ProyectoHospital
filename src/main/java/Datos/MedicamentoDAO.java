package Datos;

import Clases.Medicamento.logic.Medicamento;

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
        String sql = "INSERT INTO Medicamento (codigo, nombre, descripcion) VALUES (?,?,?)";

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
        String sql = "UPDATE Medicamento SET nombre=?, descripcion=? WHERE codigo=?";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, m.getNombre());
        ps.setString(2, m.getDescripcion());
        int count = db.executeUpdate(ps);

        if (count == 0) {
            throw new Exception("Medicamento ya existe");
        }
        if (count == 0) {
            throw new Exception("Medicamento no existe");
        }
    }

    public Medicamento buscarPorCodigo(int codigo) throws Exception {
        try {
            String sql = "SELECT * FROM Medicamento WHERE codigo=?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medicamento(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("codigo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Medicamento buscarPorNombre(String nombre) throws Exception {
        try {
            String sql = "SELECT * FROM Medico WHERE nombre=?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medicamento(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("codigo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void borrar(Medicamento o) throws Exception {
        String sql = "DELETE FROM Medicamento WHERE codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, o.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Medicamento no existe");
        }
    }

    public List<Medicamento> listar() throws Exception {
        List<Medicamento> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Medicamento";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                lista.add(new Medicamento(
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("codigo")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
