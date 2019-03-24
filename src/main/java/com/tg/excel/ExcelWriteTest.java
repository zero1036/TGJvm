package com.tg.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by linzc on 2018/5/31.
 */
public class ExcelWriteTest {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) {

        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("BankName", "BankName");
        dataMap.put("Addr", "Addr");
        dataMap.put("Phone", "Phone");
        List<Map> list = new ArrayList<Map>();
        list.add(dataMap);
        writeExcel(list, 3, "D:/readExcel.xls");

    }

    @Test
    public void test2() throws Exception{

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "BankName");
        dataMap.put("msg", "Addr");
        dataMap.put("age", 12);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(dataMap);
        writeExcelEvent(list, "D:/readExcel.xls");

    }


    public void writeExcelEvent(List<Map<String, Object>> dataList, String finalXlsxPath) throws Exception {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("没有输出结果");
        }

        OutputStream out = null;
        try {
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            //默认使用第1页sheet
            Sheet sheet = workBook.getSheetAt(1);
            //删除原有数据，除了属性列
            int rowNumber = sheet.getLastRowNum();
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);

            //第一行写入字段名
            Row firstRow = sheet.createRow(0);
            int col = 0;
            for (Map.Entry<String, Object> entry : dataList.get(0).entrySet()) {
                Cell first = firstRow.createCell(col);
                first.setCellValue(entry.getKey());
                col++;
            }

            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                Map<String, Object> dataMap = dataList.get(j);

                int colValue = 0;
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                    Cell first = row.createCell(colValue);
                    first.setCellValue(entry.getValue().toString());
                    colValue++;
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                /**/
            }
        }
    }

    public static void writeExcel(List<Map> dataList, int cloumnCount, String finalXlsxPath) {
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(1);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                Map dataMap = dataList.get(j);
                String name = dataMap.get("BankName").toString();
                String address = dataMap.get("Addr").toString();
                String phone = dataMap.get("Phone").toString();
                for (int k = 0; k <= columnNumCount; k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(name);

                    Cell second = row.createCell(1);
                    second.setCellValue(address);

                    Cell third = row.createCell(2);
                    third.setCellValue(phone);
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
                wb = new HSSFWorkbook(inputStream);
            } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
                wb = new XSSFWorkbook(inputStream);
            }
            return wb;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
