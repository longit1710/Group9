package fsoft.ads.basic;
 
import java.sql.*;
import java.util.*;
import fsoft.*;

public interface Basic extends ShareControl{
	/**
	 * Các phương thức cập nhật đối tượng
	 * @param pre - Câu lệnh PreparedStatement đã được biên dịch và truyền đầy đủ dữ liệu
	 * @return Kết quả trả về có thực hiện <b>thêm/sửa/xóa</b> không
	 */
	public boolean add(PreparedStatement pre);
	public boolean edit(PreparedStatement pre);
	public boolean del(PreparedStatement pre);
	
	public ResultSet get(String sql, int value);
	public ResultSet get(String sql, int id, String name);
	public ResultSet gets(String sql);
	
	public ArrayList<ResultSet> getRes(String multiSelect);
	public ArrayList<ResultSet> getRes(PreparedStatement pre);
	

}
