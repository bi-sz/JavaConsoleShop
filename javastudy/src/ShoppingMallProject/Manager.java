package ShoppingMallProject;

import java.util.List;

public class Manager {
	JoinFile joinFile = new JoinFile();
	List<Inventory> invenList = ManagerFile.readPrdct();
	
	CustomerFile customerFile = new CustomerFile();
	
	//메인 서비스 입력값 확인.
	public void mainCheck(int main) throws AuthenException {
		if (!String.valueOf(main).matches("\\d+")) {
	        throw new AuthenException("숫자만 입력 가능합니다.");
	    }
	}
	
	//상품 번호 확인 
	public void numCheck(int num) throws AuthenException{
		if(num > 100000) {
			throw new AuthenException("99999 까지의 상품번호만 가능합니다");
		}	
		if(num == 0) {
			throw new AuthenException("0은 입력할 수 없습니다.");
		}
		// 기존에 저장된 상품번호와 중복되는지 검사
		for (Inventory s : invenList) {
		    if (s.getprdctNum() == num) {
	         throw new AuthenException("이미 등록된 상품번호 입니다");
		    }
		}	
	}
	
	//물류 입고 상품번호 확인
	public void prdctNumCheck(int num) throws AuthenException{
		if(num > 100000) {
			throw new AuthenException("99999 까지의 상품번호만 가능합니다");
		}	
		if(num == 0) {
			throw new AuthenException("0은 입력할 수 없습니다.");
		}
		boolean isExist = false; // 상품번호가 이미 등록되었는지 확인하는 변수
	    // 기존에 저장된 상품번호와 중복되는지 검사
	    for (Inventory s : invenList) {
	        if (s.getprdctNum() == num) {
	            isExist = true;
	            break;
	        }
	    }   
	    if(!isExist) {
	        throw new AuthenException("등록되지 않은 상품번호입니다.");
	    }	
	}

	//상품명 확인
	public void titleCheck(String title) throws AuthenException{
		
		if(title.length()<1 || title.length()>12){
			throw new AuthenException("1~12자 이내의 상품명만 가능합니다");
		}
		
		for (Inventory s : invenList) {
		    if (s.getTitle().equals(title)) {
	         throw new AuthenException("이미 등록된 상품명 입니다");
		    }
		}	
	}

	//상품 가격 확인 
	public void priceCheck(int price) throws AuthenException {
		if (!String.valueOf(price).matches("\\d+")) {
	        throw new AuthenException("가격은 숫자만 입력 가능합니다.");
	    }
		
		if(price > 100000000){
			throw new AuthenException("1억 이내의 상품만 판매할 수 있습니다. 세금힘들어요..");
		}
	}
	
	//상품 수량 확인
	public void stockCheck(int stock) throws AuthenException {
		if(stock <= 0) {
			throw new AuthenException("물품의 수량이 1개 이상일 경우 등록이 가능합니다.");
		}	
	}

	//구매 물품번호 확인
	public void  shoppingPrdcNumCheck(int prdcNum) throws AuthenException{
		
		// 숫자만 입력 가능 
		if(!String.valueOf(prdcNum).matches("\\d+")) {
			throw new AuthenException("물품번호는 숫자만 가능합니다.");
		}	
		
		boolean isExist = false;
	    Inventory targetInventory = null;
	    // 입력한 물품번호가 물품목록에 있는지 확인
	    for (Inventory s : invenList) {
	        if (s.getprdctNum() == prdcNum) {
	            isExist = true;
	            break;
	        }
	    }
	    if (!isExist) {
	        throw new AuthenException("등록되지 않은 물품번호 입니다.");
	    }	
	}
	
	//구매 수량 확인
	public void  shoppingStockCheck(int prdcNum, int num) throws AuthenException{
		// 숫자만 입력 가능 
		if(!String.valueOf(num).matches("\\d+")) {
			throw new AuthenException("수량은 숫자만 가능합니다.");
		}		
		
		int stock = 0;
		String title = null ;
		
		// 입력한 수량만큼 재고가 있는지 확인
		for (Inventory s : invenList) {
			if (s.getprdctNum() == prdcNum) {
				title = s.getTitle();
				stock = s.getStock();
			}	
		}	
		if (num > stock) {
			System.out.println(title + "의 현재 재고는" + stock + "개 입니다.");
			throw new AuthenException("재고가 부족합니다.");
		}
				
	}
	
	//총 가격 계산
	public int shoppingPriceCheck(int prdcNum, int num) throws AuthenException{
		
		int stock;
		String title;
		int price = 0 ;
		int total;
		
		for (Inventory s : invenList) {
			if (s.getprdctNum() == prdcNum) {
				title = s.getTitle();
				stock = s.getStock();
				price = s.getPrice();
			}	
		}
		
		total = price * num;
		
		return total;	
	}

}
