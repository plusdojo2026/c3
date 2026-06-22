package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.BandInfo;
import dto.BandMember;

public class BandMemberDao {

	// バンド情報Idを元にそのバンドメンバーの一覧を返す
	public List<BandMember> showMember(BandInfo bi) {
		List<BandMember> bmList = new ArrayList<BandMember>();
		Connection conn = null;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// バンドメンバーのid、名前、楽器ID、バンド情報ID、楽器名を取得するSQL文を作成する
			String sql = "SELECT band_member.id, band_member.name, band_member.part_id, band_member.band_info_id, parts.name FROM band_member\r\n"
					+ "LEFT JOIN parts ON band_member.part_id = parts.id\r\n" + "WHERE band_member.band_info_id = ?;";

			// 値を設定する。
			PreparedStatement pStmt = conn.prepareStatement(sql);
			 pStmt.setInt(1, bi.getId()); //BandInfoDTO作成待ち

			// SELECT文を実行し結果を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果をコレクションにコピー
			while (rs.next()) {
				BandMember bm;
				// リストへコピー
				bm = new BandMember(rs.getInt("band_member.id"), rs.getString("band_member.name"),
						rs.getInt("band_member.part_id"), rs.getInt("band_member.band_info_id"),
						rs.getString("parts.name"));
				bmList.add(bm);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			bmList = null;
		} catch (SQLException e) {
			e.printStackTrace();
			bmList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					bmList = null;
				}
			}
		}

		return bmList;
	}

	// バンド情報Idを元にそのバンドメンバーの一覧を返す
	public List<BandMember> showMember(int biId) {
		List<BandMember> bmList = new ArrayList<BandMember>();
		Connection conn = null;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true",
					"root", "password");

			// バンドメンバーのid、名前、楽器ID、バンド情報ID、楽器名を取得するSQL文を作成する
			String sql = "SELECT band_member.id, band_member.name, band_member.part_id, band_member.band_info_id, parts.name FROM band_member\r\n"
					+ "LEFT JOIN parts ON band_member.part_id = parts.id\r\n" + "WHERE band_member.band_info_id = ?;";

			// 値を設定する。
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, biId);

			// SELECT文を実行し結果を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果をコレクションにコピー
			while (rs.next()) {
				BandMember bm;
				// リストへコピー
				bm = new BandMember(rs.getInt("band_member.id"), rs.getString("band_member.name"),
						rs.getInt("band_member.part_id"), rs.getInt("band_member.band_info_id"),
						rs.getString("parts.name"));
				bmList.add(bm);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			bmList = null;
		} catch (SQLException e) {
			e.printStackTrace();
			bmList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					bmList = null;
				}
			}
		}

		return bmList;
	}

	// メンバーIDからそのメンバーが存在するか否か調べる。
	public boolean isMember(int bmId) {
		Connection conn = null;
		boolean result;
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true",
					"root", "password");

			// バンドメンバーのid、名前、楽器ID、バンド情報ID、楽器名を取得するSQL文を作成する
			String sql = "SELECT COUNT(*) FROM band_member WHERE id = ?;";

			// 値を設定する。
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, bmId);

			// SELECT文を実行し結果を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果をコレクションにコピー
			rs.next();
			if (rs.getInt("count(*)") == 0) {
				result = false;
			} else {
				result = true;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}
		
		return result;
	}
	
	// バンドメンバーを登録する
	public boolean addMember(BandMember bm, int biId) {
		Connection conn = null;
		boolean result;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// 楽器名から楽器IDを取得する
			int partId;
			String sql = "SELECT id FROM parts WHERE name = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, bm.getPartName());

			ResultSet rs = pStmt.executeQuery();
			rs.next();
			partId = rs.getInt("id");

			// 登録する
			sql = "INSERT INTO band_member VALUES\r\n" + "(0, ?, ?, ?);";

			// 設定して編集
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, bm.getName());
			pStmt.setInt(2, partId);
			pStmt.setInt(3, biId);

			int rsInt = pStmt.executeUpdate();

			if (rsInt == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}
		return result;
	}

	// バンドメンバーを登録する
	public boolean addMember(BandMember bm) {
		Connection conn = null;
		boolean result;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql;
			PreparedStatement pStmt;
			
			// 楽器IDを取得する
			int partId;
			if (bm.getPartId() == 0) {
				sql = "SELECT id FROM parts WHERE name = ?;";
	
				pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, bm.getPartName());
	
				ResultSet rs = pStmt.executeQuery();
				rs.next();
				partId = rs.getInt("id");
			} else {
				partId = bm.getPartId();
			}
			
			// 登録する
			sql = "INSERT INTO band_member VALUES\r\n" + "(0, ?, ?, ?);";

			// 設定して編集
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, bm.getName());
			pStmt.setInt(2, partId);
			pStmt.setInt(3, bm.getBandInfoId());

			int rsInt = pStmt.executeUpdate();

			if (rsInt == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}
		return result;
	}

	// バンドメンバーを登録する
	public boolean addMember(BandMember bm, BandInfo bi) {
		Connection conn = null;
		boolean result;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql;
			PreparedStatement pStmt;
			
			// 楽器IDを取得する
			int partId;
			if (bm.getPartId() == 0) {
				sql = "SELECT id FROM parts WHERE name = ?;";
	
				pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, bm.getPartName());
	
				ResultSet rs = pStmt.executeQuery();
				rs.next();
				partId = rs.getInt("id");
			} else {
				partId = bm.getPartId();
			}

			// 登録する
			sql = "INSERT INTO band_member VALUES\r\n" + "(0, ?, ?, ?);";

			// 設定して編集
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, bm.getName());
			pStmt.setInt(2, partId);
