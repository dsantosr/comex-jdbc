package com.comex.dao;

import com.comex.database.ConnectionFactory;
import com.comex.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    // Monta um objeto Cliente a partir dos dados do resultado da consulta
    private Client montaCliente(ResultSet resultSet) throws SQLException {

        Client client = new Client();

        client.setId(resultSet.getLong("id"));
        client.setNome(resultSet.getString("name"));
        client.setCpf(resultSet.getString("cpf"));
        client.setEmail(resultSet.getString("email"));
        client.setPhone(resultSet.getString("phone"));
        client.setLogradouro(resultSet.getString("logradouro"));
        client.setBairro(resultSet.getString("bairro"));
        client.setCidade(resultSet.getString("cidade"));
        client.setEstado(resultSet.getString("uf"));
        client.setCep(resultSet.getString("cep"));

        return client;
    }

    public void create(Client client) {
        String sql = """
                     insert into client 
                        (name, email, phone, cpf, logradouro, bairro, cidade, uf, cep) 
                     values
                        (?, ?, ?, ?, ?, ?, ?, ?, ?)
                     """;

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, client.getNome());
            comando.setString(2, client.getEmail());
            comando.setString(3, client.getPhone());
            comando.setString(4, client.getCpf());
            comando.setString(5, client.getLogradouro());
            comando.setString(6, client.getBairro());
            comando.setString(7, client.getCidade());
            comando.setString(8, client.getEstado());
            comando.setString(9, client.getCep());

            comando.execute();
            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar client.", e);
        }
    }

    public Client searchByID(long id) {
        String sql = "select * from client where id = ?";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection conexao = connectionFactory.criaConexao()) {
            PreparedStatement preparoDoComando = conexao.prepareStatement(sql);
            preparoDoComando.setLong(1, id);

            ResultSet resultSet = preparoDoComando.executeQuery();

            Client possivelClient = null;
            if (resultSet.next()) {
                possivelClient = montaCliente(resultSet);
            }

            preparoDoComando.close();
            resultSet.close();

            return possivelClient;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar client por ID", e);
        }
    }

    public void update(Client client) {
        String sql = """
                     update client set 
                        name = ?, 
                        email = ?, 
                        phone = ?, 
                        cpf = ?, 
                        logradouro = ?, 
                        bairro = ?, 
                        cidade = ?, 
                        uf = ?, 
                        cep = ? 
                     where id = ?
                     """;

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, client.getNome());
            comando.setString(2, client.getEmail());
            comando.setString(3, client.getPhone());
            comando.setString(4, client.getCpf());
            comando.setString(5, client.getLogradouro());
            comando.setString(6, client.getBairro());
            comando.setString(7, client.getCidade());
            comando.setString(8, client.getEstado());
            comando.setString(9, client.getCep());
            comando.setLong(10, client.getId());

            comando.execute();
            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar client.", e);
        }
    }

    public void delete(Client client) {
        String sql = "delete from client where id = ?";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, client.getId());

            comando.execute();
            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir client.", e);
        }
    }

    public List<Client> listAll() {
        // Define a consulta SQL para selecionar todos os registros da tabela "client"
        String sql = "select * from client";

        // Cria uma instância da classe ConnectionFactory para obter uma conexão com o banco de dados
        ConnectionFactory connectionFactory = new ConnectionFactory();

        try (Connection conexao = connectionFactory.criaConexao()) {
            // Prepara o comando SQL
            PreparedStatement preparoDoComando = conexao.prepareStatement(sql);

            // Executa a consulta e obtém o resultado
            ResultSet resultSet = preparoDoComando.executeQuery();

            // Cria uma lista para armazenar os objetos Cliente
            List<Client> lista = new ArrayList<>();

            while (resultSet.next()) {
                //Cria client e adiciona a lista
                lista.add(montaCliente(resultSet));
            }

            // Fecha os recursos (PreparedStatement e ResultSet)
            preparoDoComando.close();
            resultSet.close();

            // Retorna a lista de clientes
            return lista;

        } catch (SQLException e) {
            // Em caso de erro, lança uma exceção com uma mensagem descritiva
            throw new RuntimeException("Erro ao consultar todos os clientes", e);
        }
    }
}