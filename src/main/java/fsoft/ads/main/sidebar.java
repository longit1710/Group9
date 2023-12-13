package fsoft.ads.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class sidebar
 */
@WebServlet("/sidebar")
public class sidebar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset = utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public sidebar() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);

		// Các tập hợp xác định vị trí menu lớn
		// nếu tồn tại key thì không có giá trị
		HashMap<String, String> collapsed = new HashMap<>();

		// Tập hợp xác định việc mở menu con, nếu tồn tại key thì sẽ có giá trị là show,
		// ngược lại không có
		HashMap<String, String> show = new HashMap<>();

		// Tập hợp xác định vị trí chọn menu con, nếu tồn tại key thì sẽ có giá trị
		// class= "active"
		HashMap<String, String> active = new HashMap<>();

		// Lấy tham số xác định vị trí Menu
		String pos = request.getParameter("pos");

		if (pos != null) {

			String act = "";
			if (pos.contains("product")) {
				collapsed.put("product", "");
				show.put("product", "show");
				act = pos.substring(4);
				switch (act) {
				case "view":
					String trash = request.getParameter("trash");
					if (trash != null) {
						active.put("trash", "class=\"active\" ");
					} else {
						active.put("pv", "class=\"active\" ");
					}

					break;
				case "profiles":
					active.put("profiles", "class=\"active\" ");
					break;
				case "trash":
					active.put("trash", "class=\"active\" ");
					break;
				}
			}

		} else {
			collapsed.put("Dashboard", "");
		}

		// tạo đối tượng xuất nội dung
		PrintWriter out = response.getWriter();
		out.append("<!-- ======= Sidebar ======= -->");
		out.append("<aside id=\"sidebar\" class=\"sidebar\">");

		out.append("<ul class=\"sidebar-nav\" id=\"sidebar-nav\">");

		out.append("<li class=\"nav-item\">");
		out.append(
				"<a class=\"nav-link " + collapsed.getOrDefault("Dashboard", "collapsed") + "\" href=\"/Group9/view\">");
		out.append("<i class=\"bi bi-house\"></i>");
		out.append("<span>Dashboard</span>");
		out.append("</a>");
		out.append("</li><!-- End Dashboard Nav -->");

		out.append("<li class=\"nav-item\">");
		out.append("<a class=\"nav-link " + collapsed.getOrDefault("product", "collapsed")
				+ "\" data-bs-target=\"#product-nav\" data-bs-toggle=\"collapse\" href=\"#\">");
		out.append(
				"<i class=\"bi bi-menu-button-wide\"></i><span>Sản phẩm</span><i class=\"bi bi-chevron-down ms-auto\"></i>");
		out.append("</a>");
		out.append("<ul id=\"product-nav\" class=\"nav-content collapse  " + show.getOrDefault("product", "")
				+ "  \" data-bs-parent=\"#sidebar-nav\">");
		out.append("<li>");
		out.append("<a href=\"/Group9/product/view\" " + active.getOrDefault("pv", "") + " >");
		out.append("<i class=\"bi bi-circle\"></i><span>Danh sách</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("<li>");
		out.append("<a href=\"/Group9/product/profiles\" " + active.getOrDefault("profiles", "") + ">");
		out.append("<i class=\"bi bi-circle\"></i><span>Cập nhật</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("<li>");
		out.append("<a href=\"/Group9/product/view?trash\"" + active.getOrDefault("trash", "") + ">");
		out.append("<i class=\"bi bi-circle\"></i><span>Thùng rác</span>");
		out.append("</a>");
		out.append("</li>");

		out.append("</ul>");// product nav
		out.append("</li><!-- End Components Nav -->");


		out.append("<li class=\"nav-item\">");
		out.append(
				"<a class=\"nav-link collapsed\" data-bs-target=\"#charts-nav\" data-bs-toggle=\"collapse\" href=\"#\">");
		out.append("<i class=\"bi bi-bar-chart\"></i><span>Biểu đồ</span><i class=\"bi bi-chevron-down ms-auto\"></i>");
		out.append("</a>");
		out.append("<ul id=\"charts-nav\" class=\"nav-content collapse \" data-bs-parent=\"#sidebar-nav\">");
		out.append("<li>");
		out.append("<a href=\"/Group9/product/chart\">");
		out.append("<i class=\"bi bi-circle\"></i><span>Sản phẩm</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("</ul>");
		out.append("</li><!-- End Charts Nav -->");

		out.append("<li class=\"nav-item\">");
		out.append(
				"<a class=\"nav-link collapsed\" data-bs-target=\"#notes-nav\" data-bs-toggle=\"collapse\" href=\"#\">");
		out.append("<i class=\"bi bi-bar-chart\"></i><span>Nhập/Xuất</span><i class=\"bi bi-chevron-down ms-auto\"></i>");
		out.append("</a>");
		out.append("<ul id=\"notes-nav\" class=\"nav-content collapse \" data-bs-parent=\"#sidebar-nav\">");
		out.append("<li>");
		out.append("<a href=\"/Group9/product/chart\">");
		out.append("<i class=\"bi bi-circle\"></i><span>Nhập</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("<li>");
		out.append("<a href=\"/Group9/product/chart\">");
		out.append("<i class=\"bi bi-circle\"></i><span>Xuất</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("</ul>");
		out.append("</li><!-- End Charts Nav -->");
		
		out.append("<li class=\"nav-heading\">Pages</li>");

		out.append("<li class=\"nav-item\">");
		out.append("<a class=\"nav-link \" href=\"pages-blank.html\">");
		out.append("<i class=\"bi bi-file-earmark\"></i>");
		out.append("<span>Blank</span>");
		out.append("</a>");
		out.append("</li><!-- End Blank Page Nav -->");

		out.append("</ul>");

		out.append("</aside><!-- End Sidebar-->");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}