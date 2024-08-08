package day03;

public class JobApp {

	public static void main(String[] args) {
		
		// person 객체 생성
		Person hong = new Person();
		hong.age = 20;
		hong.name = "홍길동";
		hong.gender = "남자";
		hong.먹다();
		hong.웃다();
		
		System.out.println(hong.age);
		System.out.println(hong.name);
		System.out.println(hong.gender);
		

	}

}
