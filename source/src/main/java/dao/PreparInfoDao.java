package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LiveInfo;
import dto.PreparInfo;

public class PreparInfoDao {

	// SELECT：引数infoの条件で prepar_info を検索し、リストで返す
	public List<PreparInfo> select(PreparInfo info) {
		Connection conn = null;
		List<PreparInfo> list = new ArrayList<>();

		try {
			// 1. JDBCドライバ読み込み
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. DB接続
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "c3",
					"zTfP4Ep4RMwQge3E");

			// 3. 実行するSQL文（? は後で値を埋めるプレースホルダ）
			String sql = "SELECT * FROM prepar_info "
					+ "WHERE time LIKE ? AND prepar_time LIKE ? AND prepar_items LIKE ? "
					+ "AND setlist LIKE ? AND entrance_music LIKE ? "
					+ "AND band_info_id LIKE ? AND live_info_id LIKE ? " + "ORDER BY id";

			// 4. PreparedStatement作成
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// 5. ? に値をセット（null の場合は % で「条件なし」にする）
			pStmt.setString(1, info.getTime() != null ? "%" + info.getTime() + "%" : "%");
			pStmt.setString(2, info.getPreparTime() != null ? "%" + info.getPreparTime() + "%" : "%");
			pStmt.setString(3, info.getPreparItems() != null ? "%" + info.getPreparItems() + "%" : "%");
			pStmt.setString(4, info.getSetlist() != null ? "%" + info.getSetlist() + "%" : "%");
			pStmt.setString(5, info.getEntranceMusic() != null ? "%" + info.getEntranceMusic() + "%" : "%");
			pStmt.setString(6, info.getBandInfoId() != null ? "%" + info.getBandInfoId() + "%" : "%");
			pStmt.setString(7, info.getLiveInfoId() != null ? "%" + info.getLiveInfoId() + "%" : "%");

			// 6. SQL実行 → 結果セット取得
			ResultSet rs = pStmt.executeQuery();

			// 7. 結果セットを1行ずつ DTO に詰めてリストに追加
			while (rs.next()) {
				PreparInfo pi = new PreparInfo(rs.getInt("id"), (Integer) rs.getObject("time"),
						(Integer) rs.getObject("prepar_time"), rs.getString("prepar_items"),
						(Integer) rs.getObject("setlist"), rs.getString("entrance_music"),
						(Integer) rs.getObject("band_info_id"), (Integer) rs.getObject("live_info_id"));
				list.add(pi);
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			list = null; // エラー時は null を返す方針
		} finally {
			// 8. 接続クローズ（必ず実行）
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		// 9. 検索結果を呼び出し元へ返す
		return list;
	}

	// INSERT：引数infoの内容を prepar_info に1件登録する
	// 成功したら true を返す
	public boolean insert(PreparInfo info) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "c3",
					"zTfP4Ep4RMwQge3E");

			// id は AUTO_INCREMENT 想定なので指定しない
			String sql = "INSERT INTO prepar_info "
					+ "(time, prepar_time, prepar_items, setlist, entrance_music, band_info_id, live_info_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// DTO → SQL の ? に値を流し込む
			pStmt.setObject(1, info.getTime()); // INT（NULL可）
			pStmt.setObject(2, info.getPreparTime()); // INT（NULL可）
			pStmt.setString(3, info.getPreparItems() != null ? info.getPreparItems() : ""); // VARCHAR（NULLなら空文字）
			pStmt.setObject(4, info.getSetlist()); // INT（NULL可）
			pStmt.setString(5, info.getEntranceMusic() != null ? info.getEntranceMusic() : ""); // VARCHAR（NULLなら空文字）
			pStmt.setObject(6, info.getBandInfoId()); // INT（NULL可 or 外部キー）
			pStmt.setObject(7, info.getLiveInfoId()); // INT（NULL可 or 外部キー）

