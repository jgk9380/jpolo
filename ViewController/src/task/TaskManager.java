package task;

import assist.utils.EntityManagerFactoryProxy;

import entity.LoginUsers;

import entity.task.Task;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import web.JSFUtils;


public class TaskManager {
    EntityManager em;
    Task toDoTask;
    List<Task> myToDoTaskList;

    public Task getToDoTask() {
        return toDoTask;
    }

    public TaskManager() {
        super();
        em = EntityManagerFactoryProxy.getEntityManagerFor11g();
    }

    String getLoginUserID() {
                String res= JSFUtils.resolveExpressionAsString("#{sessionManager.loginUser.loginID}");
                if(res==null) {
                    JSFUtils.addFacesInformationMessage("没有登陆");
                }
         return res;
        //return "34b01eh";
    }

    public String refershMyToDoTask() {
        String loginId = this.getLoginUserID();
        LoginUsers lu = em.find(LoginUsers.class, loginId);
        String ql = "select  o from Task o where o.dutier=:dutier and o.status=0 order by o.content  ";
        myToDoTaskList = em.createQuery(ql, Task.class).setParameter("dutier", lu.getEmployee().getId()).getResultList();
        return null;
    }

    public List<Task> getMyToCheckTask() {
        String loginId = this.getLoginUserID();
        LoginUsers lu = em.find(LoginUsers.class, loginId);
        List<Task> res =
            em.createQuery("select  o from Task o where o.productor=:productor and o.status=1 ", Task.class).setParameter("productor",
                                                                                                                          lu.getEmployee().getId()).getResultList();
        System.out.println("res=" + res.size());
        return res;
    }


    public List<Task> getMyToDoTask() {
        return myToDoTaskList;
    }

    public List<Task> getMyDoneTask() {
        String loginId = this.getLoginUserID();
        LoginUsers lu = em.find(LoginUsers.class, loginId);
        List<Task> res =
            em.createQuery("select  o from Task o where o.dutier=:dutier and o.status=2 ", Task.class).setParameter("dutier",
                                                                                                                    lu.getEmployee().getId()).getResultList();
        System.out.println("res=" + res.size());
        return res;
    }

    public List<Task> getMyDoingTask() {
        String loginId = this.getLoginUserID();
        LoginUsers lu = em.find(LoginUsers.class, loginId);
        List<Task> res =
            em.createQuery("select  o from Task o where o.dutier=:dutier and o.status=1 ", Task.class).setParameter("dutier",
                                                                                                                    lu.getEmployee().getId()).getResultList();
        System.out.println("res=" + res.size());
        return res;
    }

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        tm.getMyDoneTask();
    }


    public void myToDoTaskSelectListener(SelectionEvent se) {
        RichTable rt = (RichTable) se.getComponent();
        toDoTask = (Task) rt.getSelectedRowData();
        toDoTask.setLastResult(null);
    }

    public void submitTask(ActionEvent actionEvent) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd H:m:s");
        if (toDoTask.getLastResult() == null || toDoTask.getLastResult().length() < 2){
            JSFUtils.addFacesInformationMessage("请填写处理结果");
            return;
        }
        toDoTask.setLastDate(new Date());
        if (toDoTask.getProcessList() == null)
            toDoTask.setProcessList(toDoTask.getLastResult() + " at " + sdf.format(   toDoTask.getLastDate()));
        else {
            toDoTask.setProcessList(toDoTask.getProcessList() + "\n " + toDoTask.getLastResult() + " at " +  sdf.format(toDoTask.getLastDate()));
        }
        toDoTask.setStatus(1);
        int i = myToDoTaskList.indexOf(toDoTask);
        if (i >= (myToDoTaskList.size()-1)) {
            JSFUtils.addFacesInformationMessage("任务提交，已到最后一条任务");
            return;
        } else {
            JSFUtils.addFacesInformationMessage("任务提交");
        }

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(toDoTask);
        et.commit();

        toDoTask = myToDoTaskList.get(i + 1);
        toDoTask.setLastResult(null);

    }
}
