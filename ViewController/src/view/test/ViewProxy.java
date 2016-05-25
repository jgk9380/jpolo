package view.test;

import assist.utils.EntityManagerFactoryProxy;

import entity.JMenu;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;

import jpolo.iface.meta.IPMeta;
import jpolo.iface.polo.IPolo;
import jpolo.iface.view.IPoloView;

import jpolo.impl.view.PoloView;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.event.LaunchEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

import web.JSFUtils;


public class ViewProxy {
    IPoloView poloView;
    private EntityManagerFactory emf;
    Class<?> clazz;
    String testInputBOx;

    public void setTestInputBOx(String testInputBOx) {
        this.testInputBOx = testInputBOx;
    }

    public String getTestInputBOx() {
        return testInputBOx;
    }

    public ViewProxy() throws NoSuchFieldException, Exception {
        super();
        emf = Persistence.createEntityManagerFactory("entity");
        poloView = new PoloView<>(emf.createEntityManager(), JMenu.class);
    }


    public IPoloView<?> getPoloView() {
        return poloView;
    }


    public String query() {
        poloView.refresh();
        return null;
    }


    public void launchPopListener(LaunchEvent launchEvent) {
        // Add event code here...
    }


    public void editViewBuilderListener(DialogEvent de) {
        if (de.getOutcome().equals(DialogEvent.Outcome.ok)) {
            poloView.refresh();
            JSFUtils.hidePopup("editViewBuilder");
            JSFUtils.refresh("lpv");
        }
    }

    public String newPolo() {
        poloView.newPOlO();
        JSFUtils.refresh("lpv");
        return null;
    }

    public String deletePolo() {
        poloView.delete(poloView.getCurrent());
        JSFUtils.refresh("lpv");
        return null;
    }

    public void poloSelectListener(SelectionEvent selectionEvent) {
        RichTable rt = (RichTable) selectionEvent.getComponent();
        IPolo ip = (IPolo<?>) rt.getSelectedRowData();
        poloView.setCurrent(ip);
        //JSFUtils.showPopup("editCurrent");
    }

    public void editCurrentPoloListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
            JSFUtils.hidePopup("editCurrent");
            JSFUtils.refresh("lpv");
        }
    }

    public String commit() {
        try {
            poloView.commit();
            JSFUtils.refresh("lpv");
        } catch (Exception e) {
            JSFUtils.addFacesInformationMessage("Ã·Ωª ß∞‹£∫" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public String rollback() throws Exception {
        poloView.rollback();
        JSFUtils.refresh("lpv");
        return null;
    }

    public void setClazz(Class<?> clazz) throws NoSuchFieldException, Exception {
        this.clazz = clazz;
        poloView = new PoloView<>(EntityManagerFactoryProxy.getEntityManagerFor11g(), clazz);
        JSFUtils.refresh("f1");
    }

    public Class<?> getClazz() {
        return clazz;
    }


    public String clear() {
        poloView.clear();
        return null;
    }

    public String refresh() {
        poloView.refresh();
        JSFUtils.refresh("lpv");
        return null;
    }

    public String evictAll() {
        emf.getCache().evictAll();
        if (poloView != null) {
            poloView.refresh();
            JSFUtils.refresh("lpv");
        }
        return null;
    }


    public List<SelectItem> getClassSelectItem() {
        List<SelectItem> sl = new ArrayList<>();
        for (EntityType et : emf.getMetamodel().getEntities()) {
            IPMeta<?> ip = EntityManagerFactoryProxy.getPoloMeta(et.getJavaType());
            String lab = ip.getAlias();
            String enName = ip.getName();
            // System.out.println("l=" + lab + " n=" + enName + "  jt=" + et.getJavaType());
            if (!lab.equals(enName))
                lab = lab + " : " + enName;

            SelectItem si = new SelectItem();
            si.setLabel(lab);
            si.setValue(et.getJavaType());
            sl.add(si);
        }
        return sl;
    }


    public static void main(String[] args) throws NoSuchFieldException, Exception {
        //        ViewProxy v = new ViewProxy();
        //        IPolo<?> ip = v.poloView.newPOlO();
        //        ip.getPoloField("id").setValue(new BigDecimal(99679));
        //        ip.getPoloField("name").setValue("t3tt9");
        //        v.poloView.commit();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("entity");
        PoloView poloView = new PoloView<>(emf.createEntityManager(), JMenu.class);
        poloView.show(10);
        //        EntityManager em = emf.createEntityManager();
        //        EntityTransaction transaction = null;
        //        transaction = em.getTransaction();
        //        transaction.begin();
        //        JMenu jm = new JMenu();
        //        jm.setName("test7");
        //        jm.setValid(true);
        //        em.persist(jm);
        //        transaction.commit();
    }

    public void handlePoloDBLClick(ClientEvent clientEvent) {
        JSFUtils.showPopup("editCurrent");
    }

    public String testInputBOx() {
        System.out.println(this.getTestInputBOx());
        return null;
    }
}
