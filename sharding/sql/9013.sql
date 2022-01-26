create database edu_db1;
create database edu_db2;

use edu_db1;

create table course_1 (
   `cid` bigint(20) primary key,
   `cname` varchar(50) not null,
   `user_id` bigint(20) not null,
   `status` varchar(10) not null
);

create table course_2 (
   `cid` bigint(20) primary key,
   `cname` varchar(50) not null,
   `user_id` bigint(20) not null,
   `status` varchar(10) not null
);

use edu_db2;

create table course_1 (
   `cid` bigint(20) primary key,
   `cname` varchar(50) not null,
   `user_id` bigint(20) not null,
   `status` varchar(10) not null
);

create table course_2 (
   `cid` bigint(20) primary key,
   `cname` varchar(50) not null,
   `user_id` bigint(20) not null,
   `status` varchar(10) not null
);