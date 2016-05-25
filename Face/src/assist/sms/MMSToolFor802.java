package assist.sms;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MMSToolFor802  {
  private   Socket sock;
    private BufferedReader inputBR;
    private PrintWriter outputPW;
    private DataInputStream inputDIS;
    private DataOutputStream outputDOS;

    private String ServerAddress = "132.224.34.55"; //服务器地址
    private int ServerPort = 63020; //服务器地址
    private int TimeOut = 100; //超时

    public MMSToolFor802() {
        sock=new Socket();
    }

    private void connect() throws IOException {     
            sock.connect(new InetSocketAddress(InetAddress.getByName(ServerAddress), ServerPort), TimeOut);
            inputDIS = new DataInputStream(sock.getInputStream());
            outputDOS = new DataOutputStream(sock.getOutputStream());
            inputBR = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            outputPW = new PrintWriter(sock.getOutputStream());      
    }

    private void writeSocket(String Msg) {
        outputPW.print(Msg);
        outputPW.flush();
    }

    private String readSocket(char endcharacter) {
        int ch;
        StringBuffer buf = new StringBuffer();
        try {
            while ((ch = inputBR.read()) != -1) {
                buf.append((char) ch);
                if ((char) ch == endcharacter)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    private String readSocket() {
        int ch;
        StringBuffer buf = new StringBuffer();
        try {
            while ((ch = inputBR.read()) != -1) {
                buf.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public void close() {
        try {
            if (sock.isConnected()) {
                sock.close();
                if (outputPW != null)
                    outputPW.close();
                if (inputBR != null)
                    inputBR.close();
                if (inputDIS != null)
                    inputDIS.close();
                if (outputDOS != null)
                    outputDOS.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 补空
    private String fillWithBlankFixedLength(String theString, int length) {
        StringBuffer buf = new StringBuffer(theString);
        for (int i = theString.length(); i < length; i++) {
            buf.append(' ');
        }
        return buf.toString();
    }

    private boolean isEmptyIgnoreBlank(String theString) {
        return theString == null || theString.trim().length() == 0;
    }

    // - 彩信发送
    private boolean MMSSend(String OfficeID, String OperatorID, String Msisdn, String Title, String Body) {
        boolean SUCCESS = false;
        if (OfficeID != null && OfficeID.trim().length() > 0 && OperatorID != null && OperatorID.trim().length() > 0 &&
            Msisdn != null && Msisdn.trim().length() > 0) {
            if (sock.isConnected()) {
                String A0 = fillWithBlankFixedLength("10", 2); // 版本信息
                String A1 = fillWithBlankFixedLength("4207", 5); // 数据包长度
                String A2 = fillWithBlankFixedLength("", 20); // 流水号
                String A3 = fillWithBlankFixedLength("1", 1); // 标志位
                String A4 = fillWithBlankFixedLength("c_usermms", 12); // 服务类型
                String A5 = fillWithBlankFixedLength("", 20); // 业务号码
                String A6 = fillWithBlankFixedLength("", 1); // 号码类型
                String A7 = fillWithBlankFixedLength(OperatorID, 6); // 营业员
                String A8 = fillWithBlankFixedLength(OfficeID, 8); // 营业厅
                String A9 = fillWithBlankFixedLength("00001", 5); // 包编号
                String A10 = fillWithBlankFixedLength("1", 1); // 最后一包标志
                String A11 = fillWithBlankFixedLength("", 5); // 错误码
                String B1 = fillWithBlankFixedLength(Msisdn, 20); // 包1
                String B2 = fillWithBlankFixedLength(Title, 100); // 包2
                String B3 = fillWithBlankFixedLength(Body, 4000); // 包3
                String Request = A0 + A1 + A2 + A3 + A4 + A5 + A6 + A7 + A8 + A9 + A10 + A11 + B1 + B2 + B3 + "\n";
                writeSocket(Request);
                String Response = readSocket('\n');
                readSocket();
                SUCCESS = Response.length() > 27 && Response.charAt(27) == '1';
            }
        }
        return SUCCESS;
    }



    public static boolean sendSMS(String tele, String smsConetent,String smsHead) throws IOException {
        MMSToolFor802 nai = new MMSToolFor802();
        nai.connect();       
        boolean b = nai.MMSSend("3452973", "J0000SYS", tele, smsHead, smsConetent);
        System.out.println("发送成功");
        nai.close();
        return b;
    }
    public static void main(String[] args) throws IOException {
        MMSToolFor802 nai = new MMSToolFor802();
        nai.connect();
        //System.out.println(nai.MMSSend("接口工号仓位","接口工号","发送号码","彩信标题","彩信内容"));
        System.out.println(nai.MMSSend("34529173", "J00100SYS", "15651554341", "testfor802", "11111111"));
        nai.close();
    }

}
