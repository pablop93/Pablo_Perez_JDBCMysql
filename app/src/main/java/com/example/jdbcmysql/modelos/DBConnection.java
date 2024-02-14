package com.example.jdbcmysql.modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnection {
    private static final String URL = "jdbc:mysql://192.168.100.11:3306/jdbc_mysql";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public ArrayList<Usuario> obtenerUsuarios() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("email"),
                        resultSet.getString("telefono"),
                        resultSet.getString("pass")
                );
                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        Usuario usuario = null;
        String query = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = new Usuario(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("email"),
                            resultSet.getString("telefono"),
                            resultSet.getString("pass")
                    );
                }
            }
        }

        return usuario;
    }

    public void editarUsuario(String id, String nombre, String email, String telefono, String pass) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, email = ?, telefono = ?, pass = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, email);
            statement.setString(3, telefono);
            statement.setString(4, pass);
            statement.setString(5, id);
            statement.executeUpdate();
        }
    }

    public void borrarUsuario(String id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        }
    }

    public void insertarUsuario(String nombre, String email, String telefono, String pass) throws SQLException {
        String query = "INSERT INTO usuarios (nombre, email, telefono, pass) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, email);
            statement.setString(3, telefono);
            statement.setString(4, pass);
            statement.executeUpdate();
        }
    }
}
