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
		db = plugin.config.getMySqlDatabaseName();
		address = plugin.config.getMySqlServerAddress();
		port = plugin.config.getMySqlServerPort();
		usr = plugin.config.getMySqlDatabaseUsername();
		pass = plugin.config.getMySqlDatabasePassword();
		connect();
		createTables();
	}
	
	private void createTables() throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS `item_mail` (`id` INT(16) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
				"`sender` VARCHAR(16) NOT NULL, `receiver` VARCHAR(16) NOT NULL, `type` VARCHAR(7) NOT NULL, " +
				"`item_id` INT(5) NOT NULL, `damage` INT(2) NOT NULL DEFAULT '0', `amount` INT(4) NOT NULL DEFAULT '1', " +
				"`time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, `read` INT(1) DEFAULT '0') ENGINE = MYISAM COMMENT = 'ItemMail storage table'";
		statement(sql);
	}
	
	private void connect() throws SQLException{
		conn = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + db , usr, pass);
	}
	
	private void checkConnection() throws SQLException{
		if(conn == null || !conn.isValid(5)){
			connect();
		}
	}
	
	public void statement(String sql) throws SQLException{
		checkConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.executeUpdate();
	}
	
	public ResultSet getResultSet(String sql) throws SQLException{
		checkConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		return stmt.executeQuery();
		
	}
}
