package Datos;

import Clases.Prescribir.logic.Prescripcion;
import Clases.Paciente.logic.Paciente;
import Clases.Medico.logic.Medico;
import Clases.Receta.logic.ItemReceta;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PrescripcionDAO {

    private final Connection connection;

    public PrescripcionDAO(Connection connection) {
        this.connection = connection;
    }

    // -------------------------------------------------------
    // 🔹 Insertar una nueva prescripción
    // -------------------------------------------------------
    public void insertar(Prescripcion p) throws SQLException {
        String sql = """
            INSERT INTO Prescripcion
            (fechaConfeccion, fechaRetiro, estado, items, paciente, medico)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, p.getFechaConfeccion() != null ?
                    Timestamp.valueOf(p.getFechaConfeccion()) : null);
            stmt.setTimestamp(2, p.getFechaRetiro() != null ?
                    Timestamp.valueOf(p.getFechaRetiro()) : null);
            stmt.setString(3, p.getEstado());
            stmt.setInt(4, p.getItem().getMedicamentoCodigo());
            stmt.setString(5, p.getPaciente().getId());
            stmt.setString(6, p.getMedico().getId());

            stmt.executeUpdate();

            // Obtener el número generado (AUTO_INCREMENT)
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setNumero(rs.getInt(1));
                }
            }
        }
    }

    // -------------------------------------------------------
    // 🔹 Actualizar una prescripción existente
    // -------------------------------------------------------
    public void actualizar(Prescripcion p) throws SQLException {
        String sql = """
            UPDATE Prescripcion SET
            fechaConfeccion = ?, fechaRetiro = ?, estado = ?, items = ?, paciente = ?, medico = ?
            WHERE numero = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, p.getFechaConfeccion() != null ?
                    Timestamp.valueOf(p.getFechaConfeccion()) : null);
            stmt.setTimestamp(2, p.getFechaRetiro() != null ?
                    Timestamp.valueOf(p.getFechaRetiro()) : null);
            stmt.setString(3, p.getEstado());
            stmt.setInt(4, p.getItem().getMedicamentoCodigo());
            stmt.setString(5, p.getPaciente().getId());
            stmt.setString(6, p.getMedico().getId());
            stmt.setInt(7, p.getNumero());

            stmt.executeUpdate();
        }
    }

    // -------------------------------------------------------
    // 🔹 Eliminar una prescripción por número
    // -------------------------------------------------------
    public void eliminar(int numero) throws SQLException {
        String sql = "DELETE FROM Prescripcion WHERE numero = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.executeUpdate();
        }
    }

    // -------------------------------------------------------
    // 🔹 Buscar una prescripción por número
    // -------------------------------------------------------
    public Prescripcion obtenerPorNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM Prescripcion WHERE numero = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPrescripcion(rs);
                }
            }
        }
        return null;
    }

    // -------------------------------------------------------
    // 🔹 Obtener todas las prescripciones
    // -------------------------------------------------------
    public List<Prescripcion> obtenerTodas() throws SQLException {
        List<Prescripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Prescripcion";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSetToPrescripcion(rs));
            }
        }
        return lista;
    }

    // -------------------------------------------------------
    // 🔹 Mapear ResultSet → Prescripcion
    // -------------------------------------------------------
    private Prescripcion mapResultSetToPrescripcion(ResultSet rs) throws SQLException {
        Prescripcion p = new Prescripcion();

        p.setNumero(rs.getInt("numero"));
        p.setFechaConfeccion(rs.getTimestamp("fechaConfeccion") != null ?
                rs.getTimestamp("fechaConfeccion").toLocalDateTime() : null);
        p.setFechaRetiro(rs.getTimestamp("fechaRetiro") != null ?
                rs.getTimestamp("fechaRetiro").toLocalDateTime() : null);
        p.setEstado(rs.getString("estado"));

        // 🔹 Relaciones (referencias simples)
        Paciente paciente = new Paciente();
        paciente.setId(rs.getString("paciente"));
        p.setPaciente(paciente);

        Medico medico = new Medico();
        medico.setId(rs.getString("medico"));
        p.setMedico(medico);

        ItemReceta item = new ItemReceta();
        item.setMedicamentoCodigo(rs.getInt("items"));
        p.setItem(item);

        return p;
    }
}