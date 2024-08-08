package day03;

public class ArrayCreateByValueListExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 문자열 데이터 4개를 저장한 배열선언 및 초기화
		String[] season = {"Spring", "Summer", "Fall", "Winter"};
		
		// for문을 이용한 배열에 넣은 값 반복해서 불러오기
		for(int i = 0; i<4; i++) {
			System.out.println(season[i]);
			
		}
		
		// Summer을 여름으로 바꾸어 출력
		season[1] = "여름";

		for(int i = 0; i<4; i++) {
			
			System.out.println(season[i]);
		}
		
		// 이름, 문자열 3개를 저장한 배열 선언 및 초기화
		String[] names = null;   // 가르키려하는 heap메모리 주소가 없으면 null을 씀 
		
		names = new String[] {"홍재민", "김자바", "홍길동"};
		
	}

}
