package day09;

/**
 *  1. JDBC 드라이버 로드
 *  2. 데이터 베이스 연결
 */
import java.sql.*;
import java.io.*;

public class UserinsertExample {

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
			
			// 3. sql 실행하기 위한 Statement, PreraredStatement 객체생성
			
		//String sql = "INSERT INTO person VALUES ('홍재민', '010-9723-2066', 'nanal35@naver.com', 25)";
			//Statement st = con.createStatement();
			
			String sql2 = "insert into users values (?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql2);
			st.setString(1, "kim");
			st.setString(2, "김자바");
			st.setString(3, "1234");
			st.setInt(4, 20);
			st.setString(5, "kim@a.com");
			
			// 4. 데이터 추가 명령 전송하기
			int result = st.executeUpdate();
			if(result > 0) {
				System.out.println("데이터 저장 성공~~");
			}else {
				System.out.println("데이터 저장 실패!!!");
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
