CREATE TABLE if not exists `img`
(
    `id`     int(11)     NOT NULL AUTO_INCREMENT,

    `img`    varchar(64) NOT NULL,
    #发送者id
    `userid` int(11)     NOT NULL,
    #描述
    `description`   varchar(64) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE if not exists `user`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `name`         varchar(64) DEFAULT NULL,
    `password`     varchar(64) DEFAULT NULL,
    `email`        varchar(64) DEFAULT NULL,
    #用户封禁状态，默认1活动
    `state`        int(1)      DEFAULT 1,
    #序列化状态，1.从没2.正在3.已完成
    `p1_npy_state` int(1)      DEFAULT 1,
    `p2_npy_state` int(1)      DEFAULT 1,
    #是否为管理员，默认0为否
    `role`         int(1)      DEFAULT 0,
    PRIMARY KEY (`id`)
);
#mock数据
insert into user(name, password, email, role)
values ('admin', 'admin', 'admin@163.com', 1);
insert into user(name, password, email, role)
values ('test', 'test', '123@qq.com', 0);
insert into user(name, password, email, role)
values ('test2', 'test2', 'admin@163.com', 0);
insert into user(name, password, email, role)
values ('test3', 'test3', 'admin@163.com', 0);

insert into img(img, userid, description)
values ('aaa.png', '123', 'nice');


update user
set p2_npy_state = 3
WHERE id = 1;

UPDATE user
SET p1_npy_state = 3
WHERE id = 1;

select * from user


