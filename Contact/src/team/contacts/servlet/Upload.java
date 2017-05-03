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

@WebServlet("/upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public Upload() {
		super();
	}

	private MyService myService = new MyService();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String upload = request.getParameter("upload");
		ResultCode code = myService.upload(upload);
		writer.print(code.getCode());
	}
}
