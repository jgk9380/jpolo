package view;

import assist.database.datatransfer.TransTable;

import javax.faces.event.ActionEvent;

import web.JSFUtils;

public class GetPutTable {
    public GetPutTable() {
        super();
    }
    String tName;

    public void setTName(String tName) {
        this.tName = tName;
    }

    public String getTName() {
        return tName;
    }

    public void downTable(ActionEvent actionEvent) {
        TransTable tt=new TransTable();
        try {
            tt.getTable(tName);
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage("表下载错误："+e.getMessage());
                
        }
    }

    public void putTable(ActionEvent actionEvent) {
        TransTable tt=new TransTable();
        try {
            tt.putTable(tName);
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage("表下载错误："+e.getMessage());                
        }
    }
}
