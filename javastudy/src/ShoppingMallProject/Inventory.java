package ShoppingMallProject;

//쇼핑몰 주문, 재고관리
public class Inventory {
	private int prdctNum;		//주문번호
	private String title;		//상품명
	private int price;			//상품가격
	private int stock;			//상품수량,재고
	private int fee;			//배송비,수수료
	
	//getter, setter
	public int getprdctNum() {
		return prdctNum;
	}
	public void setprdctNum(int prdctNum) {
		this.prdctNum = prdctNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	
	//toString()
	@Override
	public String toString() {
		return "[ 물품번호: "+prdctNum+", 물품명: " + title + ", 가격: " + price + ", 수량: " + stock +" ]";
	}
}
