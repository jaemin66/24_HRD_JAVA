package day06;

public class User {
	
	private String name;
	private PayType payType;
	
	// 생성자
	public User(String name, PayType payType) {
		this.name = name;
		this.payType = payType;
	}

	// getter, setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	

	

}
