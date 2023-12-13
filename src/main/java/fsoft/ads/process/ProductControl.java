package fsoft.ads.process;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import org.javatuples.*;

import fsoft.ConnectionPool;
import fsoft.ConnectionPoolImpl;
import fsoft.objects.ProductObject;

public class ProductControl {
	private ProductModel pm;

	public ProductControl(ConnectionPool cp) {
		this.pm = new ProductModel(cp);
	}

	public ConnectionPool getCP() {
		return this.pm.getCP();
	}

	public void releaseConnection() {
		this.pm.releaseConnection();
	}

//********************************************************
	public boolean addProduct(ProductObject item) {
		return this.pm.addProduct(item);
	}

	public boolean editProduct(ProductObject item,EDIT_TYPE et) {
		return this.pm.editProduct(item, et);
	}

	public boolean delProduct(ProductObject item) {
		return this.pm.delProduct(item);
	}
	// ********************************************************

	public ProductObject getProductObject(int id) {
		return this.pm.getProductObject(id);
	}
	
	public ProductObject getProductObject(int id, String name) {
		return this.pm.getProductObject(id, name);
	}

	public ArrayList<String> viewProducts(Quartet<ProductObject, Short, Byte, PRO_ORDER> infos) {
		Triplet<ArrayList<ProductObject>, Integer,HashMap<Integer, Integer>> datas = this.pm.getProductObjects(infos);

		ProductObject similar = infos.getValue0();
		if(similar.getProduct_deleted()==0) {
			return ProductLibrary.viewProducts(datas,infos);
		}
		else {
			return ProductLibrary.viewDeleteProducts(datas, infos);
		}
		
		
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionPool cp = new ConnectionPoolImpl();

		ProductControl pc = new ProductControl(cp);
		Quartet<ProductObject, Short, Byte, PRO_ORDER> infos = new Quartet<>(null, (short) 1, (byte) 15, PRO_ORDER.NAME);

		ArrayList<String> view = pc.viewProducts(infos);
		pc.releaseConnection();
		System.out.println(view.get(0));

	}

}
