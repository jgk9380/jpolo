package test.excel;

import java.io.File;

import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import jxl.read.biff.BiffException;

public class Test {
    public Test() {
        super();
    }

    public static void main(String[] args) throws IOException, BiffException {
        String fileName = "C:\\Users\\jianggk\\Desktop\\待办\\渠道管理\\cost_exam.xls";
        
        File file = new File(fileName); //根据文件名创建一个文件对象
        
        Workbook wb = Workbook.getWorkbook(file); //从文件流中取得Excel工作区对象
        Sheet sheet = wb.getSheet(0); //从工作区中取得页，取得这个对象的时候既可以用名称来获得，也可以用序号。
        String outPut = "";

        outPut = outPut + "<b>" + fileName + "</b>\n";
        outPut = outPut + "第一个sheet的名称为：" + sheet.getName() + "\n";
        outPut = outPut + "第一个sheet共有：" + sheet.getRows() + "行" + sheet.getColumns() + "列<br>";
        outPut = outPut + "具体内容如下：\n";
        for (int i = 0; i < sheet.getRows(); i++) {
            for (int j = 0; j < sheet.getColumns(); j++) {
                Cell cell = sheet.getCell(j, i);
                outPut = outPut + cell.getContents() + " ";
            }
            outPut = outPut + "\n";
        }
       System.out.println(outPut);
    }
}
