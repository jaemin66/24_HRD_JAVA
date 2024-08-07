package day02;

public class RefType {

	public static void main(String[] args) {
		
		RefType ref = new RefType();
		RefType ref2 = new RefType();    // ref가 stack에 쌓여있고 heap영영에 RefType을 할당함
		                                 // ref와ref2는 다른객체 예를 들면 ref는 heap영역에
		                                 // 100번지 RefType이고 ref2는 200번지이다. 앞으로 이런 
		                                 // 참조형(ref,ref2)을 오브젝트라고 부른다. 
		
		if(ref == ref2) {
			System.out.println("동일한 주소를 갖는 객체입니다.");
		}else {
			System.out.println("서로 다른 주소를 갖는 객체입니다.");
		}
		
		String name = new String("홍재민");
		System.out.println(name);   // String을 참조형으로 쓴 것
		
		String name2 = "오늘도 수고했어요";
		System.out.println(name2.length());    // length는 길이를 알려줌
		
		int[] score = new int[5];
		score[0] = 10;
		score[1] = 20;
		score[2] = 30;
		score[3] = 40;
		score[4] = 50;
		
		int sum = 0;
		for(int i = 0; i<score.length; i++) {
			sum = sum + score[i];
		}
		System.out.println("총합:" + sum);
		

	}

}
