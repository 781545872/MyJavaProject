package team.contacts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import team.contacts.service.MyService;
import team.contacts.service.ResultCode;

@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public Logout() {
		super();
	}

	private MyService myService = new MyService();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		String phone = request.getParameter("phone");
		String token = request.getParameter("token");
		ResultCode code;
		//如果验证成功才予以注销
		if(myService.login(phone,token)==ResultCode.LOGIN_SUCCESS){
			code = myService.logout(phone);
		}
		else{
			code = ResultCode.LOGOUT_FIAL;
		}
		writer.print(code.getCode());
	}

	

}
