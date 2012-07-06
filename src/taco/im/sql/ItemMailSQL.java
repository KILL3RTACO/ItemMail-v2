package taco.im.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import taco.im.ItemMail;

public class ItemMailSQL {

	private Connection conn = null;
	private ItemMail plugin = null;
	private String db, address, usr, pass;
	private int port;
	
	public ItemMailSQL(ItemMail instance) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		plugin = instance;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		db = plugin.config.getMySQLDatabaseName();
		address = plugin.config.getMySQLServerAddress();
		usr = plugin.config.getMySQLDatabaseUsername();
		pass = plugin.config.getMySQLDatabasePassword();
		port = plugin.config.getMySQLServerPort();
		connect();
		createTables();
	}
	
	private void connect() throws SQLException{
		conn = DriverManager.getConnection("jdbc:mysql://" + address+ ":" + port + "/" + db, usr, pass);
	}
	
	private void createTables() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS `item_mail` (`id` INT(16) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
				"`sender` VARACHAR(16) NOT NULL, `receiver` VARCHAR(16) NOT NULL, `material_id` INT(4) NOT NULL, " +
				"`amount` INT(4) NOT NULL)";
		statement(sql);
	}
	
	private void ensureConnection(){
		try {
			if(conn == null || !conn.isValid(5)){
				connect();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getResultSet(String sql) throws SQLException{
		ensureConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		return stmt.executeQuery(sql);
	}
	
	private void statement(String sql) throws SQLException{
		ensureConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.executeUpdate();
	}
	
}
