create table `interest`
(
    `name`       varchar(20)  not null,
    `identifier` varchar(255) not null,
    primary key (`name`)
) engine = InnoDB;

-- User
create table `user`
(
    `id`         bigint                                      not null auto_increment,
    `state`      enum ('ADMIN','DELETED','MODERATOR','USER') not null,
    `visibility` enum ('PRIVATE','PUBLIC')                   not null,
    `username`   varchar(15)                                 not null,
    `name`       varchar(20)                                 not null,
    `email`      varchar(30)                                 not null,
    `identifier` varchar(255)                                not null,
    primary key (`id`),
    unique `USER_UNIQUE_USERNAME` (`username`),
    unique `USER_UNIQUE_EMAIL` (`email`)
) engine = InnoDB;

create table `user_interest`
(
    `user_id`   bigint not null,
    `interests` varchar(10),
    constraint `USER_INTEREST_FK_USER` foreign key (`user_id`) references `user` (`id`)
) engine = InnoDB;

create table `profile_setting`
(
    `user_id`    bigint not null,
    `badge_list` varchar(255),
    primary key (`user_id`)
) engine = InnoDB;

-- Badge

create table `badge`
(
    `id`         bigint       not null auto_increment,
    `name`       varchar(15)  not null,
    `identifier` varchar(255) not null,
    primary key (`id`)
) engine = InnoDB;

create table `owning`
(
    `acquired_at` datetime(6),
    `badge_id`    bigint not null,
    `user_id`     bigint not null,
    primary key (`badge_id`, `user_id`),
    constraint `OWNING_FK_BADGE` foreign key (`badge_id`) references `badge` (`id`)
) engine = InnoDB;

-- Relationship

create table `follow`
(
    `target_id` bigint not null,
    `user_id`   bigint not null,
    primary key (`target_id`, `user_id`)
) engine = InnoDB;

create table `block`
(
    `target_id` bigint not null,
    `user_id`   bigint not null,
    primary key (`target_id`, `user_id`)
) engine = InnoDB;

-- Group

create table `group`
(
    `deleted`     bit          not null,
    `id`          bigint       not null auto_increment,
    `name`        varchar(15)  not null,
    `description` varchar(200),
    `identifier`  varchar(255) not null,
    `interest`    varchar(255),
    primary key (`id`)
) engine = InnoDB;

create table `group_user`
(
    `group_id`      bigint                    not null,
    `registered_at` datetime(6),
    `user_id`       bigint                    not null,
    `role`          enum ('MANAGER','MEMBER') not null,
    primary key (`group_id`, `user_id`),
    constraint `GROUP_USER_FK_GROUP` foreign key (`group_id`) references `group` (`id`)
) engine = InnoDB;

-- Quest

create table `quest`
(
    `badge_id`          bigint,
    `created_at`        datetime(6),
    `expired_at`        datetime(6),
    `group_id`          bigint,
    `id`                bigint                                          not null auto_increment,
    `reward_count`      bigint,
    `category`          enum ('GROUP','NORMAL')                         not null,
    `state`             enum ('ACTIVE','DELETED','LOCKED','NEED_LABEL') not null,
    `label`             varchar(15),
    `title`             varchar(30)                                     not null,
    `image_description` varchar(100)                                    not null,
    `content`           varchar(300),
    `interest_name`     varchar(255),
    primary key (`id`)
) engine = InnoDB;

create table `quest_image`
(
    `quest_id`   bigint       not null,
    `identifier` varchar(255) not null,
    constraint `QUEST_IMAGE_FK_QUEST` foreign key (`quest_id`) references `quest` (`id`)
) engine = InnoDB;

create table `participant`
(
    `linked_count` bigint,
    `quest_id`     bigint                                     not null,
    `user_id`      bigint                                     not null,
    `state`        enum ('CONTINUE','DOING','FINISHED','NOT') not null,
    primary key (`quest_id`, `user_id`),
    constraint `PARTICIPANT_FK_QUEST` foreign key (`quest_id`) references `quest` (`id`)
) engine = InnoDB;

-- Post

create table `post`
(
    `id`         bigint                                                       not null auto_increment,
    `quest_id`   bigint,
    `updated_at` datetime(6),
    `user_id`    bigint                                                       not null,
    `state`      enum ('DELETED','FAIL','NEED_CHECK','NOT_DECIDED','SUCCESS') not null,
    `content`    varchar(500),
    primary key (`id`)
) engine = InnoDB;

create table `post_image`
(
    `order`      integer      not null,
    `post_id`    bigint       not null,
    `identifier` varchar(255) not null,
    primary key (`order`, `post_id`),
    constraint `POST_IMAGE_FK_POST` foreign key (`post_Id`) references `post` (`id`)
) engine = InnoDB;

create table `comment`
(
    `id`         bigint       not null auto_increment,
    `post_id`    bigint       not null,
    `updated_at` datetime(6),
    `user_id`    bigint       not null,
    `content`    varchar(200) not null,
    primary key (`id`)
) engine = InnoDB;

create table `post_like`
(
    `post_id` bigint not null,
    `user_id` bigint not null,
    primary key (`post_id`, `user_id`)
) engine = InnoDB;

create table `post_dislike`
(
    `post_id` bigint not null,
    `user_id` bigint not null,
    primary key (`post_id`, `user_id`)
) engine = InnoDB;