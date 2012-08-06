package taco.im.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import taco.im.ItemMail;

public class BlacklistTable {

	public boolean getIsBlacklisted(int id, int damage){
		try {
			String sql = "SELECT * FROM `im_blacklist` WHERE `item_id`='" + id + "' AND `damage`='" + damage + "`";
			ResultSet rs = ItemMail.db.getResultSet(sql);
			if(rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
