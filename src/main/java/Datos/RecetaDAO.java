package Datos;

import Clases.Receta.logic.ItemReceta;
import Clases.Receta.logic.Receta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecetaDAO {
    private static RecetaDAO instance;
    Database db;

    private RecetaDAO() {
        db = Database.instance();
    }

    public static RecetaDAO instance() {
        if (instance == null) instance = new RecetaDAO();
        return instance;
    }

    public void guardar(Receta r) throws Exception {
        String sql = "INSERT INTO Receta (id,medicoId, pacienteId, fechaConfeccion, fechaRetiro,estado) VALUES (?,?,?,?,?,?)";

        String confeccionString = r.getFechaConfeccion().toString();
        String retiroString = r.getFechaRetiro().toString();

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, r.getId());
        ps.setString(2, r.getMedicoId());
        ps.setString(3, r.getPacienteId());
        ps.setString(4, confeccionString);
        ps.setString(5, retiroString);
        ps.setString(6, r.getEstado().name());
        int count = db.executeUpdate(ps);
                                                                                    //Actualización para medicamentos !!Revisar!!
        for (ItemReceta item : r.getMedicamentos()) {
            String sqlItem = "INSERT INTO ItemReceta (medicamentoCodigo, descripcion, cantidad,indicaciones, duracionDias) VALUES (?,?,?,?,?)";
            PreparedStatement psItem = db.prepareStatement(sqlItem);
            psItem.setInt(1, item.getMedicamentoCodigo());
            psItem.setString(2, item.getDescripcion());
            psItem.setInt(3, item.getCantidad());
            psItem.setString(4, item.getIndicaciones());
            psItem.setInt(5, item.getDuracionDias());
            db.executeUpdate(psItem);
        }

        if (count == 0) {
            throw new Exception("Receta ya existe");
        }
    }

    public void actualizar(Receta r) throws Exception {
        String sql = "UPDATE Receta SET medicoId=?, pacienteId=?, fechaConfeccion=?, fechaRetiro=? WHERE id=?";

        String confeccionString = r.getFechaConfeccion().toString();
        String retiroString = r.getFechaRetiro().toString();

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, r.getMedicoId());
        ps.setString(2, r.getPacienteId());
        ps.setString(3, confeccionString);
        ps.setString(4, retiroString);
        int count = db.executeUpdate(ps);

        if (count == 0) {
            throw new Exception("Receta no existe");
        }
    }

    public Receta buscarPorId(String id) throws Exception {
        try {
            String sql = "SELECT * FROM Receta WHERE id=?";

            PreparedStatement ps = db.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Receta(
                        rs.getString("id"),
                        rs.getString("medicoId"),
                        rs.getString("pacienteId"),
                        rs.getDate("fechaConfeccion").toLocalDate(),
                        rs.getDate("fechaRetiro").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void borrar(Receta o) throws Exception {
        String sql = "DELETE FROM Receta WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, o.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Receta no existe");
        }
    }

    public List<Receta> listar() throws Exception {
        List<Receta> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Receta";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                lista.add(new Receta(
                        rs.getString("id"),
                        rs.getString("medicoId"),
                        rs.getString("pacienteId"),
                        rs.getDate("fechaConfeccion").toLocalDate(),
                        rs.getDate("fechaRetiro").toLocalDate()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
