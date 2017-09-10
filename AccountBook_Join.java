package accountBook;

import java.util.Arrays;
import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
class MemberDTO{
	private String mName;//������ �̸�
	private String mId;//������ ���̵�
	private String mPass;//������ �н�����
	private String cName;//���Ƹ� �̸�
	private String cMemName[] = {"��ΰ�","��ȿ��"};//���Ƹ� ���
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmPass() {
		return mPass;
	}
	public void setmPass(String mPass) {
		this.mPass = mPass;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String[] getcMemName() {
		return cMemName;
	}
	public void setcMemName(String[] cMemName) {
		this.cMemName = cMemName;
	}
	
	@Override
	public String toString() {
		return "Join [������ �̸�=" + mName + ", ������ ���̵�=" + mId + ", ������ ��й�ȣ=" + mPass
				+ "���Ƹ� �̸�="+ cName +", ���Ƹ� ȸ��=" + Arrays.toString(cMemName) + ",]";
	}
	
}
class MemberDAO{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@192.168.0.3:1521:ORCL";
	
	private static final String USER = "SCOTT"; //DB ID
	private static final String PASS ="SCOTT"; //DB PASS
	
	Member_List mList;
	
	public void MemberDAO(){
		
	}
	
	public void MemberDAO(Member_List mList){
		this.mList = mList;
		System.out.println("DAO=>"+mList);
	}
	//DB���� �޼ҵ�
	public Connection getConn(){
		Connection con = null;
		
		try{
			Class.forName(DRIVER);//����̹� �ε�
			con = DriverManager.getConnection(URL,USER,PASS);//����̹� ����
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return con;
	}
	
	public MemberDTO getMemberDTO(String id){
		MemberDTO dto = new MemberDTO();
		
		Connection con = null;//����
		PrepareStatement ps = null; //���
		ResultSet re = null; //���
		
		try{
			con = getConn();
			String sql = "select * from tb_member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1,id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				dto.setId(rs.getString("mId"));
				dto.setPwd(rs.getString("mPass"));
				dto.setTel(rs.getString("cName"));
				dto.setTel(rs.getString("cMemName"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return dto;
	}
	//���
	public Vector getMemberList(){
		Vector data = new Vector();
		
		Connection con = null;//����
		PreparedState ps = null;//���
		ResultSet rs = null; //���
		
		try{
			con = getConn();
			String sql = "select * from tb_member order by name asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				con = getConn();
				String sql = "select * from tb_member order by name asc";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				
				while(rs.next()){
					String mId = rs.getString("mId");
					String mPass = rs.getString("mPass");
					String cName = rs.getString("cName");
					String cMemName = rs.getString("cMemName");
					
					Vector row = new Vector();
					row.add(mId);
					row.add(mPass);
					row.add(cName);
					row.add(cMemName);
					
					data.add(row);
				}//end of while
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
	public boolean insertMember(MemberDTO dto){
		boolean ok = false;
		
		Connection con = null;//����
		PreparedStatement ps = null;//���
		
		try{
			con = getConn();
			String sql = "insert inot tb_member("+"mId,mPass,cName,cMemName)"+
			"values(?,?,?,?)";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getmId());
			ps.setString(2, dto.getmPass());
			ps.setString(3, dto.getcName());
			ps.setString(4, dto.getcMemName());
			int r = ps.executeUpdate();//����->����
			
			if(r>0){
				System.out.println("���� ����");
				ok = true;
			}else{
				System.out.println("���� ����");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ok;
	}
	//ȸ����������
	public boolean updateMember(MemberDTO vMem){
		System.out.println("dto="+vMem.toString());
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = getConn();
			String sql = "update tb_member set mId=?, mPass=?,cName=?,cMemName=?";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1,vMem.getmId());
			ps.setString(2,vMem.getmPass());
			ps.setString(3,vMem.getcName());
			ps.setString(4,vMem.getcMemName());
			
			int r = ps.executeUpdate();//����->���� //1-n:����,0:����
			
			if(r>0)ok = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ok;
	}
	//ȸ������ ����
	public boolean deleteMember(String mId, String mPass){
		
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;
		try{
			con = getConn();
			String sql = "delete from tb_member where mId=? and mPass=?";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1,mId());
			ps.setString(2,mPass());
			
			int r = ps.executeUpdate();//����->����
			
			if(r>0)ok = true;
		}catch(Exception e){
			System.out.println(e+"->�����߻�");
		}
		return ok;
	}
	//DB������ �ٽ� �ҷ�����
	public boolean userSelectAll(DefaultTableModel model){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			con = getConn();
			String sql = "select*from tb_member order by name asc";
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			for(int i=0;i<model.getRowCount();){
				model.removeRow(0);
			}
			while(rs.next()){
				Object data[] = {
						rs.getString(1),rs.getString(2),
						rs.getString(3),rs.getString(4)
				};
				model.addRow(data);
			}
		}catch(Exception e){
			System.out.println(e+"=> userSelectAll fail");
		}finally{
			if(rs!=null)
				try{
					rs.close();
				}catch(SQLException e2){
					e2.printStackTrace();
				}
			if(ps!=null)
				try{
					ps.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
		}
	}
}
