package web;


public class LoginUser extends AbsLoginUser implements ILoginUser {

    public LoginUser() {
        super();
    }

    @Override
    public void editPwd() {

    }

    @Override
    public String getLoginReturnUrl() {
        return "main";
    }
}
