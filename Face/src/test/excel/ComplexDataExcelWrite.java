package test.excel;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Calendar;

import java.util.Date;

import jxl.Workbook;

import jxl.write.DateFormats;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ComplexDataExcelWrite {
    public void createExcel(OutputStream os) throws WriteException,IOException {
        //����������
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //�����µ�һҳ
        WritableSheet sheet = workbook.createSheet("First Sheet", 0);
        //����Ҫ��ʾ�ľ�������
        Label formate = new Label(0,0,"���ݸ�ʽ");
        sheet.addCell(formate);
        Label floats = new Label(1,0,"������");
        sheet.addCell(floats);
        Label integers = new Label(2,0,"����");
        sheet.addCell(integers);
        Label booleans = new Label(3,0,"������");
        sheet.addCell(booleans);
        Label dates = new Label(4,0,"���ڸ�ʽ");
        sheet.addCell(dates);
        
        Label example = new Label(0,1,"����ʾ��");
        sheet.addCell(example);
        //��������
        jxl.write.Number    number = new jxl.write.Number(1,1,3.1415926535);
        sheet.addCell(number);
        //��������
        jxl.write.Number ints = new jxl.write.Number(2,1,15042699);
        sheet.addCell(ints);
        jxl.write.Boolean bools = new jxl.write.Boolean(3,1,true);
        sheet.addCell(bools);
        //����������
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        WritableCellFormat cf1 = new WritableCellFormat(DateFormats.FORMAT1);
        jxl.write.DateTime dt = new jxl.write.DateTime(4,1,date,cf1);
        sheet.addCell(dt);
        //�Ѵ���������д�뵽������У����ر������
        workbook.write();
        workbook.close();
        os.close();
        
    }
}
