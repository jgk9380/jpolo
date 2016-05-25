package web.menu;

import assist.utils.EMFactory;

import entity.JAuthority;
import entity.JMenu;
import entity.JRole;
import entity.LoginUsers;

import java.util.ArrayList;
import java.util.List;


public class Menu extends AbsMenu {


    public Menu(String id, String title, IMenuBar mb) {
        super(id, title, mb);
    }

    public Menu(String id, String title, String url, String authority, IMenu parent) {
        super(id, title, url, authority, parent);
    }

    public void add(IMenu mi) {
        children.add(mi);
    }

    @Override
    public boolean isRender(String loginID) throws Exception {
        //  System.out.println(" menu title=" + title + " url=" + url + " child=" + this.getChildrens() + "size=" + this.getChildrens().size());
        if (url == null) {
            for (IMenu im : this.getChildrens()) {
                if (im.isRender(loginID))
                    return true;
            }
            return false;
        }

        if (this.getAuthority() == null)
            return true;

        boolean res = false;
        LoginUsers lu=EMFactory.getEntityManager().find(LoginUsers.class, loginID);
//        IEntity en = EntityManager.getInstance().findEntity("loginUser", loginID);
//        IEntityView ev = (IEntityView) en.getValue("auths");
        for (JAuthority ent : lu.getAuths()) {
            String autht = (String) ent.getName();
            if ((autht.equalsIgnoreCase(getAuthority()))) {
                return true;
            }
        }

//        ev = (IEntityView) en.getValue("roles");
        for (JRole ent : lu.getRoles()) {        
            for (JAuthority auth : ent.getAuths()) {
                String autht = (String) auth.getName();
                if ((autht.equals(getAuthority()))) {
                    return true;
                }
            }
        }
        return false;
    }


    public void init() throws Exception {
        children = new ArrayList<>();
//        IEntityView iv =
//            EntityManager.getInstance().queryEntityView("menu", "select name from j_menu where parent_menu ='" + id + "' and nvl(valid,'Y')='Y' order by seq");
        List<JMenu> menuList=EMFactory.getEntityManager().createQuery("select o from JMenu o where o.pmenu.name='"+id+"' and o.valid='Y' order by o.seq").getResultList();
        for (JMenu ien : menuList) {           
            String id =  ien.getName();
            String title =  ien.getTitle();
            String url = ien.getUrl();
            String auth =  ien.getAuth();
            Menu childMenu = new Menu(id, title, url, auth, this);
            childMenu.init();
            children.add(childMenu);
        }
        return;
    }

    public static void main(String[] args) throws Exception {

    }


}
