package assist.database.datatransfer;


public interface IDataTransfer {
    // DataSource getSourceDS();
    // DataSource getTargetDS();
    //String getSchema();
    //String getTableName();
    //String getSelectFields from  where

    long transfer() throws Exception; //´«ÊäÊý¾Ý
}
