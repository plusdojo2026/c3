package test;

import java.util.ArrayList;
import java.util.List;

import dao.EachMusicDao;
import dto.EachMusic;

public class EachMusicDaoTest {

	public static void main(String[] args) {
		selectTest(1);
	}
	
	public static void selectTest(int id) {
		EachMusicDao emDao = new EachMusicDao();
		List<EachMusic> emList = new ArrayList<EachMusic>();
		
		emList = emDao.select(id);
		
		for (EachMusic em : emList) {
			System.out.println(em.getName());
		}
	}

}
