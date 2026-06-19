package test;

import java.util.List;

import dao.BandInfoDao;
import dto.BandInfo;

public class BandInfoDaoTest {
	public static void showAllData(List<BandInfo> Bandlist) {
		for (BandInfo Band : Bandlist) {
			System.out.println("バンド情報ID：" + Band.getId());
			System.out.println("バンド名：" + Band.getName());			
			System.out.println("ユーザーID：" + Band.getUser_id());
			System.out.println();
		}
	}

	public static void main(String[] args) {
		BandInfoDao dao = new BandInfoDao();

		// select()のテスト1
		System.out.println("---------- select()のテスト1 ----------");
		List<BandInfo> BandlistSel1 = dao.select(new BandInfo(0, "", 0));
		BandInfoDaoTest.showAllData(BandlistSel1);
		
		// selectById()のテスト
		System.out.println("---------- selectById() のテスト ----------");

		BandInfo band = dao.selectById(13);  // ID=0 のバンドを取得

		if (band != null) {
			System.out.println("バンド情報ID：" + band.getId());
			System.out.println("バンド名：" + band.getName());			
			System.out.println("ユーザーID：" + band.getUser_id());
		} else {
		    System.out.println("データが見つかりませんでした");
		}



				
		// insert()のテスト
		System.out.println("---------- insert()のテスト ----------");
		BandInfo insRec = new BandInfo(2, "ネクライトーキー", 2);
		if (dao.insert(insRec)) {
			System.out.println("登録成功！");
			List<BandInfo> BandlistIns = dao.select(new BandInfo(0, "サバ", 6));
			BandInfoDaoTest.showAllData(BandlistIns);
		} else {
			System.out.println("登録失敗！");
		}
		
		// update()のテスト
				System.out.println("---------- update()のテスト ----------");
				List<BandInfo> BandlistUp = dao.select(new BandInfo(0, "", 0));
				BandInfo upRec = BandlistUp.get(2);
			
				if (dao.update(upRec)) {
					System.out.println("更新成功！");
					BandlistUp = dao.select(new BandInfo(0, "", 0));
					BandInfoDaoTest.showAllData(BandlistUp);
				} else {
					System.out.println("更新失敗！");
				}
				
				
		//delete()のテスト
				System.out.println("---------- delete()のテスト ----------");

				// id=16 のレコードを取得（name や user_id は不要）
				List<BandInfo> BandlistDel = dao.select(new BandInfo(1, "", 0));
				System.out.println("削除前件数: " + BandlistDel.size());

				if (!BandlistDel.isEmpty()) {
				    BandInfo delRec = BandlistDel.get(0);

				    if (dao.delete(delRec)) {
				        System.out.println("削除成功！");
				    } else {
				        System.out.println("削除失敗！");
				    }

				    List<BandInfo> after = dao.select(new BandInfo(16, "", 0));
				    System.out.println("削除後件数: " + after.size());

				} else {
				    System.out.println("削除対象が見つかりません");
				}

					
					
			//ユーザーIdを元にバンド情報を返す

	        System.out.println("---------- showBand() のテスト ----------");
	        List<BandInfo> BandList = dao.showBand(2);
	        BandInfo rec = BandList.get(2);
	        System.out.println("ID: " + rec.getId());
	        System.out.println("名前: " + rec.getName());
	        System.out.println("ユーザーID: " + rec.getUser_id());
	    }
	}