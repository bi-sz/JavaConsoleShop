package ShoppingMallProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
	Scanner sc = new Scanner(System.in);
	
	//물품 관련
	ManagerFile managerFile = new ManagerFile();
	Manager manager = new Manager();
	
	//회원 관련
	JoinFile joinFile = new JoinFile();
	Join join = new Join();
	
	//구매내역 관련
	CustomerFile customerFile = new CustomerFile();

	int serviceSel;									//메인 서비스 입력값.
	int search;										//메인 서비스 아이디,비번 찾기 입력값.
	int managerSel, managerSel2;					//관리자 서비스 입력값
	int customerSel, customerSel2, customerSel3;	//소비자 서비스 입력값.
	int reviewSel;									//소비자 서비스 리뷰 입력값
	String content;									//소비자 서비스 리뷰
				
	//쇼핑몰 메인 서비스
	public void service() throws AuthenException {
		while (true) {
	        System.out.println("id : admin1, pw : admin1 로 로그인 => 관리자 시스템 이용 가능");
	        System.out.println("초기화된 경우 id: admin1 로 가입 시 관리자계정으로 가입됩니다.");
	        System.out.println();
	        System.out.println("=====오디움에 오신것을 환영합니다.=====");
	        System.out.println("1. 로그인 하기");
	        System.out.println("2. 회원가입 하기");
	        System.out.println("3. 회원탈퇴 하기");
	        System.out.println("4. 아이디 비밀번호 찾기");
	        System.out.println("0. 종료하기");
	        System.out.println("===============================");
	        System.out.print("입력:");

	        try {
	            serviceSel = Integer.parseInt(sc.nextLine()); // nextInt 대신 nextLine 메소드 사용
	            manager.mainCheck(serviceSel);
	            
	            //1. 로그인
	            if (serviceSel == 1) {
	            	loginService();
	            //2. 회원가입	
	            } else if (serviceSel == 2) {
	                try {
	                    joinService();
	                } catch (AuthenException e) {
	                    e.printStackTrace();
	                }
	            //3. 회원 탈퇴    
	            } else if (serviceSel == 3) {
	                deleteId();
	            //4. 아이디 비밀번호 찾기    
	            } else if (serviceSel ==4) {     	
	            	do {
	            		List<Customer> customerList = joinFile.readData();
	            		System.out.println("===============================");
	            		System.out.println("1. 이름으로 아이디 찾기");
	            		System.out.println("2. 아이디로 비밀번호 찾기");
	            		System.out.println("0. 메인 페이지로");
	            		System.out.println("===============================");
	            		System.out.print("입력:");
	            		search = sc.nextInt();
	            		
	            		if(search == 1) {
	            			System.out.print("이름 입력:");
	            			String name = sc.next();
	            			System.out.println("===============================");
	            			joinFile.searchId(customerList, name);
	            		}else if (search == 2) {
	            			System.out.print("아이디 입력:");
	            			String id = sc.next();
	            			System.out.println("===============================");
	            			joinFile.searchPw(customerList, id);
	            		}	
	            		
	            	}while(search != 0);
	            }
	            
	            else if (serviceSel == 0) {
	                quit();
	            } else {
	                System.out.println("===============================");
	                System.out.println("올바르게 입력해주세요.");
	                System.out.println("===============================");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("===============================");
	            System.out.println("숫자를 입력해주세요.");
	            System.out.println("===============================");
	        }
	    }
	}
	
	//프로그램 종료
	private void quit() {
		System.out.println("===== 다음에 또 방문해주세요 :) =====");
		System.exit(0);
	}
	
	//관리자 서비스
	public void managerService() throws AuthenException {
		JoinFile joinFile = new JoinFile();
		Join join = new Join();
		
		List<Inventory> shoppingList1 = managerFile.readPrdct();
		
		boolean prdctNum = true;
		boolean title = true;
		boolean price = true;
		boolean stock = true;
		
		do {
			List<Inventory> shoppingList2 = managerFile.readPrdct();
			
			System.out.println();
			System.out.println("========관리자 시스템========");
			System.out.println("원하는 서비스의 숫자를 입력해주세요.");
			System.out.println("==========================");
			System.out.println("1. 상품 등록");
			System.out.println("2. 상품 삭제");
			System.out.println("3. 상품 목록");
			System.out.println("4. 상품 검색");
			System.out.println("5. 상품 입고");
			System.out.println("6. 회원 목록");
			System.out.println("7. 매출 조회");
			System.out.println("0. 로그아웃");
			System.out.println("==========================");
			
			System.out.print("입력:");
			managerSel = sc.nextInt();
			
			//1. 상품 등록
			if(managerSel == 1) {
				System.out.println("==========================");
				System.out.println("=========상품 등록 =========");
				System.out.println("등록할 상품의 정보를 입력해주세요.");
				try {
					Inventory s = new Inventory();	
					do {
						try {
							System.out.print("상품 번호:");
							s.setprdctNum(sc.nextInt());
							manager.numCheck(s.getprdctNum());
							
							prdctNum = false;
							
						}catch(AuthenException e) {
							System.out.println(e.toString());
						}
					}while(prdctNum);
					
					do {
						try {
							System.out.print("상품명:");
							s.setTitle(sc.next());
							manager.titleCheck(s.getTitle());
					
							title = false;
							
						}catch(AuthenException e) {
							System.out.println(e.toString());
						}
					}while(title);
					
					do {
						try {
							System.out.print("상품 가격:");
							s.setPrice(sc.nextInt());
							manager.priceCheck(s.getPrice());
							
							price = false;
							
						}catch(AuthenException e) {
							System.out.println(e.toString());
						}
					}while(price);
					
					do {
						try {
							System.out.print("상품 수량:");
							s.setStock(sc.nextInt());
							manager.stockCheck(s.getStock());
							
							stock = false;
							
						}catch(AuthenException e) {
							System.out.println(e.toString());
						}
					}while(stock);
					
					int result = ManagerFile.addPrdct(s);

					if(result!=0){
						System.out.println();
						System.out.println("========물품이 등록되었습니다.========");
						System.out.println();
						System.out.println("-----------[등록 확인]-----------");
						System.out.println(s.toString());	
					}
					else
						System.out.println("물품등록에 실패했습니다");		

				} catch (Exception e) {
					System.out.println(e.toString());
				}
				
			//2. 상품 삭제
			}else if(managerSel == 2) {
				List<Inventory> shoppingList = managerFile.readPrdct();
				
				System.out.println("==========================");
				System.out.println("===삭제할 상품번호를 입력하세요.===");
				
				System.out.print("상품 번호:");
				int num = sc.nextInt();
				
				boolean success = false;
				
				for (Inventory s : shoppingList) {
					if(s.getprdctNum() == num) {
						System.out.println();
						success = true;
						break;
					}
				}
				
				if (!success) {
					System.out.println("입력한 상품번호가 존재하지 않습니다.");
				}else {
					ManagerFile.deletePrdct(shoppingList, num);
					System.out.println("==="+num+"번 물품이 삭제되었습니다.===");
				}
//				managerService();
				
			//3. 물품 목록	
			}else if(managerSel == 3) {
				managerFile.outPrdct();
				
			//4. 상품 검색
			}else if(managerSel == 4) {
				List<Inventory> shoppingList = managerFile.readPrdct();
				
				System.out.println("===========================");
				System.out.println("====검색할 상품명을 입력하세요.====");
				
				System.out.print("상품명: ");
				String search = sc.next();
				
				boolean success = false;
				
				for (Inventory s : shoppingList) {
					if(s.getTitle().contains(search)) {
						System.out.println();
						success = true;
						break;
					}
				}
				
				if (!success) {
					System.out.println("입력한 상품명의 물품이 존재하지 않습니다.");
				}else {
					managerFile.searchPrdct(shoppingList, search);
				}
				
				System.out.println("===========================");

			}else if(managerSel == 5) {
				chargePrdct();
			//6. 회원 목록
			}else if(managerSel == 6) {
				do {
					System.out.println("===========회원 목록 조회===========");
					System.out.println("1. 관리자 목록");
					System.out.println("2. 회원 목록");
					System.out.println("3. 탈퇴 목록");
					System.out.println("0. 이전 페이지");
					System.out.println("================================");
					
					managerSel2 = sc.nextInt();
						if(managerSel2 ==1) {
							joinFile.outDataManager();
						}else if(managerSel2 == 2){
							joinFile.outDataCustomer();
						}else if(managerSel2 == 3) {
							joinFile.outDataWithdrawal();
						}
				}while(managerSel2 != 0);
			
				
			//6. 매출 조회	
			}else if(managerSel == 7) {
				managerFile.outSellLog();
            	
            //다른 입력값을 받았을 때
            }else {
				System.out.println("올바르게 입력해주세요.");
			}
		//이전 페이지
		}while(managerSel != 0);
	}
	
	//소비자 서비스
	public void customerService(String loginId) throws AuthenException {
		do {
			System.out.println("====오디움에 오신것을 환영합니다.====");
			System.out.println("1. 쇼핑하기");
			System.out.println("2. 충전하기");
			System.out.println("3. 마이페이지");
			System.out.println("0. 로그아웃");			
			System.out.println("=============================");
			
			System.out.print("입력:");
			customerSel = sc.nextInt();
			
			// 1. 쇼핑하기
			if(customerSel == 1) {		
				do {
					List<Customer> customerList = joinFile.readData();
					List<Inventory> InventoryList = managerFile.readPrdct();
					
					managerFile.outPrdct();
					
					System.out.println("=============================================");
					
					System.out.println("1. 구매하기");
					System.out.println("0. 뒤로가기");
					
					System.out.print("입력:");
					customerSel2 = sc.nextInt();
					
					if (customerSel2 == 1) {
						shoppingService(loginId);
						
					}
				}while(customerSel2 != 0);
			//2. 충전하기	
			}else if(customerSel == 2) {
				chargeService(loginId);	
			//3. 마이페이지	
			}else if(customerSel == 3) {
				do {
					joinFile.mypageService(loginId);
				
					System.out.println("1. 구매내역 조회");
					System.out.println("0. 뒤로가기");
					
					System.out.print("입력:");
					customerSel3 = sc.nextInt();
					
					if (customerSel3 == 1) {
						customerFile.outHistory(loginId);
					}
				}while(customerSel3 != 0);
				joinFile.outDataBalance(loginId);
			//다른 입력값을 받았을 때           	
            }else {
				System.out.println("올바르게 입력해주세요.");
			}
		//0. 뒤로가기
		}while(customerSel != 0);
	}
	
	//로그인 서비스
	public String loginService() throws AuthenException {
		List<Customer> customerList  = joinFile.readData();

		System.out.println("===============================");
	    System.out.println("=============로그인=============");

	    System.out.print("아이디:");
	    String loginId = sc.next();

	    System.out.print("비밀번호:");
	    String pw = sc.next();

	    Customer user = null;
	    
	    for (Customer c : customerList) {
	        if (c.getId().equals(loginId) && c.getPw().equals(pw)) {
	            if (!c.getLeaveDate().equals("x")) { 
	                System.out.println("탈퇴한 회원입니다.");
	                return null;
	            }
	            user = c;
	            break;
	        }
	    }

	    if (user == null) {
	        System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
	        return null;
	    } else {
	        System.out.println();
	        System.out.println("=====로그인에 성공했습니다!=====");
	        System.out.println();
	        System.out.println("--------[로그인 정보]--------");
	        System.out.println(user.toString());

	        if (user.getAuth() == 1) {
	            customerService(loginId);
	        } else if (user.getAuth() == 255) {
	            try {
	                managerService();
	            } catch (AuthenException e) {
	                e.printStackTrace();
	            }
	        }
	        return loginId;
	    }
	}

	//회원가입 서비스
	public void joinService() throws AuthenException{
		
		String pw2 = null;
		boolean id = true;
		boolean pw = true;
		boolean name = true;
		
		System.out.println("====================");
		System.out.println("=======회원가입=======");
		
		try {
			Customer c = new Customer();

			// customerList 변수가 null인 경우 초기화	
			do {
				try {
					System.out.println("정보를 입력해주세요.");
					System.out.print("아이디:");
					c.setId(sc.next());
					join.idFormat(c.getId());
					
					id = false;
					
				}catch(AuthenException e) {
					System.out.println(e.toString());
				}
			}while(id);
			
			do{
				try{
					System.out.print("이름:");
					c.setName(sc.next());
					join.nameCheck(c.getName());

					name=false;

				}catch (AuthenException e) {
					System.out.println(e.toString());
				}
			}while(name);
			
			do{
				try{
					System.out.print("비밀번호:");
					c.setPw(sc.next());
					
					System.out.print("비밀번호 확인:");
					pw2 = sc.next();
					join.pwCheck(c.getPw(), pw2);

					pw=false;

				}catch (AuthenException e) {
					System.out.println(e.toString());
				}
			}while(pw);
			
			LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentDate = now.format(formatter);
            c.setJoinDate(currentDate);
			
			c.setLeaveDate("x");
	        c.setAuth(1);
	        c.setAmount(0);

			int result = joinFile.insertData(c);

			if(result!=0){
				System.out.println();
				System.out.println("====성공적으로 가입이 되었습니다!====");
				System.out.println();
				System.out.println("-----------[회원가입 확인]-----------");
				System.out.println(c.toString());	
			}
			else
				System.out.println("회원가입에 실패했습니다");		

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	//잔고충전 서비스
	public void chargeService(String loginId) {
		ManagerFile managerFile = new ManagerFile();
		Manager manager = new Manager();
		List<Customer> customerList = joinFile.readData();

	    System.out.println("=============================");
	    System.out.println("============잔고충전===========");

	    while (true) {
	        try {
	            System.out.print("충전할 금액을 입력해주세요:");
	            int charge = sc.nextInt();
	            join.chargeCheck(loginId,charge);
	            JoinFile.balanceCharge(customerList, loginId, charge);
	            System.out.println("잔액 충전이 완료되었습니다.");
	            joinFile.outDataBalance(loginId);
	            return;
	        } catch (AuthenException e) {
	            System.out.println(e.toString());
	        }
	    }
	}
	
	//재고 충전 서비스
	public void chargePrdct() {
		List<Inventory> inventoryList = managerFile.readPrdct();

	    System.out.println("=============================");
	    System.out.println("============물류입고===========");
		
	    while (true) {
		    try {
		    	System.out.print("입고할 재고의 상품번호 입력:");
		        int prdctNum = sc.nextInt();
		        manager.prdctNumCheck(prdctNum);
		        
		        System.out.print("입고할 수량을 입력:");
		        int stock = sc.nextInt();
		        manager.stockCheck(stock);
		        
		        managerFile.prdctCharge(inventoryList, prdctNum, stock);
	            System.out.println("입고가 완료되었습니다.");
	            managerFile.outPrdct();
	            	return;
	        } catch (AuthenException e) {
	            System.out.println(e.toString());
	        }
	    }
	}
	
	//구매하기 서비스
	public void shoppingService(String loginId) {
		ManagerFile managerFile = new ManagerFile();
		Manager manager = new Manager();
		JoinFile joinFile = new JoinFile();
		Join join = new Join();
		
		List<Customer> customerList = joinFile.readData();
		List<Inventory> InventoryList = managerFile.readPrdct();
		
	    System.out.println();

	    while (true) {
	        try {
	        	System.out.println("===구매하실 상품의 정보를 입력해주세요.===");
	        	
	    		System.out.print("상품번호:");
	    		int prdcNum = sc.nextInt();
	    		manager.shoppingPrdcNumCheck(prdcNum);
	    		
	    		System.out.print("구매 개수:");
	    		int num = sc.nextInt();
	    		manager.shoppingStockCheck(prdcNum, num);
	    		
	    		//토탈 금액
	    		int total = manager.shoppingPriceCheck(prdcNum, num);
	    		// 회원 잔고
	    		int useBalance = join.balanceCheck(loginId);
	    		
	    		if (useBalance >= total) {
	    			System.out.println("============결제가 완료되었습니다.===========");
	                joinFile.balanceUse(customerList, loginId, total);	                
	                managerFile.sell(InventoryList, prdcNum, num);
	                
	                //아래 코드는 결제 내역을 기록하기 위한 코드.
	                
	                //구매한 시간
	                LocalDateTime now = LocalDateTime.now();
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	                String purchaseDate = now.format(formatter);

	        		String title = null;
	        		int price = 0 ;
	        		
	        		for (Inventory s : InventoryList) {
	        			if (s.getprdctNum() == prdcNum) {
	        				title = s.getTitle();
	        				price = s.getPrice();
	        			}	
	        		}     
	                
	                //구매내역 저장
	        		customerFile.addHistory(loginId,purchaseDate,prdcNum, title, num, price, total);
	                //판매내역 저장 
	                managerFile.addSellLog(loginId,purchaseDate,prdcNum, title, num, price, total);
               
	                return;
	                
	            } else {
	                throw new AuthenException("잔액이 부족합니다. 현재 잔액: " + useBalance);
	            }
	    		
	        } catch (AuthenException e) {
	            System.out.println(e.toString());
	            break;
	        }
	    }
	}
	
	//회원탈퇴 서비스
	public void deleteId() throws AuthenException {
	    List<Customer> customerList = joinFile.readData();

	    System.out.println("=============================");
	    System.out.println("============회원 탈퇴===========");

	    System.out.print("아이디:");
	    String id = sc.next();

	    System.out.print("비밀번호:");
	    String pw = sc.next(); 
	    
	    boolean success = false;

	    for (Customer c : customerList) {
	        if (c.getId().equals(id) && c.getPw().equals(pw)) {
	        	if (!c.getLeaveDate().equals("x")) { 
	                System.out.println("이미 탈퇴한 회원입니다.");
	                return;
	            }
	            System.out.println();
	            success = true;
	            break;
	        }
	    }	    	    
	    
	    if (!success) {
	        System.out.println("입력한 아이디가 존재하지 않습니다.");
	    } else {
	        JoinFile.withdrawal(customerList, id);
	        System.out.println("=====회원탈퇴가 완료되었습니다!=====");
	    }
	    service();
	}
}
