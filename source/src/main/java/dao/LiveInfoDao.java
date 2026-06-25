package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dto.LiveInfo;

public class LiveInfoDao {
	// 引数card指定された項目で検索して、取得されたデータのリストを返す
	public List<LiveInfo> select(LiveInfo live) {
		Connection conn = null;
		List<LiveInfo> livelist = new ArrayList<LiveInfo>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"c3", "zTfP4Ep4RMwQge3E");
			String sql = "SELECT id, name, begin_date, end_date, user_id , create_flag " + "FROM live_info "
					+ "WHERE name LIKE ? " + "AND (? IS NULL OR begin_date = ?) " + "AND (? IS NULL OR end_date = ?) "
					+ "AND (? = 0 OR user_id = ?) " + "ORDER BY id";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			if (live.getName() != null) {
				pStmt.setString(1, "%" + live.getName() + "%");
			} else {
				pStmt.setString(1, "%");
			}
			if (live.getBegin_date() != null) {
				java.sql.Timestamp date = java.sql.Timestamp.valueOf(live.getBegin_date());
				pStmt.setTimestamp(2, date);
				pStmt.setTimestamp(3, date);
			} else {
				pStmt.setNull(2, java.sql.Types.TIMESTAMP);
				pStmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			if (live.getEnd_date() != null) {
				java.sql.Timestamp date = java.sql.Timestamp.valueOf(live.getEnd_date());
				pStmt.setTimestamp(4, date);
				pStmt.setTimestamp(5, date);
			} else {
				pStmt.setNull(4, java.sql.Types.TIMESTAMP);
				pStmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			if (live.getUser_id() != 0) {
				pStmt.setInt(6, live.getUser_id());
				pStmt.setInt(7, live.getUser_id());
			} else {
				pStmt.setInt(6, 0);
				pStmt.setInt(7, 0);
			}
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getInt("create_flag") == 1);
				boolean createFlag = false;
				if (rs.getInt("create_flag")== 1) {
					createFlag = true;
				}
				LiveInfo liveInfo = new LiveInfo(rs.getInt("id"), rs.getString("name"),
						rs.getTimestamp("begin_date").toLocalDateTime(), rs.getTimestamp("end_date").toLocalDateTime(),
						rs.getInt("user_id"), createFlag);
				livelist.add(liveInfo);
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

		return livelist;
	}

	// 引数cardで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(LiveInfo live) {
		Connection conn = null;
		boolean result = false;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"c3", "zTfP4Ep4RMwQge3E");

			String sql = "INSERT INTO live_info VALUES (0, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, live.getName());

			if (live.getBegin_date() != null) {
				pStmt.setTimestamp(2, java.sql.Timestamp.valueOf(live.getBegin_date()));
			} else {
				pStmt.setTimestamp(2, null);
			}

			if (live.getEnd_date() != null) {
				pStmt.setTimestamp(3, java.sql.Timestamp.valueOf(live.getEnd_date()));
			} else {
				pStmt.setTimestamp(3, null);
			}
			if (live.getUser_id() != 0) {
				pStmt.setInt(4, live.getUser_id());
			} else {
				pStmt.setNull(4, java.sql.Types.INTEGER);
			}
			if (!Objects.isNull(live.isCreate_flag())) {
				pStmt.setBoolean(5, live.isCreate_flag());
			} else {
				pStmt.setNull(5, java.sql.Types.BOOLEAN);
			}
			
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

	// 引数cardで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(LiveInfo live) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"c3", "zTfP4Ep4RMwQge3E");

			// SQL文を準備する
			String sql = "UPDATE live_info SET name=?, begin_date=?, end_date=?, user_id=?, create_flag=? " + "WHERE id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setString(1, live.getName() != null ? live.getName() : "");
			if (live.getBegin_date() != null) {
				pStmt.setTimestamp(2, java.sql.Timestamp.valueOf(live.getBegin_date()));
			} else {
				pStmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			if (live.getEnd_date() != null) {
				pStmt.setTimestamp(3, java.sql.Timestamp.valueOf(live.getEnd_date()));
			} else {
				pStmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			if (live.getUser_id() != 0) {
				pStmt.setInt(4, live.getUser_id());
			} else {
				pStmt.setNull(4, java.sql.Types.INTEGER);
			}
			
			pStmt.setBoolean(5, live.isCreate_flag());
			pStmt.setInt(6, live.getId());
			

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
	public boolean delete(LiveInfo live) {
		Connection conn = null;
		boolean result = false;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"c3", "zTfP4Ep4RMwQge3E");

			String sql = "DELETE FROM live_info WHERE id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, live.getId());

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

	// ライブ情報の一覧を返す
	public List<LiveInfo> selectByUserId(int userId) {

		Connection conn = null;
		List<LiveInfo> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"c3", "zTfP4Ep4RMwQge3E");

			String sql = "SELECT * FROM live_info WHERE user_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, userId);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				LiveInfo live = new LiveInfo(rs.getInt("id"), rs.getString("name"),
						rs.getTimestamp("begin_date").toLocalDateTime(), rs.getTimestamp("end_date").toLocalDateTime(),
						rs.getInt("user_id"), rs.getInt("create_flag") == 1);

				list.add(live);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	//idでデータをとる
	public LiveInfo select(int id) {
		Connection conn = null;
		LiveInfo data = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"c3", "zTfP4Ep4RMwQge3E");
			String sql = "SELECT id, name, begin_date, end_date, user_id , create_flag " + "FROM live_info "
					+ "WHERE id = ? " 
					+ "ORDER BY id";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, id);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				data = new LiveInfo(rs.getInt("id"), rs.getString("name"),
						rs.getTimestamp("begin_date").toLocalDateTime(), rs.getTimestamp("end_date").toLocalDateTime(),
						rs.getInt("user_id"), rs.getInt("create_flag") == 1);
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

		return data;
	}
}
