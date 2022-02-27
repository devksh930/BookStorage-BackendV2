create table user
(
    id                binary(16)   not null
        primary key,
    created_date      datetime(6)  null,
    modified_date     datetime(6)  null,
    email             varchar(512) not null,
    email_verified    bit          not null,
    nickname          varchar(100) not null,
    password          varchar(128) not null,
    profile_image_url varchar(512) null,
    role_type         varchar(255) null,
    user_id           varchar(64)  not null,
    constraint UK_a3imlf41l37utmxiquukk8ajc
        unique (user_id),
    constraint UK_n4swgcf30j6bmtb4l4cjryuym
        unique (nickname),
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email)
);
create table book
(
    id            bigint auto_increment
        primary key,
    created_date  datetime(6)  null,
    modified_date datetime(6)  null,
    author        varchar(255) null,
    description   varchar(255) null,
    discount      varchar(255) null,
    image         varchar(255) null,
    isbn          varchar(255) null,
    link          varchar(255) null,
    price         varchar(255) null,
    pubdate       varchar(255) null,
    publisher     varchar(255) null,
    title         varchar(255) null,
    constraint UK_ehpdfjpu1jm3hijhj4mm0hx9h
        unique (isbn)
);


create table book_storage
(
    id             bigint auto_increment
        primary key,
    created_date   datetime(6)  null,
    modified_date  datetime(6)  null,
    book_read_type varchar(255) not null,
    book_id        bigint       null,
    user_id        binary(16)   null,
    constraint FK6me5xg0w8j9di57bcuffv3wf2
        foreign key (user_id) references user (id),
    constraint FKkqx74j2dfw9mlyn1y0axu4m4w
        foreign key (book_id) references book (id)
);
create table book_post
(
    id              bigint auto_increment
        primary key,
    created_date    datetime(6)  null,
    modified_date   datetime(6)  null,
    book_post_type  varchar(255) not null,
    content         longtext     null,
    count           int          not null,
    description     varchar(255) null,
    is_deleted      bit          not null,
    is_post_private bit          not null,
    like_count      int          not null,
    title           varchar(255) null,
    bookstorage_id  bigint       null,
    constraint FKq056oxh20nc8auh9a54twwwwm
        foreign key (bookstorage_id) references book_storage (id)
);
