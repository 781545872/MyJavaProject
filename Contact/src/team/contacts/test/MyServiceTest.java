package team.contacts.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import team.contacts.domain.Contact;
import team.contacts.domain.ContactDetail;
import team.contacts.domain.TransmissionData;
import team.contacts.domain.User;
import team.contacts.service.MyService;
import team.contacts.service.ResultCode;
import team.contacts.util.MyTools;

public class MyServiceTest {
	private MyService myService = new MyService();
	/**
	 * 测试上传数据更新到数据库中
	 */
	@Test
	public void testUpload(){
		String upload = "{'user':{'phone':'15626485455','token':'34343423'},'data':[{'id':2,'name':'zzf','icon':'sdff','isBlack':false,'from':1,'details':[{'phone':'15626485466','province':'e45345广东省','city':'汕头市'}]}]}";
		myService.upload(upload);
	}
	
	public static void main(String[] args) {
		TransmissionData data = new TransmissionData();
		data.setUser(new User("15626485455", "3424234"));
		List<Contact> contacts = new ArrayList<>();
		Contact contact = new Contact(1, 2, "zzf", "sdff", false, 1);
		contacts.add(contact);
		List<ContactDetail> details = new ArrayList<>();
		ContactDetail detail = new ContactDetail(1, "15626485466", "广东省", "汕头市");
		details.add(detail);
		contact.setDetails(details);
		data.setData(contacts);
		data.setCode(ResultCode.GET_CONTACTS_SUCCESS.getCode());
		System.out.println(MyTools.getJson(data, TransmissionData.class));
	}
}
