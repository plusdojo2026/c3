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
		
		// select()のテスト2
				System.out.println("---------- select()のテスト2 ----------");
				List<BandInfo> BandListSel2 = dao.select(new BandInfo(0, "oral", 2));
				BandInfoDaoTest.showAllData(BandListSel2);
				
		// insert()のテスト
		System.out.println("---------- insert()のテスト ----------");
		BandInfo insRec = new BandInfo(0, "ネクライトーキー", 1);
		if (dao.insert(insRec)) {
			System.out.println("登録成功！");
			List<BandInfo> BandlistIns = dao.select(new BandInfo(0, "サバ", 6));
			BandInfoDaoTest.showAllData(BandlistIns);
		} else {
			System.out.println("登録失敗！");
		}
		
		// update()のテスト
				System.out.println("---------- update()のテスト ----------");
				List<BandInfo> BandlistUp = dao.select(new BandInfo(0, "ユニゾン", 0));
				BandInfo upRec = BandlistUp.get(0);
			
				if (dao.update(upRec)) {
					System.out.println("更新成功！");
					BandlistUp = dao.select(new BandInfo(0, "", 0));
					BandInfoDaoTest.showAllData(BandlistUp);
				} else {
					System.out.println("更新失敗！");
				}
				// delete()のテスト
				System.out.println("---------- delete()のテスト ----------");
				List<BandInfo> BandlistDel = dao.select(new BandInfo(0, "PEDRO", 0));
				if (!BandlistDel.isEmpty()) {
					BandInfo delRec = BandlistDel.get(0);
					if (dao.delete(delRec)) {
					System.out.println("削除成功！");
					BandlistDel = dao.select(new BandInfo(0, "ブランデー戦記", 0));
					BandInfoDaoTest.showAllData(BandlistDel);
				} else {
					System.out.println("削除失敗！");
				}
			}		
	}
}