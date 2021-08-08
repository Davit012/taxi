package manager;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import models.Cars;
import models.Orders;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OrderManager {

    Connection connection = db.DBConnectionProvider.getProvider().getConnection();


    public boolean addOrder(Orders orders, int id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeSdf = new SimpleDateFormat("hh:mm");
        String date = sdf.format(orders.getDate());
        String time = timeSdf.format(orders.getTime());
        CarsManager carsManager = new CarsManager();
        try {
            // order -y mysql i hamar keyword bar e, urish anun dir, orinak orders
            //date-n el, time-n el stexic string sarqe Simple Date Format-ov nor uxarke.
            String query = "INSERT INTO `orders` (`username`,`email`,`phone_number`,`dest_from`,`dest_to`,`date`,`time`,`driver_id`) " +
                    "VALUES(?,?,?,?,?,?,?,?);";
            PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, orders.getUsername());
            pStatement.setString(2, orders.getEmail());
            pStatement.setString(3, orders.getPhoneNumber());
            pStatement.setString(4, orders.getDestFrom());
            pStatement.setString(5, orders.getDestTo());
            pStatement.setString(6, date);
            pStatement.setString(7, time);
            pStatement.setInt(8, id);
            carsManager.ordersCounting(id);
            pStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public void checkDriver(int id, Orders order) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        CarsManager carsManager = new CarsManager();
        String query = "SELECT * FROM orders WHERE driver_id = " + id;

        try {
            Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String day = resultSet.getString(7);
            String hour =resultSet.getString(8);
            String orderDate = String.valueOf(order.getDate());
            String orderTime = String.valueOf(order.getTime());

            if (!day.equals(orderDate) && !hour.equals(orderTime)) {
                addOrder(order,id);
            } else {
                carsManager.orderingCarAfterFailed(id, order);
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