//			pStmt.setInt(3, bi.getId());
			pStmt.setInt(3, bm.getBandInfoId());

			int rsInt = pStmt.executeUpdate();

			if (rsInt == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}
		return result;
	}

	// バンドメンバーの編集
	public boolean editMember(BandMember bm, int biId) {
		Connection conn = null;
		boolean result;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql;
			PreparedStatement pStmt;
			
			// 楽器IDを取得する
			int partId;
			if (bm.getPartId() == 0) {
				sql = "SELECT id FROM parts WHERE name = ?;";
	
				pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, bm.getPartName());
	
				ResultSet rs = pStmt.executeQuery();
				rs.next();
				partId = rs.getInt("id");
			} else {
				partId = bm.getPartId();
			}

			// 登録する
			sql = "UPDATE band_member SET\r\n" + "name = ?, part_id = ?, band_info_id = ?\r\n" + "WHERE id = ?;";

			// 設定して編集
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, bm.getName());
			pStmt.setInt(2, partId);
			pStmt.setInt(3, biId);
			pStmt.setInt(4, bm.getId());

			int rsInt = pStmt.executeUpdate();

			if (rsInt == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}

		return result;
	}

	// バンドメンバーの編集
	public boolean editMember(BandMember bm) {
		Connection conn = null;
		boolean result;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql;
			PreparedStatement pStmt;
			
			// 楽器IDを取得する
			int partId;
			if (bm.getPartId() == 0) {
				sql = "SELECT id FROM parts WHERE name = ?;";
	
				pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, bm.getPartName());
	
				ResultSet rs = pStmt.executeQuery();
				rs.next();
				partId = rs.getInt("id");
			} else {
				partId = bm.getPartId();
			}

			// 更新する
			sql = "UPDATE band_member SET\r\n" + "name = ?, part_id = ?, band_info_id = ?\r\n" + "WHERE id = ?;";

			// 設定して編集
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, bm.getName());
			pStmt.setInt(2, partId);
			pStmt.setInt(3, bm.getBandInfoId());
			pStmt.setInt(4, bm.getId());

			int rsInt = pStmt.executeUpdate();

			if (rsInt == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}

		return result;
	}

	// バンドメンバーの編集
	public boolean editMember(BandMember bm, BandInfo bi) {
		Connection conn = null;
		boolean result;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql;
			PreparedStatement pStmt;
			
			// 楽器IDを取得する
			int partId;
			if (bm.getPartId() == 0) {
				sql = "SELECT id FROM parts WHERE name = ?;";
	
				pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, bm.getPartName());
	
				ResultSet rs = pStmt.executeQuery();
				rs.next();
				partId = rs.getInt("id");
			} else {
				partId = bm.getPartId();
			}

			// 更新する
			sql = "UPDATE band_member SET\r\n" + "name = ?, part_id = ?, band_info_id = ?\r\n" + "WHERE id = ?;";

			// 設定して編集
			pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, bm.getName());
			pStmt.setInt(2, partId);
//			pStmt.setInt(3, bi.getId());
			pStmt.setInt(3, 0);
			pStmt.setInt(4, bm.getId());

			int rsInt = pStmt.executeUpdate();

			if (rsInt == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}

		return result;
	}
	
	// メンバー情報の削除
	public boolean deleteMember(BandMember bm) {
		Connection conn = null;
		boolean result;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql = "DELETE FROM band_member WHERE id = ?;";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, bm.getId());

			int rsInt = pStmt.executeUpdate();

			if (rsInt == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}

		return result;
	}
}
