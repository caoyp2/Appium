package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataUtil {

    public Workbook workbook;
    public Sheet sheet;
    public String filePath;

    public ExcelDataUtil(String filePath, String sheetname){
        try {
            InputStream is = new FileInputStream(new File(filePath));
            if(filePath.contains(".xlsx")){
                workbook = new XSSFWorkbook(is);
            }else { //以.xls结尾的文件
                workbook = new HSSFWorkbook(is);
            }
            sheet = workbook.getSheet(sheetname);
            this.filePath = filePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取指定cell的值
     * @param row
     * @param col
     * @return
     */
    public String getCellData(int row,int col){
        String data = "";
        Cell cell = sheet.getRow(row).getCell(col);
        if(cell.getCellType() == CellType.STRING){
            data = cell.getStringCellValue();
        }else if(cell.getCellType() == CellType.NUMERIC){
            DecimalFormat df = new DecimalFormat();
            data = df.format(cell.getNumericCellValue());
        }
        return data;
    }

    public void setCellData(int row, int col, String data){
        Cell cell = sheet.getRow(row).getCell(col);
        cell.setCellValue(data);

        try {
            OutputStream os = new FileOutputStream(filePath);
            // 将内容写入 excel 文件中
            workbook.write(os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭流
    public void close(){
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object[][] getTestData(){
        List<Object[]> records = new ArrayList<Object[]>();
        int rownum = sheet.getLastRowNum() - sheet.getFirstRowNum();
        for(int i=1; i <= rownum; i++){
            Row row = sheet.getRow(i);
            int colnum = row.getLastCellNum();
            String[] datas = new String[colnum];
            for(int j=0; j < colnum; j++){
                Cell cell = row.getCell(j);
                if(cell.getCellType() == CellType.STRING){
                    datas[j] = cell.getStringCellValue();
                }else if(cell.getCellType() == CellType.NUMERIC){
                    DecimalFormat df = new DecimalFormat();
                    datas[j] = df.format(cell.getNumericCellValue());
                }
            }
            records.add(datas);
        }

        //将records转成Object[][]
        Object[][] objects = new Object[records.size()][];
        for(int x=0; x < objects.length; x++){
            Object[] coldatas = records.get(x);
            objects[x] = new Object[coldatas.length];
            for(int y=0; y < coldatas.length; y++){
                objects[x][y] = coldatas[y];
            }
        }

        return objects;
    }

    public static void main(String[] args) {

    }
}
