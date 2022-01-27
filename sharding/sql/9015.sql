-- 在多個數據庫中添加公共表
# use user_db;
# use edu_db_1;
use edu_db_2;

create table t_dict(
   `dict_id` bigint(20) primary key,
   `status` varchar(100) not null,
   `value` varchar(100) not null
);