package web.menu;

import assist.utils.DebugUtils1;
import assist.utils.EntityManagerFactoryProxy;

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
        LoginUsers lu = EntityManagerFactoryProxy.getEntityManagerFor11g().find(LoginUsers.class, loginID);

        if (lu == null) {
            //System.out.println("loginID=" + loginID);
            DebugUtils1.error("´íÎóµÄµÇÂ½¹¤ºÅ£º" + loginID, null);
            return false;
        }

        for (JAuthority ent : lu.getAuths()) {
            String autht = ent.getName();
            if ((autht.equalsIgnoreCase(getAuthority()))) {
                return true;
            }
        }

        for (JRole role : lu.getRoles()) {
            //System.out.println("role.name=" + role.getName());
            for (JAuthority auth : role.getAuths()) {
                String autht = auth.getName();
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
        //List<JMenu> menuList=EMFactory.getEntityManagerFor11g().createQuery("select o from JMenu o where o.pmenu.name='"+id+"' and o.valid='Y'  order by o.seq").getResultList();
        JMenu menu =
            EntityManagerFactoryProxy.getEntityManagerFor11g().createQuery("select o from JMenu o where o.name='" + id + "'",
                                                           JMenu.class).getSingleResult();
        List<JMenu> menuList = menu.getChildern();
        //System.out.println("        init:" + menuList.size());
        for (JMenu ien : menuList) {
            if (ien.getValid()) {
                //System.out.println("add childMenu="+ien.getName());
                String id = ien.getName();
                String title = ien.getTitle();
                String url = ien.getUrl();
                String auth = ien.getAuth();                
                Menu childMenu = new Menu(id, title, url, auth, this);
               // System.out.println("chileMenu.auth="+childMenu.getAuthority());
                childMenu.init();
              //  System.out.println("chileMenu.auth1="+childMenu.getAuthority());
                children.add(childMenu);
            }
        }
        return;
    }

    public static void main(String[] args) throws Exception {
      Menu m=new Menu("pm1",null,null);
      m.init();
    }


}
