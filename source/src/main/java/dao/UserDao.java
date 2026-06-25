package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.User;

public class UserDao {
	// 引数で指定されたidpwでログイン成功ならtrueを返す
	public User login(User users) {
		Connection conn = null;
		User resultUser = null;
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"c3", "zTfP4Ep4RMwQge3E");
			
			// SELECT文を準備する
			String sql = "SELECT user_id, password, type FROM users WHERE user_id=? AND password=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, users.getUser_id());
			pStmt.setString(2, users.getPassword());
			
			// SELECT文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			
			// ユーザーIDとパスワードが一致するユーザーがいれば結果をtrueにする
			if (rs.next()) {
				resultUser = new User();
				resultUser.setUser_id(rs.getString("user_id"));
				resultUser.setPassword(rs.getString("password"));
				resultUser.setType(rs.getInt("type"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultUser;
	}
	
	// 新規登録
	public boolean register(User user) {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?"
							+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"c3", "zTfP4Ep4RMwQge3E");
			
			// 同じIDが存在するか確認
			String checkSql = "SELECT user_id FROM users WHERE user_id = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setString(1, user.getUser_id());
			
			ResultSet rs = checkStmt.executeQuery();
			
			if (rs.next()) {
				// 既に存在するID
				return false;
			}
			
			// 登録
			String insertSql = "INSERT INTO users(user_id, password, type) VALUES(?, ?, ?)";
			
			PreparedStatement insertStmt = conn.prepareStatement(insertSql);
			
			insertStmt.setString(1, user.getUser_id());
			insertStmt.setString(2, user.getPassword());
			insertStmt.setInt(3, user.getType());
			
			int count = insertStmt.executeUpdate();
			
			return count == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String select(String name) {
		String id = "";
		
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?"
							+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"c3", "zTfP4Ep4RMwQge3E");
			
			// 同じIDが存在するか確認
			String checkSql = "SELECT id FROM users WHERE user_id = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setString(1, name);
			
			ResultSet rs = checkStmt.executeQuery();
			
			if (rs.next()) {
				id = rs.getString("id");
			}
						
		} catch (Exception e) {
			e.printStackTrace();
			return "";
			
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return id;
	}
	
}
