package Datos;

import Clases.Receta.logic.ItemReceta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRecetaDAO {
    private final Database db = Database.instance();

    // Insertar un nuevo item
    public void insertar(ItemReceta item, String recetaId) throws SQLException {
        String sql = "INSERT INTO ItemReceta (recetaId, medicamentoCodigo, descripcion, cantidad, indicaciones, duracionDias) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, recetaId);
            stm.setInt(2, item.getMedicamentoCodigo());
            stm.setString(3, item.getDescripcion());
            stm.setInt(4, item.getCantidad());
            stm.setString(5, item.getIndicaciones());
            stm.setInt(6, item.getDuracionDias());
            stm.executeUpdate();
        }
    }

    // Actualizar un item existente
    public void actualizar(ItemReceta item) throws SQLException {
        String sql = "UPDATE ItemReceta SET descripcion=?, cantidad=?, indicaciones=?, duracionDias=? WHERE medicamentoCodigo=?";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, item.getDescripcion());
            stm.setInt(2, item.getCantidad());
            stm.setString(3, item.getIndicaciones());
            stm.setInt(4, item.getDuracionDias());
            stm.setInt(5, item.getMedicamentoCodigo());
            stm.executeUpdate();
        }
    }

    // Eliminar un item
    public void eliminar(String recetaId, int medicamentoCodigo) throws SQLException {
        String sql = "DELETE FROM ItemReceta WHERE recetaId=? AND medicamentoCodigo=?";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, recetaId);
            stm.setInt(2, medicamentoCodigo);
            stm.executeUpdate();
        }
    }

    // Buscar un item por código
    public ItemReceta obtener(int medicamentoCodigo) throws SQLException {
        String sql = "SELECT * FROM ItemReceta WHERE medicamentoCodigo=?";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setInt(1, medicamentoCodigo);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new ItemReceta(
                        rs.getInt("medicamentoCodigo"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getString("indicaciones"),
                        rs.getInt("duracionDias")
                );
            }
            return null;
        }
    }

    // Listar todos los items
    public List<ItemReceta> listar() throws SQLException {
        List<ItemReceta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM ItemReceta";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ItemReceta item = new ItemReceta(
                        rs.getInt("medicamentoCodigo"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getString("indicaciones"),
                        rs.getInt("duracionDias")
                );
                resultado.add(item);
            }
        }
        return resultado;
    }

    public List<ItemReceta> obtenerPorReceta(String recetaId) throws SQLException {
        List<ItemReceta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM ItemReceta WHERE recetaId=?";
        try (PreparedStatement stm = db.prepareStatement(sql)) {
            stm.setString(1, recetaId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ItemReceta item = new ItemReceta(
                        rs.getInt("medicamentoCodigo"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getString("indicaciones"),
                        rs.getInt("duracionDias")
                );
                resultado.add(item);
            }
        }
        return resultado;
    }
}