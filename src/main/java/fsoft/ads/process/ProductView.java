package fsoft.ads.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.javatuples.Quartet;

import fsoft.ConnectionPool;
import fsoft.library.Utilities;
import fsoft.library.Utilities_Date;
import fsoft.objects.ProductObject;

/**
 * Servlet implementation class ProductView
 */
@WebServlet("/product/view")
public class ProductView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductView() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	    view(request, response);
	}
	
	protected void view(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException{
		response.setContentType(CONTENT_TYPE);
		// Tìm bộ quản lí kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		ProductControl pc = new ProductControl(cp);
		if (cp == null) {
			getServletContext().setAttribute("CPool", pc.getCP());
		}
		//tìm từ khóa
		String key = request.getParameter("keyword");
		String saveKey = (key != null && !key.equalsIgnoreCase("")) ? key.trim() : "";
		
		//tạo đối tượng lưu trữ thông tin bộ lọc
		ProductObject similar = new ProductObject();
		similar.setProduct_id(10);
		
		String trash = request.getParameter("trash");
		if(trash != null) {
			similar.setProduct_deleted(1);
		}
		similar.setProduct_name(saveKey);
		short page = Utilities.getPageParam(request, "p"); //lấy tham số phân trang
				
		//lấy cấu trúc
		Quartet<ProductObject, Short, Byte, PRO_ORDER> infos = new Quartet<>(similar, page, (byte) 10, PRO_ORDER.ID);
		ArrayList<String> view = pc.viewProducts(infos);
		
		//tạo đối tượng xuất nội dung	
		PrintWriter out = response.getWriter();

		
		out.append("<main id=\"main\" class=\"main\">");

		out.append("<div class=\"pagetitle d-flex justify-content-between\">");
		out.append("<h1>Thông tin sản phẩm</h1>");
		out.append("<nav class\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/Group9/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Sản phẩm</li>");
		out.append("<li class=\"breadcrumb-item active\">Danh sách</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section\">");
		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");

		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body\">");
		out.append(
				"<button type=\"button\" class=\"btn btn-primary btnthem mt-2\" data-bs-toggle=\"modal\" data-bs-target=\"#addProduct\">");
		out.append("<i class=\"bi bi-person-add\"></i> Thêm mới");
		out.append("</button>");

		out.append(
				"<div class=\"modal fade\" id=\"addProduct\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"addProductLabel\" aria-hidden=\"true\">");
		out.append("<div class=\"modal-dialog modal-lg\">");
		out.append("<div class=\"modal-content\">");

		out.append("<form method=\"post\" action=\"/Group9/product/view\" class=\"needs-validation\" novalidate>");

		out.append("<div class=\"modal-header text-bg-primary\">");
		out.append(
				"<h1 class=\"modal-title fs-5\" id=\"addProductLabel\"><i class=\"bi bi-person-plus\"></i> Thêm mới sản phẩm</h1>");
		out.append(
				"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		out.append("</div>");
		out.append("<div class=\"modal-body\">");
		
		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-sm-6\">");
		out.append("<label for=\"product_name\" class=\"form-label\">Tên sản phẩm</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"product_name\" name=\"txtName\" required>");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin tên sản phẩm</div>");
		out.append("</div>");
		
		out.append("<div class=\"col-sm-6\">");
		out.append("<label for=\"product_description\" class=\"form-label\">Mô tả sản phẩm</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"product_description\" name=\"txtDes\" required>");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin mô tả sản phẩm</div>");
		out.append("</div>");
		out.append("</div>");
		

		out.append("<div class=\"row mb-3\">");
		out.append("<div class=\"col-sm-4\">");
		out.append("<label for=\"product_color\" class=\"form-label\">Màu sắc</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"product_color\" name=\"txtColor\">");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin màu sắc sản phẩm</div>");
		out.append("</div>");
		
		out.append("<div class=\"col-sm-4\">");
		out.append("<label for=\"product_unit\" class=\"form-label\">Đơn vị</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"product_unit\" name=\"txtUnit\"required>");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin đơn vị sản phẩm</div>");
		out.append("</div>");

		out.append("<div class=\"col-sm-4\">");
		out.append("<label for=\"product_price\" class=\"form-label\">Giá</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"product_price\" name=\"txtPrice\"required>");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin giá sản phẩm</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3\">");

		out.append("<div class=\"col-sm-4\">");
		out.append("<label for=\"product_sex\" class=\"form-label\">Giới tính</label>");
		out.append("<select class=\"form-select\" id=\"product_sex\" name=\"slcSex\">");
		out.append("<option value=\"\"></option>");
		out.append("<option value=\"1\">Male</option>");
		out.append("<option value=\"2\">Female</option>");
		out.append("</select>");
		out.append("<div class=\"invalid-feedback\" > Xác định giới tính </div>");
		out.append("</div>");

		out.append("<div class=\"col-sm-4\">");
		out.append("<label for=\"product_size\" class=\"form-label\">Kích cỡ</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"product_size\" name=\"txtSize\"required>");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin kích cỡ sản phẩm</div>");
		out.append("</div>");

		out.append("<div class=\"col-sm-4\">");
		out.append("<label for=\"product_quantity\" class=\"form-label\">Số lượng</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"product_quantity\" name=\"txtQuantity\"required>");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin số lượng sản phẩm</div>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3\">");

		out.append("<div class=\"col-sm-4\">");
		out.append("<label for=\"category_id\" class=\"form-label\">Mã loại</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"category_id\" name=\"txtCategory_id\"required>");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin loại sản phẩm</div>");
		out.append("</div>");

		out.append("<div class=\"col-sm-4\">");
		out.append("<label for=\"manufacturer_id\" class=\"form-label\">Mã NSX</label>");
		out.append(
				"<input type=\"text\" class=\"form-control\" id=\"manufacturer_id\" name=\"txtManufacturer_id\"required>");
		out.append("<div class=\"invalid-feedback\">Thiếu thông tin NSX</div>");
		out.append("</div>");
		

		out.append("</div>");

		out.append("</div>");
		out.append("<div class=\"modal-footer\">");
		out.append(
				"<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-person-plus-fill\"></i>Thêm mới</button>");
		out.append("<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>");

		out.append("</div>");

		out.append("</form>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");
		out.append(view.get(0));

		out.append("</div>");// card-body
		out.append("</div>");// card

		// biểu đồ

		out.append(view.get(1));

		out.append("</div>");// col-lg-12

		out.append("</div>");
		out.append("</section>");

		out.append("</main><!-- End #main -->");
		
		// Tham chiếu tìm kiếm header
		RequestDispatcher header = request.getRequestDispatcher("/header?pos=productview");
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");

		// Lấy thông tin trên giao diện
		String name = request.getParameter("txtName");
		String des = request.getParameter("txtDes");
		String color = request.getParameter("txtColor");
		String unit = request.getParameter("txtUnit");
		int price = Integer.parseInt(request.getParameter("txtPrice"));
		int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
		String size = request.getParameter("txtSize");
		int sex = Utilities.getIntParam(request, "slcSex");
		int cate = Integer.parseInt(request.getParameter("txtCategory_id"));
		int manu = Integer.parseInt(request.getParameter("txtManufacturer_id"));
		
		String date = Utilities_Date.getDate();

		// Tạo đối tượng lưu trữ mới
		ProductObject nPro = new ProductObject();
		nPro.setProduct_name(name);
		nPro.setProduct_description(des);
		nPro.setProduct_color(color);
		nPro.setProduct_unit(unit);
		nPro.setProduct_price(price);
		nPro.setProduct_quantity(quantity);
		nPro.setProduct_sex(sex);
		nPro.setProduct_size(size);
		nPro.setCategory_id(cate);
		nPro.setManufacturer_id(manu);
		nPro.setProduct_last_modified(date);

		System.out.println(nPro.toString());
		
		// Tìm bộ quản lý kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
		ProductControl pc = new ProductControl(cp);
		if (cp == null) {
			getServletContext().setAttribute("CPool", pc.getCP());
		}

		// Thực hiện thêm mới
		boolean result = pc.addProduct(nPro);
		
		pc.releaseConnection();

		// Trả kết quả
		if (result) {
			response.sendRedirect("/Group9/product/view");
		} else {
			response.sendRedirect("/Group9/product/view?err=notok");
		}

	}

}
