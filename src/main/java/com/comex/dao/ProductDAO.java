package com.comex.dao;

import com.comex.database.ConnectionFactory;
import com.comex.database.DatabaseUtils;
import com.comex.model.Category;
import com.comex.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void create(Product produto) {
        String sql = "insert into produto (nome, descricao, preco) values (?, ?, ?)";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, produto.getName());
            comando.setString(2, produto.getDescription());
            comando.setDouble(3, produto.getPrice());

            comando.execute();
            Long idGerado = DatabaseUtils.recuperaIdGerado(comando);
            produto.setId(idGerado);

            insereCategoriasProduto(produto);

            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto.", e);
        }
    }

    public Product read(long id) {
        String sql = "select p.*, c.* from produto p " +
                     "left join categoria_produto cp on p.id = cp.produto_id " +
                     "left join categoria c on cp.categoria_id = c.id " +
                     "where p.id = ?";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, id);
            ResultSet resultSet = comando.executeQuery();

            Product produto = null;

            while (resultSet.next()) {
                if (produto == null) {
                    produto = new Product();
                    produto.setId(resultSet.getLong("p.id"));
                    produto.setName(resultSet.getString("p.nome"));
                    produto.setDescription(resultSet.getString("p.descricao"));
                    produto.setPrice(resultSet.getDouble("p.preco"));
                }

                Long categoriaId = resultSet.getLong("c.id");
                if (!resultSet.wasNull()) {
                    Category categoria = new Category();
                    categoria.setId(categoriaId);
                    categoria.setName(resultSet.getString("c.nome"));

                    produto.adicionaCategoria(categoria);
                }
            }

            comando.close();
            return produto;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar produto.", e);
        }
    }

    public void update(Product produto) {
        String sql = "update produto set nome = ?, descricao = ?, preco = ? where id = ?";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, produto.getName());
            comando.setString(2, produto.getDescription());
            comando.setDouble(3, produto.getPrice());
            comando.setLong(4, produto.getId());

            comando.execute();

            // Atualiza as categorias associadas ao produto
            excluiCategoriasProduto(produto);
            insereCategoriasProduto(produto);

            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto.", e);
        }
    }

    public void delete(Product produto) {
        String sql = "delete from produto where id = ?";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            // Exclui as categorias associadas ao produto
            excluiCategoriasProduto(produto);

            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, produto.getId());

            comando.execute();
            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto.", e);
        }
    }

    public List<Product> listAll() {
        String sql = "select p.*, c.* from produto p " +
                     "left join categoria_produto cp on p.id = cp.produto_id " +
                     "left join categoria c on cp.categoria_id = c.id " +
                     "order by p.id";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultSet = comando.executeQuery();

            List<Product> produtos = new ArrayList<>();
            Product produto = null;

            while (resultSet.next()) {
                Long produtoId = resultSet.getLong("p.id");

                if (produto == null || !produto.getId().equals(produtoId)) {
                    produto = new Product();
                    produto.setId(produtoId);
                    produto.setName(resultSet.getString("p.nome"));
                    produto.setDescription(resultSet.getString("p.descricao"));
                    produto.setPrice(resultSet.getDouble("p.preco"));

                    produtos.add(produto);
                }

                Long categoriaId = resultSet.getLong("c.id");
                if (!resultSet.wasNull()) {
                    Category categoria = new Category();
                    categoria.setId(categoriaId);
                    categoria.setName(resultSet.getString("c.nome"));

                    produto.adicionaCategoria(categoria);
                }
            }

            comando.close();
            return produtos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os produtos.", e);
        }
    }

    private void excluiCategoriasProduto(Product produto) {
        String sql = "delete from categoria_produto where produto_id = ?";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, produto.getId());

            comando.execute();
            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir categorias do produto.", e);
        }
    }

    private void insereCategoriasProduto(Product produto) {
        String sql = "insert into categoria_produto (produto_id, categoria_id) values (?, ?)";

        try (Connection conexao = new ConnectionFactory().criaConexao()) {
            PreparedStatement comando = conexao.prepareStatement(sql);

            for (Category categoria : produto.getCategories()) {
                comando.setLong(1, produto.getId());
                comando.setLong(2, categoria.getId());
                comando.execute();
            }

            comando.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar categorias do produto.", e);
        }
    }

}