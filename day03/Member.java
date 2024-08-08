package day03;

public class Member {
	
	String name;
	String id;
	String password;
	int age;
	
	// 기본 생성자
	public Member() {
		
	}
	
	// 인자 생성자
	public Member(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	
	
	// 메소드 정의
	boolean login(String id, String password) {
		boolean result;
		if(id.equals("hong")&& password.equals("12345")) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}
	
	// 로그아웃 메소드 정의
	public void logout(String id) {
		System.out.println(id+"님이 로그아웃 되었습니다.");
	}

}
