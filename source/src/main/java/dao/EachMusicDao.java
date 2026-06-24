package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.EachMusic;
import dto.PreparInfo;

public class EachMusicDao {
	
	// 準備情報Idを元に紐付けられたEachMusic情報を返す
	public List<EachMusic> select(PreparInfo pi) {
		List<EachMusic> emList = new ArrayList<EachMusic>();
		Connection conn = null;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// 曲ごとID、名前、順番、照明要望、SE、備考、準備情報IDを取得するSQL文
			String sql = "SELECT id, name, setlist, light_req, se, memo, prepar_info_id\n"
					+ "FROM each_music\n"
					+ "WHERE prepar_info_id = ?\n"
					+ "ORDER BY setlist ASC;";

			// 値を設定する。
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, pi.getId());

			// SELECT文を実行し結果を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果をコレクションにコピー
			while (rs.next()) {
				EachMusic em = new EachMusic();
				// リストへコピー
				
				if (rs.getString("id") != null)
					em.setId(Integer.parseInt(rs.getString("id")));
				else
					em.setId(0);
				
				if (rs.getString("name") != null)
					em.setName(rs.getString("name"));
				else
					em.setName("");
				
				if (rs.getString("setlist") != null)
					em.setSetlist(Integer.parseInt(rs.getString("setlist")));
				else
					em.setSetlist(0);
				
				if (rs.getString("light_req") != null)
					em.setLightReq(rs.getString("light_req"));
				else
					em.setLightReq("");
				
				if (rs.getString("se") != null)
					em.setSe(rs.getString("se"));
				else
					em.setSe("");
				
				if (rs.getString("memo") != null)
					em.setMemo(rs.getString("memo"));
				else
					em.setMemo("");
				
				if (rs.getString("prepar_info_id") != null)
					em.setPreparInfoId(Integer.parseInt(rs.getString("prepar_info_id")));
				else
					em.setPreparInfoId(0);
				
				emList.add(em);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			emList = null;
		} catch (SQLException e) {
			e.printStackTrace();
			emList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					emList = null;
				}
			}
		}

		return emList;
	}
	
	// 準備情報Idを元に紐付けられたEachMusic情報を返す
	public List<EachMusic> select(int piId) {
		List<EachMusic> emList = new ArrayList<EachMusic>();
		Connection conn = null;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// 曲ごとID、名前、順番、照明要望、SE、備考、準備情報IDを取得するSQL文
			String sql = "SELECT id, name, setlist, light_req, se, memo, prepar_info_id\n"
					+ "FROM each_music\n"
					+ "WHERE prepar_info_id = ?\n"
					+ "ORDER BY setlist ASC;";

			// 値を設定する。
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, piId);

			// SELECT文を実行し結果を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果をコレクションにコピー
			while (rs.next()) {
				EachMusic em = new EachMusic();
				// リストへコピー
				
				if (rs.getString("id") != null)
					em.setId(Integer.parseInt(rs.getString("id")));
				else
					em.setId(0);
				
				if (rs.getString("name") != null)
					em.setName(rs.getString("name"));
				else
					em.setName("");
				
				if (rs.getString("setlist") != null)
					em.setSetlist(Integer.parseInt(rs.getString("setlist")));
				else
					em.setSetlist(0);
				
				if (rs.getString("light_req") != null)
					em.setLightReq(rs.getString("light_req"));
				else
					em.setLightReq("");

				if (rs.getString("se") != null)
					em.setSe(rs.getString("se"));
				else
					em.setSe("");
				
				if (rs.getString("memo") != null)
					em.setMemo(rs.getString("memo"));
				else
					em.setMemo("");
				
				if (rs.getString("prepar_info_id") != null)
					em.setPreparInfoId(Integer.parseInt(rs.getString("prepar_info_id")));
				else
					em.setPreparInfoId(0);
				
				emList.add(em);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			emList = null;
		} catch (SQLException e) {
			e.printStackTrace();
			emList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					emList = null;
				}
			}
		}

		return emList;
	}
	// 曲情報を1件登録するメソッド 
	public boolean insert(EachMusic music) {
	    Connection conn = null;
	    boolean result = false;

	    try {
	        // JDBCドライバ読み込み
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // DB接続
	        conn = DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/c3?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	                "root", "password");

	        // INSERT文
	        String sql = "INSERT INTO each_music "
	                   + "(name, setlist, light_req, se, memo, prepar_info_id) "
	                   + "VALUES (?, ?, ?, ?, ?, ?)";

	        PreparedStatement pStmt = conn.prepareStatement(sql);

	        // DTO の値をセット
	        pStmt.setString(1, music.getName());
	        pStmt.setObject(2, music.getSetlist());
	        pStmt.setString(3, music.getLightReq());
	        pStmt.setString(4, music.getSe());
	        pStmt.setString(5, music.getMemo());
	        pStmt.setObject(6, music.getPreparInfoId());

	        // 1件登録できたら true
	        if (pStmt.executeUpdate() == 1) {
	            result = true;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        result = false;

	    } finally {
	        try { if (conn != null) conn.close(); } catch (SQLException e) {}
	    }

	    return result;
	}

	
}
