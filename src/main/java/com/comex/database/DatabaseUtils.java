package com.comex.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    public static Long recuperaIdGerado(PreparedStatement comando) {
        try {
            ResultSet rs = comando.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar o ID gerado.", e);
        }
    }
}
