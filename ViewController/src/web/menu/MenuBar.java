package web.menu;


import assist.utils.EntityManagerFactoryProxy;

import entity.JMenu;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.event.ItemEvent;
import oracle.adf.view.rich.render.ClientEvent;

import web.JSFUtils;

public class MenuBar implements IMenuBar {
    private List<IMenu> rootMenuList;
    private List<IMenu> openCommandList;
    private IMenu currentCommand = null;
    private final int pageSize = 11;

 

    public MenuBar(String rootMenuDI) {
        super();
        rootMenuList = new ArrayList<>();
        openCommandList = new ArrayList<>(pageSize);
        try {
            this.init(rootMenuDI);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addMenu(IMenu menu) {
        rootMenuList.add(menu);
    }

    @Override
    public void refresh() {
        JSFUtils.refresh("menAndNP"); //  mainTemp.jsff 中的navigationPane
        JSFUtils.refresh("pageScope"); //mainTemp.jsf
        //JSFUtils.addPartialTarget("menuBar"); //menuBar.jsff
        // JSFUtils.addPartialTarget("ifHead");
    }

    @Override
    public void setCurrentCommand(IMenu currentCommand) {
        this.currentCommand = currentCommand;
        //        for (IMenu mitemp : openCommandList) {
        //            mitemp.setSelected(false);
        //        }
        //        if (currentCommand != null) {
        //            currentCommand.setSelected(true);
        //        }
    }

    @Override
    public IMenu getCurrentCommand() {
        return currentCommand;
    }

    @Override
    public List<IMenu> getMenuList() {
        return rootMenuList;
    }

    @Override
    public List<IMenu> getCommandList() {
        return openCommandList;
    }

    @Override
    public void removeCommand(IMenu mi) { //如果删除的是当前item，当前ITEM向前一个，如果移动-1 ，则移动到第0个
        mi.setOpened(false);
        int i = openCommandList.indexOf(mi);
        openCommandList.remove(mi);
        if (openCommandList.size() == 0) {
            currentCommand = null;
            return;
        }
        if (currentCommand.equals(mi)) {
            if (i <= 0)
                i = 1;
            currentCommand = openCommandList.get(i - 1);
        }
    }

    public void command(IMenu mi) {
        if (openCommandList.contains(mi)) { //页面无需重新加载
            currentCommand = mi;
            return;
        }
        if (openCommandList.size() >= pageSize) {
            openCommandList.get(0).setOpened(false);
            openCommandList.remove(0);
        }
        mi.setOpened(true);
        openCommandList.add(mi);
        currentCommand = mi;
    }


    public void menuAction(ClientEvent ce) {
        IMenu mb = (IMenu) ce.getComponent().getAttributes().get("menuItem");
        //mb.setSelected(true);
        command(mb);
        refresh();
    }

    public void itemListener(ItemEvent itemEvent) {
        if (ItemEvent.Type.remove.equals(itemEvent.getType())) {
            IMenu mb = (IMenu) itemEvent.getComponent().getAttributes().get("menuItem");
            removeCommand(mb);
            refresh();
        } else {
           // System.out.println("pageHead.eventType=" + itemEvent.getType());
        }
    }

    public void itemActionListener(ActionEvent actionEvent) {
        IMenu mb = (IMenu) actionEvent.getComponent().getAttributes().get("menuItem");
        setCurrentCommand(mb);
        refresh();
    }

    void init(String rootMenuID) throws Exception {
        JMenu rootMenu =
            EntityManagerFactoryProxy.getEntityManagerFor11g().createQuery("select o from JMenu o where   o.name='" +
                                                           rootMenuID + "'", JMenu.class).getSingleResult();
        List<JMenu> menuList = rootMenu.getChildern();
        
        rootMenuList = new ArrayList<>();
        for (JMenu ien : menuList) {
            if (ien.getValid()) {
                //System.out.println("add menu="+ien.getName());
                Menu childMenu = new Menu( ien.getName(), ien.getTitle(), this);
                childMenu.init();
                rootMenuList.add(childMenu);
            }
        }

    }

    public static void main(String[] args) throws Exception {
        MenuBar mb = new MenuBar("poRootMenu");
        for (IMenu im : mb.rootMenuList) {
            System.out.println("menu"+im.getTitle());
            for (IMenu i2 : im.getChildrens()) {
                System.out.println("cmenu   " + i2.getTitle() + " " + i2.isRender("jgk974")+" auth="+i2.getAuthority());
            }
        }
    }
}
