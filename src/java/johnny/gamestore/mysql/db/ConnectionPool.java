
package johnny.gamestore.mysql.db;


import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool {

	private ConnectionPool() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo", "root", "root");
	}

	public Connection getConnection() {
		try {
			DataSource con = null;
			return ((DataSource) con).getConnection();
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public void freeConnection(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
