

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logins
 */
public class Logins extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Connection conn;
	static Statement stmt;
	static HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logins() {
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
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/student?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	//	System.out.println("inside servlet1");
		String user = request.getParameter("uid");
		String passwordData = request.getParameter("pwd");
		try {
			ResultSet set = stmt.executeQuery(
					"Select * From login where username='" + user + "' and password='" + passwordData + "'");
			if (set.next()) {
				session = request.getSession();
				session.setAttribute("sessionusername", user);
				RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
				rd.forward(request, response);
			} else {
				PrintWriter w = response.getWriter();
				w.println("<p>invalid username/password</p>");
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.include(request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}