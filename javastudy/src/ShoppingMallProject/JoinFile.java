package ShoppingMallProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JoinFile {
	//회원정보 저장
	public static int insertData(Customer c) {
    	String[] headers = {"아이디", "이름", "비밀번호", "잔고", "가입일", "탈퇴일", "권한", "사용금액"};
        int result = 0;

        try {
        	File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = null;
            XSSFWorkbook workbook = null;

            if (!file.exists()) { // 파일이 없을 경우 새로 생성
                workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("회원목록");
                XSSFRow headerRow = sheet.createRow(0);

                for (int i = 0; i < headers.length; i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }
                fis = null;
            } else { // 파일이 존재할 경우 해당 파일 읽어오기
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }

            XSSFSheet sheet = workbook.getSheet("회원목록");
            if (sheet == null) { // 시트가 없을 경우 생성
                sheet = workbook.createSheet("회원목록");
                XSSFRow headerRow = sheet.createRow(0);

                for (int i = 0; i < headers.length; i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }
            }

            int rows = sheet.getPhysicalNumberOfRows();
            XSSFRow dataRow = sheet.createRow(rows);

            XSSFCell idCell = dataRow.createCell(0);
            idCell.setCellValue(c.getId());

            XSSFCell nameCell = dataRow.createCell(1);
            nameCell.setCellValue(c.getName());

            XSSFCell pwCell = dataRow.createCell(2);
            pwCell.setCellValue(c.getPw());

            XSSFCell balanceCell = dataRow.createCell(3);
            balanceCell.setCellValue(0);

            XSSFCell joinDateCell = dataRow.createCell(4);
            joinDateCell.setCellValue(c.getJoinDate());

            XSSFCell leaveDateCell = dataRow.createCell(5);
            leaveDateCell.setCellValue(c.getLeaveDate());

            XSSFCell authCell = dataRow.createCell(6);
            authCell.setCellValue(c.getId().equals("admin1") ? 255 : c.getAuth());
            
            XSSFCell amountCell = dataRow.createCell(7);
            amountCell.setCellValue(c.getAmount());

            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("==회원 목록에 정보가 업로드 되었습니다.==");
            result = 1;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
	//회원정보 읽어오기
    public static List<Customer> readData() {
    	List<Customer> customerList = new ArrayList<Customer>();
        String[] headers = {"아이디", "이름", "비밀번호", "잔고", "가입일", "탈퇴일", "권한", "사용금액"};

        try {
            File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            XSSFSheet sheet = workbook.getSheet("회원목록");

            int rows = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);

                Customer customer = new Customer();
                customer.setId(row.getCell(0).getStringCellValue());
                customer.setName(row.getCell(1).getStringCellValue());
                customer.setPw(row.getCell(2).getStringCellValue());
                customer.setBalance((int)row.getCell(3).getNumericCellValue());
                customer.setJoinDate(row.getCell(4).getStringCellValue());
                customer.setLeaveDate(row.getCell(5).getStringCellValue());
                customer.setAuth((int) row.getCell(6).getNumericCellValue());
                customer.setAmount((int) row.getCell(7).getNumericCellValue());

                customerList.add(customer);
            }

            workbook.close();
            fis.close();

        } catch (IOException e) {    	
        	//오류는 표시되지 않도록 주석처리
//          e.printStackTrace();
        }

        return customerList;
    }
    
    //회원 탈퇴하기
    public static int withdrawal(List<Customer> customerList, String id) {
    	int result = 0;
    	
        try {
        	File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("회원목록");

            int rows = sheet.getPhysicalNumberOfRows();

            for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // rowIndex 0: header row
                XSSFRow row = sheet.getRow(rowIndex);
                XSSFCell idCell = row.getCell(0);
                if (idCell.getStringCellValue().equals(id)) { // found matching id
                    XSSFCell leaveDateCell = row.createCell(5);
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedNow = now.format(formatter);
                    leaveDateCell.setCellValue(formattedNow);

                    FileOutputStream fileOut = new FileOutputStream(file);
                    workbook.write(fileOut);
                    fileOut.close();
                    System.out.println("=========회원탈퇴 되었습니다.========");
                    result = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //회원 삭제하기
    public static void deleteData(List<Customer> customerList, String id) {
    }
  
    //아이디 찾기
    public static void searchId(List<Customer> customerList, String name) {
    	boolean found = false;
        for (Customer c : customerList) {
            if (c.getName().equals(name)) {
                System.out.println(c.searchId());
                found = true;
            }
        }
        if (!found) {
            System.out.println("입력한 이름과 일치하는 회원이 없습니다.");
        }
    }
    
    //비밀번호찾기
    public static void searchPw(List<Customer> customerList, String id) {
    	boolean found = false;
        for (Customer c : customerList) {
            if (c.getId().equals(id)) {
                System.out.println(c.searchPw());
                found = true;
            }
        }
        if (!found) {
            System.out.println("입력한 ID와 일치하는 회원이 없습니다.");
        }
    }
    
    //관리자 목록 조회
    public static void outDataManager() {
    	List<Customer> customers = readData();
    	
    	System.out.println("==========관리자 목록 입니다.=========");
        System.out.println("-------------------------------");
        System.out.printf("%-10s %-10s\n", "아이디", "성명");
        System.out.println("-------------------------------");

        for (Customer customer : customers) {
            String id = customer.getId();
            if(customer.getAuth()==255) {
                System.out.printf("%-10s %-10s\n", id, customer.getName());
            }
        }
        System.out.println("-------------------------------");
        System.out.println("=================================");
        
        System.out.println();
    }
    
    //회원 목록 조회
    public static void outDataCustomer() {
    	List<Customer> customers = readData();

        System.out.println("===========회원 목록 입니다==========");
        System.out.println("-------------------------------");
        System.out.printf("%-10s %-10s %-10s\n", "아이디", "성명", "사용금액");
        System.out.println("-------------------------------");

        for (Customer customer : customers) {
            String id = customer.getId();
            if(customer.getAuth() != 255 && customer.getLeaveDate().equals("x")) {
                System.out.printf("%-10s %-10s %-10d\n", id, customer.getName(), customer.getAmount());
            }
        }
        System.out.println("-------------------------------");
        System.out.println("================================");
    }
    
    //탈퇴환 회원 목록 조회
    public static void outDataWithdrawal() {
    	List<Customer> customers = readData();

        System.out.println("==========회원 탈퇴 목록 입니다=========");
        System.out.println("-------------------------------");
        System.out.printf("%-10s %-10s %-10s\n", "아이디", "성명", "사용금액");
        System.out.println("-------------------------------");

        for (Customer customer : customers) {
            String id = customer.getId();
            if(customer.getAuth() != 255 && !customer.getLeaveDate().equals("x")) {
                System.out.printf("%-10s %-10s %-10d\n", id, customer.getName(), customer.getAmount());
            }
        }
        System.out.println("-------------------------------");
        System.out.println("================================");
    }
    
    //잔액 충전하기
    public static int balanceCharge(List<Customer> customerList, String loginId, int charge) {
    	int result = 0;

        try {
            File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("회원목록");

            int rows = sheet.getPhysicalNumberOfRows();

            for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // rowIndex 0: header row
                XSSFRow row = sheet.getRow(rowIndex);
                XSSFCell idCell = row.getCell(0);
                if (idCell.getStringCellValue().equals(loginId)) { // 회원목록에서 id를 조회
                    XSSFCell balanceCell = row.getCell(3);
                    int currentBalance = (int) balanceCell.getNumericCellValue(); // 기존 잔액 가져오기
                    balanceCell.setCellValue(currentBalance + charge); // 잔액에 충전한 금액 더하기

                    FileOutputStream fileOut = new FileOutputStream(file);
                    workbook.write(fileOut);
                    fileOut.close();
                    System.out.println("=========잔액이 충전되었습니다.========");
                    result = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //잔액 사용하기 + 사용금액 갱신
    public static int balanceUse(List<Customer> customerList, String loginId, int use) {
    	int result = 0;

        try {
            File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("회원목록");

            int rows = sheet.getPhysicalNumberOfRows();

            for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // rowIndex 0: header row
                XSSFRow row = sheet.getRow(rowIndex);
                XSSFCell idCell = row.getCell(0);
                if (idCell.getStringCellValue().equals(loginId)) { // 회원목록에서 id를 조회
                    XSSFCell balanceCell = row.getCell(3);
                    int currentBalance = (int) balanceCell.getNumericCellValue(); // 기존 잔액 가져오기
                    balanceCell.setCellValue(currentBalance - use); // 잔액에서 결제한 금액 차감
                    
                    XSSFCell amountCell = row.getCell(7);	
                    int currentAmount = (int) amountCell.getNumericCellValue(); // 기존 사용금액 가져오기
                    amountCell.setCellValue(currentAmount + use); // 사용금액에 결제한 금액 추가
                    

                    FileOutputStream fileOut = new FileOutputStream(file);
                    workbook.write(fileOut);
                    fileOut.close();
                    System.out.println("-----------------구매 영수증-----------------");
                    System.out.println("[기존 잔고:"+currentBalance+"원|결제 금액:"+use+"원|결제 후 잔고:"+(currentBalance-use)+"]");
                    System.out.println("------------------------------------------");
                    result = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //잔고 출력
    public static void outDataBalance(String loginId) {
    	List<Customer> customers = readData();
    	
    	String name = "";
    	int balance = 0;
    	for (Customer customer : customers) {
    	    if (customer.getId().equals(loginId)) {
    	        name = customer.getName();
    	        balance = customer.getBalance();
    	        break;
    	    }
    	}

//   	System.out.println("===== "+name +"님의 마이페이지 입니다.=====");
    	System.out.println("잔고 : " + balance);
//    	System.out.println("====================================");
    }

    //마이페이지 - 회원정보 보여주기
    public static void mypageService(String loginId) {
    	List<Customer> customers = readData();

        String name = "";
        int balance = 0;
        int amount = 0;

        for (Customer customer : customers) {
            if (customer.getId().equals(loginId)) {
                name = customer.getName();
                balance = customer.getBalance();
                amount = customer.getAmount();
                break;
            }
        }

        System.out.println("===" + name + " 님의 마이페이지 입니다.===");
        System.out.println("-------------------------------");
        System.out.printf("%-10s %-10s %-10s\n", "아이디", "성명", "잔고", "사용금액");
        System.out.println("-------------------------------");
        System.out.printf("%-10s %-10s %-10d\n", loginId, name, balance, amount);
        System.out.println("-------------------------------");
        System.out.println("================================");
    }
}
