package day04;
import java.util.ArrayList;
public class Main {

		
		/**
		 * 클래스 구성 멤버
		 * 필드 : 객체의 데이터를 저장하는 역할을 한다.
		 * 생성자 : 기본생성자와 인자생성자가 있다. ## 인자생성자를 만들면 기본생성자도 만들어 줘야함
		 *          기본생성자만 사용할 떈 컴파일러가 자동으로 만들어줌
		 * 메소드 : 1. 반환타입도 있고, 매개변수도 있는 메소드 정의
		 *          2. 반환타입은 없고, 매개변수는 있는 메소드 정의
		 *          3. 반환타입은 있고, 매개변수는 없는 매소드 정의
		 *          4. 반환타입과 매개변수가 없는 메소드 정의
		 *          반환타입이 있으면 return문 사용
		 *          
		 */
		
	public static void main(String[] args) {
		House house1 = new House("홍재민",3,"대전 동구 용운동");
		house1.showOwner(house1.ownerName);
		System.out.println("방의 갯수: "+ house1.getRoomNum());
		System.out.println("집의 정보1: "+ house1.showInfo());
		house1.showInfo2();
		
		
		// 집 정보를 배열에 넣어보자  ArrayList는 기본형은 담을 수 없음 ex) int, double
		// 단 Integer 같이 클래스는 담을 수 있음( ex)String, Double 등)
		// ArrayList는 사이즈를 안 정해줘도 됨 
		ArrayList<House> arrList = new ArrayList<House>();
		arrList.add(house1);
		arrList.add(new House("강길동",2,"대전 서구 갈마동"));
		arrList.add(new House("이자바",4,"대전 중구 대흥동"));
		
		for(House house : arrList) {
			house.showInfo2();
		}
		

	}

}

// 집 클래스 모델링
// 추상화
class House{
	// has - a
	
	String ownerName;
	int roomNum;
	String address;
	public House() {}
	
	
	// 인자 생성자
	public House(String ownerName, int roomNum, String address) {
		this.ownerName = ownerName;
		this.roomNum = roomNum;
		this.address = address;
	}
	
	
	
	// 메소드 정의
	// 1. 반환없고, 주인 이름을 매개변수로 전달 받아 이름을 같이 출력하는 메소드 showOwner() 정의
	void showOwner(String Name) {
		System.out.println("현재 전달받은 집 주인 이름은 " + Name + "입니다.");
	}
	
	// 2. 방의 갯수를 반환하는 getRoomNum() 메소드 정의
	int getRoomNum() {
		return roomNum;
	}
	
	
	// 3. 모든 멤버 변수의 값을 반환하는 showInfo() 메소드 정의
	String showInfo() {
		String result = "집 주인이름:" + ownerName + "\n방의 갯수:" + roomNum +"\n집 주소:" + address;
		return result;
	}
	
	// showInfo2()  void면 .사용하고 return은 sysout이용하여 출력받아야함
	void showInfo2() {
		System.out.println("집 주인이름:" + ownerName + "\n방의 갯수:" + roomNum +"\n집 주소:" + address);
	}
	
	
}