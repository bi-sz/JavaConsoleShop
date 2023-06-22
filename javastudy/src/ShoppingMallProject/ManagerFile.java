package ShoppingMallProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ManagerFile {
	
	//등록한 물품 엑셀에 저장
    public static int addPrdct(Inventory s) {
    	String[] headers = {"상품번호", "상품명", "가격", "수량"};
        int result = 0;

        try {
            File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = null;
            XSSFWorkbook workbook = null;

            if (!file.exists()) { // 파일이 없을 경우 새로 생성
                workbook = new XSSFWorkbook();
            } else { // 파일이 존재할 경우 해당 파일 읽어오기
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }

            // 상품목록 sheet 생성 또는 가져오기
            XSSFSheet sheet = workbook.getSheet("상품목록");
            if (sheet == null) { // 시트가 없을 경우 생성
                sheet = workbook.createSheet("상품목록");
                XSSFRow headerRow = sheet.createRow(0);

                for (int i = 0; i < headers.length; i++) {
                    XSSFCell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }
            }

            int rows = sheet.getPhysicalNumberOfRows();
            XSSFRow dataRow = sheet.createRow(rows);
            XSSFCell numCell = dataRow.createCell(0);
            numCell.setCellValue(s.getprdctNum());

            XSSFCell titleCell = dataRow.createCell(1);
            titleCell.setCellValue(s.getTitle());

            XSSFCell priceCell = dataRow.createCell(2);
            priceCell.setCellValue(s.getPrice());

            XSSFCell stockCell = dataRow.createCell(3);
            stockCell.setCellValue(s.getStock());

            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("물품이 정상적으로 등록되었습니다.");
            result = 1;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    //엑셀 읽어오기
    public static List<Inventory> readPrdct() {
        List<Inventory> invens = new ArrayList<>();

        try {
            String path = "src/ShoppingMallProject/OdiumMall.xlsx";
            FileInputStream fis = new FileInputStream(new File(path));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            // Get desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheet("상품목록");
            
            if (sheet == null) {
                throw new IOException("상품목록 시트를 찾을 수 없습니다.");
            }

            // Get header row
            XSSFRow headerRow = sheet.getRow(0);

         // Get column indexes for each field
            int numIndex = headerRow.getCell(0).getColumnIndex();
            int titleIndex = headerRow.getCell(1).getColumnIndex();
            int priceIndex = headerRow.getCell(2).getColumnIndex();
            int stockIndex = headerRow.getCell(3).getColumnIndex();

            // Iterate through each row and add to list of customers
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                Inventory inven = new Inventory();

                // Get values from each cell and set customer fields
                int prdctNum = (int)row.getCell(numIndex).getNumericCellValue();
                String title = row.getCell(titleIndex).getStringCellValue();
                int price = (int)row.getCell(priceIndex).getNumericCellValue();
                int stock = (int) row.getCell(stockIndex).getNumericCellValue();

                inven.setprdctNum(prdctNum);
                inven.setTitle(title);
                inven.setPrice(price);
                inven.setStock(stock);

                invens.add(inven);
            }

            fis.close();
        } catch (IOException e) {
//        	e.printStackTrace();
//       	System.out.println("파일이 생성되기 전 입니다.");
//       	System.out.println("정상적으로 파일이 생성되면 오류가 사라지니 무시하고 진행해주세요.");
        }
        
        return invens;
    }
    
    //물품 출력
    public static void outPrdct() {
    	List<Inventory> invens = readPrdct();

        System.out.println("============판매하는 상품 리스트 입니다.============");
        System.out.println("--------------------------------------------");
        System.out.printf("%-5s %-20s %-8s %-5s\n", "물품번호", "물품명", "가격", "수량");
        System.out.println("--------------------------------------------");

        for (Inventory inven : invens) {
            int id = inven.getprdctNum();
            
            if (id > 0) { // 물품 번호가 0보다 큰 경우에만 출력 , del 인 경우에는 출력 x 
                System.out.printf("%-5d %-20s %,8d %5d\n", inven.getprdctNum(), inven.getTitle(), inven.getPrice(), inven.getStock());
            }
        }
    }
    
    //물품 삭제
    public static void deletePrdct(List<Inventory> shoppingList, int num){
    	try {
            String path = "src/ShoppingMallProject/OdiumMall.xlsx";
            FileInputStream fis = new FileInputStream(new File(path));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheet("상품목록");
            // Get header row
            XSSFRow headerRow = sheet.getRow(0);

            // Get column indexes for each field
            int numIndex = headerRow.getCell(0).getColumnIndex();
            int titleIndex = headerRow.getCell(1).getColumnIndex();
            int priceIndex = headerRow.getCell(2).getColumnIndex();
            int stockIndex = headerRow.getCell(3).getColumnIndex();

            // Remove the row of the product with the given num
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);

                if (row.getCell(numIndex).getNumericCellValue() == num) {
                    sheet.removeRow(row);
                    break;
                }
            }
            // Shift rows up to fill the empty row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);

                if (row == null) {
                    sheet.shiftRows(i+1, sheet.getLastRowNum(), -1);
                    i--;
                }
            }

            // Write the changes to the Excel file
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //물품 검색
    public static void searchPrdct(List<Inventory> shoppingList, String str) {
    	for (Inventory item : shoppingList) {
    		if (item.getTitle().contains(str)) {
    			System.out.println(item.toString());
    		}
    	}
    }
    
    //물품 정렬
    public static void sortPrdct() {
        try {
            String path = "src/ShoppingMallProject/OdiumMall.xlsx";
            FileInputStream fis = new FileInputStream(new File(path));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Get header row
            XSSFRow headerRow = sheet.getRow(0);

            // Get column indexes for each field
            int numIndex = headerRow.getCell(0).getColumnIndex();
            int titleIndex = headerRow.getCell(1).getColumnIndex();
            int priceIndex = headerRow.getCell(2).getColumnIndex();
            int stockIndex = headerRow.getCell(3).getColumnIndex();

            // Get all rows and store them in a list
            List<XSSFRow> rows = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                rows.add(sheet.getRow(i));
            }

            // Sort the list based on the prdctNum field
            Collections.sort(rows, new Comparator<XSSFRow>() {
                @Override
                public int compare(XSSFRow o1, XSSFRow o2) {
                    int prdctNum1 = (int) o1.getCell(numIndex).getNumericCellValue();
                    int prdctNum2 = (int) o2.getCell(numIndex).getNumericCellValue();
                    return Integer.compare(prdctNum1, prdctNum2);
                }
            });

            // Rewrite the sorted data to the Excel file
            for (int i = 1; i <= rows.size(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }
                XSSFRow sortedRow = rows.get(i-1);
                row.getCell(numIndex).setCellValue(sortedRow.getCell(numIndex).getNumericCellValue());
                row.getCell(titleIndex).setCellValue(sortedRow.getCell(titleIndex).getStringCellValue());
                row.getCell(priceIndex).setCellValue(sortedRow.getCell(priceIndex).getNumericCellValue());
                row.getCell(stockIndex).setCellValue(sortedRow.getCell(stockIndex).getNumericCellValue());
            }

            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //물류 입고
    public static int prdctCharge(List<Inventory> inventoryList, int prdct, int stock) {
    	int result = 0;

        try {
            File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("상품목록");

            int rows = sheet.getPhysicalNumberOfRows();

            for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // rowIndex 0: header row
                XSSFRow row = sheet.getRow(rowIndex);
                XSSFCell prdctNumCell = row.getCell(0);
                if (prdctNumCell.getNumericCellValue() == prdct) { // 회원목록에서 id를 조회
                    XSSFCell stockCell = row.getCell(3);
                    int currentStock = (int) stockCell.getNumericCellValue(); // 기존 잔액 가져오기
                    stockCell.setCellValue(currentStock + stock); // 잔액에 충전한 금액 더하기

                    FileOutputStream fileOut = new FileOutputStream(file);
                    workbook.write(fileOut);
                    fileOut.close();
                    System.out.println("=========물품이 입고되었습니다.========");
                    result = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //물품 판매하기
    public static void sell(List<Inventory> shoppingList,int prdcNum, int num) {
        try {
            File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("상품목록");

            int rows = sheet.getPhysicalNumberOfRows();

            for (int rowIndex = 1; rowIndex < rows; rowIndex++) { // rowIndex 0: header row
                XSSFRow row = sheet.getRow(rowIndex);
                XSSFCell prdcNumCell = row.getCell(0);
                if (prdcNumCell.getNumericCellValue() == prdcNum) { // 상품목록에서 prdcNum를 조회
                    XSSFCell stockCell = row.getCell(3);
                    int currentBalance = (int) stockCell.getNumericCellValue(); // 기존 수량 가져오기
                    stockCell.setCellValue(currentBalance - num); // 수량에서 결제한 수량 차감

                    FileOutputStream fileOut = new FileOutputStream(file);
                    workbook.write(fileOut);
                    fileOut.close();
//                  System.out.println("=========결제가 완료되었습니다.========");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //판매내역 저장
    public static int addSellLog(String loginId, String purchaseDate, int prdcNum, String title, int num, int price, int total) {
    	String[] headers = {"번호", "판매일자", "구매자id", "상품번호", "상품명", "수량", "가격", "결제금액"};
        int result = 0;

        try {
            File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
            FileInputStream fis = null;
            XSSFWorkbook workbook = null;

            if (!file.exists()) { // 파일이 없을 경우 새로 생성
                workbook = new XSSFWorkbook();
            } else { // 파일이 존재할 경우 해당 파일 읽어오기
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }

            // 판매내역 sheet 생성 또는 가져오기
            XSSFSheet sheet = workbook.getSheet("판매내역");
            if (sheet == null) { // 시트가 없을 경우 생성
                sheet = workbook.createSheet("판매내역");
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

            XSSFCell buyerCell = dataRow.createCell(2);
            buyerCell.setCellValue(loginId);

            XSSFCell prdcNumCell = dataRow.createCell(3);
            prdcNumCell.setCellValue(prdcNum);

            XSSFCell titleCell = dataRow.createCell(4);
            titleCell.setCellValue(title);

            XSSFCell numCell2 = dataRow.createCell(5);
            numCell2.setCellValue(num);

            XSSFCell priceCell = dataRow.createCell(6);
            priceCell.setCellValue(price);

            XSSFCell totalCell = dataRow.createCell(7);
            totalCell.setCellValue(total);

            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            //확인 완료 
//          System.out.println("판매내역이 정상적으로 기록되었습니다.");
            result = 1;

        } catch (IOException e) {
        	//시트가 생성되어있지 않아도 오류가 표기되지 않게 함.
//          e.printStackTrace();
        }

        return result;
    }

    //판매내역 불러오기
  	public static List<String[]> readSellLog() {
  		List<String[]> sellLogList = new ArrayList<>();

  	    try {
  	        File file = new File("src/ShoppingMallProject/OdiumMall.xlsx");
  	        FileInputStream fis = new FileInputStream(file);

  	        XSSFWorkbook workbook = new XSSFWorkbook(fis);
  	        XSSFSheet sheet = workbook.getSheet("판매내역");

  	        if (sheet == null) {
  	            throw new IOException("판매내역이 없습니다.");
  	        }

  	        int rowCount = sheet.getLastRowNum();
  	        for (int i = 1; i <= rowCount; i++) {
  	            XSSFRow row = sheet.getRow(i);
  	            String[] sellLog = new String[8];

  	            for (int j = 0; j < 8; j++) {
  	                XSSFCell cell = row.getCell(j);
  	                if (cell.getCellTypeEnum() == CellType.STRING) {
  	                    sellLog[j] = cell.getStringCellValue();
  	                } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
  	                    sellLog[j] = String.valueOf((int) cell.getNumericCellValue());
  	                }
  	            }
  	            sellLogList.add(sellLog);
  	        }

  	        fis.close();
  	    } catch (IOException e) {
  	        e.printStackTrace();
  	    }

  	    return sellLogList;
  	}

  	//판매내역 출력하기
  	public static void outSellLog() {
  		List<String[]> sellLogList = readSellLog();

  	    System.out.println("==================================판매내역 입니다.=======================================");
  	    System.out.println("------------------------------------------------------------------------------------");
  	    System.out.printf("%-5s %-16s %-10s %-5s %-20s %-5s %-7s %-10s\n", "번호", "판매일자", "구매자 ID", "상품번호", "상품명", "수량", "가격", "결제금액");
  	    System.out.println("------------------------------------------------------------------------------------");

  	    int total = 0; // 총 매출을 저장할 변수

  	    for (String[] sell : sellLogList) {
  	        int id = Integer.parseInt(sell[0]);
  	        String dateTime = sell[1];
  	        String buyerId = sell[2];
  	        int productNo = Integer.parseInt(sell[3]);
  	        String productName = sell[4];
  	        int amount = Integer.parseInt(sell[5]);
  	        int price = Integer.parseInt(sell[6]);
  	        int payment = Integer.parseInt(sell[7]);

  	        System.out.printf("%-5d %-20s %-10s %-5d %-20s %-5d %-10d %-10d\n", id, dateTime, buyerId, productNo, productName, amount, price, payment);
  	        total += payment; // 결제금액을 누적하여 총 매출 계산.
  	    }

  	    System.out.println("------------------------------------------------------------------------------------");
  	    System.out.printf("총 매출: %d원\n", total); // 총 매출 출력
  	    System.out.println("====================================================================================");
  	    System.out.println();
  	}
}