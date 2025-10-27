package data;

import logic.ItemReceta;
import logic.Medico;
import logic.Paciente;
import logic.Prescripcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescripcionDAO {
    Database db;

    public PrescripcionDAO() {
        db = Database.instance();
    }

    public void guardar(Prescripcion p) throws Exception {
        String sql = "INSERT INTO Prescripcion (fechaConfeccion, fechaRetiro, estado, itemRecetaId, paciente, medico) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setTimestamp(1, p.getFechaConfeccion() != null ? Timestamp.valueOf(p.getFechaConfeccion()) : null);
        ps.setTimestamp(2, p.getFechaRetiro() != null ? Timestamp.valueOf(p.getFechaRetiro()) : null);
        ps.setString(3, p.getEstado());
        ps.setString(4, p.getItem().getItemRecetaId());
        ps.setString(5, p.getPaciente().getId());
        ps.setString(6, p.getMedico().getId());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("No se pudo guardar la prescripción");
        }
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            p.setNumero(rs.getInt(1));
        }
    }



    public void actualizar(Prescripcion p) throws Exception {
        String sql = "UPDATE Prescripcion SET fechaConfeccion = ?, fechaRetiro = ?, estado = ?, items = ?, paciente = ?, medico = ? WHERE numero = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setTimestamp(1, p.getFechaConfeccion() != null ? Timestamp.valueOf(p.getFechaConfeccion()) : null);
        ps.setTimestamp(2, p.getFechaRetiro() != null ? Timestamp.valueOf(p.getFechaRetiro()) : null);
        ps.setString(3, p.getEstado());
        ps.setInt(4, p.getItem().getMedicamentoCodigo());
        ps.setString(5, p.getPaciente().getId());
        ps.setString(6, p.getMedico().getId());
        ps.setInt(7, p.getNumero());
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Prescripcion no existe");
        }
    }

    public Prescripcion buscarPorNumero(int numero) throws Exception {
        String sql = "SELECT * FROM Prescripcion WHERE numero = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, numero);
        ResultSet rs = db.executeQuery(ps);
        if (rs.next()) {
            return from(rs, "");
        }
        return null;
    }

    public void borrar(int numero) throws Exception {
        String sql = "DELETE FROM Prescripcion WHERE numero = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, numero);
        int count = db.executeUpdate(ps);
        if (count == 0) {
            throw new Exception("Prescripcion no existe");
        }
    }

    public List<Prescripcion> listar() throws Exception {
        return search(new Prescripcion());
    }

    public List<Prescripcion> search(Prescripcion e) throws Exception {
        List<Prescripcion> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Prescripcion WHERE estado LIKE ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, "%" + e.getEstado() + "%"); // Ejemplo: búsqueda por estado
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) {
            resultado.add(from(rs, ""));
        }
        return resultado;
    }

    public Prescripcion from(ResultSet rs, String alias) throws Exception {
        Prescripcion e = new Prescripcion();
        e.setNumero(rs.getInt(alias + "numero"));
        e.setFechaConfeccion(rs.getTimestamp(alias + "fechaConfeccion") != null ? rs.getTimestamp(alias + "fechaConfeccion").toLocalDateTime() : null);
        e.setFechaRetiro(rs.getTimestamp(alias + "fechaRetiro") != null ? rs.getTimestamp(alias + "fechaRetiro").toLocalDateTime() : null);
        e.setEstado(rs.getString(alias + "estado"));

        Paciente paciente = new Paciente();
        paciente.setId(rs.getString(alias + "paciente"));
        e.setPaciente(paciente);

        Medico medico = new Medico();
        medico.setId(rs.getString(alias + "medico"));
        e.setMedico(medico);

        ItemReceta item = new ItemReceta();
        item.setItemRecetaId(rs.getString(alias + "itemRecetaId"));
        e.setItem(item);

        return e;
    }


}