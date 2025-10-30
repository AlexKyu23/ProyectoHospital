package data;

import logic.ItemReceta;
import logic.Receta;
import logic.EstadoReceta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecetaDAO {
    Database db;

    public RecetaDAO() {
        db = Database.instance();
    }

    public void guardar(Receta r) throws Exception {
        String sql = "INSERT INTO Receta (id, medicoId, pacienteId, fechaConfeccion, fechaRetiro, estado) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, r.getId());
        ps.setString(2, r.getMedicoId());
        ps.setString(3, r.getPacienteId());
        ps.setDate(4, Date.valueOf(r.getFechaConfeccion()));
        ps.setDate(5, r.getFechaRetiro() != null ? Date.valueOf(r.getFechaRetiro()) : null);
        ps.setString(6, r.getEstado().name());

        int count = db.executeUpdate(ps);
        if (count == 0) throw new Exception("⚠️ No se pudo guardar la receta (ya existe o error SQL)");

        // Guardar ítems asociados
        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        for (ItemReceta item : r.getMedicamentos()) {
            item.setRecetaId(r.getId());
            itemDAO.guardar(item);
        }
    }

    public void actualizar(Receta r) throws Exception {
        String sql = "UPDATE Receta SET medicoId=?, pacienteId=?, fechaConfeccion=?, fechaRetiro=?, estado=? WHERE id=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, r.getMedicoId());
        ps.setString(2, r.getPacienteId());
        ps.setDate(3, Date.valueOf(r.getFechaConfeccion()));
        ps.setDate(4, r.getFechaRetiro() != null ? Date.valueOf(r.getFechaRetiro()) : null);
        ps.setString(5, r.getEstado().name());
        ps.setString(6, r.getId());

        int count = db.executeUpdate(ps);
        if (count == 0) throw new Exception("⚠️ Receta no encontrada para actualizar");

        // Actualizar ítems
        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        itemDAO.borrar(r.getId());
        for (ItemReceta item : r.getMedicamentos()) {
            item.setRecetaId(r.getId());
            itemDAO.guardar(item);
        }
    }

    public Receta buscarPorId(String id) throws Exception {
        String sql = "SELECT * FROM Receta WHERE id=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = db.executeQuery(ps);

        if (rs.next()) {
            Receta receta = from(rs);
            ItemRecetaDAO itemDAO = new ItemRecetaDAO();
            receta.setMedicamentos(itemDAO.buscarPorRecetaId(id));
            return receta;
        }
        return null;
    }

    public void borrar(String id) throws Exception {
        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        itemDAO.borrar(id);

        String sql = "DELETE FROM Receta WHERE id=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        int count = db.executeUpdate(ps);

        if (count == 0) throw new Exception("⚠️ Receta no encontrada para borrar");
    }

    public List<Receta> listar() throws Exception {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receta";
        PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(ps);

        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        while (rs.next()) {
            Receta receta = from(rs);
            receta.setMedicamentos(itemDAO.buscarPorRecetaId(receta.getId()));
            lista.add(receta);
        }
        return lista;
    }

    public List<Receta> search(String filtro) throws Exception {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receta WHERE id LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ResultSet rs = db.executeQuery(ps);

        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        while (rs.next()) {
            Receta receta = from(rs);
            receta.setMedicamentos(itemDAO.buscarPorRecetaId(receta.getId()));
            lista.add(receta);
        }
        return lista;
    }

    private Receta from(ResultSet rs) throws Exception {
        Receta r = new Receta(
                rs.getString("id"),
                rs.getString("medicoId"),
                rs.getString("pacienteId"),
                rs.getDate("fechaConfeccion").toLocalDate(),
                rs.getDate("fechaRetiro") != null ? rs.getDate("fechaRetiro").toLocalDate() : null
        );
        r.setEstado(EstadoReceta.valueOf(rs.getString("estado")));
        return r;
    }
}
