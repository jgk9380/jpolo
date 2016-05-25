drop table code_cost_type
create table code_cost_type(
  id number primary key,
  name varchar2(20),
  type  varchar2(20) check (type in ('manual','auto')),
  auto_sql varchar2(1000) --select agent,month,cost from where month=:month
)

--1、	渠道月度月度成本
drop table agent_month_cost
create table agent_month_cost(
    agent_id varchar2(20) references j_code_channel(id) ,
    month char(6),--
    cost_type number references code_cost_type(id), -- 佣金，补贴，欠费。。。。
    cost number 
）
alter table AGENT_MONTH_COST  add constraint cost_pk primary key (AGENT_ID, MONTH);

--2,渠道月度目标管理
drop table 
create table agent_month_dest(
  agent_id varchar2(20) references j_code_channel(id),
  month char(6),
  dest_type number references code_dest_type(id) ,
  dest number ,
  remark varchar2(400)
  


alter table j_code_city add(manager varchar2(20) references comtest.employees(id))

alter table j_code_grid add(manager varchar2(20) references comtest.employees(id))

alter table j_code_channel add(manager varchar2(20) references comtest.employees(id))


drop table J_code_six_address
create table J_code_six_address(
  six_Address varchar2(400) primary key,
  grid_id  varchar2(32) references j_code_grid(id),
  manager varchar2(20) references comtest.employees(id)
)

create table J_channel_contact(     --代理商合同
  id varchar2(20)   primary key,    --合同号
  start_date date not null,
  end_date date not null,
  enter_fee number,--进场费
  subsidy_fee number ,--补贴费
  rent_contact_id varchar2(20),     --  房租合同号	
  rent_fee number,                  --房租合同年租金	
  Subsidy_period_rent_fee number   --补贴期房租金额 
)

create table j_code_contact_dest_type(--代理商合同指标
  id number primary key,
  name varchar2(40)
）

drop table j_channel_contact_dest

create table code_channel_contact_dest(--合同指标
  contact_id varchar2(20) references j_channel_contact(id),
  dest_type_id number  references j_code_contact_dest_type(id),
  dest number,
  constraint code_channel_contact_dest_pk primary key (CONTACT_ID, DEST_TYPE_ID)  
)


