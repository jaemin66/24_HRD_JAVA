package day09;

/**
 *  1. JDBC 드라이버 로드
 *  2. 데이터 베이스 연결
 */
import java.sql.*;
import java.util.ArrayList;

public class PersonSelect {

	public static void main(String[] args) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

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

			// String sql = "INSERT INTO person VALUES ('홍재민', '010-9723-2066',
			// 'nanal35@naver.com', 25)";
			// Statement st = con.createStatement();

			//String sql2 = "select name, phone from Person ";
		// 이름이 홍길동과 같은 사람의 모든 열의 정보를 검색해서 출력하기
			String sql2 = "select * from person";
			// 4. 데이터 조회 명령 SQL 전송하기
			st = con.createStatement();
			rs = st.executeQuery(sql2);

			String phone = "";
			String email = "";
			String name = "";
			int age = 0;
			Person person = null;
			ArrayList<Person> arlist = new ArrayList<Person>();
			while(rs.next()) {
				phone = rs.getString("phone");
				email = rs.getString("email");
				name = rs.getString("name");
				age = rs.getInt("age");
				
				person = new Person(name, phone, email, age);
				arlist.add(person);
					
			}
			for(Person p : arlist) {
				System.out.println("이름: "+p.getName());
				System.out.println("전화번호: "+p.getPhone());
				System.out.println("이메일: "+p.getEmail());
				System.out.println("나이: "+p.getAge());
			}
			
//			System.out.println("전화번호:" + person.getPhone());
//			System.out.println("이메일:" + person.getEmail());
		

		} catch (Exception e) {
			System.out.println("드라이버 로딩 실패");

		} finally {
			try {
				con.close();
				st.close();
				rs.close();
				
				System.out.println("연결끊기");
			} catch (SQLException es) {

			}
		}

	}

}
