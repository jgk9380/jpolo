package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class DynamicCompileTest {
    public static void main(String[] args) throws IOException {
        DynamicCompileTest dt=new DynamicCompileTest();
        dt.eval("str");
    }

    public Object eval(String str) throws IOException {
        //����java�ļ�
        String s = " class Dynout{\n";
        s += "Object rt(){\n";
        s += "String mc = \"new MyClass()\";\n";
        s += " return mc" + ";\n";
        s += "}\n";
        s += "}\n";
        File f = new File("./Dynout.java");
        PrintWriter pw = new PrintWriter(new FileWriter(f));
        pw.println(s);
        pw.close();
        //��̬����
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        String[] cpargs = new String[] { "-d", "./", "Dynout.java" };
        int status = javac.run(null, null, null, cpargs);
        if (status != 0) {
            System.out.println("û�гɹ�����Դ�ļ�!");
            return null;
        }
        //����Temp��rt�������ؽ��:
        //       //MyClassLoader mc = new MyClassLoader();
        //       Class clasz = mc.loadClass("Test.class",true);
        //       Method rt = clasz.getMethod("rt", new Class[]{ String[].class });
        //      return rt.invoke(null, new Object[] { new String[0] });
        //�������û�з��ؾ�ֱ�ӵ���
        return null;
    }
    
    public void test() throws IOException {
        // �������
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        int result = javaCompiler.run(null, null, null, "-d", "./test", "./test/Hello.java");
        System.out.println(result == 0 ? "��ϲ����ɹ�" : "�Բ������ʧ��:" + result);

        // ���г���
        Runtime run = Runtime.getRuntime();
        Process process = run.exec("java -cp ./ temp/com/Hello");
        InputStream in = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String info = "";
        while ((info = reader.readLine()) != null) {
            System.out.println(info);

        }
    }

}
