package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ResponseCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginSuccessView extends HttpServlet {

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			/* 출력을 위한 응답 객체의 속성 추가
			 * 세션 객체 획득
			 * 이름 데이터를 세션으로부터 획득 -> 사용 */
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			out.print(session.getAttribute("name"));
			out.println("<br>");
			out.println("<button onclick = 'location.href=\"cont?command=logout\"'> 로그아웃</br>");
	}

}
