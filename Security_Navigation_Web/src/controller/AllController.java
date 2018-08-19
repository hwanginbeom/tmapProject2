package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.PlaceDAO;
import model.domain.CustomerDTO;
import model.domain.PlaceDTO;


public class AllController extends HttpServlet {
       
    public AllController() {
        super();
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		if(command.equals("login")) {
			login(request,response);
		}else if(command.equals("signup")) {
			signup(request,response);
		}else if(command.equals("logout")){
			logout(request,response);
		}else if(command.equals("all")){
			all(request,response);
		}else if(command.equals("search")){
			search(request,response);
		}else {
			response.sendRedirect("login.html");
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		session.setAttribute("type", request.getParameter("type"));
		session.setAttribute("distance", request.getParameter("distance"));
		System.out.println(request.getParameter("lat")+request.getParameter("lng")+request.getParameter("type")+request.getParameter("distance"));
		System.out.println(session.getAttribute("type"));
		String type = (String) session.getAttribute("type");
		try {
			
			ArrayList<PlaceDTO> typesearch = PlaceDAO.typeSearch(type);
			System.out.println(typesearch.size());
			if (typesearch.size() == 0) {
				request.setAttribute("msg", "No Result");
				request.getRequestDispatcher("msgView.jsp").forward(request, response);
			} else {
				session.setAttribute("typesearch", typesearch);
				response.sendRedirect("loginSucc.jsp");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Error");
			request.getRequestDispatcher("msgView.jsp").forward(request, response);
		}
	}
	private void setLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		String clat2=request.getParameter("lat");
		String clng2=request.getParameter("lng");
		double clat = Double.parseDouble(clat2);
		double clng = Double.parseDouble(clng2);
		session.setAttribute("clat", clat);
		session.setAttribute("clng", clng);
		System.out.println("setLocation");
		response.sendRedirect("loginSucc.jsp");
		
	}
	private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		System.out.println("----- " + name);
		if (id != null && pw!=null&&name != null) {
			try {
				if (CustomerDAO.signup(id, pw,name)) {
					System.out.println("Complete SignUp");
					response.sendRedirect("index.html");
				} else {
					request.setAttribute("msg", "Error SignUp");
					try {
						request.getRequestDispatcher("msgView.jsp").forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Error");
			response.sendRedirect("index.html");
		}		
	}
	// 濡쒓렇�씤 泥섎━ 硫붿냼�뱶
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String type = "restaurant";
		if (id != null && pw != null) {
			try {
				String name = CustomerDAO.loginCheck(id, pw);
				if (name != null) { // �쉶�썝�씪 寃쎌슦
					HttpSession session = request.getSession();
					session.setAttribute("dataAll", PlaceDAO.typeSearchAll());
					session.setAttribute("name", name);
					session.setAttribute("type", type);
					response.sendRedirect("loginSucc.jsp");
				} else { // 鍮꾪쉶�썝�씪 寃쎌슦
					// �슂泥� 媛앹껜�뿉 "�떦�떊�� �쉶�썝�씠 �븘�땲�떗�땲�떎"
					request.setAttribute("msg", "Error");
					request.getRequestDispatcher("msgView.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			response.sendRedirect("login.html");
		}

	}
	

	// 濡쒓렇�븘�썐 泥섎━ 硫붿냼�뱶
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		session = null;
		//response.sendRedirect("byView.jsp");
		response.sendRedirect("index.html");

	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String newName = request.getParameter("newName");
		System.out.println("----- " + newName);
		if (id != null && newName != null) {
			try {
				if (CustomerDAO.update(id, newName)) {
					CustomerDAO.update(id, newName);
					HttpSession session = request.getSession();
					session.setAttribute("newName", newName);
					response.sendRedirect("updateSucc");
				} else {
					request.setAttribute("msg", "Error");
					request.getRequestDispatcher("msgView.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			response.sendRedirect("login.html");
		}

	}

	/*
	 * 紐⑤뱺 寃��깋 寃곌낵�씤 ArrayList瑜� �쉷�뱷 -> request�뿉 ���옣 -> forward 諛⑹떇�쑝濡� 紐⑤몢 蹂닿린 �솕硫댁쑝濡� �씠�룞 濡쒓렇�씤 �븞�븳
	 * 寃쎌슦 寃곌낵 蹂닿린�뒗 遺덇� 濡쒓렇�씤 �쟾 - login.html濡� �솕硫� �씠�룞 濡쒓렇�씤 �썑 - 紐⑤뱺 寃��깋 濡쒓렇�씤 �뿬遺� �솗�씤 - session�뿉
	 * "name" 蹂꾩묶 議댁옱 �뿬遺� �솗�씤 寃�利앹� controller? 紐⑤뱺 寃��깋 寃곌낵 異쒕젰�빐二쇰뒗 view�뿉�꽌? -getSession()
	 * 1.blank or ture �씤 寃쎌슦 , �뾾�쑝硫� �꽭�뀡 �깮�꽦, �엳�쑝硫� �쑀吏� 2. false�씤 寃쎌슦, �뾾�뼱�룄 �깮�꽦 遺덇�, �엳�뼱�빞留� 諛쏆븘�샂.
	 * HttpSession session = request.getSession(false);
	 */
	protected void all(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
			try {
				ArrayList<CustomerDTO> all = CustomerDAO.allSearch();
				if (all.size() == 0) {
					request.setAttribute("msg", "No Result");
					request.getRequestDispatcher("msgView.jsp").forward(request, response);
				} else {
					session.setAttribute("all", all);
					response.sendRedirect("allView.jsp");
				}

			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Error");
				request.getRequestDispatcher("errorView.jsp").forward(request, response);
			}
		

	}

}

