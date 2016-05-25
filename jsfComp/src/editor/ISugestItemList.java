package editor;

import java.util.List;

import javax.faces.model.SelectItem;

public interface ISugestItemList {
    List<SelectItem> getList(String name);
}
