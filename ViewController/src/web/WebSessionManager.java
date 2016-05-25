package web;


import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import web.menu.MenuBar;

public class WebSessionManager {
    LoginUser loginUser; //=new LoginUser();
    MenuBar menuBar; //=new MenuBar();

    public WebSessionManager() {
        super();
        menuBar = new MenuBar("poRootMenu");
        loginUser = new LoginUser();
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public String test_close_action() {
        // Add event code here...
        FacesContext fc = FacesContext.getCurrentInstance();
        ExtendedRenderKitService erks = Service.getRenderKitService(fc, ExtendedRenderKitService.class);
        erks.addScript(fc, "alert(234);");
        return null;
    }

    public String test_pop_action() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExtendedRenderKitService extendedRenderKitService =
            Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
        String script = "var popup; popup = AdfPage.PAGE.findComponent('" + "p2" + "'); popup.show();";
        extendedRenderKitService.addScript(facesContext, script);
        return null;
    }

}
