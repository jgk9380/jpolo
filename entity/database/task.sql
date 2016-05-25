--任务单

create table task_type(
  id number primary key,
  name varchar2(20)
)

create table task_subject(
  id number primary key,
  name varchar2(20)
)
drop table task_List

create table task_List(
      id number primary key,
      task_type_id number references task_type(id),       --任务描叙   回访，核查，关注
      task_subject_id number references task_subject(id),    --任务主题  欠费，垃圾短信，收入核查，实名制，异常发展。
      task_object varchar2(200), --任务对象 ，可能为号码，可能为代理商工号等等，可能为空。
      content varchar2(200),     --任务内容  --回访内容
      end_date date not null,    --任务截止日期。 默认3天。     
      dutier  varchar2(20) references comtest.employees(id), --任务责任人 
      ---------------------------      
      recall_last_result varchar2(20),--任务回复结果      
      recall_last_date date, --回复时间。     
      recall_check_result varchar(20),--任务核查结果      
      recall_check_date date, --核查时间。          
      recall_result clob,  --结果列表。  
      ------------------------------                     
      status number(1) default 0 check (status in (0,1，2)),--0，执行中 1：待确认  -2：确认完成
      score number(1)  default 0 check (score>=0 and score<=5),--任务得分
      productor varchar2(20) references comtest.employees(id), --派单人，核查人，任务所有人。
      remark varchar2(200),--
      count number(2) defalut 1 --次数
)

--任务生成：status= 0，task_type_id,task_subject_id,task_object ,end_date,dutier.
--任务回复：status=1
--任务核查：通过 status=2 ，不通过，status=0,recall_check_result
--任务完成：

--从报表生任务单：
