package ShoppingMallProject;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer {
	private static final AtomicInteger count = new AtomicInteger(0);
	private String id;
	private String name;
	private String pw;
	private int balance;
	private String joinDate;
	private String leaveDate;
	private int auth;
	private int amount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String date) {
		this.joinDate = date;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
			
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "[ 아이디:"+id+", 이름:"+name+", 비밀번호:"+pw+", 잔고:"+balance+"]";
	}
	
	public String searchId() {
		return "[ 아이디:"+id+" ]";
	}
	
	public String searchPw() {
		return "[ 아이디:"+id+", 비밀번호:"+pw+"]";
	}
	
}
