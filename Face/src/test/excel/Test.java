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
        String fileName = "C:\\Users\\jianggk\\Desktop\\����\\��������\\cost_exam.xls";
        
        File file = new File(fileName); //�����ļ�������һ���ļ�����
        
        Workbook wb = Workbook.getWorkbook(file); //���ļ�����ȡ��Excel����������
        Sheet sheet = wb.getSheet(0); //�ӹ�������ȡ��ҳ��ȡ����������ʱ��ȿ�������������ã�Ҳ��������š�
        String outPut = "";

        outPut = outPut + "<b>" + fileName + "</b>\n";
        outPut = outPut + "��һ��sheet������Ϊ��" + sheet.getName() + "\n";
        outPut = outPut + "��һ��sheet���У�" + sheet.getRows() + "��" + sheet.getColumns() + "��<br>";
        outPut = outPut + "�����������£�\n";
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
