package br.com.eventos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.eventos.model.Evento;
import br.com.eventos.model.Validacao;

public class ValidacaoDAO {


    public void inserir(Validacao validacao) throws SQLException {
        String sql = "INSERT INTO validacoes (ingresso_id, operador, resultado, datahora, obs) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, validacao.getIngressosId());
            ps.setString(2, validacao.getOperador());
            ps.setString(3, validacao.getResultado());
            ps.setTimestamp(4, Timestamp.valueOf(validacao.getDataHora()));
            ps.setString(5, validacao.getObs());

            ps.executeUpdate();
        }


    }

    public List<Validacao> listarPorEvento(int idEvento) throws SQLException {
        String sql = "SELECT v.* \n" +
                "FROM validacoes v\n" +
                "JOIN ingressos i ON v.ingresso_id = i.id\n" +
                "WHERE i.evento_id = ?\n";

        List<Validacao> lista = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idEvento);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Validacao validacao = new Validacao (
                        rs.getInt("id"),
                        rs.getInt("ingresso_id"),
                        rs.getString("operador"),
                        rs.getString("resultado"),
                        rs.getTimestamp("datahora").toLocalDateTime(),
                        rs.getString("obs")
                );
                lista.add(validacao);
            }
        }
        return lista;
    }

}



