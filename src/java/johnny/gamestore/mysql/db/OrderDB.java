
package johnny.gamestore.mysql.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import johnny.gamestore.mysql.beans.Order;
import johnny.gamestore.mysql.beans.OrderItem;


public class OrderDB {
    public static int insert(Order order) {
        
        Connection con = null;
        ConnectionPool pool = null;
        PreparedStatement ps = null;
        int generatedkey = 0;

        String query
                = "INSERT INTO SalesOrder (UserName, Address, CreditCard, ConfirmationNumber, DeliveryDate) "
                + "VALUES (?, ?, ?, ?, ?)";
        String query2
                = "INSERT INTO OrderItem (OrderId, ProductId, ProductName, ProductType, Price, Image, Maker, Discount, Quantity) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
        	con.setAutoCommit(false); //transaction block start
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getUserName());
            ps.setString(2, order.getAddress());
            ps.setString(3, order.getCreditCard());
            ps.setString(4, order.getConfirmationNumber());
            ps.setDate(5, new java.sql.Date(order.getDeliveryDate().getTime()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
               generatedkey=rs.getInt(1);
            }
            
            if (generatedkey > 0) {
                for(int i = 0; i < order.getItems().size(); i++) {
                    OrderItem item = order.getItems().get(i);
                    ps = con.prepareStatement(query2);
                    ps.setInt(1, generatedkey);
                    ps.setString(2, item.getProductId());
                    ps.setString(3, item.getProductName());
                    ps.setInt(4, item.getProductType());
                    ps.setDouble(5, item.getPrice());
                    ps.setString(6, item.getImage());
                    ps.setString(7, item.getMaker());
                    ps.setDouble(8, item.getDiscount());
                    ps.setInt(9, item.getQuantity());
                    ps.executeUpdate();
                }
            } else {
            	con.rollback();
                return 0;
            }            
            con.commit(); //transaction block end
            return generatedkey;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
           
			pool.freeConnection(con);
        }
    }

    public static int update(Order order) {
        Connection con = null;
        ConnectionPool pool = null;
        PreparedStatement ps = null;

        String query = "UPDATE SalesOrder SET "
                + "Address = ?, "
                + "CreditCard = ?, "
                + "ConfirmationNumber = ?, "
                + "DeliveryDate = ? "
                + "WHERE OrderId = ?";
        String query2 = "DELETE FROM OrderItem "
                + "WHERE OrderId = ?";
        String query3
                = "INSERT INTO OrderItem (OrderId, ProductId, ProductName, ProductType, Price, Image, Maker, Discount, Quantity) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con.setAutoCommit(false); //transaction block start
            ps = con.prepareStatement(query);
            ps.setString(1, order.getAddress());
            ps.setString(2, order.getCreditCard());
            ps.setString(3, order.getConfirmationNumber());
            ps.setDate(4, new java.sql.Date(order.getDeliveryDate().getTime()));
            ps.setInt(5, order.getId());
            ps.executeUpdate();
            
            ps = con.prepareStatement(query2);
            ps.setInt(1, order.getId());
            ps.executeUpdate();
            
            for(int i = 0; i < order.getItems().size(); i++) {
                OrderItem item = order.getItems().get(i);
                ps = con.prepareStatement(query3);
                ps.setInt(1, order.getId());
                ps.setString(2, item.getProductId());
                ps.setString(3, item.getProductName());
                ps.setInt(4, item.getProductType());
                ps.setDouble(5, item.getPrice());
                ps.setString(6, item.getImage());
                ps.setString(7, item.getMaker());
                ps.setDouble(8, item.getDiscount());
                ps.setInt(9, item.getQuantity());
                ps.executeUpdate();
            } 
            con.commit(); //transaction block end
            return 1;            
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
    }

    public static int delete(int orderid) {
        Connection conn =null;
        ConnectionPool pool = null;
        PreparedStatement ps = null;

        String query = "DELETE FROM SalesOrder "
                + "WHERE OrderId = ?";
        String query2 = "DELETE FROM OrderItem "
                + "WHERE OrderId = ?";
        try {
        	conn.setAutoCommit(false); //transaction block start
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderid);
            ps.executeUpdate();
            
            ps = conn.prepareStatement(query2);
            ps.setInt(1, orderid);
            ps.executeUpdate();
            conn.commit(); //transaction block end
            return 1;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(conn);
        }
    }

    public static boolean exists(int orderid) {
        ConnectionPool pool =null;
        Connection conn =null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT OrderId FROM SalesOrder "
                + "WHERE OrderId = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderid);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(conn);
        }
    }
    
    public static List<Order> getList() {
        return getList("");
    }
    public static List<Order> getList(String username) {
        ConnectionPool pool = null;
        Connection conn =null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> list = new ArrayList();
        
        String query = "SELECT * FROM SalesOrder ";
        if (!username.isEmpty()) {
            query += "WHERE UserName = ?";
        }
        String query2 = "SELECT * FROM OrderItem "
                + "WHERE OrderId = ?";
        try {
            ps = conn.prepareStatement(query);
            if (!username.isEmpty()) {
                ps.setString(1, username);
            }
            rs = ps.executeQuery();            
            Order order = null;
            while (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("OrderId"));
                order.setUserName(rs.getString("UserName"));
                order.setAddress(rs.getString("Address"));
                order.setCreditCard(rs.getString("CreditCard"));
                order.setConfirmationNumber(rs.getString("ConfirmationNumber"));
                order.setDeliveryDate(rs.getDate("DeliveryDate"));
                list.add(order);
            }
            for(int i = 0; i < list.size(); i++) {
                List<OrderItem> items = new ArrayList();
                ps = conn.prepareStatement(query2);
                ps.setInt(1, list.get(i).getId());
                rs = ps.executeQuery();
                OrderItem orderitem = null;
                while (rs.next()) {
                    orderitem = new OrderItem();
                    orderitem.setOrderItemId(rs.getInt("OrderItemId"));
                    orderitem.setOrderItemId(rs.getInt("OrderId"));
                    orderitem.setProductId(rs.getString("ProductId"));
                    orderitem.setProductName(rs.getString("ProductName"));
                    orderitem.setProductType(rs.getInt("ProductType"));
                    orderitem.setPrice(rs.getDouble("Price"));
                    orderitem.setImage(rs.getString("Image"));
                    orderitem.setMaker(rs.getString("Maker"));
                    orderitem.setDiscount(rs.getInt("Discount"));
                    orderitem.setQuantity(rs.getInt("Quantity"));
                    items.add(orderitem);
                }
                list.get(i).setItems(items);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(conn);
        }
    }
    
    public static Order getOrder(int id) {
        ConnectionPool pool = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM SalesOrder "
                + "WHERE OrderId = ?";
        String query2 = "SELECT * FROM OrderItem "
                + "WHERE OrderId = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Order order = null;
            while (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("OrderId"));
                order.setUserName(rs.getString("UserName"));
                order.setAddress(rs.getString("Address"));
                order.setCreditCard(rs.getString("CreditCard"));
                order.setConfirmationNumber(rs.getString("ConfirmationNumber"));
                order.setDeliveryDate(rs.getDate("DeliveryDate"));
            }
            
            List<OrderItem> list = new ArrayList();
            ps = conn.prepareStatement(query2);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            OrderItem orderitem = null;
            while (rs.next()) {
                orderitem = new OrderItem();
                orderitem.setOrderItemId(rs.getInt("OrderItemId"));
                orderitem.setOrderId(rs.getInt("OrderId"));
                orderitem.setProductId(rs.getString("ProductId"));
                orderitem.setProductName(rs.getString("ProductName"));
                orderitem.setProductType(rs.getInt("ProductType"));
                orderitem.setPrice(rs.getDouble("Price"));
                orderitem.setImage(rs.getString("Image"));
                orderitem.setMaker(rs.getString("Maker"));
                orderitem.setDiscount(rs.getInt("Discount"));
                orderitem.setQuantity(rs.getInt("Quantity"));
                list.add(orderitem);
            }
            order.setItems(list);
            return order;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(conn);
        }
    }
    
    public static int setItemQuantity(int orderid, int itemid, int quantity) {       
        ConnectionPool pool = null;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "UPDATE SalesOrder SET "
                + "Quantity = ? "
                + "WHERE OrderId = ? "
                + "AND OrderItemId = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setInt(2, orderid);
            ps.setInt(3, itemid);

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(conn);
        }
    }
}
