package fsoft.ads.process;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.javatuples.*;

import fsoft.library.Utilities_Helper;
import fsoft.objects.ProductObject;

public class ProductLibrary {
	
	public static ArrayList<String> viewProducts(
			Triplet<ArrayList<ProductObject>, Integer, HashMap<Integer, Integer>> datas,
			Quartet<ProductObject, Short, Byte, PRO_ORDER> infos) {

		ArrayList<ProductObject> items = datas.getValue0();
		int total = datas.getValue1();
		HashMap<Integer, Integer> days = datas.getValue2();
		// thông tin phân trang
		short page = infos.getValue1();
		byte totalPerPage = infos.getValue2();
		ProductObject similar = infos.getValue0();

		// lấy cấu trúc trình bày và danh sách phân trang
		ArrayList<String> tmp = new ArrayList<>();

		// danh sach
		StringBuilder list = new StringBuilder();
		list.append("<table class=\"table table-striped table-hover table-sm \">");
		list.append("<thead>");
		list.append("<tr>");
		//list.append("<th scope=\"col\">#</th>");
		list.append("<th scope=\"col\">Tên sản phẩm</th>");
		list.append("<th scope=\"col\">Kích cỡ</th>");
		list.append("<th scope=\"col\">Màu sắc</th>");
		list.append("<th scope=\"col\">Giới tính</th>");
		list.append("<th scope=\"col\">Giá</th>");
		list.append("<th scope=\"col\" colspan=\"3\" >Thực hiện</th>");
		list.append("</tr>");
		list.append("</thead>");
		
		list.append("<tbody>");

		items.forEach(item -> {
			Integer daysValue = days.get(item.getProduct_id());
			if (daysValue != null && daysValue.intValue() <= 2) {

				list.append("<tr class=\"align-items-center\">");
			} else {
				list.append("<tr class=\"align-items-center\" >");
			}
			String value ="";
			int sex = item.getProduct_sex();
			switch(sex) {
				case 1:
					value += "Nam";
					break;
				case 2:
					value += "Nữ";
					break;
				default:
			}
			int price = item.getProduct_price();
			String formatPrice = ProductLibrary.formatNumberWithComma(price);
			//list.append("<th scope=\"row\">" + item.getProduct_id() + "</th>");
			list.append("<td class=\"align-middle\">" + item.getProduct_name() + "</td>");
			list.append("<td class=\"align-middle\">" + item.getProduct_size() + "</td>");
			list.append("<td class=\"align-middle\">" + item.getProduct_color() + "</td>");
			list.append("<td class=\"align-middle\">" + value + "</td>");
			list.append("<td class=\"align-middle\">" + formatPrice + "</td>");
			list.append("<td><a href=\"/Group9/product/profiles?id=" + item.getProduct_id()
					+ "&t=over\"class=\"btn btn-outline-primary btn-sm\" > <i class=\"bi bi-eye-fill\"></i> </a></td>");
			list.append("<td><a href=\"/Group9/product/profiles?id=" + item.getProduct_id()
					+ "&t=edit\"class=\"btn btn-warning btn-sm\" > <i class=\"bi bi-pencil-square\"></i> </a></td>");

			//
			list.append(
					"<td><a href=\"#\" class=\"btn btn-danger btn-sm\" data-bs-toggle=\"modal\"  data-bs-target=\"#delProduct"
							+ item.getProduct_id() + "\" > <i class=\"bi bi-trash3\"></i> </a></td>");
			list.append(ProductLibrary.getDelModel(item, false));
			list.append("</tr>");
		});

		list.append("</tbody>");
		list.append("</table>");



		String key = similar.getProduct_name();
		// phân trang
		list.append(ProductLibrary.getPaging("/Group9/product/view", key, total, page, totalPerPage));


		tmp.add(list.toString());// danh sách

		// Biểu đồ
		tmp.add(ProductLibrary.viewChart(items).toString());

		return tmp;
	}
	
