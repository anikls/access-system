/*==============================================================*/
/*  Пользователи                                                */
/*==============================================================*/
create table users
(
    id bigint not null,
    room_id bigint,
    constraint pk_users primary key (id)
);

comment on table users is 'Пользователи системы доступа';
comment on column users.id is 'Идентификатор';
comment on column users.room_id is 'Комната, в которую вошел пользователь';

/*==============================================================*/
/* Комнаты                                                      */
/*==============================================================*/
create table rooms
(
    id bigint not null,
    name varchar(255) not null,
    unique (name),
    constraint pk_rooms primary key (id)
);

comment on table rooms is 'Комнаты';
comment on column rooms.id is 'Идентификатор';
comment on column rooms.name is 'Название комнаты';

alter table users
    add constraint fk_users foreign key (room_id)
        references rooms (id)
        on delete restrict on update restrict;

insert into rooms (id, name) VALUES
   (1, 'Большая красная переговорка'),
   (2, 'Кухня'),
   (3, 'Игровая'),
   (4, 'Холл'),
   (5, 'Туалет');

CREATE ALIAS ADDUSERS AS $$
int addTestData(java.sql.Connection con, long maxCountUser) throws Exception {
    int cnt = 0;
    Statement statement = con.createStatement();
    for (int i = 0; i <= maxCountUser; i++) {
        statement.executeUpdate("insert into users (id, room_id) VALUES (" + i + ", null )");
        cnt++;
    }
    return cnt;
}
$$;

CALL ADDUSERS(10000);
