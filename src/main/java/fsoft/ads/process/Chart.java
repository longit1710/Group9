package fsoft.ads.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fsoft.ConnectionPool;
import fsoft.objects.ProductObject;

/**
 * Servlet implementation class chart
 */
@WebServlet("/product/chart")
public class Chart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Chart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
		// Tìm bộ quản lí kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		ProductControl pc = new ProductControl(cp);
		if (cp == null) {
			getServletContext().setAttribute("CPool", pc.getCP());
		}
		
		//abc
		PrintWriter out = response.getWriter();
		
		out.append("<main id=\"main\" class=\"main\">");
		
		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/Group9/view\"><i class=\"bi bi-house-fill\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Biểu đồ</li>");
		out.append("<li class=\"breadcrumb-item active\">Sản phẩm</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");
		ProductImpl pro = new ProductImpl(cp);
		ArrayList<ProductObject> list = pro.getProductObjects(null, (byte)10);

		out.append(this.viewChart(list));
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
	
	private StringBuilder viewChart(ArrayList<ProductObject> list) {
		
		StringBuilder names = new StringBuilder();
		StringBuilder quantities = new StringBuilder();
		list.forEach(item->{
			quantities.append(item.getProduct_quantity());
			names.append("'"+item.getProduct_name()+"'");
			if(list.indexOf(item)<list.size()-1) {
				quantities.append(",");
				names.append(",");
			}
		});
		
		
		
		StringBuilder tmp = new StringBuilder();
		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Biểu đồ Sản phẩm</h5>");
		tmp.append("<div id=\"barChart\"></div>");
		tmp.append("<script>");
		tmp.append("document.addEventListener(\"DOMContentLoaded\", () => {");
		tmp.append("new ApexCharts(document.querySelector(\"#barChart\"), {");
		tmp.append("series: [{");
		tmp.append("name: 'Số lượng Sản phẩm',");
		tmp.append("data: ["+quantities+"]");
		tmp.append("}],");
		tmp.append("chart: {type: 'bar', height: 350, fontFamily: 'Tahoma, sans-serif'},");
		tmp.append("plotOptions: {bar: {borderRadius: 4, horizontal: true,}},");
		tmp.append("dataLabels: {enabled: false},");
		tmp.append("");
		tmp.append("xaxis: {");
		tmp.append("categories: ["+names+"],");
		tmp.append("labels: {");
		tmp.append("show: true,");
		tmp.append("style: {");
		tmp.append("colors: [],");
		tmp.append("fontSize: '18px',");
		tmp.append("fontFamily: 'Helvetica, Arial, sans-serif',");
		tmp.append("fontWeight: 600,");
		tmp.append("cssClass: 'apexcharts-xaxis-label',");
		tmp.append("},");
		tmp.append("}");
		tmp.append("},");
		tmp.append("");
		tmp.append("yaxis: {");
		tmp.append("show: true,");
		tmp.append("labels: {");
		tmp.append("show: true,");
		tmp.append("align: 'right',");
		tmp.append("minWidth: 250,");
		tmp.append("maxWidth: 400,");
		tmp.append("style: {");
		tmp.append("colors: [],");
		tmp.append("fontSize: '15px',");
		tmp.append("fontFamily: 'Helvetica, Arial, sans-serif',");
		tmp.append("fontWeight: 400,");
		tmp.append("cssClass: 'apexcharts-yaxis-label',");
		tmp.append("},");
		tmp.append("},");
		tmp.append("}");
		tmp.append("}).render();");
		tmp.append("});");
		tmp.append("</script>");
		tmp.append("</div>");
		tmp.append("</div>");
		
		
		return tmp;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
