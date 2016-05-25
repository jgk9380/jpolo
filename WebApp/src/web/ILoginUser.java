package web;


public interface ILoginUser {
    String getUserName();

    String getLoginID();

    public void setLoginID(String userID);

    String getPwd();

    void setPwd(String pwd);

    String getNPwd1();

    void setNPwd1(String pwd1);

    String getNPwd2();

    void setNPwd2(String pwd2);

    Boolean isLogined();

    String loginAction() throws Exception;

    String loginOutAction();

    void editPwd();

   // boolean hasPrivage(String privageName); //�Ƿ����ĳ��Ȩ��

    public String QueryPwdAction() throws Exception ;
}
