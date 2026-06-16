package test;

import java.util.List;

import dao.LiveInfoDao;
import dto.LiveInfo;

public class LiveInfoDaoTest {
	public static void showAllData(List<LiveInfo> livelist) {
		for (LiveInfo live : livelist) {
			System.out.println("ライブ情報ID：" + live.getId());
			System.out.println("ライブ名：" + live.getName());
			System.out.println("開始日時：" + live.getBegin_date());
			System.out.println("終了日時：" + live.getEnd_date());
			System.out.println("ユーザーID：" + live.getUser_id());
			System.out.println("作成前後識別：" + live.isCreate_flag());
			System.out.println();
		}
	}

	public static void main(String[] args) {
		LiveInfoDao dao = new LiveInfoDao();

		// select()のテスト1
		System.out.println("---------- select()のテスト1 ----------");
		List<LiveInfo> livelistSel1 = dao.select(new LiveInfo(0, "", null, java.time.LocalDateTime.of(2026, 6, 11, 16, 30, 0), 0,true));
		LiveInfoDaoTest.showAllData(livelistSel1);
		
		// select()のテスト2
				System.out.println("---------- select()のテスト2 ----------");
				List<LiveInfo> liveListSel2 = dao.select(new LiveInfo(0, "", null, null, 0,false));
				LiveInfoDaoTest.showAllData(liveListSel2);
				
		// insert()のテスト
		System.out.println("---------- insert()のテスト ----------");
		LiveInfo insRec = new LiveInfo(0, "テスト・フェス", java.time.LocalDateTime.of(2026, 10, 16, 15, 0, 0), java.time.LocalDateTime.of(2026, 10, 19, 20, 0, 0), 2, true);
		if (dao.insert(insRec)) {
			System.out.println("登録成功！");
			List<LiveInfo> livelistIns = dao.select(new LiveInfo(0, "", null, null, 0, false));
			LiveInfoDaoTest.showAllData(livelistIns);
		} else {
			System.out.println("登録失敗！");
		}
		
		// update()のテスト
				System.out.println("---------- update()のテスト ----------");
				List<LiveInfo> livelistUp = dao.select(new LiveInfo(0, "オータム・ハーモニー・ナイト", null, null, 0, false));
				LiveInfo upRec = livelistUp.get(0);
				upRec.setEnd_date(java.time.LocalDateTime.of(2026, 6, 11, 19, 30, 0));
			
				if (dao.update(upRec)) {
					System.out.println("更新成功！");
					livelistUp = dao.select(new LiveInfo(0, "", null, null, 0, false));
					LiveInfoDaoTest.showAllData(livelistUp);
				} else {
					System.out.println("更新失敗！");
				}
				// delete()のテスト
				System.out.println("---------- delete()のテスト ----------");
				List<LiveInfo> livelistDel = dao.select(new LiveInfo(0, "ニューイヤー・ホープ・カウントダウン 2026-2027", null, null, 0,true));
				if (!livelistDel.isEmpty()) {
					LiveInfo delRec = livelistDel.get(0);
					if (dao.delete(delRec)) {
					System.out.println("削除成功！");
					livelistDel = dao.select(new LiveInfo(0, "", null, null, 0, false));
					LiveInfoDaoTest.showAllData(livelistDel);
				} else {
					System.out.println("削除失敗！");
				}
			}
				// ライブ情報一覧のテスト
				System.out.println("---------- ライブ情報一覧のテスト()のテスト ----------");
				List<LiveInfo> livelistVeiw = dao.selectByUserId(1);
				for(LiveInfo li:livelistVeiw) {
					System.out.println("ライブ名：" + li.getName());
				}
				// idでとってくるテスト
				System.out.println("---------- idでとってくる()のテスト ----------");
				LiveInfo idTestLive = dao.select(3);
				System.out.println("ライブ名：" + idTestLive.getName());
	}
	
}

