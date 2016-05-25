package web;

import java.math.BigDecimal;


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

   // boolean hasPrivage(String privageName); //是否具有某种权利

    String QueryPwdAction() throws Exception ;
    
    boolean isHasAuth(String priv);//是否有某种权限
    int getPositionTypeId();//对应的人的具体岗位
    
    
}
