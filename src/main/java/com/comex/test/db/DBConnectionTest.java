package com.comex.test.db;

// Importa a classe Connection e SQLException do pacote java.sql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {
    public static void main(String[] args) {
        // Dados da conexao.
        String addr = "localhost";
        String port = "3306";
        String username = "root";
        String password = "root";
        String dbName = "comex";

        try {
            // Tenta estabelecer uma conexão com o banco de dados MySQL.
            Connection con = DriverManager.getConnection("jdbc:mysql://"+ addr+":"+port+"/"+dbName+"?user="+username+"&password="+password);

            // Se a conexão for estabelecida com sucesso, imprime a mensagem de confirmação.
            System.out.println("Connection established");

            // Fecha a conexão com o banco de dados para liberar recursos.
            con.close();

        } catch (SQLException e) {
            // Caso ocorra uma exceção SQL, lança uma nova RuntimeException com a exceção original como causa.
            throw new RuntimeException(e);
        }
    }
}