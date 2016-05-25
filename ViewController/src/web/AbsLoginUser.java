package web;

import assist.sms.SmsService;

import assist.utils.EntityManagerFactoryProxy;

import entity.JAuthority;
import entity.JRole;
import entity.LoginUsers;

import java.math.BigDecimal;


public abstract class AbsLoginUser implements ILoginUser {
    private String userName;
    private String loginId;
    private String pwd;
    private String nPwd1;
    private String nPwd2;
    private boolean isLogined;

    public abstract String getLoginReturnUrl();

    public AbsLoginUser() {
        super();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setIsLogined(boolean isLogined) {
        this.isLogined = isLogined;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getLoginID() {
        return loginId;
    }

    @Override
    public void setLoginID(String userID) {
        loginId = userID;

    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String getNPwd1() {

        return nPwd1;
    }

    @Override
    public void setNPwd1(String pwd1) {
        nPwd1 = pwd1;

    }

    @Override
    public String getNPwd2() {

        return nPwd2;
    }

    @Override
    public void setNPwd2(String pwd2) {
        nPwd2 = pwd2;
    }

    @Override
    public Boolean isLogined() {
        return isLogined;
    }

    @Override
    public String loginOutAction() {
        isLogined = false;
        return "login";
    }

    @Override
    public String loginAction() throws Exception {
        if (getLoginID() == null || getPwd() == null) {
            JSFUtils.addFacesInformationMessage("密码或用户名为空");
            return null;
        }
        //        IEntityManager em = EntityManager.getInstance();
        //        IEntity tempLoginUser = em.findEntity("loginUser", getLoginID());
        LoginUsers tempLoginUser =
            EntityManagerFactoryProxy.getEntityManagerFor11g().find(LoginUsers.class, getLoginID());
        if (tempLoginUser == null) {
            JSFUtils.addFacesInformationMessage("不存在的用户");
            return null;
        }

        if (!tempLoginUser.getIsvalid().equals("Y")) {
            JSFUtils.addFacesInformationMessage("禁用用户");
            return null;
        }
        //
        if (!getPwd().equals(tempLoginUser.getPassword())) {
            JSFUtils.addFacesInformationMessage("密码不正确");
            return null;
        }
        //
        if (tempLoginUser.getEmployee() == null) {
            JSFUtils.addFacesInformationMessage("登陆账号信息有误");
            return null;
        }

        setUserName(tempLoginUser.getEmployee().getName());
        setIsLogined(true);
        JSFUtils.storeOnSession("loginUser", this);
        return getLoginReturnUrl();

    }

    @Override
    public String QueryPwdAction() throws Exception {
        if (getLoginID() == null) {
            JSFUtils.addFacesInformationMessage("用户名为空");
            return null;
        }
        LoginUsers tempLoginUser =
            EntityManagerFactoryProxy.getEntityManagerFor11g().find(LoginUsers.class, getLoginID());
        if (tempLoginUser == null) {
            JSFUtils.addFacesInformationMessage("不存在的用户");
            return null;
        }
        pwd = (String) tempLoginUser.getPassword();
        SmsService.sendSms(getLoginID(), "你的密码为：" + pwd + ",请妥善保管！", "密码查询");
        JSFUtils.addFacesInformationMessage("密码已发送，请注意查收");
        return null;
    }

    public static void main(String[] args) {
        LoginUsers tempLoginUser = EntityManagerFactoryProxy.getEntityManagerFor11g().find(LoginUsers.class, "jgk974");
        System.out.println(tempLoginUser.getEmployee().getName());
    }

    @Override
    public boolean isHasAuth(String priv) {
        LoginUsers tempLoginUser =
            EntityManagerFactoryProxy.getEntityManagerFor11g().find(LoginUsers.class, getLoginID());
        for (JRole role : tempLoginUser.getRoles()) {
            for (JAuthority auth : role.getAuths())
                if (auth.getName().equalsIgnoreCase(priv))
                    return true;
        }
        for (JAuthority auth : tempLoginUser.getAuths()) {
            if (auth.getName().equalsIgnoreCase(priv))
                return true;
        }

        return false;
    }

    @Override
    public int getPositionTypeId() {
        LoginUsers tempLoginUser =
            EntityManagerFactoryProxy.getEntityManagerFor11g().find(LoginUsers.class, getLoginID());
        if (tempLoginUser.getEmployee() != null)
            return tempLoginUser.getEmployee().getPositionTypeId().intValue();
        else
            return new BigDecimal(0).intValue();
    }

}
