package proxy.test;

public class BeanInfoTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("peida");
        try {
              Object o=BeanInfoUtil.getProperty(userInfo, "userName");
            System.out.println("get="+o);
            userInfo.show();
            BeanInfoUtil.setProperty(userInfo, "userName", "jgk");
            // BeanInfoUtil.getProperty(userInfo, "userName");
            userInfo.show();
            o=BeanInfoUtil.getProperty(userInfo, "userName");
            System.out.println("get="+o);

            BeanInfoUtil.setPropertyByIntrospector(userInfo, "userName","jianggk");
            userInfo.show();
           
            o=BeanInfoUtil.getPropertyByIntrospector(userInfo, "userName");
            System.out.println("get="+o);
            
            BeanInfoUtil.setProperty(userInfo, "age", 8);
            userInfo.show();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
