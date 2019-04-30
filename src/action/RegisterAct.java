package action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.UserBusi;
import pojo.User;

/**
 * Servlet implementation class RegisterAct
 */
@WebServlet("/RegisterAct")
public class RegisterAct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterAct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		User user = new User();
		UserBusi userbusi = new UserBusi();
		int rs = 0;
		String html; 
//		String vali = request.getParameter("vali");
		
/*		if(vali.equals(request.getSession().getAttribute("valiNum"))){
			System.out.println("������֤ͨ����");*/
			
			user.setId(request.getParameter("userId_2").trim());
			user.setPassword(request.getParameter("userPsd").trim());
			System.out.println("ע���û�ID��"+user.getId()+"  ���룺"+user.getPassword());
			
			try {
				rs = userbusi.register(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(rs == 1) {
				response.sendRedirect("login.jsp");
			}else {
				html="ע��ʧ�ܣ�<br><a href='login.jsp'>����ע��</a>";
				response.getWriter().write(html);
			}
/*		}else{
			html="������֤ʧ�ܣ�<br><a href='login.jsp'>����ע��</a>";
			response.getWriter().write(html);
		}*/
	}

}
