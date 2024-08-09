package day04;

public class Account {
	
	/* balance 필드 은닉화 하기
	private int balance;
	
	// getter, setter 메소드 정의하기
	public int getBalance() {
		return balance;
	}
	
	public void setBlance(int balance) {
		if(balance < Account.MIN_BALANCE || balance > Account.MAX_BALANCE) {
			return;
		}
		this.balance = balance;
	}*/
	
	//int number;
	//String name;
	//int fnumber;
	
	private int balance;
	private String ano;
	private String owner;
	
	// 상수 필드 정의 하기
	static final int MIN_BALANCE = 0;
	static final int MAX_BALANCE = 1000000;
	
	public Account(String ano, String owner, int balance) {
		this.ano = ano;
		this.balance = balance;
		this.owner = owner;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	

}
