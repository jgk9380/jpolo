package web.menu;


import assist.utils.EMFactory;

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

    public MenuBar() {
        super();
        rootMenuList = new ArrayList<>();
        //addTestMenu();
        openCommandList = new ArrayList<>(pageSize);
        try {
            this.init();
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
        JSFUtils.addPartialTarget("menAndNP"); //  mainTemp.jsff 中的navigationPane
        JSFUtils.addPartialTarget("pageScope"); //mainTemp.jsf
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
            System.out.println("pageHead.eventType=" + itemEvent.getType());
        }
    }

    public void itemActionListener(ActionEvent actionEvent) {
        IMenu mb = (IMenu) actionEvent.getComponent().getAttributes().get("menuItem");
        setCurrentCommand(mb);
        refresh();
    }

    void init() throws Exception {
        //        IEntityView iv =
        //            EntityManager.getInstance().queryEntityView("menu", "select name from j_menu where parent_menu ='reBar' and type='menu' order by seq");
        List<JMenu> menuList =
            EMFactory.getEntityManager().createQuery("select o from JMenu o where o.type='menu' and o.pmenu.name='reBar' order by o.seq").getResultList();
        rootMenuList = new ArrayList<>();
        for (JMenu ien : menuList) {
            Menu childMenu = new Menu((String) ien.getName(),  ien.getTitle(), this);
            childMenu.init();
            rootMenuList.add(childMenu);
        }

    }

    public static void main(String[] args) throws Exception {
        MenuBar mb = new MenuBar();
        // mb.init();
    }


}
