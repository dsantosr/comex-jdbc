package com.comex.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Método para criar uma conexão com o banco de dados
    public Connection criaConexao() {

        // Informações de usuário e senha para acessar o banco de dados
        String usuario = "root";
        String senha = "root";

        try { // Tentativa de estabelecer uma conexão com o banco de dados MySQL
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/comex", usuario, senha);

        } catch (SQLException e) { // Se houver uma exceção ao tentar conectar, lança uma exceção com uma mensagem personalizada
            throw new RuntimeException("Erro ao tentar se conectar no banco de dados.");
        }
    }
}
