package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.BandInfo;

public class BandInfoDao {
	// 引数card指定された項目で検索して、取得されたデータのリストを返す
	public List<BandInfo> select(BandInfo Band) {
		Connection conn = null;
		List<BandInfo> Bandlist = new ArrayList<BandInfo>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"root", "password");
			String sql = "SELECT id, name, user_id " + "FROM Band_info " + "WHERE name LIKE ? " + "AND (? = 0 OR user_id = ?) " + "ORDER BY id";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			if (Band.getName() != null) {
				pStmt.setString(1, "%" + Band.getName() + "%");
			} else {
				pStmt.setString(1, "%");
			}
			if (Band.getUser_id() != 0) {
				pStmt.setInt(2, Band.getUser_id());
				pStmt.setInt(3, Band.getUser_id());
			} else {
				pStmt.setInt(2, 0);
				pStmt.setInt(3, 0);
			}
			
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				BandInfo BandInfo = new BandInfo(rs.getInt("id"), rs.getString("name"),
						rs.getInt("user_id"));
				Bandlist.add(BandInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return Bandlist;
	}

// 引数cardで指定されたレコードを登録し、成功したらtrueを返す
public boolean insert(BandInfo Band) {
	Connection conn = null;
	boolean result = false;

	try {

		Class.forName("com.mysql.cj.jdbc.Driver");

		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
				"root", "password");

		String sql = "INSERT INTO Band_info VALUES (0, ?, ?)";
		PreparedStatement pStmt = conn.prepareStatement(sql);

		pStmt.setString(1, Band.getName());

		if (Band.getUser_id() != 0) {
			pStmt.setInt(2, Band.getUser_id());
		} else {
			pStmt.setNull(2, java.sql.Types.INTEGER);
		}
		
		int count = pStmt.executeUpdate();
		if (count == 1) {
			result = true;
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
		throw new RuntimeException(e);

	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	} finally {

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	return result;
	}

//引数cardで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(BandInfo Band) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"root", "password");

			// SQL文を準備する
			String sql = "UPDATE Band_info SET name=?, user_id=? " + "WHERE id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setString(1, Band.getName() != null ? Band.getName() : "");
			
			if (Band.getUser_id() != 0) {
				pStmt.setInt(2, Band.getUser_id());
			} else {
				pStmt.setNull(2, java.sql.Types.INTEGER);
			}
			pStmt.setInt(3, Band.getId());

			int count = pStmt.executeUpdate();
			if (count == 1) {
				result = true;
			}

			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}
	
	// 引数cardで指定された番号のレコードを削除し、成功したらtrueを返す
		public boolean delete(BandInfo Band) {
			Connection conn = null;
			boolean result = false;

			try {

				Class.forName("com.mysql.cj.jdbc.Driver");

				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
						"root", "password");

				String sql = "DELETE FROM Band_info WHERE id=?";
				PreparedStatement pStmt = conn.prepareStatement(sql);

				pStmt.setInt(1, Band.getId());

				if (pStmt.executeUpdate() == 1) {
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {

				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			return result;
		}
}