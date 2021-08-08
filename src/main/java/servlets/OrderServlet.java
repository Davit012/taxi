package servlets;

import lombok.SneakyThrows;
import manager.CarsManager;
import manager.OrderManager;
import models.Orders;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

@WebServlet(urlPatterns = "/orders")
public class OrderServlet extends HttpServlet {
    CarsManager carsManager = new CarsManager();


    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        Date date = sdf.parse(req.getParameter("date"));
        Date time = format.parse(req.getParameter("time"));
        Orders order = Orders.builder()
                .username(name)
                .email(email)
                .phoneNumber(phone)
                .destFrom(from)
                .destTo(to)
                .date(date)
                .time(time)
                .build();
            carsManager.orderingCar(order);
            resp.sendRedirect("index.jsp");
            req.getSession().removeAttribute("msg");
    }
}
