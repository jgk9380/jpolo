package proxy.test;

public class UserInfo {
    
    private long userId;
    private String userName;
    private int age;
    private String emailAddress;
    
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public void show(){
        System.out.println("userName="+userName +" age="+age );
    }
    
}

//��1.������Ե�ֵ�����磬BeanUtils.getProperty(userInfo,"userName")�������ַ���
//����2.�������Ե�ֵ�����磬BeanUtils.setProperty(userInfo,"age",8)���������ַ�������������Զ���װ��
//      �������Ե�ֵ���ַ�������õ�ֵҲ���ַ��������ǻ������͡�������3.BeanUtils���ص㣺
//��������1). �Ի����������͵����ԵĲ�������WEB������ʹ���У�¼�����ʾʱ��ֵ�ᱻת�����ַ��������ײ������õ��ǻ������ͣ���Щ����ת��������BeanUtils�Զ���ɡ�
//��������2��. �������������͵����ԵĲ��������������б����ж��󣬲�����null�����磬private Date birthday=new Date();���������Ƕ�������Զ����������������磬BeanUtils.setProperty(userInfo,"birthday.time",111111);������
//3.PropertyUtils���BeanUtils��ͬ���ڣ�����getProperty��setProperty����ʱ��û������ת����ʹ�����Ե�ԭ�����ͻ��߰�װ�ࡣ����age���Ե�����������int�����Է���PropertyUtils.setProperty(userInfo, "age", "8")�ᱬ���������Ͳ�ƥ�䣬�޷���ֵ�������ԡ