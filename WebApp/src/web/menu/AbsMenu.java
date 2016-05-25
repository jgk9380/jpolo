package web.menu;

import java.util.List;

public abstract class AbsMenu implements IMenu {
    String title, url, authority;
    private boolean opened = false;
    String id;
    List<IMenu> children; //可能是Menu，也可能是IMenuItem
    IMenu parent;
    IMenuBar menuBar;


    public AbsMenu(String id, String title, IMenuBar mb) {
        super();
        this.id = id;
        this.title = title;
        this.menuBar = mb;
        parent = null;
    }

    public AbsMenu(String id, String title, String url, String authority, IMenu parent) {
        super();
        this.url = url;
        this.id = id;
        this.title = title;
        this.authority = authority;
        this.menuBar = null;
        this.parent = parent;
    }

    @Override
    public boolean isOpened() {
        return opened;
    }

    @Override
    public void setOpened(boolean isopen) {
        opened = isopen;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isCurrentCommand() {
        return this.getMenuBar().getCurrentCommand().equals(this);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public IMenuBar getMenuBar() {
        if (menuBar != null)
            return menuBar;
        else
            return parent.getMenuBar();
    }

    @Override
    public List<IMenu> getChildrens() {
        return children;
    }

    @Override
    public IMenu getParent() {
        return parent;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean isItem() {
        if (this.getChildrens().size() > 0)
            return false;
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Menu)) {
            return false;
        }
        final Menu other = (Menu) object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