	public static ArrayList<String> viewDeleteProducts(
			Triplet<ArrayList<ProductObject>, Integer, HashMap<Integer, Integer>> datas,
			Quartet<ProductObject, Short, Byte, PRO_ORDER> infos) {
		// bóc tách dữ liệu để xử lý
		ArrayList<ProductObject> items = datas.getValue0();
		int total = datas.getValue1();
		// thông tin phân trang
		short page = infos.getValue1();
		byte totalPerPage = infos.getValue2();
		ProductObject similar = infos.getValue0();

		// cấu trúc trình bày danh sách và phân trang
		ArrayList<String> tmp = new ArrayList<>();

		// danh sách
		StringBuilder list = new StringBuilder();

		list.append("<table class=\"table table-striped table-sm\">");
		list.append("<thead>");
		list.append("<tr>");
		list.append("<th scope=\"col\">#</th>");
		list.append("<th scope=\"col\">Tên sản phẩm</th>");
		list.append("<th scope=\"col\">Kích cỡ</th>");
		list.append("<th scope=\"col\">Màu sắc</th>");
		list.append("<th scope=\"col\">Giới tính</th>");
		list.append("<th scope=\"col\">Ngày xoá</th>");
		list.append("<th scope=\"col\" colspan=\"2\">Thực hiện</th>");
		list.append("</tr>");
		list.append("</thead>");

		list.append("<tbody>");
		
		items.forEach(item -> {
			
			String value ="";
			int sex = item.getProduct_sex();
			switch(sex) {
				case 1:
					value += "Nam";
					break;
				case 2:
					value += "Nữ";
					break;
				default:
			}
			list.append("<tr>");
			list.append("<th scope=\"row\">" + item.getProduct_id() + "</th>");
			list.append("<td>" + item.getProduct_name() + "</td>");
			list.append("<td>" + item.getProduct_size() + "</td>");
			list.append("<td>" + item.getProduct_color() + "</td>");
			list.append("<td>" + value + "</td>");
			list.append("<td>" + item.getProduct_last_modified() + "</td>");
			///
			list.append("<td><a href=\"/Group9/product/profiles?id=" + item.getProduct_id()
					+ "&res\"class=\"btn btn-primary btn-sm\" > <i class=\"bi bi-reply\"></i> </a></td>");

			/////
			list.append(
					"<td><a href=\"#\" class=\"btn btn-danger btn-sm\" data-bs-toggle=\"modal\"  data-bs-target=\"#delProduct"
							+ item.getProduct_id() + "\" > <i class=\"bi bi-trash3\"></i> </a></td>");
			list.append(ProductLibrary.getDelModel(item, true));
			
	
			list.append("</tr>");

		});

		list.append("</tbody>");
		list.append("</table>");
		String key = similar.getProduct_name();
		// phân trang
		list.append(ProductLibrary.getPaging("/Group9/product/view", key, total, page, totalPerPage));


		tmp.add(list.toString());// danh sách

		// Biểu đồ
		tmp.add(ProductLibrary.viewChart(items).toString());

		return tmp;

	}

