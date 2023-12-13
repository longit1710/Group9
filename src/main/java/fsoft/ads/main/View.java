package fsoft.ads.main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class View
 */
@WebServlet("/view")
public class View extends HttpServlet {
	
	/**
	 * Phần Blank để thực hiện view đơn giản
	 * getSession là pt đsd để lấy ra hoặc tạo ra các phiên làm việc cho một người dùng
	 * khi họ tương tác với web trên servlet 
	 * getAttribute là pt đsd để lấy giá trị của một thuộc tính đã được đặt trong một phiên
	 * trong web dựa trên servlet
	 * getRequestDispatcher là pt để chuyển hướng yêu cầu từ một servlet đến một tài nguyên khác trong cùng 
	 * website (servlet, jsp, html)
	 */
	
	private static final long serialVersionUID = 1L;
       
	//xác định kiểu nội dung xuất về trình khách (client)
	private static final String CONTENT_TYPE = "text/html; charset=utf-8"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		view(request, response);	
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	
	protected void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
		
		//Tạo đối tượng xuất nội dung
		PrintWriter out = response.getWriter();
		
		//Tham chiếu tìm kiếm header
		RequestDispatcher header = request.getRequestDispatcher("/header");
		if(header != null) {
			header.include(request,response);
		}		
			
		
		
		out.append("<main id=\"main\" class=\"main\">");
		
		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>Blank Page</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/Group9/view\"><i class=\"bi bi-house-fill\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">A</li>");
		out.append("<li class=\"breadcrumb-item active\">B</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");
		
		out.append("<section class=\"section\">");
		out.append("<div class=\"row\">");
		
		out.append("<div class=\"col-lg-12\">");
		
		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body\">");
		out.append("<h5 class=\"card-title\">C</h5>");
		out.append("<p>This is an examle page with no contrnt. You can use it as a starter for your custom pages.</p>");
		out.append("</div>");
		out.append("</div>");
		
		out.append("</div>");
		out.append("</div>");
		out.append("</section>");
		
		out.append("</main><!-- End #main -->");
		
		//Tham chiếu tìm kiếm footer
		RequestDispatcher footer = request.getRequestDispatcher("/footer");
		if(footer != null) {
			footer.include(request,response);
		}	
				
		
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}