			// 1件登録できたら戻り値 true
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		return result;
	}

	// 登録後に自動採番されたIDを返すメソッド
	public int insertAndReturnId(PreparInfo info) {
		Connection conn = null;
		int generatedId = -1; // 失敗時は -1 を返す

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo",
					"c3", "zTfP4Ep4RMwQge3E");

			String sql = "INSERT INTO prepar_info "
					+ "(time, prepar_time, prepar_items, setlist, entrance_music, band_info_id, live_info_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			// RETURN_GENERATED_KEYS を指定
			PreparedStatement pStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			pStmt.setObject(1, info.getTime());
			pStmt.setObject(2, info.getPreparTime());
			pStmt.setString(3, info.getPreparItems() != null ? info.getPreparItems() : "");
			pStmt.setObject(4, info.getSetlist());
			pStmt.setString(5, info.getEntranceMusic() != null ? info.getEntranceMusic() : "");
			pStmt.setObject(6, info.getBandInfoId());
			pStmt.setObject(7, info.getLiveInfoId());

			int result = pStmt.executeUpdate();

			if (result == 1) {
				// 自動採番されたIDを取得
				ResultSet rs = pStmt.getGeneratedKeys();
				if (rs.next()) {
					generatedId = rs.getInt(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		return generatedId;
	}

	// UPDATE：引数infoの id を持つレコードを更新する
	// 成功したら true を返す
	public boolean update(PreparInfo info) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "c3",
					"zTfP4Ep4RMwQge3E");

			String sql = "UPDATE prepar_info SET "
					+ "time=?, prepar_time=?, prepar_items=?, setlist=?, entrance_music=?, "
					+ "band_info_id=?, live_info_id=? " + "WHERE id=?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// 更新後の値をセット
			pStmt.setObject(1, info.getTime());
			pStmt.setObject(2, info.getPreparTime());
			pStmt.setString(3, info.getPreparItems() != null ? info.getPreparItems() : "");
			pStmt.setObject(4, info.getSetlist());
			pStmt.setString(5, info.getEntranceMusic() != null ? info.getEntranceMusic() : "");
			pStmt.setObject(6, info.getBandInfoId());
			pStmt.setObject(7, info.getLiveInfoId());
			pStmt.setInt(8, info.getId()); // ← どのレコードを更新するか（主キー）

			if (pStmt.executeUpdate() == 1) {
				result = true;
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		return result;
	}

	// DELETE：指定された id のレコードを削除する
	// 成功したら true を返す
	public boolean delete(int id) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "c3",
					"zTfP4Ep4RMwQge3E");

			String sql = "DELETE FROM prepar_info WHERE id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// 削除対象の主キーを指定
			pStmt.setInt(1, id);

			if (pStmt.executeUpdate() == 1) {
				result = true;
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		return result;

	}

	public List<PreparInfo> selectByLiveInfoId(int liveInfoId) {

		Connection conn = null;
		List<PreparInfo> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"c3", "zTfP4Ep4RMwQge3E");

			String sql = "SELECT * FROM prepar_info WHERE live_info_id = ? ORDER BY setlist ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, liveInfoId);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				PreparInfo prepar = new PreparInfo(rs.getInt("id"), (Integer) rs.getObject("time", Integer.class),
						(Integer) rs.getObject("prepar_time", Integer.class), rs.getString("prepar_items"),
						(Integer) rs.getObject("setlist", Integer.class), rs.getString("entrance_music"),
						(Integer) rs.getObject("band_info_id", Integer.class),
						(Integer) rs.getObject("live_info_id", Integer.class));

				list.add(prepar);
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

	public List<PreparInfo> selectByBandId(int bandId) {

		Connection conn = null;
		List<PreparInfo> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo",
					"c3", "zTfP4Ep4RMwQge3E");

			// band_info_id で検索し、setlist（順番）で昇順ソート
			String sql = "SELECT prepar_info.id, prepar_info.time, prepar_info.prepar_time, prepar_info.prepar_items, prepar_info.setlist, prepar_info.entrance_music, prepar_info.band_info_id, prepar_info.live_info_id FROM prepar_info\r\n"
					+ "LEFT JOIN live_info ON live_info.id = prepar_info.live_info_id\r\n"
					+ "WHERE band_info_id = ? ORDER BY live_info.begin_date ASC;";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, bandId);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				PreparInfo prepar = new PreparInfo(rs.getInt("prepar_info.id"), (Integer) rs.getObject("prepar_info.time", Integer.class),
						(Integer) rs.getObject("prepar_info.prepar_time", Integer.class), rs.getString("prepar_info.prepar_items"),
						(Integer) rs.getObject("prepar_info.setlist", Integer.class), rs.getString("prepar_info.entrance_music"),
						(Integer) rs.getObject("prepar_info.band_info_id", Integer.class),
						(Integer) rs.getObject("prepar_info.live_info_id", Integer.class));

				list.add(prepar);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		return list;
	}

	// id で 1 件の準備情報を取得する
	public PreparInfo selectById(int id) {

		Connection conn = null;
		PreparInfo data = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo&connectTimeout=30000",
					"c3", "zTfP4Ep4RMwQge3E");

			String sql = "SELECT * FROM prepar_info WHERE id = ? ORDER BY id";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, id);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				data = new PreparInfo(rs.getInt("id"), (Integer) rs.getObject("time", Integer.class),
						(Integer) rs.getObject("prepar_time", Integer.class), rs.getString("prepar_items"),
						(Integer) rs.getObject("setlist", Integer.class), rs.getString("entrance_music"),
						(Integer) rs.getObject("band_info_id", Integer.class),
						(Integer) rs.getObject("live_info_id", Integer.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		return data;
	}

	// prepar_infoテーブルに登録されたライブ情報IDからlive_infoテーブルの情報を持ってくる
	public List<LiveInfo> selectLiveInfoByPreparId(int preparId) {

		PreparInfo prepar = selectById(preparId);
		List<LiveInfo> list = new ArrayList<>();

		if (prepar == null || prepar.getLiveInfoId() == null) {
			return list;
		}

		LiveInfoDao liveDao = new LiveInfoDao();

		LiveInfo live = liveDao.select(prepar.getLiveInfoId());

		if (live != null) {
			list.add(live);
		}
		return list;
	}
}