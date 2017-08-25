import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static void main(String[] args) {
    	String path = "D:/test/PSIS151_03002411376_20170328.xlsx";
    	Map<String,List<Map<String,Object>>> sourceData = new HashMap<String,List<Map<String,Object>>>();
        
        try {
        	getDetailDataFromXlsx(path, sourceData);
        	getSummaryDataFromXlsx(path, sourceData);
        	System.out.println(sourceData);
        	List<Map<String,String>> resultData = getResultData(sourceData);
        	createSheetAndWriteResult(path,resultData);
        	System.out.println(sourceData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @author kenan.zhang
     * @Description: 输入明细数据和汇总数据,获取结果数据
     * @date 2017-08-24
     */
     public static List<Map<String,String>> getResultData(Map<String,List<Map<String,Object>>> sourceData){
    	 //TODO
    	 return null;
     }
     
    /**
     * 
     * @author kenan.zhang
     * @throws Exception 
     * @Description: 创建一个新的sheet,并将汇总数据和明细数据的对应关系写入
     * @date 2017-08-24
     */
     public static void createSheetAndWriteResult(String path,List<Map<String,String>> result) throws Exception {
    	 InputStream is = new FileInputStream(path);
         XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
         FileOutputStream fos = new FileOutputStream(path);
    	 xssfWorkbook.createSheet("结果数据");
    	 //向"结果数据"sheet写入结果数据
    	 XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(xssfWorkbook.getNumberOfSheets()-1);
    	 for(int i=0;i<result.size();i++){
    		 Map<String,String> data = result.get(i);
    		 XSSFRow row = xssfSheet.createRow(i+1);
    		 for(int j=0;j<3;j++){
    			 Cell cell = row.createCell(j);
    			 if(i==0){
    	    		 switch (j) {
    				 case 0:
    					cell.setCellValue("序号");
    					break;
    				 case 1:
    					cell.setCellValue("汇总数据");
    					break;
    				 case 2:
    					cell.setCellValue("明细数据");
    					break;
    				 default:
    					break;
    				 }
    			 }else{
    				 switch (j) {
    				 case 0:
    					cell.setCellValue(data.get("serialNo"));
    					break;
    				 case 1:
    					cell.setCellValue(data.get("summaryMoney"));
    					break;
    				 case 2:
    					cell.setCellValue(data.get("detailMoney"));
    					break;
    				 default:
    					break;
    				 }
    			 }
    		 }
    	 }
    	 xssfWorkbook.write(fos);
    	 fos.close();
     }

    /**
    * 
    * @author kenan.zhang
    * @Description: 处理xlsx文件,从中获取明细数据------最后一个sheet存储汇总数据,前面的sheet存储明细数据
    * @throws
    * @date 2017-08-23
    */
    public static Map<String,List<Map<String,Object>>> getDetailDataFromXlsx(String path,Map<String,List<Map<String,Object>>> result) throws Exception {
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<Map<String,Object>> rowList = new LinkedList<Map<String,Object>>();
        int numberOfSheets = xssfWorkbook.getNumberOfSheets();
        //循环每一页,并处理当前循环页---明细数据
        for (int i=0;i<numberOfSheets-1;i++) {
        	XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(i);
            if (xssfSheet == null) {
                continue;
            }
            //处理当前页,循环读取每一行,从第5行开始为明细数据
            for (int rowNum = 4; rowNum < xssfSheet.getPhysicalNumberOfRows(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                Map<String,Object> c = new HashMap<String,Object>();
                BigDecimal detailMoney = new BigDecimal(getStringVal(xssfRow.getCell(2)));
                c.put("serialNo", xssfRow.getCell(0).toString());
                c.put("detailMoney", detailMoney);
                rowList.add(c);
            }
            //明细数据
            result.put("detailData", rowList);
        }
        return result;
    }
    
    
    /**
     * 
     * @author kenan.zhang
     * @Description: 处理xlsx文件,从中获取汇总数据------最后一个sheet存储汇总数据,前面的sheet存储明细数据
     * @throws
     * @date 2017-08-24
     */
     public static Map<String,List<Map<String,Object>>> getSummaryDataFromXlsx(String path,Map<String,List<Map<String,Object>>> result) throws Exception {
         InputStream is = new FileInputStream(path);
         XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
         
         List<Map<String,Object>> rowList = new LinkedList<Map<String,Object>>();
         int numberOfSheets = xssfWorkbook.getNumberOfSheets();
         //只使用最后一个sheet
         XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numberOfSheets-1);
         //处理当前页,循环读取每一行,从第8行开始为汇总数据
         for (int rowNum = 7; rowNum < xssfSheet.getPhysicalNumberOfRows(); rowNum++) {
             XSSFRow xssfRow = xssfSheet.getRow(rowNum);
             Map<String,Object> c = new HashMap<String,Object>();
             BigDecimal summaryMoney;
             BigDecimal decimal5 = new BigDecimal(getStringVal(xssfRow.getCell(4)));
             BigDecimal decimal6 = new BigDecimal(getStringVal(xssfRow.getCell(5)));
             if(decimal5.compareTo(decimal6) == 1){
            	 summaryMoney = decimal5;
             }else{
            	 summaryMoney = decimal6;
             }
             c.put("serialNo", xssfRow.getCell(0).toString());
             c.put("summaryMoney", summaryMoney);
             rowList.add(c);
         }
         //明细数据
         result.put("summaryData", rowList);
         return result;
     }

    public static String getStringVal(XSSFCell cell) {
    	
    	DecimalFormat df = new DecimalFormat("0.00");  
        switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_NUMERIC:
	            return df.format(cell.getNumericCellValue());
	        default:
	            return "0";
        }
    }
}