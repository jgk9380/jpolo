package assist.database.datatransfer;

import annotation.Enum;

public class TransTable {
    public TransTable() {
        super();
    }

    public void getTable(String tname, Enum.TransType tt) throws Exception {
        DataTransferBySpring dtf = new DataTransferBySpring("dss", "ora11g", "regionj", tname, tt);
        dtf.transfer();
    }

    public void putTable(String tname, Enum.TransType tt) throws Exception {
        DataTransferBySpring dtf = new DataTransferBySpring("ora11g", "dss", "jemtest", tname, tt);
        dtf.transfer();
    }

    public static void getTables() throws Exception {
        TransTable tt = new TransTable();
        //        tt.getTable("j_code_city");
        //        tt.getTable("j_code_cgroup");
        //        tt.getTable("j_code_grid");
        //        tt.getTable("j_code_channel");
        //        tt.getTable("fang_six_address");
        //���ձ�REGIONJ_CHANNEL_PHOTO;
        tt.getTable("REGIONJ_M_ALL_USER", Enum.TransType.drop);
        //�������У�REGIONJ_M_PHOTO_USER
        //        tt.getTable("REGIONJ_M_PHOTO_USER");
        //        //�����û�:REGIONJ_M_NEW_USER
        //        tt.getTable("REGIONJ_M_NEW_USER");

    }

    public static void putTables() throws Exception {
        TransTable tt = new TransTable();
        //tt.putTable("j_po_menu");
        getTables();
    }

    public static void main(String[] args) throws Exception {
        putTables();
    }
}
