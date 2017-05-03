package team.contacts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team.contacts.domain.TransmissionData;
import team.contacts.service.MyService;
import team.contacts.service.ResultCode;
import team.contacts.util.MyTools;

@WebServlet("/getContacts")
public class GetContacts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetContacts() {
		super();
	}

	private MyService myService = new MyService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String phone = request.getParameter("phone");
		//获取客户端传来的验证码
		String token = request.getParameter("token");
		ResultCode code = myService.login(phone, token);
		String result;
		//先验证客户，如果验证成功则进
		if(code==ResultCode.LOGIN_SUCCESS){
			result = myService.getContacts(phone);
		}	
		else{
			TransmissionData data = new TransmissionData();
			data.setCode(ResultCode.GET_CONTACTS_FAIL.getCode());
			result = MyTools.getJson(data,TransmissionData.class);
		}	
		writer.print(result);
	}

}
