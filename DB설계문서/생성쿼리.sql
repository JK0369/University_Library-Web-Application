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
insert into student values(100,   '�ֿ쿬',  4,  '��ǻ�Ͱ��к�','wy0723','1415');
insert into student values(200,   '�����',  4,  '��ǻ�Ͱ��к�','sk0293', '1415');
insert into student values(300,   '������',  4,  '��ǻ�Ͱ��к�','hj0123','1415');
insert into student values(400,   '���ؼ�',  4,  '��ǻ�Ͱ��к�','js1942','1415');
insert into student values(500,   '�ڽ���',  3,  '��������а�','sh9302','1415');
insert into student values(600,   '������',  3,  '������а�','jh9573','1415');
insert into student values(700,   '������',  3,  '��ǻ�Ͱ��к�', 'lee1048','1415');
insert into student values(800,   '�輮��',  2,  '��ǻ��','ssh4927','1234');
insert into student values(900,   '������',  2,  '���а�','mj1027','1234');
insert into student values(1000,  '���¹�',    2,  '��ǻ�Ͱ��к�','sb4058','1415');
insert into student values(1100,  '������',    1,  '������а�','ch4856','1415');
insert into student values(1200,  '������',    1,  '������а�','bombe12','1415');

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
insert into manager values('701225-1234567', 'ȫ�浿', 20,'manager1','1415',1,'1999-04-16');
insert into manager values('701225-1234561', '���ϳ�', 20,'manager2','1415',2,'1999-05-16');
insert into manager values('701225-1234561', '������', 20,'root','1415',0,'1994-03-04');

drop table if exists book;
create table book(
   isbn varchar(20) ,
   author varchar(20),
   bname varchar(40),
   publisher varchar(20) ,
   bno varchar(20),
   cnt int,
   avail tinyint(1),    # ���⿩�� : ������ 1 ������ 0
   primary key(isbn)
);
insert into book values('9788994492032', '����','�ڹ�������','��������','8-00001',0,1);
insert into book values('9788970509419', '�ֿ���','�αٵα� �ڷᱸ��','�������ǻ�','8-00003',0,0);
insert into book values('9791185459547', '���� ���ְ�','������ �˰���','����Ͻ��Ͻ�','8-00007',0,0);
insert into book values('9791163030072', '���� �ù�','Do it! �ڷᱸ���� �Բ� ���� �˰��� �Թ�: �ڹ� ��','�������ۺ���','8-00004',0,0);
insert into book values('9788996094067', '������','�������� �����ڷᱸ��','�������̵��','8-00005',0,1);


drop table if exists borrow;
create table borrow(
    sno int not null ,
    isbn varchar(20) ,
    first date,         #������
    deadline date,      #�ݳ�������
    extraCnt int,
    mno int,
    back date,          #�ݳ���
    late tinyint(1),    #��ü����
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
    first date,         #������
    howlong int,        #��ü��
    penalty long,       #��ü��
    primary key(sno, isbn, first),
    foreign key(sno) references student(sno),
    foreign key (isbn) references book(isbn)
);
insert into overdue values (1200, 9791163030072, '2019-04-13',1,null);

