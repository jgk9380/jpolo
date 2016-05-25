--保有视图
drop materialized view j_photo_retain_mv;
create materialized view j_photo_retain_mv 
refresh on demand 
as   
   select a.develop_channel,a.acct_year,a.pu photo_u,a.pf photo_f ,
   u01, u02, u03, u04, u05, u06, u07, u08,  u09 ,u10, u11, u12, 
   f01, f02, f03, f04, f05, f06, f07, f08, f09, f10, f11, f12  from     
   
   
   (
        select develop_channel,acct_year,net_type1,product_id,sum(photo_user_counts) pu,sum(photo_user_charge) pf
       from  REGIONJ_CHANNEL_PHOTO 
       group by develop_channel,acct_year,net_type1,product_id
   ) a,   
   

   (
            select develop_channel,acct_year, net_type1,product_id,
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),1),'YYYYMM')  THEN sumu ELSE 0 END) u01,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),2),'YYYYMM')  THEN sumu ELSE 0 END) u02,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),3),'YYYYMM')  THEN sumu ELSE 0 END) u03,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),4),'YYYYMM')  THEN sumu ELSE 0 END) u04,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),5),'YYYYMM')  THEN sumu ELSE 0 END) u05,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),6),'YYYYMM')  THEN sumu ELSE 0 END) u06,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),7),'YYYYMM')  THEN sumu ELSE 0 END) u07,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),8),'YYYYMM')  THEN sumu ELSE 0 END) u08,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),9),'YYYYMM')  THEN sumu ELSE 0 END)  u09, 
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),10),'YYYYMM') THEN sumu ELSE 0 END) u10,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),11),'YYYYMM') THEN sumu ELSE 0 END) u11,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),12),'YYYYMM') THEN sumu ELSE 0 END) u12,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),1),'YYYYMM')  THEN sumf ELSE 0 END) f01,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),2),'YYYYMM')  THEN sumf ELSE 0 END) f02,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),3),'YYYYMM')  THEN sumf ELSE 0 END) f03,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),4),'YYYYMM')  THEN sumf ELSE 0 END) f04,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),5),'YYYYMM')  THEN sumf ELSE 0 END) f05,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),6),'YYYYMM')  THEN sumf ELSE 0 END) f06,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),7),'YYYYMM')  THEN sumf ELSE 0 END) f07,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),8),'YYYYMM')  THEN sumf ELSE 0 END) f08,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),9),'YYYYMM')  THEN sumf ELSE 0 END) f09,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),10),'YYYYMM') THEN sumf ELSE 0 END) f10,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),11),'YYYYMM') THEN sumf ELSE 0 END) f11,  
            sum(CASE acct_month WHEN to_char(add_months(to_date(acct_year , 'YYYY'),12),'YYYYMM') THEN sumf ELSE 0 END) f12 
        from
        (   
            select develop_channel,acct_month, net_type1,product_id ,sum(curr_user_counts) sumu,sum(curr_user_charge) sumf
            from REGIONJ_M_PHOTO_USER   
            group by develop_channel,acct_month, net_type1,product_id 
         )  a,       
         
        (select unique acct_year  from REGIONJ_CHANNEL_PHOTO ) b  
             
         
         group by develop_channel,acct_year ,  net_type1,product_id  
      
    ) b
    
    
     where a.develop_channel=b.develop_channel and a.acct_year=b.acct_year and a.net_type1=b.net_type1 and a.product_id=b.product_id;
   
--新增视图
drop materialized view j_NEW_retain_mv;
drop materialized view j_NEW_retain_mv
create materialized view j_NEW_retain_mv
refresh on demand
as
  select develop_channel, acct_month,net_type,product_id,item_flag,grid,sum(curr_user_new)/count(*) u,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),0),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u01 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),1),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u02 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),2),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u03 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),3),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u04 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),4),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u05 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),5),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u06 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),6),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u07 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),7),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u08 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),8),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u09 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),9),'YYYYMM')  THEN  curr_user_new_count ELSE 0 END)  u10 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),10),'YYYYMM') THEN  curr_user_new_count ELSE 0 END)  u11 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),11),'YYYYMM') THEN curr_user_new_count ELSE 0 END)   u12 ,      
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),0),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f01 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),1),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f02 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),2),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f03 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),3),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f04 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),4),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f05 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),5),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f06 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),6),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f07 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),7),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f08 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),8),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f09 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),9),'YYYYMM') THEN  curr_user_new_charge ELSE 0 END)  f10 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),10),'YYYYMM') THEN  curr_user_new_charge ELSE 0 END)  f11 ,
        sum(CASE charge_month WHEN to_char(add_months(to_date(acct_month,'YYYYMM'),11),'YYYYMM')  THEN  curr_user_new_charge ELSE 0 END)  f12       
        from REGIONJ_M_NEW_USER  group by develop_channel, acct_month,net_type,product_id,item_flag,grid 
  