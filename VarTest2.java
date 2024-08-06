package day01;

public class VarTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 1.정수변수
		
		int x;
		// 변수 초기화
		x = 5;
		
		int y = 8;
		
		int result = x + y;
		
		System.out.println(result);
		
		// 2.실수변수
		// float(4byte), double(8byte)
		double height = 155.7;
		float weight = 45.5f;
		
		// 3.문자형 변수
		// char
		
		char blood = 'A';
		
		// 4. 문자열 변수
		// String
		String name = "홍재민";
		
		System.out.print("키:"+height+"\n몸무게:"+ weight +"\n");
		
		System.out.println("혈액형:"+ blood +"형 입니다.");		
		
		System.out.println("이름:"+ name +"입니다.");
		
		// 5. 논리형 변수 (참 거짓)
		
		// boolean형
		boolean stop = true;
		
		if (stop == true) {
			System.out.println("중지합니다.");
			
		
		}else {
			System.out.println("시작합니다.");
		}
			
		
	}

}
