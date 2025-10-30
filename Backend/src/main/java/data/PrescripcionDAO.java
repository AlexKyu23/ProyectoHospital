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
        if (count == 0) throw new Exception("⚠️ No se pudo guardar la prescripción");

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) p.setNumero(rs.getInt(1));
    }

    public void actualizar(Prescripcion p) throws Exception {
        String sql = "UPDATE Prescripcion SET fechaConfeccion=?, fechaRetiro=?, estado=?, itemRecetaId=?, paciente=?, medico=? WHERE numero=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setTimestamp(1, p.getFechaConfeccion() != null ? Timestamp.valueOf(p.getFechaConfeccion()) : null);
        ps.setTimestamp(2, p.getFechaRetiro() != null ? Timestamp.valueOf(p.getFechaRetiro()) : null);
        ps.setString(3, p.getEstado());
        ps.setString(4, p.getItem().getItemRecetaId());
        ps.setString(5, p.getPaciente().getId());
        ps.setString(6, p.getMedico().getId());
        ps.setInt(7, p.getNumero());

        int count = db.executeUpdate(ps);
        if (count == 0) throw new Exception("⚠️ Prescripción no encontrada para actualizar");
    }

    public Prescripcion buscarPorNumero(int numero) throws Exception {
        String sql = "SELECT * FROM Prescripcion WHERE numero=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, numero);
        ResultSet rs = db.executeQuery(ps);
        return rs.next() ? from(rs) : null;
    }

    public void borrar(int numero) throws Exception {
        String sql = "DELETE FROM Prescripcion WHERE numero=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, numero);
        int count = db.executeUpdate(ps);
        if (count == 0) throw new Exception("⚠️ Prescripción no encontrada para borrar");
    }

    public List<Prescripcion> listar() throws Exception {
        List<Prescripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Prescripcion";
        PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(ps);
        while (rs.next()) lista.add(from(rs));
        return lista;
    }

    private Prescripcion from(ResultSet rs) throws Exception {
        Prescripcion p = new Prescripcion();
        p.setNumero(rs.getInt("numero"));
        p.setFechaConfeccion(rs.getTimestamp("fechaConfeccion") != null ? rs.getTimestamp("fechaConfeccion").toLocalDateTime() : null);
        p.setFechaRetiro(rs.getTimestamp("fechaRetiro") != null ? rs.getTimestamp("fechaRetiro").toLocalDateTime() : null);
        p.setEstado(rs.getString("estado"));

        // 🔹 Cargar objetos completos usando sus DAOs
        ItemReceta item = new ItemRecetaDAO().buscarPorId(rs.getString("itemRecetaId"));
        p.setItem(item);

        Paciente paciente = new PacienteDAO().buscarPorId(rs.getString("paciente"));
        p.setPaciente(paciente);

        Medico medico = new MedicoDAO().buscarPorId(rs.getString("medico"));
        p.setMedico(medico);

        return p;
    }
}
