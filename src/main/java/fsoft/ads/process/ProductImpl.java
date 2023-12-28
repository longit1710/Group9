package fsoft.ads.process;

import java.util.*;

import org.javatuples.Quartet;

import fsoft.*;
import fsoft.ads.basic.BasicImpl;
import fsoft.objects.*;
import java.sql.*;

public class ProductImpl extends BasicImpl implements Product {
	public ProductImpl(ConnectionPool cp, String objectName) {
		super(cp, objectName);
	}

	public ProductImpl(ConnectionPool cp) {
		super(cp, "Product");
	}

	@Override
	public boolean addProduct(ProductObject item) {
		//Kiểm tra xem sản phẩm đã tồn tại trong CSDL hay chưa
		if (isExisting(item)) {
			return false;
		}
		//Sử dụng StringBuilder để xây dựng câu lệnh SQL
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO tblproduct( ");
		sql.append("product_name, product_size, product_color,");
		sql.append("product_price, product_unit, product_description,");
		sql.append("product_sex, product_quantity, product_sold,");
		sql.append("product_deleted, manufacturer_id, category_id, product_last_modified) ");
		sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		try {
			//Thực hiện câu lệnh SQL sử dụng PreparedSatatement
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getProduct_name());
			pre.setString(2, item.getProduct_size());
			pre.setString(3, item.getProduct_color());
			pre.setInt(4, item.getProduct_price());
			pre.setString(5, item.getProduct_unit());
			pre.setString(6, item.getProduct_description());
			pre.setInt(7, item.getProduct_sex());
			pre.setInt(8, item.getProduct_quantity());
			pre.setInt(9, item.getProduct_sold());
			pre.setInt(10, item.getProduct_deleted());
			pre.setInt(11, item.getManufacturer_id());
			pre.setInt(12, item.getCategory_id());
			pre.setString(13, item.getProduct_last_modified());

			return this.add(pre);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}

	private boolean isExisting(ProductObject item) {
		boolean flag = false;
		String sql = "SELECT product_id FROM tblproduct WHERE product_name='" + item.getProduct_name() + "' ";
		ResultSet rs = this.get(sql, 0);
		if (rs != null) {
			try {
				if (rs.next()) {
					flag = true;
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public boolean editProduct(ProductObject item, EDIT_TYPE et) {
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE tblproduct SET ");
		switch (et) {
		case NORMAL:
			sql.append("product_name=?, product_description=?, product_size=?, ");
			sql.append("product_color=?, product_unit=?, product_sex=?, ");
			sql.append("product_price=?, product_quantity=? ");
			break;
		case TRASH:
			sql.append("product_deleted=?, product_last_modified=? ");
			break;
		case RESTORE:
			sql.append("product_deleted=?, product_last_modified=? ");
			break;
		default:
		}
		sql.append("WHERE product_id=?");
//		// In log để kiểm tra giá trị của các biến
//		System.out.println("SQL: " + sql.toString());
//		System.out.println("product_deleted: " + item.getProduct_deleted());
//		System.out.println("product_last_modified: " + item.getProduct_last_modified());
//		System.out.println("product_id: " + item.getProduct_id());

		// Biên dịch
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case NORMAL:
				pre.setString(1, item.getProduct_name());
				pre.setString(2, item.getProduct_description());
				pre.setString(3, item.getProduct_size());
				pre.setString(4, item.getProduct_color());
				pre.setString(5, item.getProduct_unit());
				pre.setInt(6, item.getProduct_sex());
				pre.setInt(7, item.getProduct_price());
				pre.setInt(8, item.getProduct_quantity());
				pre.setInt(9, item.getProduct_id());
				break;
			case TRASH:
				pre.setInt(1, 1);
				pre.setString(2, item.getProduct_last_modified());
				pre.setInt(3, item.getProduct_id());
				break;
			case RESTORE:
				pre.setInt(1, 0);
				pre.setString(2, item.getProduct_last_modified());
				pre.setInt(3, item.getProduct_id());
				break;

			default:
			}

			return this.edit(pre);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public boolean delProduct(ProductObject item) {
		String sql = "DELETE FROM tblproduct WHERE product_id=?";
		try {
			PreparedStatement pre = this.con.prepareStatement(sql);
			pre.setInt(1, item.getProduct_id());
			return this.del(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet getProduct(int id) {
		String sql = "SELECT * FROM tblproduct WHERE product_id=?";

		return this.get(sql, id);
	} //trả về một ResultSet chứa thông tin của một sản phẩm dựa trên product_id

	@Override
	public ResultSet getProduct(int id, String name) {
		String sql = "SELECT * FROM tblproduct WHERE (product_id=?) and (product_name=?) ";
		return this.get(sql, id, name);
	}

	@Override
	public ArrayList<ResultSet> getProducts(ProductObject similar, int at, byte total) {
		// select tất bản ghi

		return this.getProducts(new Quartet<>(similar, at, total, PRO_ORDER.NAME));
	}

	@Override
	public ArrayList<ResultSet> getProducts(Quartet<ProductObject, Integer, Byte, PRO_ORDER> infos) {
		ProductObject similar = infos.getValue0();
		int at = infos.getValue1();
		byte total = infos.getValue2();
		PRO_ORDER po = infos.getValue3();

		String countDays = "(DATE(NOW()) - DATE(STR_TO_DATE(p.product_last_modified, \"%d/%m/%Y\"))) AS days";

		// select tất bản ghi
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT p.*, " + countDays + " FROM tblproduct p ");
		sql.append(this.createConditions(similar)).append(" ");
		switch (po) {
		case NAME:
			sql.append("ORDER BY product_name ASC ");
			break;
		default:
			sql.append("ORDER BY DATE(STR_TO_DATE(p.product_last_modified, \"%d/%m/%Y\")) DESC ");
			break;
		}

		sql.append("LIMIT ").append(at).append(", ").append(total).append("; ");

		// select lấy tổng số bản ghi

		sql.append("SELECT COUNT(product_id) AS TOTAL FROM tblproduct ").append(this.createConditions(similar))
				.append(";");

		return this.getRes(sql.toString());
	} //Trả về một danh sách ArrayList của ResultSet chứa danh sách sản phẩm dựa trên thông tin bộ lọc và sắp xếp

	private StringBuilder createConditions(ProductObject similar) {
		StringBuilder tmp = new StringBuilder();

		if (similar != null) {
			if (similar.getProduct_deleted() > 0) {
				tmp.append("product_deleted>0 ");
			} else {
				tmp.append("product_deleted=0 ");
			}

		}

		// lấy từ khoá tìm kiếm
		String key = similar.getProduct_name();
		if (key != null && !key.equalsIgnoreCase("")) {
			tmp.append(" AND (");
			tmp.append("(product_name LIKE '%" + key + "%') OR ");
			tmp.append("(product_color LIKE '%" + key + "%') OR ");
			tmp.append("(product_sex LIKE '%" + key + "%') ");

			tmp.append(") ");
		}
		if (!tmp.toString().equalsIgnoreCase("")) {
			tmp.insert(0, " WHERE ");
		}

		return tmp;
	}

	public ArrayList<ProductObject> getProductObjects(ProductObject similar, byte total) {

		ArrayList<ProductObject> items = new ArrayList<>();
		ProductObject item;
		String sql = "SELECT * FROM tblproduct ";
		sql += "";
		sql += "ORDER BY product_id DESC";
		sql += "";
		sql += " LIMIT ?";

		try {
			PreparedStatement pre = this.con.prepareStatement(sql);
			// truyền giá trị cho tham số
			pre.setByte(1, total);
			ResultSet rs = pre.executeQuery();// Lấy về tập kết quả
			if (rs != null) {
				while (rs.next()) {
					item = new ProductObject();
					item.setProduct_id(rs.getInt("product_id"));
					item.setProduct_name(rs.getString("product_name"));
					item.setProduct_size(rs.getString("product_size"));
					item.setProduct_color(rs.getString("product_color"));
					item.setProduct_price(rs.getInt("product_price"));
					item.setProduct_unit(rs.getString("product_unit"));
					item.setProduct_description(rs.getString("product_description"));
					item.setProduct_sex(rs.getInt("product_sex"));
					item.setProduct_quantity(rs.getInt("product_quantity"));
					item.setProduct_sold(rs.getInt("product_sold"));
					item.setProduct_deleted(rs.getInt("product_deleted"));
					item.setManufacturer_id(rs.getInt("manufacturer_id"));
					item.setCategory_id(rs.getInt("category_id"));
					item.setProduct_last_modified(rs.getString("product_last_modified"));

					// Đưa vào tập hợp
					items.add(item);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			// Trở về trạng thái an toàn của kết nối
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return items;
	} //Trả về danh sách các đối tượng ProductObject dựa trên thông tin bộ lọc và số lượng cần lấy.
}
