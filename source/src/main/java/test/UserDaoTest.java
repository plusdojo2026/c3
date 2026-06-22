package test;


import dao.UserDao;
import dto.User;
public class UserDaoTest {
	public static void main(String[] args) {
		testSelect("evrin");
//		testIsLoginOK1(); // ユーザーが見つかる場合のテスト
//		testIsLoginOK2(); // ユーザーが見つからない場合のテスト
	}
	// ユーザーが見つかる場合のテスト
		public static void testIsLoginOK1() {
			UserDao dao = new UserDao();
			 User user = dao.login(new User("staff", "passwords"));
			 if (user != null) {
				System.out.println("testIsLoginOK1：テストが成功しました");
			} else {
				System.out.println("testIsLoginOK1：テストが失敗しました");
			}
		}
		// ユーザーが見つからない場合のテスト
		public static void testIsLoginOK2() {
			UserDao dao = new UserDao();
			User user = dao.login(new User("user_id", "pass"));
			if (user == null){
				System.out.println("testIsLoginOK2：テストが成功しました");
			} else {
				System.out.println("testIsLoginOK2：テストが失敗しました");
			}
		}

		public static void testSelect(String name) {
			UserDao dao = new UserDao();
			String id = dao.select(name);
			System.out.println("id:" + id);
		}
}
