package web.menu;

import java.util.List;

public interface IMenuBar {

    void refresh();

    void setCurrentCommand(IMenu currentCommand);

    IMenu getCurrentCommand();

    List<IMenu> getMenuList();

    void addMenu(IMenu menu);

    List<IMenu> getCommandList();

    void command(IMenu mi);

    void removeCommand(IMenu mi);
}
