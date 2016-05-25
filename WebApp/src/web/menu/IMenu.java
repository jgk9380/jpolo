package web.menu;

import java.util.List;


public interface IMenu {
    //�˵�����������Ҳ������Ҷ  ֧�ֶ���˵������Ҫ����˵�����menu.jsff
    /*--------------����------------*/
    String getId();

    String getTitle();

    String getUrl();

    String getAuthority();

    boolean isItem();

    IMenuBar getMenuBar();

    IMenu getParent();

    List<IMenu> getChildrens(); //�п�����Menu

    /*--------------����------------*/

    boolean isCurrentCommand();

    boolean isOpened();

    void setOpened(boolean isopen);

    boolean isRender(String loginID) throws Exception;


}
