package fsoft.ads.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fsoft.ConnectionPool;
import fsoft.library.Utilities_Helper;
import fsoft.objects.ProductObject;

/**
 * Servlet implementation class Chart
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
		PrintWriter out = response.getWriter();
		out.append("<!doctype html>");
		out.append("<html lang=\"en\">");
		out.append("<head>");
		out.append("<meta charset=\"utf-8\">");
		out.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.append("<title>Product View</title>");
		out.append("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN\" crossorigin=\"anonymous\">");
		out.append("<link href=\"/Group9/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\" />");
		out.append("<link href=\"/Group9/css/apexcharts.css\" rel=\"stylesheet\" type=\"text/css\" />");
		out.append("<script src=\"/Group9/js/apexcharts.min.js\" language=\"javascript\"></script>");
		out.append("</head>");
		out.append("<body>");
		
		out.append("<div class=\"container-lg\">");
		out.append("<div class=\"card my-4\">");
		out.append("<div class=\"card-header text-bg-primary\"></div>");
		out.append("<div class=\"card-body\">");
		ProductImpl pro = new ProductImpl(cp);
		ArrayList<ProductObject> list = pro.getProductObjects(null, (byte)10);

		out.append(this.viewChart(list));
		
		out.append("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL\" crossorigin=\"anonymous\"></script>");
		out.append("<script src=\"/Group9/js/apexcharts.min.js\" language=\"javascript\"></script>");
		out.append("<script src=\"https://cdn.jsdelivr.net/npm/apexcharts@3.35.1/dist/apexcharts.min.js\"></script>");
		out.append("</body>");
		out.append("</html>");
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
	
	private static StringBuilder viewChart(ArrayList<ProductObject> list) {
		    StringBuilder names = new StringBuilder();
		    StringBuilder price = new StringBuilder();
		    StringBuilder quantities = new StringBuilder();
		    StringBuilder sold = new StringBuilder();

		    list.forEach(item -> {
		        price.append(item.getProduct_price());
		        quantities.append(item.getProduct_quantity());
		        sold.append(item.getProduct_sold());
		        names.append("'" + Utilities_Helper.decode(item.getProduct_name()) + " (" + item.getProduct_id() + ")'");
		        if (list.indexOf(item) < list.size() - 1) {
		            price.append(",");
		            quantities.append(",");
		            sold.append(",");
		            names.append(",");
		        }
		    });

		    StringBuilder tmp = new StringBuilder();
		    tmp.append("<div class=\"card\">");
		    tmp.append("<div class=\"col-sm-4\">");
		    tmp.append("<label for=\"select\" class=\"form-label\">Hiển thị theo</label>");
		    tmp.append("<select class=\"form-select\" id=\"select\" name=\"slcSelect\" onchange=\"updateChart()\">");
		    tmp.append("<option value=\"\"></option>");
		    tmp.append("<option value=\"product_sold\">Số lượng đã bán</option>");
		    tmp.append("<option value=\"product_price\">Giá tiền</option>");
		    tmp.append("<option value=\"product_quantity\">Số lượng hàng</option>");
		    tmp.append("</select>");
		    tmp.append("</div>");
		    tmp.append("<div class=\"card-body col-lg-10\">");
		    tmp.append("<h5 class=\"card-title\">Biểu đồ Sản phẩm</h5>");
		    tmp.append("<div id=\"barChart\"></div>");
		    tmp.append("<script>");
		    tmp.append("document.addEventListener(\"DOMContentLoaded\", () => {");
		    tmp.append("  updateChart();");
		    tmp.append("});");

		    tmp.append("function updateChart() {");
		    tmp.append("  var selectedProperty = document.getElementById('select').value;");
		    tmp.append("  var values = [];");

		    tmp.append("  switch (selectedProperty) {");
		    tmp.append("    case 'product_price':");
		    tmp.append("      values = [" + price.toString() + "];");
		    tmp.append("      break;");
		    tmp.append("    case 'product_quantity':");
		    tmp.append("      values = [" + quantities.toString() + "];");
		    tmp.append("      break;");
		    tmp.append("    case 'product_sold':");
		    tmp.append("      values = [" + sold.toString() + "];");
		    tmp.append("      break;");
		    tmp.append("  }");

		    tmp.append("  var names = [" + names.toString() + "];");

		    tmp.append("  new ApexCharts(document.querySelector(\"#barChart\"), {");
		    tmp.append("    series: [{");
		    tmp.append("      name: selectedProperty,");
		    tmp.append("      data: values");
		    tmp.append("    }],");
		    tmp.append("    chart: {type: 'bar', height: 350, fontFamily: 'Tahoma, sans-serif'},");
		    tmp.append("    plotOptions: {bar: {borderRadius: 4, horizontal: true,}},");
		    tmp.append("    dataLabels: {enabled: false},");
		    tmp.append("    xaxis: {");
		    tmp.append("      categories: names,");
		    tmp.append("      labels: {");
		    tmp.append("        show: true,");
		    tmp.append("        style: {");
		    tmp.append("          colors: [],");
		    tmp.append("          fontSize: '18px',");
		    tmp.append("          fontFamily: 'Helvetica, Arial, sans-serif',");
		    tmp.append("          fontWeight: 600,");
		    tmp.append("          cssClass: 'apexcharts-xaxis-label',");
		    tmp.append("        },");
		    tmp.append("      },");
		    tmp.append("    },");
		    tmp.append("    yaxis: {");
		    tmp.append("      show: true,");
		    tmp.append("      labels: {");
		    tmp.append("        show: true,");
		    tmp.append("        align: 'right',");
		    tmp.append("        minWidth: 250,");
		    tmp.append("        maxWidth: 400,");
		    tmp.append("        style: {");
		    tmp.append("          colors: [],");
		    tmp.append("          fontSize: '15px',");
		    tmp.append("          fontFamily: 'Helvetica, Arial, sans-serif',");
		    tmp.append("          fontWeight: 400,");
		    tmp.append("          cssClass: 'apexcharts-yaxis-label',");
		    tmp.append("        },");
		    tmp.append("      },");
		    tmp.append("    },");
		    tmp.append("  }).render();");
		    tmp.append("}");

		    tmp.append("document.addEventListener(\"DOMContentLoaded\", () => {");
		    tmp.append("  var selectElement = document.getElementById('select');");
		    tmp.append("  selectElement.addEventListener('change', () => {");
		    tmp.append("    var selectedValue = selectElement.value;");
		    tmp.append("    console.log(selectedValue);");
		    tmp.append("  });");
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
