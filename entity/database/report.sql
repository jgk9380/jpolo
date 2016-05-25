
--批量数据任务
create table  batch_task(
  id number primary key,-- id_seq_gen
  target --dss，ora11g
  cyc  --day，month,manual，执行周期
  hour   --每天执行时间
  day    --每月执行时间
  desc   --说明
）

--数据任务列表
create table batch_task_sql(
  id number primary key,
  task_id number references batch_task(id),
  task_seq  number(2) not null
  sql
  desc --
)

--数据传输列表
drop table j_table_transfer_task
create table j_table_transfer_task(   
    id number primary key ,
    source_DS varchar2(20) check (source_DS in ('ora11g','dss')),     
    target_ds varchar2(20) check (target_ds in ('ora11g','dss')),     
 
    table_name varchar2(40),          --包含用户名 
    trans_type varchar2(10) check (trans_type in ('append','drop','truncate')),          --truncate,append   
    cycle varchar2(20) check (cycle in ('month','day','manaul')) ,               --day，month,manual，执行周期
    day number(2),                    --每月执行日期
    hour  number(2),                  --每天执行时间
    remark varchar2(200)              --说明
)



 
--报表
drop table  j_report
create table j_report(            
            id number primary key,--ID
            require_emp_id varchar2(20) references COMTEST.employees(id),--需求人
           
        --申请人填写
            report_name varchar2(20) unique,        --报表名称
            remark_Desc clob,                       --需求简单描叙
            fields_Desc clob,                       --需求字段描叙
            params_desc clob,                       --查询参数描叙。
            output_desc clob,                       --输出描叙

        --实施人填写    
            data_source varchar2(20) check (data_source in ('ora11g','dss')),               --数据源 ora11g，dss
            is_single number(1) check(is_single in (1,0)),                    -- 是否单条记录。表格显示还是form显示
            fields_sql clob,                        --sql中的字段部分      --sql =select [fields_sql] from  [from_sql] where [where_sql]
            from_sql clob,                          --form 部分
            where_sql clob ,                        --where 部分    
             child_report_id references j_report(id),--子表ID
            child_report_link varchar2(400),        --子表链接字符串，如name=p[name], name=v[name]  用户行双击
            implement_remark varchar2(400),         --实施说明
            
        --分配人填写
            implement_emp_id varchar2(20) references comtest.employees(id),--需求人
        --流程控制    
            report_status varchar2(20)               --1、申请中  2、实施中  3、已完成  
)


--报表参数 
drop table j_report_param
create table j_report_param(                                      
            id number primary key,
            report_id  number references j_report(id) not null,          --    
            name  varchar2(20) not null,                                 --参数名称  命名参数 --根据sql语句产生
            label varchar2(20) not null,                                 --标签：    默认name
            type  varchar2(20) not null check (type in ('Date','String','Number')),  --参数类型 date,number,string    默认字符
            default_value varchar2(20),                                  -- String, BigDecmial(),toDate('YYYYMMDD-hh24:mm:ss')
            select_Scope_Sql   clob ,                                         -- select label,value from table  -可以预览
            is_single number(1)  default 1 check( is_single in(1,0)  )                         --是否单个 默认是  
)

--报表显示字段
drop table j_report_column
create table j_report_column( 
          id number primary key,
          report_id  number references j_report(id) not null,  
          header_text varchar2(20),--字段头部
          footer_type varchar2(20),                     -- 无，计算，字符串
                footer_calc_type varchar2(20) check (footer_calc_type in ('count','sum','average','Const')),    --count,sum,average,Const
          foot_text varchar2(20),                       --字符
          seq number(2) not null,    
          child_report_link varchar2(400)               --子表链接字符串，如 765{ name=p[name], name=v[name]}  用于行字段单击   
)