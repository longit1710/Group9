package fsoft.ads.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fsoft.ConnectionPool;
import fsoft.library.Utilities;
import fsoft.library.Utilities_Date;
import fsoft.library.Utilities_Helper;
import fsoft.objects.ProductObject;

/**
 * Servlet implementation class profiles
 */
@WebServlet("/product/profiles")
public class Profiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset = utf-8";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profiles() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		view(request, response);

		// abc
	}
	protected void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
		
		int id = Utilities.getIntParam(request, "id");
		// xác định vị trí tab làm việc
		String tab = request.getParameter("t");
		HashMap<String, String> tab_active = new HashMap<>();
		HashMap<String, String> show_active = new HashMap<>();
		if (tab != null && (tab.equalsIgnoreCase("over") || tab.equalsIgnoreCase("edit"))) {
			tab_active.put(tab, "active");
			show_active.put(tab, "show active");
		} else {
			tab_active.put("over", "active");
			show_active.put("over", "show active");
		}

		String name = "", size = "", color = "";
		int price = 0;
		String unit = "";
		String description = "";
		int sex = 0;
		int quantity = 0;
		int sold = 0;
		int deleted = 0;
		int manu_id = 0;
		int cat_id = 0;
		String last_modified = "";

		if (id > 0) {
			// Tìm bộ quản lí kết nối
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

			ProductControl pc = new ProductControl(cp);
			if (cp == null) {
				getServletContext().setAttribute("CPool", pc.getCP());
			}
			ProductObject ePro = null;

			// tìm tham số phục hồi xoá
			String res = request.getParameter("res");
			if (res != null) {
				ePro = new ProductObject();
				ePro.setProduct_id(id);
				String date = Utilities_Date.getDate();
				ePro.setProduct_last_modified(date);
				System.out.println("ID phuc hoi: "+id);
				boolean result = pc.editProduct(ePro, EDIT_TYPE.RESTORE);
				if (result) {
					response.sendRedirect("/Group9/product/view?trash");
				} else {
					response.sendRedirect("/Group9/product/view?trash&err");
				}
			} else {
				ePro = pc.getProductObject(id);
				pc.releaseConnection();

				if (ePro != null) {
					System.out.println(ePro.toString());
					name = ePro.getProduct_name();
					size = ePro.getProduct_size();
					color = ePro.getProduct_color();
					price = ePro.getProduct_price();
					unit = ePro.getProduct_unit();
					description = ePro.getProduct_description();
					sex = ePro.getProduct_sex();
					quantity = ePro.getProduct_quantity();
					sold = ePro.getProduct_sold();
					manu_id = ePro.getManufacturer_id();
					cat_id = ePro.getCategory_id();
					last_modified = ePro.getProduct_last_modified();
					deleted = ePro.getProduct_deleted();
				}
			}
		} else {
			response.sendRedirect("/Group9/product/view");
		}
		// tạo đối tượng xuất nội dung
		PrintWriter out = response.getWriter();
		out.append("<main id=\"main\" class=\"main\">");

		out.append("<div class=\"pagetitle d-flex justify-content-between\">");
		out.append("<h1>Thông tin sản phẩm</h1>");
		out.append("<nav class\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/Group9/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Sản phẩm</li>");
		out.append("<li class=\"breadcrumb-item active\">Thông tin sản phẩm</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");
		// start section
		out.append("<section class=\"section profile\">");
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-xl-3\">");
		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body profile-card pt-4 d-flex flex-column align-items-center\">");
		out.append("<img src=\"/Group9/img/profile-img.jpg\" alt=\"Profile\" class=\"rounded-circle\">");
		out.append("<h2>" + name + "</h2>");
		out.append("<h3>" + description + "</h3>");
		out.append("<div class=\"social-links mt-2\">");
		out.append("<a href=\"#\" class=\"twitter\"><i class=\"bi bi-twitter\"></i></a>");

		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("</div>");

		out.append("<div class=\"col-xl-9\">");

		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body pt-3\">");
		out.append("<!-- Bordered Tabs -->");
		out.append("<ul class=\"nav nav-tabs nav-tabs-bordered\">");

		out.append("<li class=\"nav-item\">");
		out.append("<button class=\"nav-link " + tab_active.getOrDefault("over", "")
				+ "\" data-bs-toggle=\"tab\" data-bs-target=\"#profile-overview\"><i class=\"bi bi-info-square\"></i> Thông tin chung</button>");
		out.append("</li>");

		out.append("<li class=\"nav-item\">");
		out.append("<button class=\"nav-link" + tab_active.getOrDefault("edit", "")
				+ "\" data-bs-toggle=\"tab\" data-bs-target=\"#profile-edit\"><i class=\"bi bi-pencil-square\"></i> Chỉnh sửa</button>");
		out.append("</li>");

		out.append("</ul>");
		out.append("<div class=\"tab-content pt-2\">");

		out.append("<div class=\"tab-pane fade " + show_active.getOrDefault("over", "")
				+ " profile-overview\" id=\"profile-overview\">");
		out.append("<h5 class=\"card-title\">Giới thiệu</h5>");
		out.append("<p class=\"small fst-italic\">" + description + "</p>");

		out.append("<h5 class=\"card-title\">Chi tiết</h5>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Tên sản phẩm</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + name + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Kích cỡ</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + size + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Màu sắc</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + color + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Giới tính</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + sex + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Giá</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + price + "</div>");
		out.append("</div>");
		
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Số lượng</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + quantity + "</div>");
		out.append("</div>");


		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">NSX</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + manu_id + "</div>");
		out.append("</div>");

		out.append("</div>");

		out.append("<div class=\"tab-pane fade " + show_active.getOrDefault("edit", "")
				+ " profile-edit pt-3\" id=\"profile-edit\">");

		out.append("<!-- Profile Edit Form -->");
		out.append("<form class=\"needs-validation\" method=\"post\" action=\"/Group9/product/profiles?id=" + id
				+ "\" novalidate>");
		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"profileImage\" class=\"col-md-4 col-lg-3 col-form-label\">Hình ảnh</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<img src=\"/Group9/img/profile-img.jpg\" alt=\"Profile\">");
		out.append("<div class=\"pt-2\">");
		out.append(
				"<a href=\"#\" class=\"btn btn-primary btn-sm\" title=\"Tải ảnh mới\"><i class=\"bi bi-upload\"></i></a>");
		out.append(
				"<a href=\"#\" class=\"btn btn-danger btn-sm\" title=\"Loại bỏ ảnh\"><i class=\"bi bi-trash\"></i></a>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"productName\" class=\"col-md-4 col-lg-3 col-form-label\">Tên sản phẩm</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<input name=\"txtName\" type=\"text\" class=\"form-control\" id=\"productName\" required value=\""
				+ name + "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"about\" class=\"col-md-4 col-lg-3 col-form-label\">Giới thiệu</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<textarea name=\"txtDescription\" class=\"form-control\" id=\"description\" style=\"height: 100px\">" + description
				+ "</textarea>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"size\" class=\"col-md-4 col-lg-3 col-form-label\">Kích cỡ</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"txtSize\" type=\"text\" class=\"form-control\" id=\"size\" value=\"" + size
				+ "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"color\" class=\"col-md-4 col-lg-3 col-form-label\">Màu sắc</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"txtColor\" type=\"text\" class=\"form-control\" id=\"color\" value=\"" + color + "\">");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"unit\" class=\"col-md-4 col-lg-3 col-form-label\">Đơn vị</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"txtUnit\" type=\"text\" class=\"form-control\" id=\"unit\" value=\"" + unit + "\">");
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"sex\" class=\"col-md-4 col-lg-3 col-form-label\">Giới tính</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"txtSex\" type=\"text\" class=\"form-control\" id=\"sex\" value=\"" + sex + "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"price\" class=\"col-md-4 col-lg-3 col-form-label\">Giá</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"txtPrice\" type=\"text\" class=\"form-control\" id=\"price\" value=\"" + price
				+ "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3\">");
		out.append("<label for=\"quantity\" class=\"col-md-4 col-lg-3 col-form-label\">Số lượng</label>");
		out.append("<div class=\"col-md-8 col-lg-9\">");
		out.append("<input name=\"txtQuantity\" type=\"text\" class=\"form-control\" id=\"quantity\" value=\""
				+ quantity + "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"text-center\">");
		out.append(
				"<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-save2\"></i> Lưu thay đổi</button>");
		out.append("</div>");
		out.append("</form><!-- End Profile Edit Form -->");

		out.append("</div>");

		out.append("</div><!-- End Bordered Tabs -->");

		out.append("</div>");
		out.append("</div>");

		out.append("</div>");
		out.append("</div>");
		out.append("</section>");
		//end section
		out.append("</main><!-- End #main -->");

		// Tham chiếu tìm kiếm header
		RequestDispatcher header = request.getRequestDispatcher("/header?post=productprofiles");
		if (header != null) {
			header.include(request, response);
		}

		// Tham chiếu tìm kiếm footer
		RequestDispatcher footer = request.getRequestDispatcher("/footer");
		if (footer != null) {
			footer.include(request, response);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		
		// lấy id để chỉnh sửa //đánh dấu xoá(lưu thùng rác)
		int id = Utilities.getIntParam(request, "id");

		if (id > 0) {
			
			ProductObject nPro = new ProductObject();
			nPro.setProduct_id(id);
			// Tìm bộ quản lí kết nối
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

			ProductControl pc = new ProductControl(cp);
			if (cp == null) {
				getServletContext().setAttribute("CPool", pc.getCP());
			}

			boolean result = false;
			
			// Lấy thông tin trên giao diện
			String date = Utilities_Date.getDate();
			String name = request.getParameter("txtName");
			if(name != null && !name.equalsIgnoreCase("")) {
				// lấy thông tin đối tượng lưu trữ mới
				nPro.setProduct_last_modified(date);
				nPro.setProduct_name(name);
				String des = request.getParameter("txtDescription");
				String size = request.getParameter("txtSize");
				String color = request.getParameter("txtColor");
				String unit = request.getParameter("txtUnit");
				String sex = request.getParameter("txtSex");
				String price = request.getParameter("txtPrice");
				String quantity = request.getParameter("txtQuantity");
				
				nPro.setProduct_name(Utilities_Helper.encode(name));
				nPro.setProduct_description(des);
				nPro.setProduct_size(size);
				nPro.setProduct_color(color);
				nPro.setProduct_unit(unit);
				nPro.setProduct_sex(Integer.parseInt(sex));
				nPro.setProduct_price(Integer.parseInt(price));
				nPro.setProduct_quantity(Integer.parseInt(quantity));
				
				// Thực hiện update
				result = pc.editProduct(nPro, EDIT_TYPE.NORMAL);
			} else {
				System.out.println("Xu ly xoa");
				String del = request.getParameter("del");
				if (del != null) {
					nPro.setProduct_last_modified(date);
					if (del.equalsIgnoreCase("trash")) {
						// Thực hiện xóa lưu thùng rác
						result = pc.editProduct(nPro, EDIT_TYPE.TRASH);				
					} else if (del.equalsIgnoreCase("abs")) {
						result = pc.delProduct(nPro);
					}
				}
				System.out.println("Del: "+del);
			}
				
			System.out.println("result: "+ result);
			
			pc.releaseConnection();
			// Trả kết quả
			if (result) {
				String del = request.getParameter("del");
				if (del != null && del.equalsIgnoreCase("abs")) {
					response.sendRedirect("/Group9/product/view?trash");
				} else {
					response.sendRedirect("/Group9/product/view");
				}

			} else {
				response.sendRedirect("/Group9/product/view?err=notok");
			}
		}
	}

}
