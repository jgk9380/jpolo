package domain;

import java.util.List;

public interface IObjectMeta<O> {//<O>Ӧ�÷ŵ�ʵ������
    //����

    String getName();
    
    List<String> getPropertyNameLsit();

    boolean isCreatAble();

    boolean isUpdatAble();

    boolean isDeletAble();

    //curd
    O create();

    boolean merge(O v);

    boolean persist(O v);

    void delete(O v);

    //��ѯ
    List<O> nativeQuery(String sql);

    List<O> query(String oql);

    O nativeQuerySingle(String sql);

    O querySingle(String oql);

    O load(Object o); //if null ,throw Exceptios

    O find(Object o); //if null ,return null;

}
