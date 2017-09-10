package accountBook;

import java.util.Arrays;
import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
class MemberDTO{
	private String mName;//관리자 이름
	private String mId;//관리자 아이디
	private String mPass;//관리자 패스워드
	private String cName;//동아리 이름
	private String cMemName[] = {"김민경","김효원"};//동아리 멤버
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
		return "Join [관리자 이름=" + mName + ", 관리자 아이디=" + mId + ", 관리자 비밀번호=" + mPass
				+ "동아리 이름="+ cName +", 동아리 회원=" + Arrays.toString(cMemName) + ",]";
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
	//DB연결 메소드
	public Connection getConn(){
		Connection con = null;
		
		try{
			Class.forName(DRIVER);//드라이버 로딩
			con = DriverManager.getConnection(URL,USER,PASS);//드라이버 연결
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return con;
	}
	
	public MemberDTO getMemberDTO(String id){
		MemberDTO dto = new MemberDTO();
		
		Connection con = null;//연결
		PrepareStatement ps = null; //명령
		ResultSet re = null; //결과
		
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
	//출력
	public Vector getMemberList(){
		Vector data = new Vector();
		
		Connection con = null;//연결
		PreparedState ps = null;//명령
		ResultSet rs = null; //결과
		
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
		
		Connection con = null;//연결
		PreparedStatement ps = null;//명령
		
		try{
			con = getConn();
			String sql = "insert inot tb_member("+"mId,mPass,cName,cMemName)"+
			"values(?,?,?,?)";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getmId());
			ps.setString(2, dto.getmPass());
			ps.setString(3, dto.getcName());
			ps.setString(4, dto.getcMemName());
			int r = ps.executeUpdate();//실행->저장
			
			if(r>0){
				System.out.println("가입 성공");
				ok = true;
			}else{
				System.out.println("가입 실패");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ok;
	}
	//회원정보수정
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
			
			int r = ps.executeUpdate();//실행->수정 //1-n:성공,0:실패
			
			if(r>0)ok = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ok;
	}
	//회원정보 삭제
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
			
			int r = ps.executeUpdate();//실행->삭제
			
			if(r>0)ok = true;
		}catch(Exception e){
			System.out.println(e+"->오류발생");
		}
		return ok;
	}
	//DB데이터 다시 불러오기
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
