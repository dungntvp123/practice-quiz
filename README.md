# swp391-se1702net-group2
create database SWP211

use SWP211

create table [User]
(
	id int identity(1,1) primary key,
	[username] varchar(30) not null,
	[password] varchar(30) not null,
	[firstname] nvarchar(30),
	[lastname] nvarchar(30),
	[email] varchar(30) not null,
	[time] datetime,
	[role] int,
	[confirmkey] varchar(20),
)


create table [ADMIN] 
(
	[id] int identity(1,1) primary key,
	[userid] int foreign key references [User](id),
)

create table [Student] 
(
	[id] int identity(1,1) primary key,
	[userid] int foreign key references [User](id),
	[status] int,
)

create table [Lecture]
(
	[id] int identity(1,1) primary key,
	[userid] int foreign key references [User](id),
	[status] int,
)

create table [Subject]
(
	[id] int identity(1,1) primary key,
	[name] varchar(30),
	[numofchapter] int
)

create table [Question]
(
	[id] int identity(1,1) primary key,
	[subjectid] int foreign key references [Subject](id),
	[description] text,
	[chapter] int,
)

create table [Selection]
(
	[id] int identity(1,1) primary key,
	[questionid] int foreign key references [Question](id),
	[coefficient] decimal(4,2),
	[charid] varchar(1),
	[description] text,
)

create table [Class]
(
	[id] int identity(1,1) primary key,
	[name] varchar(30),
	[subjectid] int foreign key references [Subject](id),
	[lectureid] int foreign key references [Lecture](id),
	[startclass] date,
	[finishedclass] date,
	[status] int
)

create table [Student_Class_Detail] 
(
	[id] int identity(1,1) primary key,
	[studentid] int foreign key references [Student](id),
	[Classid] int foreign key references [Class](id),
	[status] int,
)

create table [Test] 
(
	[id] int identity(1,1) primary key,
	[name] varchar(30),
	[duration] int,
	[starttime] datetime,
	[finishedtime] datetime,
	[numofques] int,
	[coefficient] decimal(4,2),
	[classid] int foreign key references [Class](id),
	[status] int,
)

create table [Test_Detail] 
(
	[id] int identity(1,1) primary key,
	[testid] int foreign key references [Test](id),
	[questionid] int foreign key references [Question](id),
	[coefficient] decimal(4,2),
)

create table [Student_Test] 
(
	[id] int identity(1,1) primary key,
	SCDid int foreign key references [Student_Class_Detail](id),
	Testid int foreign key references [Test](id),
	[starttime] datetime,
	[finishedtime] datetime,
	[totalscore] decimal(4,2)
)

create table [Student_Test_Detail]
(
	[id] int identity(1,1) primary key,
	[STid] int foreign key references [Student_Test](id),
	[TDid] int foreign key references [Test_Detail](id),
	[selected] varchar(30),
	[score] decimal(4,2),

)




























