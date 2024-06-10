package com.comex.dao;

import com.comex.database.ConnectionFactory;
import com.comex.database.DatabaseUtils;
import com.comex.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public void create(Category categoria) {
        String sql = "insert into categoria (nome) values (?)";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, categoria.getName());

            comando.execute();

            Long idGerado = DatabaseUtils.recuperaIdGerado(comando);
            categoria.setId(idGerado);

            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar categoria.", e);
        }
    }

    public Category searchByID(long id) {
        String sql = "select * from categoria where id = ?";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection conexao = connectionFactory.criaConexao()) {
            PreparedStatement preparoDoComando = conexao.prepareStatement(sql);
            preparoDoComando.setLong(1, id);

            ResultSet resultSet = preparoDoComando.executeQuery();

            Category possivelCategoria = null;
            if (resultSet.next()) {
                possivelCategoria = montaCategoria(resultSet);
            }

            preparoDoComando.close();
            resultSet.close();

            return possivelCategoria;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar categoria por ID", e);
        }
    }

    public void update(Category categoria) {
        String sql = "update categoria set nome = ? where id = ?";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, categoria.getName());
            comando.setLong(2, categoria.getId());

            comando.execute();
            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria.", e);
        }
    }

    public void delete(Category categoria) {
        String sql = "delete from categoria where id = ?";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, categoria.getId());

            comando.execute();
            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir categoria.", e);
        }
    }

    public List<Category> listAll() {
        String sql = "select * from categoria";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection conexao = connectionFactory.criaConexao()) {
            PreparedStatement preparoDoComando = conexao.prepareStatement(sql);
            ResultSet resultSet = preparoDoComando.executeQuery();

            List<Category> lista = new ArrayList<>();
            while (resultSet.next()) {
                lista.add(montaCategoria(resultSet));
            }

            preparoDoComando.close();
            resultSet.close();

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar todas as categorias", e);
        }
    }

    private Category montaCategoria(ResultSet resultSet) throws SQLException {
        Category categoria = new Category();
        categoria.setId(resultSet.getLong("id"));
        categoria.setName(resultSet.getString("nome"));

        return categoria;
    }

}