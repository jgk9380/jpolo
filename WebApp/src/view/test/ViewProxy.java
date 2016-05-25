package view.test;

import assist.utils.EMFactory;

import entity.Employees;
import entity.JRole;

import jpolo.iface.entity.IPolo;

import jpolo.impl.view.View;


public class ViewProxy {
    View<JRole> roleView;
    View<Employees> empView;


    public ViewProxy() throws NoSuchFieldException, Exception {
        super();
        roleView = new View<>(EMFactory.getEntityManager(),JRole.class);
        empView = new View<>(EMFactory.getEntityManager(),Employees.class);
    }

    public View<JRole> getRoleView() {
        return roleView;
    }

    public View<Employees> getEmpView() {
        return empView;
    }

    public IPolo getPolo() {
        empView.next();
        return empView.getCurrent();
    }

    public static void main(String[] args) throws NoSuchFieldException, Exception {
        ViewProxy v=new ViewProxy();
        System.out.println(v.getEmpView().getCurrent().getField("name").getMeta().getLabel());
        System.out.println(v.getEmpView().getCurrent().getField("name").getMeta().getType());
    }
}
