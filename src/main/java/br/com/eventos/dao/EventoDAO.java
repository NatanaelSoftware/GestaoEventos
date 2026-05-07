package br.com.eventos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import br.com.eventos.model.Evento;


public class EventoDAO {


    public void inserir(Evento evento) throws SQLException {
        String sql = "INSERT INTO eventos (nome, datahora, local, capacidade, valor_base, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, evento.getNome());
            ps.setTimestamp(2, Timestamp.valueOf(evento.getData()));
            ps.setString(3, evento.getLocal());
            ps.setInt(4, evento.getCapacidade());
            ps.setDouble(5, evento.getValorBase());
            ps.setString(6, evento.getStatus());
            ps.executeUpdate();
        }
    }

    public Evento buscarporId(int id) throws SQLException {
        String sql = "SELECT * FROM eventos WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
                 ps.setInt(1, id);
                 ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Evento(
                            rs.getString("nome"),
                            rs.getTimestamp("datahora").toLocalDateTime(),
                            rs.getString("local"),
                            rs.getInt("id"),
                            rs.getInt("capacidade"),
                            rs.getDouble("valor_base"),
                            rs.getString("status")
                    );
                }
        }
        return null;
    }

    public List<Evento> listarTodos() throws SQLException {
        String sql = "SELECT * FROM eventos";

        List<Evento> lista = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evento evento = new Evento(
                        rs.getString("nome"),
                        rs.getTimestamp("datahora").toLocalDateTime(),
                        rs.getString("local"),
                        rs.getInt("id"),
                        rs.getInt("capacidade"),
                        rs.getDouble("valor_base"),
                        rs.getString("status")
                );
                lista.add(evento);
            }

        }
        return lista;
    }


    public void atualizar(Evento evento) throws  SQLException{
        String sql = "UPDATE eventos SET nome = ?, datahora = ?, local = ?, capacidade = ?, valor_base = ?, status = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, evento.getNome());
            ps.setTimestamp(2, Timestamp.valueOf(evento.getData()));
            ps.setString(3, evento.getLocal());
            ps.setInt(4, evento.getCapacidade());
            ps.setDouble(5, evento.getValorBase());
            ps.setString(6, evento.getStatus());
            ps.setInt(7, evento.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE  FROM eventos WHERE id = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int contarIngressosAtivos(int idEvento) throws SQLException{
        String sql = "SELECT COUNT(*) FROM ingressos WHERE evento_id = ? AND status != 'CANCELADO'";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEvento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
