package test;

import java.util.ArrayList;
import java.util.List;

import dao.BandMemberDao;
import dto.BandMember;

public class BandMemberDaoTest {
	
	public static void main(String[] args) {
		showMemberTest(1);
		isMemberTest(11);
		isMemberTest(1);
		showMemberTest(3);
//		addMemberTest(new BandMember(0, "田中", 3, 3));
//		showMemberTest(3);
		editMemberTest(new BandMember(10, "中田仁", 4, 3));
	}
	
	public static void showMemberTest(int biId){
		BandMemberDao bmDao = new BandMemberDao();
		List<BandMember> bmList = new ArrayList<BandMember>();
		bmList = bmDao.showMember(biId);
		for (BandMember bm : bmList) {
			System.out.println(bm.getId() + "：" + bm.getName() +"：" + bm.getPartName());
		}
	}
	
	public static void isMemberTest(int bmId) {
		BandMemberDao bmDao = new BandMemberDao();
		System.out.println(bmDao.isMember(bmId));
	}

	public static void addMemberTest(BandMember bm) {
		BandMemberDao bmDao = new BandMemberDao();
		boolean rs;
		rs = bmDao.addMember(bm);
		System.out.println(rs);
	}
	
	public static void editMemberTest(BandMember bm) {
		BandMemberDao bmDao = new BandMemberDao();
		boolean rs;
		rs = bmDao.editMember(bm);
		System.out.println(rs);
	}
	
}
