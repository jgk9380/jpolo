package agent;

import assist.utils.EntityManagerFactoryProxy;

import entity.JMenu;

import entity.agent.Channel;

import entity.agent.ChannelMonthCost;

import entity.agent.ChannelDest;
import entity.agent.CodeCostType;

import entity.agent.CodeDestType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jxl.read.biff.BiffException;

import oracle.adf.view.rich.component.rich.input.RichInputFile;

import org.apache.myfaces.trinidad.model.UploadedFile;

import web.JSFUtils;

/**
 * 1、成本查询
 * 2、新增成本
 * 3、成本上传
 * */

public class CostDestManager {
    ChannelMonthCost agentMonthCost;
    ChannelDest agentMonthDest;
    EntityManager em;

    List<Map> errMapList;
    List<Map> sucessMapList;
    List<ChannelMonthCost> successCostList;
    List<ChannelDest> successDestList;

    public CostDestManager() {
        super();
        agentMonthCost = new ChannelMonthCost();
        agentMonthDest = new ChannelDest();
        em = EntityManagerFactoryProxy.getEntityManagerFor11g();
    }

    public void setAgentMonthCost(ChannelMonthCost agentMonthCost) {
        this.agentMonthCost = agentMonthCost;
    }

    public ChannelMonthCost getAgentMonthCost() {
        return agentMonthCost;
    }
    List<SelectItem> agentSelectItemList;

    public List<SelectItem> getAgentSelectItemList() {
        if (agentSelectItemList != null)
            return agentSelectItemList;
        String ql = "select o from Channel o order by o.id ";
        agentSelectItemList = new ArrayList<>();
        List<Channel> ctemp = em.createQuery(ql, Channel.class).getResultList();
        for (Channel c : ctemp) {
            SelectItem si = new SelectItem();
            si.setLabel(c.getName() + ":" + c.getId());
            si.setValue(c);
            agentSelectItemList.add(si);
        }
        return agentSelectItemList;
    }


    public List<SelectItem> getCostTypeSelectItemList() {
        List<SelectItem> res = new ArrayList<>();
        String ql = "select o from CodeCostType o order by o.id ";
        List<CodeCostType> ctemp = em.createQuery(ql, CodeCostType.class).getResultList();
        for (CodeCostType c : ctemp) {
            SelectItem si = new SelectItem();
            si.setLabel(c.getName());
            si.setValue(c);
            res.add(si);
        }
        return res;
    }

    public void costAdd(ActionEvent actionEvent) {
        if (agentMonthCost.getJcodeChannel() == null || agentMonthCost.getMonth() == null ||
            agentMonthCost.getCodeCostType() == null || agentMonthCost.getCost() == null) {
            JSFUtils.addFacesInformationMessage("数据不完整");
            return;
        }
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(agentMonthCost);
        et.commit();
        agentMonthCost = new ChannelMonthCost();
        JSFUtils.refresh("addCost");
        JSFUtils.addFacesInformationMessage("已提交");
    }


    String saveFile(ValueChangeEvent event) {
        InputStream in;
        FileOutputStream out;
        UploadedFile file = (UploadedFile) event.getNewValue();

        String fileUploadLoc = "C:/Temp";
        boolean exists = (new File(fileUploadLoc)).exists();
        if (!exists) {
            (new File(fileUploadLoc)).mkdirs();
        }

        if (!file.getFilename().endsWith("xls")) {
            JSFUtils.addFacesInformationMessage("不是xls文件");
            return null;
        }

        if (file == null || file.getLength() == 0) {
            JSFUtils.addFacesInformationMessage("文件有误");
            return null;
        }

        try {
            out = new FileOutputStream(fileUploadLoc + "/" + file.getFilename());
            in = file.getInputStream();
            for (int bytes = 0; bytes < file.getLength(); bytes++) {
                out.write(in.read());
            }
            in.close();
            out.close();
            JSFUtils.addFacesInformationMessage("File Uploaded" + file.getFilename() + "(" + file.getLength() +
                                                "bytes)");
            return fileUploadLoc + "/" + file.getFilename();
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesInformationMessage("error:" + e.getMessage());
            return null;
        }


    }

