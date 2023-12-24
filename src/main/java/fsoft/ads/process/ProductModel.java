package fsoft.ads.process;

import fsoft.*;
import fsoft.objects.*;
import java.util.*;
import org.javatuples.*;
import java.sql.*;

public class ProductModel {

	private Product p;

	public ProductModel(ConnectionPool cp) {
		this.p = new ProductImpl(cp);
	}
	
	public ConnectionPool getCP() {
		return this.p.getCP();
	}
	public void releaseConnection() {
		this.p.releaseConnection();
	}
	
	// **********************************************************
	public boolean addProduct(ProductObject item) {
		return this.p.addProduct(item);
	}

	public boolean editProduct(ProductObject item, EDIT_TYPE et) {
		return this.p.editProduct(item, et);
	}

	public boolean delProduct(ProductObject item) {
		return this.p.delProduct(item);
	}

	// **********************************************************

	
	public ProductObject getProductObject(int id) {
		ProductObject item = null;

		ResultSet rs = this.p.getProduct(id);
		
		if (rs != null) {
			try {
				if (rs.next()) {
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
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	
		return item;
	}
	
	public ProductObject getProductObject(int id, String name) {
		ProductObject item = null;

		ResultSet rs = this.p.getProduct(id, name);
		if (rs != null) {
			try {
				if (rs.next()) {
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
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
		return item;
	}

	// **********************************************************

	public Triplet<ArrayList<ProductObject>, Integer, HashMap<Integer, Integer>> getProductObjects(
	        Quartet<ProductObject, Short, Byte, PRO_ORDER> infos) {

	    int at = (infos.getValue1() - 1) * infos.getValue2();

	    Quartet<ProductObject, Integer, Byte, PRO_ORDER> infos2 = new Quartet<>(infos.getValue0(), at, infos.getValue2(),
	            infos.getValue3());

	    ArrayList<ResultSet> res = this.p.getProducts(infos2);

	    ArrayList<ProductObject> list = new ArrayList<>();
	    HashMap<Integer, Integer> days = new HashMap<>();
	    int total = 0;

	    if (res.size() > 0) {
	        // Lấy danh sách ResultSet
	        ResultSet rs = res.get(0);
	       

	        try {
	            // Duyệt qua tất cả các bản ghi trong ResultSet sản phẩm
	            while (rs.next()) {
	                ProductObject item = new ProductObject();

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

	                list.add(item);

	                // Đặt giá trị số ngày vào HashMap theo ID của sản phẩm
	                days.put(rs.getInt("product_id"), rs.getInt("days"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

            // Lấy tổng số bản ghi từ ResultSet tổng
	        rs = res.get(1);
	        if(rs != null) {
	            try {
					if (rs.next()) {
					    total = rs.getInt("total");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }

	    return new Triplet<>(list, total, days);
	}


	

}
