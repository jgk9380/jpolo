
--客户群表
create table j_code_cgroup ( --
id primary key,
name,
staff_id
) as 

--业务单元表
create table j_code_city( 
id primary key,
name ,
staff_id

)as 

--网格表
create table  j_code_grid(
  id primary key,--
  name,--
  city_id references j_code_city(id),
  cgroup_id references j_code_cgroup(id),
  staff_id  references   --网格经理
) as 

--渠道表
create table j_code_channel(
    id primary key,
    head_id ,
    grid_id references j_grid_grid(id),
    --各类渠道属性
        渠道类型
        负责人
        联系电话
        .
        .
        .
    --各类渠道属性
    staff_id  --渠道经理
 ）as

--工号渠道对应关系

--每日新增余额为负( >1元 )的号码清单
create table j_d_neuser（
  day --YYYYMMDD
  device_number  --
  leave_fee--
  channel_id
)