    /**{
     EntityTransaction et = em.getTransaction();
     String excelFileName =saveFile(event);
     List<Map<String, String>> l;
     try {
         et.begin();
         l = ExcelParase.paraseExcel(excelFileName);
         for (Map<String, String> m : l) {
             if (m.get("month") == null || m.get("agent_id") == null || m.get("cost_type") == null ||
                 Double.parseDouble(m.get("cost")) == 0d) {
                 System.out.println(m);
                 continue;
             }
             ChannelMonthCost amc = new ChannelMonthCost();
             amc.setMonth(m.get("month"));
             Channel jcc = em.find(Channel.class, m.get("agent_id"));
             System.out.println("agent_id=" + m.get("agent_id") + "  " + jcc);
             if (jcc == null) {
                 JSFUtils.addFacesErrorMessage("提交失败：" + m.get("agent_id") + " is error");
                 et.rollback();
                 return;
             }
             amc.setJcodeChannel(jcc);
             amc.setCost(new java.math.BigDecimal(Double.parseDouble(m.get("cost"))));
             amc.setCodeCostType(em.find(CodeCostType.class,
                                         new java.math.BigDecimal(Double.parseDouble(m.get("cost_type")))));
             System.out.println("channel=" + jcc.getId() + " cost=" + amc.getCost() + " type=" +
                                amc.getCodeCostType().getName());
             em.persist(amc);
         }
         et.commit();
         JSFUtils.addFacesInformationMessage("存入数据库成功，共" + l.size() + "条记录");
     } catch (Exception e) {
         e.printStackTrace();
         JSFUtils.addFacesInformationMessage("error:" + e.getMessage());
     }

    }*/

    public void costFileUploaded(ValueChangeEvent event) {
        //write to database

        String excelFileName = saveFile(event);
        List<Map<String, String>> l;
        errMapList = new ArrayList<>();
        sucessMapList = new ArrayList<>();
        successCostList = new ArrayList<>();
        try {
            l = ExcelParase.paraseExcel(excelFileName);
            for (Map<String, String> m : l) {
                if (m.get("month") == null || m.get("agent_id") == null || m.get("cost_type") == null ||
                    Double.parseDouble(m.get("cost")) == 0d) {
                    m.put("error", "有空值");
                    errMapList.add(m);
                    continue;
                }
                ChannelMonthCost amc = new ChannelMonthCost();
                amc.setMonth(m.get("month"));
                Channel jcc = em.find(Channel.class, m.get("agent_id"));
                //System.out.println("agent_id=" + m.get("agent_id") + "  " + jcc);
                if (jcc == null) {
                    m.put("error", "错误的agent_id");
                    errMapList.add(m);
                    continue;
                }
                amc.setJcodeChannel(jcc);
                amc.setCost(new java.math.BigDecimal(Double.parseDouble(m.get("cost"))));
                CodeCostType cct =
                    em.find(CodeCostType.class, new java.math.BigDecimal(Double.parseDouble(m.get("cost_type"))));
                if (cct == null) {
                    m.put("error", "cost_type");
                    errMapList.add(m);
                    continue;
                }
                amc.setCodeCostType(cct);
                sucessMapList.add(m);
                successCostList.add(amc);
            }
            JSFUtils.addFacesInformationMessage("错误的数据，共" + errMapList.size() + "条记录，成功记录" + sucessMapList.size() +
                                                "条,具体见下表");

        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesInformationMessage("error:" + e.getMessage());
        }


    }


    public ChannelDest getAgentMonthDest() {
        return agentMonthDest;
    }

