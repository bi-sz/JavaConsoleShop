package ShoppingMallProject;

import java.util.List;
import java.util.regex.Pattern;

public class Join {
	JoinFile joinFile = new JoinFile();
    List<Customer> customerList = joinFile.readData();
	
	//아이디 확인
	public void idFormat(String str) throws AuthenException{
		
		if(str.length()<4 || str.length()>12){
			throw new AuthenException("4~12자 이내의 아이디만 가능합니다");
		}

		int cnt1=0;
		int cnt2=0;

		for(int i=0;i<str.length();i++){
			char ch = str.charAt(i);
			
			if((ch>='a' && ch<='z') || (ch>='A' && ch<='Z'))
				cnt1++;
			else if(ch>='0' && ch<='9')
				cnt2++;
			}

			if(cnt1==0 || cnt2==0)
				throw new AuthenException("아이디는 영문자와 숫자를 혼용해서 만들어주세요");	
			
			// 기존에 저장된 회원 정보와 중복되는 아이디인지 검사
		    for (Customer c : customerList) {
		        if (c.getId().equals(str)) {
		            throw new AuthenException("이미 사용 중인 아이디입니다");
		        }
		    }
		}

	//비밀번호 확인
	public void pwCheck(String pw1, String pw2) throws AuthenException{

		if(pw1.length() < 4){
			throw new AuthenException("4자 이상의 비밀번호로 입력해주세요.");
		}
		
		int cnt1=0;
		int cnt2=0;

		for(int i=0;i<pw1.length();i++){
			char ch = pw1.charAt(i);
			
			if((ch>='a' && ch<='z') || (ch>='A' && ch<='Z'))
				cnt1++;
			else if(ch>='0' && ch<='9')
				cnt2++;
		}
			if(cnt1==0 || cnt2==0)
				throw new AuthenException("비밀번호는 영문자와 숫자를 혼용해서 만들어주세요");	
			if(!pw1.equals(pw2))
				throw new AuthenException("비밀번호가 다릅니다");	
		}

	//이름 확인
	public void nameCheck(String name) throws AuthenException {
		boolean check = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name);
			if (!check)
				throw new AuthenException("이름은 한글로 입력해주세요");
	}

	//잔고 충전 확인
	public void chargeCheck(String loginId, int charge) throws AuthenException{
		
		if(charge <= 0){
		    throw new AuthenException("충전할 금액은 0원보다 커야 합니다.");
		}
		
		if(charge >= 100000000){
	        throw new AuthenException("1억 미만의 금액만 충전이 가능합니다.");
	    }	
		
	    for (Customer c : customerList) {
	    	if (c.getId().equals(loginId)) {
	    		if (c.getBalance() + charge >= 100000000) {
	    			System.out.println(c.getBalance());
	    			throw new AuthenException("잔고의 금액은 1억이 넘을 수 없습니다.");
	    		}
	    	}

	    }
	}

	//잔고 확인
	public int balanceCheck(String loginId) throws AuthenException{

    	int balance = 0;
    	for (Customer customer : customerList) {
    	    if (customer.getId().equals(loginId)) {
    	        balance = customer.getBalance();
    	        break;
    	    }
    	}
    	
    	return balance;
	}
}
