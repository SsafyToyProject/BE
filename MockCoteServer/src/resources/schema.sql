drop database if exists mockcote;
set global time_zone = "+9:00";
create database mockcote;
use mockcote;

create table problems(
	problem_id int not null unique,
    difficulty int,
    title varchar(2000),
    primary key(problem_id)
);

create table queries(
	query_id int auto_increment,
    title varchar(100),
    query_str varchar(2000),
    num_problems int,
    primary key(query_id)
);

create table candidates(
	query_id int,
    problem_id int,
    foreign key (query_id) references queries(query_id),
    foreign key (problem_id) references problems(problem_id)
);

create table users(
	user_id int auto_increment,
    handle varchar(50) unique,
    password varchar(250),
    level int,
    primary key(user_id)
);

create table studies(
	study_id int auto_increment,
    owner_id int,
    name varchar(50),
    description varchar(2000),
    code varchar(50),
    primary key(study_id),
    foreign key (owner_id) references users(user_id)
);

create table study_members(
	study_id int,
    user_id int,
    foreign key(study_id) references studies(study_id),
    foreign key(user_id) references users(user_id),
	unique (study_id, user_id)
);

create table sessions(
	session_id int auto_increment,
    study_id int,
    query_id int,
    start_at timestamp,
    end_at timestamp,
    problem_pool varchar(100),
    primary key (session_id),
    foreign key (study_id) references studies(study_id),
    foreign key (query_id) references queries(query_id)
);

create table session_participants (
	session_id int,
    user_id int,
    foreign key (session_id) references sessions(session_id),
    foreign key (user_id) references users(user_id),
    unique(session_id, user_id)
);

create table session_problems (
	session_id int,
    problem_id int,
    foreign key (session_id) references sessions(session_id),
    foreign key (problem_id) references problems(problem_id)
);

create table session_trackers (
	session_id int,
    user_id int,
    problem_id int,
    solved_at timestamp,
    performance int,
    language varchar(50),
    code_link varchar(100),
    description varchar(2000),
    foreign key (session_id) references sessions(session_id),
    foreign key (user_id) references users(user_id),
    foreign key (problem_id) references problems(problem_id)
);


