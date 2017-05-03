package team.contacts.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import team.contacts.dao.MyDao;
import team.contacts.domain.Contact;
import team.contacts.domain.User;
import team.contacts.util.MyTools;

public class MyDaoTest {

	private MyDao myDao = new MyDao();
	private Random random = new Random();

	@Test
	public void testSaveUser() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("13");
		sb.append(random.nextInt(90000) + 10000);
		sb.append(random.nextInt(9000) + 1000);
		myDao.saveUser(new User(sb.toString(), MyTools.getAToken()));
	}

	@Test
	public void testRebuildDatabase() throws Exception {
		myDao.rebuildDatabase();
	}

	@Test
	public void testSaveContact() throws SQLException {
		myDao.saveContact(new Contact(1, 2, "zzf", "dfdf", false, 2));
	}

	@Test
	public void testQueryUser() throws SQLException {
		User user = myDao.queryUser("15626485455");
		System.out.println(user.getToken());
	}

	@Test
	public void testQueryContacts() throws Exception {
		List<Contact> list = myDao.queryContacts(1);
		for (Contact contact : list) {
			System.out.println(contact.getName());
		}
	}


}
