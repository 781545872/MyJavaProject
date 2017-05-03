package team.contacts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import team.contacts.service.MyService;
import team.contacts.service.ResultCode;


@WebServlet("/getToken")
public class GetToken extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    public GetToken() {
        super();
    }


	private MyService myService = new MyService();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		//获取客户端传来的手机号
		String phone = request.getParameter("phone");
		ResultCode code = myService.register(phone);
		writer.print(code.getCode());
	}
}
