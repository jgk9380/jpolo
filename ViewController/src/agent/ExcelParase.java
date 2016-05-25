package agent;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import jxl.read.biff.BiffException;

public class ExcelParase {
    public ExcelParase() {
        super();
    }

    public static List<Map<String, String>> paraseExcel(String fileName) throws IOException, BiffException {
        List<Map<String, String>> res = new ArrayList<>();
        File file = new File(fileName); //�����ļ�������һ���ļ�����

        Workbook wb = Workbook.getWorkbook(file); //���ļ�����ȡ��Excel����������
        Sheet sheet = wb.getSheet(0); //�ӹ�������ȡ��ҳ��ȡ����������ʱ��ȿ�������������ã�Ҳ��������š�
        String outPut = "";
        Cell[] headCellss = sheet.getRow(0);
        for (int i = 1; i < sheet.getRows(); i++) {
            Map<String, String> row = new HashMap<>();
            for (int j = 0; j < headCellss.length; j++) {
                Cell cell = sheet.getCell(j, i);
                //outPut = outPut + cell.getContents() + " ";
                row.put(headCellss[j].getContents(), cell.getContents());
            }
            res.add(row);
            outPut = outPut + "\n";
        }
        return res;
    }

    public void test(String fileName) throws IOException, BiffException {
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


    public static void main(String[] args) throws IOException, BiffException {
        String fileName = "C:\\Users\\jianggk\\Desktop\\����\\��������\\cost_exam.xls";
        ExcelParase ep = new ExcelParase();
        List<Map<String, String>> l = ep.paraseExcel(fileName);
        for (Map<String, String> m : l)
            System.out.println(m);
    }
}
