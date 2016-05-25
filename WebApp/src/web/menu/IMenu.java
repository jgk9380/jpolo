package web.menu;

import java.util.List;


public interface IMenu {
    //菜单可能是树，也可能是叶  支持二层菜单，如果要三层菜单更改menu.jsff
    /*--------------基本------------*/
    String getId();

    String getTitle();

    String getUrl();

    String getAuthority();

    boolean isItem();

    IMenuBar getMenuBar();

    IMenu getParent();

    List<IMenu> getChildrens(); //有可能是Menu

    /*--------------基本------------*/

    boolean isCurrentCommand();

    boolean isOpened();

    void setOpened(boolean isopen);

    boolean isRender(String loginID) throws Exception;


}
