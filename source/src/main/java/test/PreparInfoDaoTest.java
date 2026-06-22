package test;

import java.util.List;

import dao.PreparInfoDao;
import dto.LiveInfo;
import dto.PreparInfo;

public class PreparInfoDaoTest {

    public static void main(String[] args) {

        PreparInfoDao dao = new PreparInfoDao();

        // 1. INSERT テスト
        System.out.println("=== INSERT TEST ===");

        PreparInfo insertData = new PreparInfo(
                0,                      // id（AUTO_INCREMENT）
                30,                     // time
                10,                     // prepar_time
                "ギター準備・音合わせ",     // prepar_items
                1,                      // setlist
                "入場曲A",               // entrance_music
                1,                      // band_info_id
                1                       // live_info_id
        );

        boolean insertResult = dao.insert(insertData);
        System.out.println("INSERT結果: " + insertResult);


        // 2. SELECT（条件検索）テスト
        System.out.println("\n=== SELECT TEST ===");

        PreparInfo condition = new PreparInfo(); // 空条件 → 全件検索
        List<PreparInfo> allList = dao.select(condition);

        if (allList != null) {
            for (PreparInfo pi : allList) {
                print(pi);
            }
        } else {
            System.out.println("SELECT 失敗");
        }


        // 3. UPDATE テスト（ID=1 を更新する例）
        System.out.println("\n=== UPDATE TEST ===");

        PreparInfo updateData = new PreparInfo(
                1,                      // 更新対象のID
                40,                     // time
                15,                     // prepar_time
                "更新後の準備項目",
                2,                      // setlist
                "更新後の入場曲",
                1,                      // band_info_id
                1                       // live_info_id
        );

        boolean updateResult = dao.update(updateData);
        System.out.println("UPDATE結果: " + updateResult);


       // 4. DELETE テスト（ID=1 を削除する例）
        System.out.println("\n=== DELETE TEST ===");

        boolean deleteResult = dao.delete(1);
        System.out.println("DELETE結果: " + deleteResult);
        
        //ライブ情報IDからライブ情報テーブルをリストで持ってくるテスト
        
        System.out.println("---------- ライブ情報IDからライブ情報テーブルをリストで持ってくるリスト ----------");
        
        List<LiveInfo> liveInfoList = dao.selectLiveInfoByPreparId(2);
        
        if (liveInfoList != null && !liveInfoList.isEmpty()) {
        	for (LiveInfo live : liveInfoList) {
        		System.out.println(
        				"ID" + live.getId()
        				+ ", name=" + live.getName()
        				+ ", begin_date=" + live.getBegin_date()
        				+ ", end_date=" + live.getEnd_date()
        				+ ", user_id=" + live.getUser_id()
        				+ ", create_flag=" + live.isCreate_flag());
        	}
        } else {
        	System.out.println("該当データなし");
        }
        


       // 5. SELECT BY LIVE_INFO_ID テスト
       System.out.println("\n=== SELECT BY LIVE_INFO_ID TEST ===");

        List<PreparInfo> liveList = dao.selectByLiveInfoId(1);

        if (liveList != null && !liveList.isEmpty()) {
            for (PreparInfo pi : liveList) {
                print(pi);
            }
        } else {
            System.out.println("該当データなし or SELECT失敗");
        }
 
     // 6. SELECT BY BAND_ID テスト
     System.out.println("\n=== SELECT BY BAND_ID TEST ===");

     List<PreparInfo> bandList = dao.selectByBandId(1);  // ← band_info_id = 1 を検索

     if (bandList != null && !bandList.isEmpty()) {
         for (PreparInfo pi : bandList) {
             print(pi);
         }
     } else {
         System.out.println("該当データなし or SELECT失敗");
     }

  // 7. SELECT BY ID テスト
     System.out.println("\n=== SELECT BY ID TEST ===");

     PreparInfo idTestPrepar = dao.selectById(11);  // ← 取得したいIDに変更

     if (idTestPrepar != null) {
         print(idTestPrepar);
     } else {
         System.out.println("該当データなし or SELECT失敗");
     }
    }
   
    // 共通の出力メソッド（コードを短くするため）
    private static void print(PreparInfo pi) {
        System.out.println(
                "ID=" + pi.getId() +
                ", time=" + pi.getTime() +
                ", prepar_time=" + pi.getPreparTime() +
                ", prepar_items=" + pi.getPreparItems() +
                ", setlist=" + pi.getSetlist() +
                ", entrance_music=" + pi.getEntranceMusic() +
                ", band_info_id=" + pi.getBandInfoId() +
                ", live_info_id=" + pi.getLiveInfoId()
        );
    }
}

