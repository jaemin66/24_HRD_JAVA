package day09;

/**
 *  1. JDBC 드라이버 로드
 *  2. 데이터 베이스 연결
 */
import java.sql.*;
import java.io.*;

public class BoardSelectExample {

	public static void main(String[] args) {
		
		Connection con = null;

		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 데이터 베이스 연결
			String url = "jdbc:mysql://localhost:3306/contacts";
			String id = "root";
			String pwd = "1234";
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("DB 연결 성공~~");
			
			
			String sql2 = "select * from boards where bwriter = ?"; 
			PreparedStatement st = con.prepareStatement(sql2);
			
			st.setString(1, "winter");
			
			
			
			// 4. 데이터 조회 명령 sql 전송하기
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				System.out.println("데이터 조회 성공~~");
				
				int bno = rs.getInt("bno");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String bwriter = rs.getString("bwriter");
				String d = rs.getDate("bdate").toString();
				String fname = rs.getString("bfilename");
				//String fdata = null;
				Blob blob = rs.getBlob("bfiledata");
				InputStream is = blob.getBinaryStream();
				OutputStream os = new FileOutputStream("C:\\Users\\COMPUTER\\eclipse-workspace\\JavaOOP\\src\\day09\\cat.jpg");
				is.transferTo(os);
				System.out.println("번호:" + bno);
				System.out.println("제목:" + btitle);
				System.out.println("내용:" + bcontent);
				System.out.println("작성자:" + bwriter);
				System.out.println("작성일:" + d);
				System.out.println("파일이름:" + fname);
				//System.out.println("파일정보:" + );
				os.flush();
				os.close();
				is.close();
				
			}
				
					
			
		} catch (Exception e) {
			System.out.println("드라이버 로딩 실패");

		}finally {
			try {
				con.close();
				System.out.println("연결끊기");
			}catch(SQLException es) {
				
			}
		}

	}

}
