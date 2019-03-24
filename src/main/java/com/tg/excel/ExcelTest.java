package com.tg.excel;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import jxl.Cell;
import jxl.CellType;
import jxl.read.biff.CellValue;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.*;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


/**
 * Created by linzc on 2018/5/31.
 */
public class ExcelTest {
    private Gson gson = new Gson();

    @Test
    public void test() {
        Map<String, Object> fieldMap = new HashMap<String, Object>();

        fieldMap.put("name", "tg");
        fieldMap.put("age", 33);

        List<Map<String, Object>> ourrr = Lists.newArrayList();
        ourrr.add(fieldMap);

        String s = gson.toJson(ourrr);


        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        File file = new File("D:/readExcel.xls");
        List excelList = this.readExcel(file);
        System.out.println("list中的数据打印出来");
        for (int i = 0; i < excelList.size(); i++) {
            List list = (List) excelList.get(i);
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j));
            }
            System.out.println();
        }
    }


    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List readExcel(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList = new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    List innerList = new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        Cell cell = sheet.getCell(j, i);


                        String cellinfo = sheet.getCell(j, i).getContents();
                        if (cellinfo.isEmpty()) {
                            continue;
                        }
                        innerList.add(cellinfo);
                        System.out.print(cellinfo);
                    }
                    outerList.add(i, innerList);
                    System.out.println();
                }
                return outerList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void test2() {

        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        File file = new File("D:/readExcel.xls");
        List<String> excelList = this.readEventFromExcel(file);
        System.out.println("list中的数据打印出来");
        for (String value : excelList) {

            System.out.println(value);
        }
    }

    public List<String> readEventFromExcel(File file) {
        List<String> list = Lists.newArrayList();
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
//            int sheet_size = wb.getNumberOfSheets();

            // 每个页签创建一个Sheet对象
            Sheet sheet = wb.getSheet(0);
            Map<String, Object> map = new HashMap<>();

            // sheet.getRows()返回该页的总行数
            for (int i = 1; i < sheet.getRows(); i++) {
                for (int j = 0; j < sheet.getColumns(); j++) {
                    Cell fieldCell = sheet.getCell(j, 0);
                    String fieldName = fieldCell.getContents();
                    if (Strings.isNullOrEmpty(fieldName)) {
                        throw new IllegalArgumentException(String.format("行%s列%s格式错误", 0, j));
                    }

                    Cell cell = sheet.getCell(j, i);
                    if (CellType.NUMBER.equals(cell.getType())) {
                        try {
                            Double value = Double.valueOf(cell.getContents());
                            map.put(fieldName, value);
                        } catch (NumberFormatException ex) {
                            throw new IllegalArgumentException(String.format("行%s列%s格式错误", i, j));
                        }
                    } else {
                        String value = cell.getContents();
                        if (Strings.isNullOrEmpty(value)) {
                            throw new IllegalArgumentException(String.format("行%s列%s格式错误", 0, j));
                        }
                        map.put(fieldName, value);
                    }
                }
                String jsonValue = gson.toJson(map);
                list.add(jsonValue);
            }
        } catch (BiffException | IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