	private static StringBuilder getDelModel(ProductObject item, boolean isAbsolute) {
		StringBuilder out = new StringBuilder();

		out.append("<div class=\"modal fade\" id=\"delProduct" + item.getProduct_id()
				+ "\" tabindex=\"-1\" aria-labelledby=\"delProductLabel" + item.getProduct_id()
				+ "\" aria-hidden=\"true\">");
		out.append("<div class=\"modal-dialog\">");
		out.append("<div class=\"modal-content\">");
		out.append("<div class=\"modal-header text-bg-danger \">");
		out.append("<h1 class=\"modal-title fs-5\" id=\"delProductLabel" + item.getProduct_id()
				+ "\"><i class=\"bi bi-exclamation-triangle\"></i> Xoá sản phẩm</h1>");
		out.append(
				"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		out.append("</div>");
		out.append("<div class=\"modal-body\">");
		out.append("<h3 class=\"text-center\">Bạn có chắc chắn xoá?</h3>");
		out.append("<p class=\"fs-6\">" + item.getProduct_name() + "</p>");
		if (isAbsolute) {
			out.append("<p class=\"\">Hệ thống sẽ xoá sản phẩm vĩnh viễn, bạn không thể phục hồi trở lại! </p>");
		} else {
			out.append("<p class=\"\">Hệ thống tạm thời lưu vào thùng rác, bạn có thể phục hồi trong 30 ngày! </p>");
		}

		out.append("</div>");
		if (isAbsolute) {
			out.append("<form method=\"post\" action=\"/Group9/product/profiles?id=" + item.getProduct_id()
					+ "&del=abs\">");

		} else {
			out.append("<form method=\"post\" action=\"/Group9/product/profiles?id=" + item.getProduct_id()
					+ "&del=trash\">");

		}

		out.append("<div class=\"modal-footer text-bg-light \">");
		out.append(
				"<button type=\"submit\" class=\"btn btn-danger\"><i class=\"bi bi-exclamation-triangle\"></i> Xoá</button>");

		out.append(
				"<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\"><i class=\"bi bi-x-square\"></i> Huỷ</button>");

		out.append("</div>");
		out.append("</form>");
		out.append("</div>");
		out.append("</div>");
		out.append("</div>");

		return out;
	}

	public static String getPaging(String url, String key, int total, short page, byte totalPerPage) {
		// tính số trang
		short countPages = (short) (total / totalPerPage);
		if (total % totalPerPage != 0) {
			countPages++;
		}

		if (page <= 0 || page > countPages) {
			page = 1;
		}

		// xử lý key
		if (key != null && !key.equalsIgnoreCase("")) {
			key = "&keyword=" + key;
		} else {
			key = "";
		}

		StringBuilder left = new StringBuilder();
		StringBuilder right = new StringBuilder();
		int count = 0;

		for (short p = (short) (page - 1); p > 0; p--) {
			left.insert(0, "<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "?p=" + p + key + "\">" + p
					+ "</a></li>");
			if (++count >= 2) {
				break;
			}
		}
		if (page - 1 >= 3) {
			left.insert(0, "<li class=\"page-item\"><a class=\"page-link\" href=\"#\">...</a></li>");
		}
		count = 0;
		for (short p = (short) (page + 1); p <= countPages; p++) {
			right.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "?p=" + p + key + "\">" + p
					+ "</a></li>");
			if (++count >= 2) {
				break;
			}
		}
		if (countPages - page >= 3) {
			right.append("<li class=\"page-item disable\"><a class=\"page-link\" href=\"#\">...</a></li>");
		}

		StringBuilder tmp = new StringBuilder();// tạo cấu trúc hoàn chỉnh
		// tmp.append("<div class=\"card\">");
		// tmp.append("<div class=\"card-body\">");
		// tmp.append("<h5 class=\"card-title\">Disabled and active states</h5>");
		tmp.append("<nav aria-label=\"...\">");
		tmp.append("<ul class=\"pagination justify-content-center\">");
		tmp.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url
				+ "?p=1\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
		tmp.append(left);
		tmp.append("<li class=\"page-item active\" aria-current=\"page\"><a class=\"page-link\" href=\"#\">" + page
				+ "</a></li>");
		tmp.append(right);
		tmp.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "?p=" + countPages
				+ "\" ><span aria-hidden=\"true\">&raquo;</span></a></li>");
		tmp.append("</ul>");
		tmp.append("</nav>");
		// tmp.append("</div>");
		// tmp.append("</div>");

		return tmp.toString();

	}
	
	private static StringBuilder viewChart(ArrayList<ProductObject> list) {

		StringBuilder names = new StringBuilder();
		StringBuilder quantities = new StringBuilder();
		list.forEach(item -> {
			quantities.append(item.getProduct_quantity());
			names.append("'" + Utilities_Helper.decode(item.getProduct_name()) + " (" + item.getProduct_id() + ")'");
			if (list.indexOf(item) < list.size() - 1) {
				quantities.append(",");
				names.append(",");
			}
		});

		StringBuilder tmp = new StringBuilder();
		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body col-lg-10\">");
		tmp.append("<h5 class=\"card-title\">Biểu đồ Sản phẩm</h5>");
		tmp.append("<div id=\"barChart\"></div>");
		tmp.append("<script>");
		tmp.append("document.addEventListener(\"DOMContentLoaded\", () => {");
		tmp.append("new ApexCharts(document.querySelector(\"#barChart\"), {");
		tmp.append("series: [{");
		tmp.append("name: 'Số lượng Sản phẩm',");
		tmp.append("data: [" + quantities + "]");
		tmp.append("}],");
		tmp.append("chart: {type: 'bar', height: 350, fontFamily: 'Tahoma, sans-serif'},");
		tmp.append("plotOptions: {bar: {borderRadius: 4, horizontal: true,}},");
		tmp.append("dataLabels: {enabled: false},");
		tmp.append("");
		tmp.append("xaxis: {");
		tmp.append("categories: [" + names + "],");
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
	public static String formatNumberWithComma(int number) {
        // Sử dụng DecimalFormat để thêm dấu chấm sau mỗi 3 chữ số
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }
	

}
