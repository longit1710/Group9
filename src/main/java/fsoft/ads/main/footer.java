package fsoft.ads.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class footer
 */
@WebServlet("/footer")
public class footer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset = utf-8";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public footer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
		//
		// tạo đối tượng xuất nội dung
		PrintWriter out = response.getWriter();
		out.append("<!-- ======= Footer ======= -->");
		out.append("<footer id=\"footer\" class=\"footer\">");
		out.append("<div class=\"copyright\">");
		out.append("&copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved");
		out.append("</div>");
		out.append("<div class=\"credits\">");
		out.append("<!-- All the links in the footer should remain intact. -->");
		out.append("<!-- You can delete the links only if you purchased the pro version. -->");
		out.append("<!-- Licensing information: https://bootstrapmade.com/license/ -->");
		out.append(
				"<!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->");
		out.append("Designed by <a href=\"https://bootstrapmade.com/\">BootstrapMade</a>");
		out.append("</div>");
		out.append("</footer><!-- End Footer -->");

		out.append("<a href=\"#\" class=\"back-to-top d-flex align-items-center justify-content-center\"><i class=\"bi bi-arrow-up-short\"></i></a>");

		out.append("<script src=\"/Group9/js/apexcharts/apexcharts.min.js\"></script>");
		out.append("<script src=\"/Group9/js/bootstrap.bundle.min.js\"></script>");
		
		out.append("<script src=\"/Group9/js/main.js\"></script>");

		out.append("</body>");

		out.append("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
