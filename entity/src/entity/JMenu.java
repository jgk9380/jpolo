package entity;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@NamedQueries({ @NamedQuery(name = "JMenu.findAll", query = "select o from JMenu o") })
@Table(name = "J_PO_MENU", catalog = "jemtest")
public class JMenu implements Serializable {
    private static final long serialVersionUID = -511445134610393844L;
    @Column(length = 20)
    private String project;
    @Column(length = 20)
    private String auth;
    @Column(length = 80)
    private String icon;
    @Id
    @Column(nullable = false, length = 20)
    private String name;
    private BigDecimal seq;
    @Column(length = 40)
    private String title;
    @Column(length = 20)
    private String type;
    @Column(length = 80)
    private String url;
    
    @Column(name = "VALID")
    private Boolean valid;
    @ManyToOne
    @JoinColumn(name = "PARENT_MENU")
    private JMenu pmenu;
    @OneToMany(mappedBy = "pmenu", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @OrderBy(value = "seq")
    private List<JMenu> childern;

    public JMenu() {
    }

    public JMenu(String auth, String icon, String name, JMenu JMenu, BigDecimal seq, String title, String type,
                 String url, Boolean valid) {
        this.auth = auth;
        this.icon = icon;
        this.name = name;
        this.pmenu = JMenu;
        this.seq = seq;
        this.title = title;
        this.type = type;
        this.url = url;
        this.valid = valid;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BigDecimal getSeq() {
        return seq;
    }

    public void setSeq(BigDecimal seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getValid() {
//        if(valid) return "Y";
//        return "N";
        if(valid==null) return true;
        return valid;
    }

    public void setValid(Boolean valid) {
//        if(valid==null)
//            this.valid=true;
//        if(valid.equalsIgnoreCase("Y"))
//            this.valid=true;
//        this.valid = false;
        this.valid=valid;
    }

    public JMenu getPmenu() {
        return pmenu;
    }

    public void setPmenu(JMenu JMenu) {
        this.pmenu = JMenu;
    }

    public List<JMenu> getChildern() {
        return childern;
    }

    public void setChildern(List<JMenu> JMenuList) {
        this.childern = JMenuList;
    }

    public JMenu addJMenu(JMenu JMenu) {
        getChildern().add(JMenu);
        JMenu.setPmenu(this);
        return JMenu;
    }

    public JMenu removeJMenu(JMenu JMenu) {
        getChildern().remove(JMenu);
        JMenu.setPmenu(null);
        return JMenu;
    }

    public String toString() {
        return name + ":" + title;
    }
}
