package org.example;

import org.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodDAO {
    public void createGood(Good Good) throws SQLException {
        String query = "INSERT INTO products (name, description, price) VALUES (?, ?, ?)";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, Good.getName());
            statement.setString(2, Good.getDescription());
            statement.setDouble(5, Good.getPrice());
            statement.executeUpdate();
        }
    }

    public Good getGood(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if(res.next()) {
                return new Good(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("description"),
                        res.getDouble("price")
                        );
            }
        }

        return null;
    }

    public void updateGood(Good Good) throws SQLException {
        String query = "UPDATE products SET name = ?, description = ?, price = ? WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, Good.getName());
            statement.setString(2, Good.getDescription());
            statement.setDouble(5, Good.getPrice());
            statement.setInt(6, Good.getId());
            statement.executeUpdate();
        }
    }

    public void deleteGood(int id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";
        try(Connection con = DBUtil.getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
