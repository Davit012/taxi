package manager;
import models.Orders;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class CarsManager {
    Connection connection = db.DBConnectionProvider.getProvider().getConnection();


    public void orderingCar(Orders order) {
        OrderManager orderManager = new OrderManager();
        String sql = "SELECT * from cars";
        int minOrdersDriver = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            boolean result = false;
            while (resultSet.next()) {
                int orderCount = resultSet.getInt(4);
                if (orderCount == 0) {
                    orderManager.addOrder(order,resultSet.getInt(1));

                    return;
                }
            }
                orderingCarByMinOrders(order);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void orderingCarByMinOrders(Orders order){
        OrderManager orderManager = new OrderManager();
        String sql = "SELECT * from cars";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int minOrdersDriver = 0;
            int minOrders = 10;
            while (resultSet.next()) {
                if (minOrders > resultSet.getInt(4)) {
                    minOrders = resultSet.getInt(4);
                    minOrdersDriver = resultSet.getInt(1);
                }
            }
            orderManager.checkDriver(minOrdersDriver, order);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public void orderingCarAfterFailed(int id, Orders order) {
        OrderManager orderManager = new OrderManager();
        String sql = "SELECT * from cars WHERE id != " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int minOrdersDriver = 0;
            int minOrders = 10;
            while (resultSet.next()) {
                    if (minOrders > resultSet.getInt(4)) {
                        minOrders = resultSet.getInt(4);
                        minOrdersDriver = resultSet.getInt(1);
                }
            }
            orderManager.checkDriver(minOrdersDriver, order);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void ordersCounting(int id)  {
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE cars SET orders_count = orders_count + 1 WHERE id = " + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
