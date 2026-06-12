package test;

import java.util.ArrayList;
import java.util.List;

import dao.PartsDao;
import dto.Parts;

public class PartsDaoTest {
	public static void main(String[] args) {
		showAllPartsTest();
	}
	
	public static void showAllPartsTest() {
		List<Parts> partsList = new ArrayList<Parts>();
		PartsDao ptDao = new PartsDao();
		partsList = ptDao.showAllParts();
		
		for (Parts pt : partsList) {
			System.out.println(pt.getId() + ":" + pt.getName());
		}
	}
}
