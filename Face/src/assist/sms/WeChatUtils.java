package assist.sms;


public class WeChatUtils {
//    final static String weChaturl = "http://122.194.14.94:8002/WechatLoginServlet";
//
//    public static void test() throws Exception {
//        String tempUtl = "http://122.194.14.94:8002/WechatLoginServlet";
//        String postData =
//            "param={\"msg_content\":\"test"+"��1ʼ"+"32221\",\"username\":\"15651554341\",\"trans_type\":\"sms_send\"}";
//        //String utf8Data = URLEncoder.encode(postData, "utf-8");
//
//        URL yahoo = new URL(tempUtl+"?"+postData);
//        System.out.println(tempUtl+"?"+postData);
////        HttpURLConnection con = (HttpURLConnection) yahoo.openConnection();
////        con.setDoOutput(true);
////        // Read from the connection. Default is true.
////        con.setDoInput(true);
////        // Set the post method. Default is GET
////        con.setRequestMethod("POST");
////        // Post cannot use caches
////        // Post ������ʹ�û���
////        con.setUseCaches(false);
////        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");          
////        System.out.println("Content-Type:" + con.getRequestProperty("Content-Type"));
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream(), "UTF-8"));
//        String inputLine = null;
//        UTF8JSONObject res = null;
//        while ((inputLine = in.readLine()) != null) {
//            res = new UTF8JSONObject(inputLine);
//        }
//        in.close();
//        res.show();
//    }
//
//
//    public static UTF8JSONObject invoke(UTF8JSONObject json) throws Exception {
//        String tempUtl = weChaturl + "?param=" + json.toUTF8String();
//        //        System.out.println(json.toString());
//        System.out.println("tu=" + tempUtl);
//        URL yahoo = new URL(tempUtl);
//        BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));
//        String inputLine = null;
//        UTF8JSONObject res = null;
//        while ((inputLine = in.readLine()) != null) {
//            res = new UTF8JSONObject(inputLine);
//        }
//        in.close();
//        return res;
//    }
//
//    public static void sendSMS(String tele, String content) throws Exception {
//        /* ���ŷ���
//            ���������{ "trans_type":"sms_send", " username":"15651605970", "msg_content":"���ŷ�������11111"   }
//            ��������{ ��result��:��0��,��err_msg��:���� }
//            ˵�������ŷ��͵��ֻ������ϳɹ��򷵻�0�����򷵻ش�����99���������ݲ�����200�����֡�
//            http://122.194.14.94:8002/WechatLoginServlet?param={"trans_type":"sms_send","username":"15651520039","msg_content":"���ŷ��Ͳ���"}
//         */
//        if (content.length() >= 200)
//            throw new Exception("��������̫��");
//        UTF8JSONObject jsonObject = new UTF8JSONObject();
//        jsonObject.put("trans_type", "sms_send");
//        jsonObject.put("username", tele);
//        jsonObject.put("msg_content", content);
//        invoke(jsonObject).show();
//
//    }
//
//    public static void main(String[] args) throws JSONException, UnsupportedEncodingException, Exception {
//        //        UTF8JSONObject json = new UTF8JSONObject();
//        //        json.put("username", "15651554341");
//        //        json.put("trans_type", "���");
//        //        json.show();
//        //        WeChatUtils.invoke(json).show();
//        WeChatUtils.sendSMS("15651554341", "test����form webchat");
//        //WeChatUtils.test();
//
//    }
}
