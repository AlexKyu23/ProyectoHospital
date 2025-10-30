package data;

import logic.ItemReceta;
import logic.Receta;
import logic.EstadoReceta;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        if (count == 0) {
            throw new Exception("Receta ya existe");
        }
        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        for (ItemReceta item : r.getMedicamentos()) {
            item.setRecetaId(r.getId());
            itemDAO.guardar(item);
        }
    }

    public void actualizar(Receta r) throws Exception {
        String sql = "UPDATE Receta SET medicoId = ?, pacienteId = ?, fechaConfeccion = ?, fechaRetiro = ?, estado = ? WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, r.getMedicoId());
        ps.setString(2, r.getPacienteId());
        ps.setDate(3, Date.valueOf(r.getFechaConfeccion()));
        ps.setDate(4, r.getFechaRetiro() != null ? Date.valueOf(r.getFechaRetiro()) : null);
        ps.setString(5, r.getEstado().name());
        ps.setString(6, r.getId());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Receta no existe");
        }
        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        itemDAO.borrar(r.getId()); // Eliminar viejos ítems
        for (ItemReceta item : r.getMedicamentos()) {
            item.setRecetaId(r.getId());
            itemDAO.guardar(item);
        }
    }

    public Receta buscarPorId(String id) throws Exception {
        String sql = "SELECT * FROM Receta WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            Receta receta = from(rs, "");
            ItemRecetaDAO itemDAO = new ItemRecetaDAO();
            receta.setMedicamentos(itemDAO.search(new ItemReceta(id, "", 0, "", 0, "", 0)));
            return receta;
        }
        return null;
    }

    public void borrar(String id) throws Exception {
        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        itemDAO.borrar(id);
        String sql = "DELETE FROM Receta WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, id);
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Receta no existe");
        }
    }

    public List<Receta> listar() throws Exception {
        List<Receta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Receta";
        PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(ps);
        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        while (rs.next()) {
            Receta receta = from(rs, "");
            receta.setMedicamentos(itemDAO.search(new ItemReceta(receta.getId(), "", 0, "", 0, "", 0)));
            resultado.add(receta);
        }
        return resultado;
    }


    public List<Receta> search(Receta e) throws Exception {
        List<Receta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Receta WHERE id LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + e.getId() + "%");
        ResultSet rs = db.executeQuery(ps);
        ItemRecetaDAO itemDAO = new ItemRecetaDAO();
        while (rs.next()) {
            Receta receta = from(rs, "");
            receta.setMedicamentos(itemDAO.search(new ItemReceta(receta.getId(), "", 0, "", 0, "", 0)));
            resultado.add(receta);
        }
        return resultado;
    }


    public Receta from(ResultSet rs, String alias) throws Exception {
        Receta e = new Receta(
                rs.getString(alias + "id"),
                rs.getString(alias + "medicoId"),
                rs.getString(alias + "pacienteId"),
                rs.getDate(alias + "fechaConfeccion").toLocalDate(),
                rs.getDate(alias + "fechaRetiro") != null ? rs.getDate(alias + "fechaRetiro").toLocalDate() : null
        );
        e.setEstado(EstadoReceta.valueOf(rs.getString(alias + "estado")));
        return e;
    }
}