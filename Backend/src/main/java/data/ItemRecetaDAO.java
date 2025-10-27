package data;

import logic.ItemReceta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRecetaDAO {
    Database db;

    public ItemRecetaDAO() {
        db = Database.instance();
    }

    public void guardar(ItemReceta item) throws Exception {
        String sql = "INSERT INTO ItemReceta (itemRecetaId, recetaId, medicamentoCodigo, descripcion, cantidad, indicaciones, duracionDias) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, item.getItemRecetaId());
        ps.setString(2, item.getRecetaId());
        ps.setInt(3, item.getMedicamentoCodigo());
        ps.setString(4, item.getDescripcion());
        ps.setInt(5, item.getCantidad());
        ps.setString(6, item.getIndicaciones());
        ps.setInt(7, item.getDuracionDias());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("ItemReceta ya existe");
        }
    }

    public void actualizar(ItemReceta item) throws Exception {
        String sql = "UPDATE ItemReceta SET recetaId = ?, medicamentoCodigo = ?, descripcion = ?, cantidad = ?, indicaciones = ?, duracionDias = ? WHERE itemRecetaId = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, item.getRecetaId());
        ps.setInt(2, item.getMedicamentoCodigo());
        ps.setString(3, item.getDescripcion());
        ps.setInt(4, item.getCantidad());
        ps.setString(5, item.getIndicaciones());
        ps.setInt(6, item.getDuracionDias());
        ps.setString(7, item.getItemRecetaId());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("ItemReceta no existe");
        }
    }

    public ItemReceta buscarPorId(String itemRecetaId) throws Exception {
        String sql = "SELECT * FROM ItemReceta WHERE itemRecetaId = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, itemRecetaId);
        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            return from(rs, "");
        }
        return null;
    }

    public void borrar(String itemRecetaId) throws Exception {
        String sql = "DELETE FROM ItemReceta WHERE itemRecetaId = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, itemRecetaId);
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("ItemReceta no existe");
        }
    }

    public List<ItemReceta> listar() throws Exception {
        return search(new ItemReceta("", "", 0, "", 0, "", 0));
    }

    public List<ItemReceta> search(ItemReceta e) throws Exception {
        List<ItemReceta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM ItemReceta WHERE descripcion LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + e.getDescripcion() + "%");
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) {
            resultado.add(from(rs, ""));
        }
        return resultado;
    }

    public List<ItemReceta> buscarPorRecetaId(String recetaId) throws Exception {
        List<ItemReceta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM ItemReceta WHERE recetaId = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, recetaId);
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) {
            resultado.add(from(rs, ""));
        }
        return resultado;
    }

    public ItemReceta from(ResultSet rs, String alias) throws Exception {
        ItemReceta e = new ItemReceta(
                rs.getString(alias + "itemRecetaId"),
                rs.getString(alias + "recetaId"),
                rs.getInt(alias + "medicamentoCodigo"),
                rs.getString(alias + "descripcion"),
                rs.getInt(alias + "cantidad"),
                rs.getString(alias + "indicaciones"),
                rs.getInt(alias + "duracionDias")
        );
        return e;
    }
}