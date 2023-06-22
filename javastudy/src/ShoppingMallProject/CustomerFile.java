package ShoppingMallProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CustomerFile {
	//구매내역 저장
	public static int addHistory(String loginId, String purchaseDate, int prdcNum, String title, int num, int price, int total) {
	    String[] headers = {"번호", "구매일자", "상품번호", "상품명", "수량", "가격", "결제금액"};
	    int result = 0;

	    try {
	        File file = new File("src/ShoppingMallProject/History.xlsx");
	        FileInputStream fis = null;
	        XSSFWorkbook workbook = null;

	        if (!file.exists()) { // 파일이 없을 경우 새로 생성
	            workbook = new XSSFWorkbook();
	        } else { // 파일이 존재할 경우 해당 파일 읽어오기
	            fis = new FileInputStream(file);
	            workbook = new XSSFWorkbook(fis);
	        }

	        // 상품목록 sheet 생성 또는 가져오기
	        XSSFSheet sheet = workbook.getSheet(loginId);
	        if (sheet == null) { // 시트가 없을 경우 생성
	            sheet = workbook.createSheet(loginId);
	            XSSFRow headerRow = sheet.createRow(0);

	            for (int i = 0; i < headers.length; i++) {
	                XSSFCell cell = headerRow.createCell(i);
	                cell.setCellValue(headers[i]);
	            }
	        }
	        int rows = sheet.getPhysicalNumberOfRows();
	        XSSFRow dataRow = sheet.createRow(rows);

	        XSSFCell numCell = dataRow.createCell(0);
	        numCell.setCellValue(rows);

	        XSSFCell dateCell = dataRow.createCell(1);
	        dateCell.setCellValue(purchaseDate);

	        XSSFCell prdcNumCell = dataRow.createCell(2);
	        prdcNumCell.setCellValue(prdcNum);

	        XSSFCell titleCell = dataRow.createCell(3);
	        titleCell.setCellValue(title);

	        XSSFCell numCell2 = dataRow.createCell(4);
	        numCell2.setCellValue(num);

	        XSSFCell priceCell = dataRow.createCell(5);
	        priceCell.setCellValue(price);

	        XSSFCell totalCell = dataRow.createCell(6);
	        totalCell.setCellValue(total);

	        FileOutputStream fileOut = new FileOutputStream(file);
	        workbook.write(fileOut);
	        fileOut.close();
	        //확인 완료
	 //     System.out.println("구매내역이 정상적으로 저장되었습니다.");
	        result = 1;
	    } catch (IOException e) {
	    	//파일이 생성되어있지 않아도 오류가 뜨지 않게 함.
	 //     e.printStackTrace();
	    }
	    return result;
	}

	//구매내역 불러오기
	public static List<String[]> readHistory(String loginId) {
		List<String[]> historyList = new ArrayList<>();

	    try {
	        File file = new File("src/ShoppingMallProject/History.xlsx");
	        FileInputStream fis = new FileInputStream(file);

	        XSSFWorkbook workbook = new XSSFWorkbook(fis);
	        XSSFSheet sheet = workbook.getSheet(loginId);

	        if (sheet == null) {
	            throw new IOException("구매 이력이 없습니다.");
	        }

	        int rowCount = sheet.getLastRowNum();
	        for (int i = 1; i <= rowCount; i++) {
	            XSSFRow row = sheet.getRow(i);
	            String[] history = new String[7];

	            for (int j = 0; j < 7; j++) {
	                XSSFCell cell = row.getCell(j);
	                if (cell.getCellTypeEnum() == CellType.STRING) {
	                	history[j] = cell.getStringCellValue();
	                } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
	                    history[j] = String.valueOf(cell.getNumericCellValue());
	                }else {
	                    history[j] = "";
	                }
	            }
	            historyList.add(history);
	        }

	        fis.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return historyList;
	}

	//구매내역 출력하기
	public static void outHistory(String loginId) {
		List<String[]> historyList = readHistory(loginId);

	    System.out.println("==============================구매목록 입니다..===================================");
	    System.out.println("-----------------------------------------------------------------------------");
	    System.out.printf("%-5s %-16s %-5s %-20s %-5s %-7s %-10s\n", "번호", "구매일자", "상품번호", "상품명", "수량", "가격", "결제금액");
	    System.out.println("-----------------------------------------------------------------------------");

	    int totalPayment = 0; // 총 결제금액을 저장할 변수

	    for (String[] history : historyList) {
	        int num = (int) Double.parseDouble(history[0]); // 번호
	        String purchaseDate = history[1]; // 구매일자
	        int prdcNum = (int) Double.parseDouble(history[2]); // 상품번호
	        String title = history[3]; // 상품명
	        int amount = (int) Double.parseDouble(history[4]); // 수량
	        int price = (int) Double.parseDouble(history[5]); // 가격
	        int total = (int) Double.parseDouble(history[6]); // 결제금액

	        System.out.printf("%-5d %-20s %-5d %-20s %-5d %-10d %-10d\n", num, purchaseDate, prdcNum, title, amount, price, total);
	        totalPayment += total; // 결제금액을 누적하여 총 결제금액 계산
	    }

	    System.out.println("-----------------------------------------------------------------------------");
	    System.out.printf("총 결제금액: %d원\n", totalPayment); // 총 결제금액 출력
	    System.out.println("=============================================================================");
	    System.out.println();
	}

}
