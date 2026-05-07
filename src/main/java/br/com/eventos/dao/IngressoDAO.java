package br.com.eventos.dao;

import java.sql.*;


import br.com.eventos.model.Evento;
import br.com.eventos.model.Ingresso;
import br.com.eventos.model.IngressoMeia;
import br.com.eventos.model.IngressoInteira;

public class IngressoDAO {


    public void inserir(Ingresso ingresso) throws SQLException {
        String sql = "INSERT INTO ingressos (evento_id, tipo, comprador_nome, status, data_emissao, comprovacao_meia, preco_pago) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ingresso.getEvento().getId());
            ps.setString(2, ingresso.getTipo());
            ps.setString(3, ingresso.getCompradorNome());
            ps.setString(4, ingresso.getStatus());
            ps.setTimestamp(5, Timestamp.valueOf(ingresso.getDataEmissao()));;

            if (ingresso instanceof IngressoMeia) {
                ps.setString(6,((IngressoMeia) ingresso).getComprovacaoMeia());
            }else {
                ps.setString(6, null);
            }

            ps.setDouble(7, ingresso.getPrecoPago());
            ps.executeUpdate();
        }
    }

    public Ingresso buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM ingressos WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");
                int eventoId = rs.getInt("evento_id");
                Evento evento = new EventoDAO().buscarporId(eventoId);
                String compradorNome = rs.getString("comprador_nome");

                if (tipo.equals("MEIA")) {
                    String comprovacao = rs.getString("comprovacao_meia");
                    return new IngressoMeia(rs.getInt("id"), evento, compradorNome, comprovacao);
                } else {
                    return new IngressoInteira(rs.getInt("id"), evento, compradorNome);
                }
            }
        }
        return null;
    }

    public void mudarStatus(int id, String status) throws  SQLException {
        String sql = "UPDATE ingressos SET status = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

        }
    }

    public double somarDinheiroDasVendas (int idEvento) throws SQLException{
        String sql = "SELECT SUM(preco_pago) FROM ingressos WHERE evento_id = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, idEvento);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    public int contarIngressosVendidos(int idEvento) throws SQLException{
        String sql = "SELECT COUNT(*) FROM ingressos WHERE evento_id = ?";

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
