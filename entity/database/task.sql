--����

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
      task_type_id number references task_type(id),       --��������   �طã��˲飬��ע
      task_subject_id number references task_subject(id),    --��������  Ƿ�ѣ��������ţ�����˲飬ʵ���ƣ��쳣��չ��
      task_object varchar2(200), --������� ������Ϊ���룬����Ϊ�����̹��ŵȵȣ�����Ϊ�ա�
      content varchar2(200),     --��������  --�ط�����
      end_date date not null,    --�����ֹ���ڡ� Ĭ��3�졣     
      dutier  varchar2(20) references comtest.employees(id), --���������� 
      ---------------------------      
      recall_last_result varchar2(20),--����ظ����      
      recall_last_date date, --�ظ�ʱ�䡣     
      recall_check_result varchar(20),--����˲���      
      recall_check_date date, --�˲�ʱ�䡣          
      recall_result clob,  --����б�  
      ------------------------------                     
      status number(1) default 0 check (status in (0,1��2)),--0��ִ���� 1����ȷ��  -2��ȷ�����
      score number(1)  default 0 check (score>=0 and score<=5),--����÷�
      productor varchar2(20) references comtest.employees(id), --�ɵ��ˣ��˲��ˣ����������ˡ�
      remark varchar2(200),--
      count number(2) defalut 1 --����
)

--�������ɣ�status= 0��task_type_id,task_subject_id,task_object ,end_date,dutier.
--����ظ���status=1
--����˲飺ͨ�� status=2 ����ͨ����status=0,recall_check_result
--������ɣ�

--�ӱ��������񵥣�
