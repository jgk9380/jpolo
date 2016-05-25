package assist.sms;


public class SmsClient {

    public static void main(String[] args) throws Exception {
        SmsService.sendSms("15651554341", "testTitle1", "TestContent1");
        System.out.println("Ok");
    }
}
