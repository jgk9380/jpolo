package web;


import web.menu.MenuBar;

public class WebSessionManager {
    LoginUser loginUser; //=new LoginUser();
    MenuBar menuBar; //=new MenuBar();

    public WebSessionManager() {
        super();
        menuBar = new MenuBar();
        loginUser = new LoginUser();
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
