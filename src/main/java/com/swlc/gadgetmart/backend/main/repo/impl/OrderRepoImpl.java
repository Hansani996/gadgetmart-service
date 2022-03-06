package com.swlc.gadgetmart.backend.main.repo.impl;



import com.sun.org.apache.xpath.internal.operations.Or;
import com.swlc.gadgetmart.backend.main.database.DBConnection;
import com.swlc.gadgetmart.backend.main.dto.OrderDetailDto;
import com.swlc.gadgetmart.backend.main.dto.OrderDto;
import com.swlc.gadgetmart.backend.main.repo.OrderRepo;
import org.apache.commons.dbutils.DbUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepoImpl implements OrderRepo {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static ResultSet resultSet1;

    @Override
    public boolean placeOrder(OrderDto orderDTO) throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "INSERT INTO orders (user_id, address, contact,totalCost) VALUES (?,?,?,?)";
        connection.setAutoCommit(false);

        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setInt(1, orderDTO.getUserId());
        preparedStatement.setString(2, orderDTO.getAddress());
        preparedStatement.setString(3, orderDTO.getContact());
        preparedStatement.setDouble(4, orderDTO.getTotalCost());
        int i = preparedStatement.executeUpdate();
        if (i > 0){
            preparedStatement = connection.prepareStatement("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int k = 0;
                orderDTO.setOrderId(resultSet.getInt(1));
                List<OrderDetailDto> orderDetails = orderDTO.getOrderDetail();
                for (OrderDetailDto orderDetail : orderDetails) {
                    preparedStatement = connection.prepareStatement("INSERT INTO order_detail (order_id,name," +
                            "description,image,price,deliveryCost,brand,category,discount,shop,soldOut,warranty,qty) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ");

                    preparedStatement.setInt(1, orderDTO.getOrderId());
                    preparedStatement.setString(2, orderDetail.getName());
                    preparedStatement.setString(3, orderDetail.getDescription());
                    preparedStatement.setString(4, orderDetail.getImage());
                    preparedStatement.setDouble(5, orderDetail.getPrice());
                    preparedStatement.setDouble(6, orderDetail.getDeliveryCost());
                    preparedStatement.setString(7, orderDetail.getBrand());
                    preparedStatement.setString(8, orderDetail.getCategory());
                    preparedStatement.setInt(9, orderDetail.getDiscount());
                    preparedStatement.setString(10, orderDetail.getShop());
                    preparedStatement.setBoolean(11, orderDetail.isSoldOut());
                    preparedStatement.setString(12, orderDetail.getWarranty());
                    preparedStatement.setInt(13, orderDetail.getQty());
                    k = preparedStatement.executeUpdate();
                }
                connection.commit();
                closeConnection();
                return k > 0;
            } else {
                closeConnection();
                return false;
            }
        } else {
            closeConnection();
            return false;
        }
    }

    @Override
    public boolean updateOrder(OrderDto orderDTO) throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "update orders set status=? where order_id=?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, orderDTO.getStatus());
        preparedStatement.setInt(2, orderDTO.getOrderId());
        int i = preparedStatement.executeUpdate();
        closeConnection();
        return i > 0;
    }

    @Override
    public List<OrderDto> getAllOrder(String userId) throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select * from orders where user_id=? ";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, userId);
        resultSet = preparedStatement.executeQuery();

        ArrayList<OrderDto> orders = getOrders(false);
        closeConnection();
        return orders;
    }

    @Override
    public List<OrderDto> getAllOrder() throws Exception {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select o.order_id,u.name,o.address,o.contact,o.paymentMethod,o.totalCost,o.status from orders o, " +
                "user_login u where o.user_id = u.user_id order by order_id desc ";
        preparedStatement = connection.prepareStatement(SQL);
        resultSet = preparedStatement.executeQuery();

        ArrayList<OrderDto> orders = getOrders(true);
        closeConnection();
        return orders;
    }

    public ArrayList<OrderDto> getOrders(boolean status) throws Exception{
        ArrayList<OrderDto> orders = new ArrayList<>();
        while (resultSet.next()) {

            String SQL2 = "select * from order_detail where order_detail.order_id = ?";
            preparedStatement = connection.prepareStatement(SQL2);
            preparedStatement.setInt(1, resultSet.getInt(1));
            resultSet1 = preparedStatement.executeQuery();

            List<OrderDetailDto> orderDetails = new ArrayList<>();
            while (resultSet1.next()) {
                OrderDetailDto orderDetail = new OrderDetailDto();
                orderDetail.setOrderDetailId(resultSet1.getString(1));
                orderDetail.setOrderId(resultSet1.getString(2));
                orderDetail.setName(resultSet1.getString(3));
                orderDetail.setDescription(resultSet1.getString(4));
                orderDetail.setImage(resultSet1.getString(5));
                orderDetail.setPrice(resultSet1.getDouble(6));
                orderDetail.setDeliveryCost(resultSet1.getDouble(7));
                orderDetail.setBrand(resultSet1.getString(8));
                orderDetail.setCategory(resultSet1.getString(9));
                orderDetail.setDiscount(resultSet1.getInt(10));
                orderDetail.setShop(resultSet1.getString(11));
                orderDetail.setSoldOut(resultSet1.getBoolean(12));
                orderDetail.setWarranty(resultSet1.getString(13));
                orderDetail.setQty(resultSet1.getInt(14));
                orderDetails.add(orderDetail);
            }

            OrderDto order = new OrderDto();
            order.setOrderId(resultSet.getInt(1));
            if (status){
                order.setName(resultSet.getString(2));
            } else {
                order.setUserId(resultSet.getInt(2));
            }
            order.setAddress(resultSet.getString(3));
            order.setContact(resultSet.getString(4));
            order.setTotalCost(resultSet.getDouble(6));
            order.setStatus(resultSet.getString(7));
            order.setOrderDetail(orderDetails);
            orders.add(order);
        }
        return orders;
    }

    private void closeConnection() {
        try {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(resultSet1);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.close(connection);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
