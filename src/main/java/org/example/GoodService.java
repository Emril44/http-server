package org.example;

import java.sql.SQLException;

public class GoodService {
    private final GoodDAO goodDAO = new GoodDAO();

    public void createGood(Good good) throws SQLException {
        goodDAO.createGood(good);
    }

    public Good getGood(int id) throws SQLException {
        return goodDAO.getGood(id);
    }

    public void updateGood(Good good) throws SQLException {
        goodDAO.updateGood(good);
    }

    public void deleteGood(int id) throws SQLException {
        goodDAO.deleteGood(id);
    }
}
