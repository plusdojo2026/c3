package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Parts;

public class PartsDao {

	// 全てのパート情報を返す
	public List<Parts> showAllParts(){
		Connection conn = null;
		List<Parts> ptList = new ArrayList<Parts>();
		
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/c3?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"c3", "zTfP4Ep4RMwQge3E");

			// 登録されている担当楽器情報を全て取得する。
			String sql = "SELECT id, name FROM parts;";

			// 設定する。
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SELECT文を実行し結果を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果をコレクションにコピー
			while (rs.next()) {
				Parts pt;
				// リストへコピー
				pt = new Parts(rs.getInt("id"), rs.getString("name"));
				ptList.add(pt);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			ptList = null;
		} catch (SQLException e) {
			e.printStackTrace();
			ptList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					ptList = null;
				}
			}
		}
		
		return ptList;
	}
	
}
