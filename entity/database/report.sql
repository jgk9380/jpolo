
--������������
create table  batch_task(
  id number primary key,-- id_seq_gen
  target --dss��ora11g
  cyc  --day��month,manual��ִ������
  hour   --ÿ��ִ��ʱ��
  day    --ÿ��ִ��ʱ��
  desc   --˵��
��

--���������б�
create table batch_task_sql(
  id number primary key,
  task_id number references batch_task(id),
  task_seq  number(2) not null
  sql
  desc --
)

--���ݴ����б�
drop table j_table_transfer_task
create table j_table_transfer_task(   
    id number primary key ,
    source_DS varchar2(20) check (source_DS in ('ora11g','dss')),     
    target_ds varchar2(20) check (target_ds in ('ora11g','dss')),     
 
    table_name varchar2(40),          --�����û��� 
    trans_type varchar2(10) check (trans_type in ('append','drop','truncate')),          --truncate,append   
    cycle varchar2(20) check (cycle in ('month','day','manaul')) ,               --day��month,manual��ִ������
    day number(2),                    --ÿ��ִ������
    hour  number(2),                  --ÿ��ִ��ʱ��
    remark varchar2(200)              --˵��
)



 
--����
drop table  j_report
create table j_report(            
            id number primary key,--ID
            require_emp_id varchar2(20) references COMTEST.employees(id),--������
           
        --��������д
            report_name varchar2(20) unique,        --��������
            remark_Desc clob,                       --���������
            fields_Desc clob,                       --�����ֶ�����
            params_desc clob,                       --��ѯ��������
            output_desc clob,                       --�������

        --ʵʩ����д    
            data_source varchar2(20) check (data_source in ('ora11g','dss')),               --����Դ ora11g��dss
            is_single number(1) check(is_single in (1,0)),                    -- �Ƿ�����¼�������ʾ����form��ʾ
            fields_sql clob,                        --sql�е��ֶβ���      --sql =select [fields_sql] from  [from_sql] where [where_sql]
            from_sql clob,                          --form ����
            where_sql clob ,                        --where ����    
             child_report_id references j_report(id),--�ӱ�ID
            child_report_link varchar2(400),        --�ӱ������ַ�������name=p[name], name=v[name]  �û���˫��
            implement_remark varchar2(400),         --ʵʩ˵��
            
        --��������д
            implement_emp_id varchar2(20) references comtest.employees(id),--������
        --���̿���    
            report_status varchar2(20)               --1��������  2��ʵʩ��  3�������  
)


--������� 
drop table j_report_param
create table j_report_param(                                      
            id number primary key,
            report_id  number references j_report(id) not null,          --    
            name  varchar2(20) not null,                                 --��������  �������� --����sql������
            label varchar2(20) not null,                                 --��ǩ��    Ĭ��name
            type  varchar2(20) not null check (type in ('Date','String','Number')),  --�������� date,number,string    Ĭ���ַ�
            default_value varchar2(20),                                  -- String, BigDecmial(),toDate('YYYYMMDD-hh24:mm:ss')
            select_Scope_Sql   clob ,                                         -- select label,value from table  -����Ԥ��
            is_single number(1)  default 1 check( is_single in(1,0)  )                         --�Ƿ񵥸� Ĭ����  
)

--������ʾ�ֶ�
drop table j_report_column
create table j_report_column( 
          id number primary key,
          report_id  number references j_report(id) not null,  
          header_text varchar2(20),--�ֶ�ͷ��
          footer_type varchar2(20),                     -- �ޣ����㣬�ַ���
                footer_calc_type varchar2(20) check (footer_calc_type in ('count','sum','average','Const')),    --count,sum,average,Const
          foot_text varchar2(20),                       --�ַ�
          seq number(2) not null,    
          child_report_link varchar2(400)               --�ӱ������ַ������� 765{ name=p[name], name=v[name]}  �������ֶε���   
)