    public static void main(String[] args) {
        EntityManager em = EntityManagerFactoryProxy.getEntityManagerFor11g();
        em.find(Channel.class, "34b0xs3");
        List<JMenu> l = em.createQuery("select o from entity.JMenu o", JMenu.class).getResultList();
        System.out.println(l.size());
        em.getEntityManagerFactory().getCache().evictAll();
        l = em.createQuery("select o from entity.JMenu o", JMenu.class).getResultList();
        System.out.println(l.size());
    }

    public void destAdd(ActionEvent actionEvent) {
        if (agentMonthDest.getJCodeChannel() == null || agentMonthDest.getYearOrMonth() == null ||
            agentMonthDest.getCodeDestType() == null || agentMonthDest.getDest() == null) {
            return;
        }
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(agentMonthDest);
        et.commit();
        agentMonthDest = new ChannelDest();
        JSFUtils.refresh("addDest");
        JSFUtils.addFacesInformationMessage("已提交");
    }

    public void destFileUploaded(ValueChangeEvent valueChangeEvent) {
        String excelFileName = saveFile(valueChangeEvent);
        List<Map<String, String>> l;
        errMapList = new ArrayList<>();
        sucessMapList = new ArrayList<>();
        successDestList = new ArrayList<>();
        //ChannelDest
        try {
            l = ExcelParase.paraseExcel(excelFileName);
            for (Map<String, String> m : l) {
                if (m.get("yearOrMonth") == null || m.get("agent_id") == null || m.get("dest_type") == null ||
                    Double.parseDouble(m.get("dest")) == 0d) {
                    m.put("error", "有空值");
                    errMapList.add(m);
                    continue;
                }

                ChannelDest channelDest = new ChannelDest();
                channelDest.setYearOrMonth(m.get("yearOrMonth"));
                Channel jcc = em.find(Channel.class, m.get("agent_id"));
                //System.out.println("agent_id=" + m.get("agent_id") + "  " + jcc);
                if (jcc == null) {
                    m.put("error", "错误的agent_id");
                    errMapList.add(m);
                    continue;
                }
                channelDest.setJCodeChannel(jcc);
                channelDest.setDest(new java.math.BigDecimal(Double.parseDouble(m.get("dest"))));
                CodeDestType cct =
                    em.find(CodeDestType.class, new java.math.BigDecimal(Double.parseDouble(m.get("dest_type"))));
                if (cct == null) {
                    m.put("error", "错误的dest_type");
                    errMapList.add(m);
                    continue;
                }
                channelDest.setCodeDestType(cct);
                sucessMapList.add(m);
                successDestList.add(channelDest);
            }
            JSFUtils.addFacesInformationMessage("错误的数据，共" + errMapList.size() + "条记录，成功记录" + sucessMapList.size() +
                                                "条,具体见下表");

        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesInformationMessage("error:" + e.getMessage());
        }


    }


    public List<Map> getErrCostList() {
        return errMapList;
    }

    public List<Map> getToSaveCostList() {
        return sucessMapList;
    }

    public void saveToDataBase(ActionEvent actionEvent) {
        EntityTransaction et = em.getTransaction();
        try {

            et.begin();
            for (ChannelMonthCost cmc : successCostList) {
                em.merge(cmc);
            }
            et.commit();
            JSFUtils.addFacesInformationMessage("保存成功:" + successCostList.size() + "条");
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
            JSFUtils.addFacesInformationMessage("error:" + e.getMessage());
        }
    }

    public void cancel(ActionEvent actionEvent) {
        errMapList = new ArrayList<>();
        sucessMapList = new ArrayList<>();
        successCostList = new ArrayList<>();
        JSFUtils.refresh("pgl4");
    }

    public void saveDestToDataBase(ActionEvent actionEvent) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            for (ChannelDest cmc : successDestList) {
                em.merge(cmc);
            }           
            JSFUtils.addFacesInformationMessage("保存成功:" + successDestList.size() + "条");
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesInformationMessage("error:" + e.getMessage());
          
                et.rollback();
        }
    }
}
