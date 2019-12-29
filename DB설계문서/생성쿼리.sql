drop database if exists library;
create database library;

use library;

drop table if exists student;
create table student(
   sno int not null ,
   sname varchar(20),
   year int,
   dept varchar(20),
   id varchar(20) not null,
   pw varchar(20) not null,
   primary key(sno)
);
insert into student values(100,   '최우연',  4,  '컴퓨터공학부','wy0723','1415');
insert into student values(200,   '김수교',  4,  '컴퓨터공학부','sk0293', '1415');
insert into student values(300,   '이현재',  4,  '컴퓨터공학부','hj0123','1415');
insert into student values(400,   '김준수',  4,  '컴퓨터공학부','js1942','1415');
insert into student values(500,   '박신혜',  3,  '응용통계학과','sh9302','1415');
insert into student values(600,   '고준희',  3,  '국어국문학과','jh9573','1415');
insert into student values(700,   '이진수',  3,  '컴퓨터공학부', 'lee1048','1415');
insert into student values(800,   '김석현',  2,  '컴퓨터','ssh4927','1234');
insert into student values(900,   '성민제',  2,  '수학과','mj1027','1234');
insert into student values(1000,  '오승배',    2,  '컴퓨터공학부','sb4058','1415');
insert into student values(1100,  '이찬혁',    1,  '독어독문학과','ch4856','1415');
insert into student values(1200,  '방정식',    1,  '독어독문학과','bombe12','1415');

drop table if exists manager;
create table manager(
   rrn varchar(20) not null ,   #rrn = resident registration number
   mname varchar(20),
   career int,
   id varchar(20) not null,
   pw varchar(20) not null,
   mno int not null,
   dateOfEntry date,
   primary key(mno)
);
insert into manager values('701225-1234567', '홍길동', 20,'manager1','1415',1,'1999-04-16');
insert into manager values('701225-1234561', '김하나', 20,'manager2','1415',2,'1999-05-16');
insert into manager values('701225-1234561', '김종권', 20,'root','1415',0,'1994-03-04');

drop table if exists book;
create table book(
   isbn varchar(20) ,
   author varchar(20),
   bname varchar(40),
   publisher varchar(20) ,
   bno varchar(20),
   cnt int,
   avail tinyint(1),    # 대출여부 : 있으면 1 없으면 0
   primary key(isbn)
);
insert into book values('9788994492032', '남궁','자바의정석','도우출판','8-00001',0,1);
insert into book values('9788970509419', '최영규','두근두근 자료구조','생능출판사','8-00003',0,0);
insert into book values('9791185459547', '페드로 도밍고스','마스터 알고리즘','비즈니스북스','8-00007',0,0);
insert into book values('9791163030072', '보요 시바','Do it! 자료구조와 함께 배우는 알고리즘 입문: 자바 편','이지스퍼블리싱','8-00004',0,0);
insert into book values('9788996094067', '윤성우','윤성우의 열혈자료구조','오렌지미디어','8-00005',0,1);


drop table if exists borrow;
create table borrow(
    sno int not null ,
    isbn varchar(20) ,
    first date,         #대출일
    deadline date,      #반납예정일
    extraCnt int,
    mno int,
    back date,          #반납일
    late tinyint(1),    #연체여부
    primary key(sno, isbn, first),
    foreign key(sno) references student(sno),
    foreign key (isbn) references book(isbn)
);

insert into borrow values (1200, 9791163030072, '2019-04-13','2019-04-27',0,1,null,1);
insert into borrow values (400, 9788970509419, '2019-04-28','2019-05-12',0,1,null,0);
insert into borrow values (100, 9791185459547, '2019-04-28','2019-05-12',0,1,null,0);

drop table if exists overdue;
create table overdue(
    sno int not null ,
    isbn varchar(20) ,
    first date,         #대출일
    howlong int,        #연체일
    penalty long,       #연체금
    primary key(sno, isbn, first),
    foreign key(sno) references student(sno),
    foreign key (isbn) references book(isbn)
);
insert into overdue values (1200, 9791163030072, '2019-04-13',1,null);

