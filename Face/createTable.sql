
--�ͻ�Ⱥ��
create table j_code_cgroup ( --
id primary key,
name,
staff_id
) as 

--ҵ��Ԫ��
create table j_code_city( 
id primary key,
name ,
staff_id

)as 

--�����
create table  j_code_grid(
  id primary key,--
  name,--
  city_id references j_code_city(id),
  cgroup_id references j_code_cgroup(id),
  staff_id  references   --������
) as 

--������
create table j_code_channel(
    id primary key,
    head_id ,
    grid_id references j_grid_grid(id),
    --������������
        ��������
        ������
        ��ϵ�绰
        .
        .
        .
    --������������
    staff_id  --��������
 ��as

--����������Ӧ��ϵ

--ÿ���������Ϊ��( >1Ԫ )�ĺ����嵥
create table j_d_neuser��
  day --YYYYMMDD
  device_number  --
  leave_fee--
  channel_id
)
