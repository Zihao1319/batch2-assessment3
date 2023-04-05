drop database if exists user_db;

CREATE DATABASE user_db;

USE user_db;

CREATE TABLE users (
    user_id         varchar(20) not null, 
    username        varchar(20) not null,
    name            varchar(20),

    primary key (user_id)
);

create table task (
    user_id        varchar(20) not null, 
    task_id        int not null auto_increment,
    description    varchar(255) not null,
    priority       int not null,
    due_date       date not null,

    constraint 	user_task_fk foreign key (user_id) references users (user_id)  

);

GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
