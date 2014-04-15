drop database if exists shop4j;
create database shop4j;

use shop4j;


-- create table category
drop table if exists category;
create table category(
        id bigint not null auto_increment primary key,
        name varchar(20) not null,
        id_parent bigint,

        foreign key id_category_id_parent(id_parent)
        references category(id)
        on update cascade
        on delete no action
);


insert into category(name,id_parent)
    values
        ("Clothes",null),
        ("Woman",1),
        ("Men",1),
        ("Children",1),
        ("TShirt",2),
        ("Shoes",2),
        ("Boots",6),
        ("Flip flop",6);


/* create table product*/
drop table if exists product;
create table product(
    id bigint not null auto_increment primary key,
    id_category bigint not null,
    name varchar(20) not null,
    description varchar(200),
    price float not null,
	perc_reduction float not null default 0,

    foreign key product_category(id_category)
    references category(id)
    on update cascade
    on delete no action
);

insert into product(id_category,name,description,price)
    values
        (5,"V TShirt","",20),
        (5,"Polo TShirt","",30),
        (7,"Macys","",75),
        (7,"H & M","",110),
        (8,"Sport flip flop","",35.99),
        (8,"H & M flip flop","",20);


/*call child_categories(2);*/


drop table if exists color;
create table color(

id_color bigint not null auto_increment primary key,
name varchar(15)  not null,
description varchar (30) not null,
code varchar (20) not null
);

insert into color(name,description) values ("Red","Red product"),("Green","Green product"),("Blue","Blue product");


drop table if exists size;
create table size(
id_size bigint not null auto_increment primary key,
name varchar(10) not null,
id_cat bigint not null,
foreign key id_cat(id_cat) references category(id)
on update cascade
on delete cascade
);

drop table if exists product_color;
create table product_color(
id_prod_col bigint not null primary key auto_increment,
id_product bigint not null,
id_color bigint not null,
foreign key id_product(id_product) references product(id)
on update cascade
on delete cascade,
foreign key id_color(id_color) references color(id_color)
);

insert into product_color(id_product,id_color) values(1,1),(1,2),(1,3),(2,1),(2,3);

drop table if exists product_color_size;
create table product_color_size(
id_pcs bigint not null primary key auto_increment,
id_prod_col bigint not null,
foreign key id_prod_col(id_prod_col) references product_color(id_prod_col)
on update cascade
on delete cascade,
nr_pieces int not null,
id_size bigint not null,
foreign key id_size(id_size) references size(id_size)
on update cascade
on delete cascade
);

drop table if exists country;
create table country(
        id bigint not null auto_increment primary key,
        name varchar(100) not null
);

-- create table county
drop table if exists county;
create table county(
    id bigint not null auto_increment primary key,
    name varchar(100) not null,
    id_country bigint not null,
    foreign key county_country(id_country)
    references country(id)
    on update cascade
    on delete no action
);


-- create table locality
drop table if exists locality;
create table locality(
    id bigint not null auto_increment primary key,
    name varchar(100) not null,
    id_county bigint not null,
    foreign key locality_county(id_county)
    references county(id)
    on update cascade
    on delete no action
);

-- create table user
drop table if exists user;
create table user(
    id bigint not null auto_increment primary key,
    username varchar(50) not null unique,
    user_password char(32) not null,
    rolename varchar(50) not null,
    email varchar(100) not null unique,
    passwordStatus int not null,
	ban int not null
);
UPDATE `shop4j`.`user` SET `passwordStatus`='2' WHERE `id`='1';
UPDATE `shop4j`.`user` SET `passwordStatus`='2' WHERE `id`='2';
UPDATE `shop4j`.`user` SET `passwordStatus`='2' WHERE `id`='4';
insert into user(username,user_password,rolename,email) values ("client1","client1","client","gigi@yahoo.com"),("operator1","operator1","operator","gigi2@yahoo.com"),
("admin1","admin1","admin","gigi3@yahoo.com"),("superadmin1","superadmin1","superadmin","gigi4@yahoo.com");

drop table if exists client;
create table client(
    id bigint not null primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    phone_number varchar(20),
    foreign key client_user(id)
    references user(id)
    on update cascade
    on delete cascade
);

insert into client(id,first_name,last_name,phone_number) values (1,"Gigi","Muresan","0712345678");

-- create table adress
drop table if exists adress;
create table adress(
    id bigint not null auto_increment primary key,
    id_locality bigint not null,
    street varchar(200) not null,
    number bigint not null,
    staircase varchar(10),
    block bigint,
    flat bigint,
    id_client bigint not null,
    foreign key adress_locality(id_locality)
    references locality(id)
    on update cascade
    on delete no action,
    foreign key adress_client(id_client)
    references client(id)
    on update cascade
    on delete no action
);

-- create table messages
drop table if exists messages;
create table messages(
    id bigint not null auto_increment primary key,
    subject varchar(50)not null,
    message varchar(2000) not null,
    send_date date not null,
    is_replied bit not null,
    id_user bigint,
    id_client bigint,
    foreign key messages_user(id_user)
    references user(id)
    on update cascade
    on delete cascade,
    foreign key messages_client(id_client)
    references client(id)
    on update cascade
    on delete cascade
);

drop table if exists command_status;
create table command_status(
    id bigint not null auto_increment primary key,
    status varchar(30)
);

insert into command_status(status) values ("In progress"),("delivered");

drop table if exists command;
create table command(
    id bigint not null auto_increment primary key,
    order_date date not null,
    delivery_date date,   
    id_adress bigint not null,
    id_client bigint not null,
    id_status bigint not null,
	id_operator bigint,
    foreign key command_adress(id_adress)
    references adress(id)
    on update no action
    on delete no action,
    foreign key command_client(id_client)
    references client(id)
    on update no action
    on delete no action,
    foreign key command_status(id_status)
    references command_status(id)
    on update no action
    on delete no action,
	foreign key command_operator(id_operator)
	references user(id)
	on update no action
	on delete no action
);

drop table if exists returnedOrders;

create table returnedOrders(
	id bigint not null auto_increment primary key,
    id_command bigint unique,
    returnDate Date not null,
	addDate Date,
    returned bool not null,retreived bool not null,
 foreign key returned_command(id_command)
    references command(id)
    on update no action
    on delete no action

);

drop table if exists returnedProducts;
create table returnedProducts(
id bigint not null auto_increment primary key,
id_command bigint not null,
id_product bigint not null,

foreign key returned_product1(id_command)
references returnedOrders(id)
on update cascade 
on delete cascade,

foreign key returned_product2(id_product)
references product_color_size(id_pcs)
on update cascade 
on delete cascade
);

drop table if exists client_product;
create table client_product(
id_client_product bigint not null auto_increment primary key,
id_command bigint not null,
foreign key id_comm(id_command) references command(id)
on update cascade
on delete no action,
id_pcs bigint not null,
foreign key id_prod_col_size(id_pcs) references product_color_size(id_pcs)
on update no action
on delete no action,
nr_pieces int not null,
perc_reduction double,
price double not null
);

/* insert values*/


/* recursive procedure for category */
delimiter $$

drop procedure if exists shop4j.parent_categories $$
CREATE PROCEDURE shop4j.parent_categories(in param bigint)
begin
    select c.id, c.name, c.id_parent from category c join
    (select
        @id_parent as _user_id,
        (
            select @id_parent := id_parent
            from category   
            where id = _user_id
        ) as parent
    from
    (
        select @id_parent := param
    )alias,
    category c
    where @id_parent is not null) t
    on t.parent = c.id;
end $$
/* recursive procedure for sizes of a category*/
delimiter $$
drop procedure if exists shop4j.sizes_parent_categories $$
CREATE PROCEDURE shop4j.sizes_parent_categories(in param bigint)
begin
    select s.id_size, s.name, s.id_cat from size s join
    (
        select c.id, c.name, c.id_parent from category c join
        (select
            @id_parent as _user_id,
            (
                select @id_parent := id_parent
                from category   
                where id = _user_id
            ) as parent
        from
        (
            select @id_parent := param
        )alias,
        category c
        where @id_parent is not null) t
        on t.parent = c.id
    )categories
    on s.id_cat = categories.id;
end $$

drop procedure if exists shop4j.child_categories $$
create procedure shop4j.child_categories(in param bigint)
begin

    declare found int default 1;

    drop table if exists cat_tree;
    create table cat_tree
    (
        cat_id int primary key
    );

    insert into cat_tree
        select id from category
        where id_parent = param;

    set found = row_count();

    while found > 0
    do
        insert ignore into cat_tree
            select c_child.id
            from cat_tree c
                join category c_child
            where c.cat_id = c_child.id_parent;
        set found = row_count();
    end while;

    select c.id, c.name, c.id_parent
    from category c
    join cat_tree ct
    on c.id = ct.cat_id;
   
    drop table cat_tree;

end $$


delimiter $$
use shop4j$$
drop procedure if exists shop4j.products_child_categories $$
create procedure shop4j.products_child_categories(in param bigint)
begin

    declare found int default 1;

    drop table if exists cat_tree;
    create table cat_tree
    (
        cat_id int primary key
    );

    insert into cat_tree
        select id from category
        where id_parent = param;

    set found = row_count();

    while found > 0
    do
        insert ignore into cat_tree
            select c_child.id
            from cat_tree c
                join category c_child
            where c.cat_id = c_child.id_parent;
        set found = row_count();
    end while;

    select p.id_prod_col, p.id_product, p.id_color
    from product_color p
    join product prod on prod.id = p.id_product
    join cat_tree ct on prod.id_category = ct.cat_id
    union
    select p.id_prod_col, p.id_product, p.id_color
    from product_color p
    join product prod on prod.id = p.id_product
    join category c on c.id = prod.id_category
    where c.id = param;
   
    drop table cat_tree;

end $$

call products_child_categories(5)$$

delimiter $$
use shop4j$$
drop procedure if exists shop4j.generic_products_child_categories $$
create procedure shop4j.generic_products_child_categories(in param bigint)
begin

    declare found int default 1;

    drop table if exists cat_tree;
    create table cat_tree
    (
        cat_id int primary key
    );

    insert into cat_tree
        select id from category
        where id_parent = param;

    set found = row_count();

    while found > 0
    do
        insert ignore into cat_tree
            select c_child.id
            from cat_tree c
                join category c_child
            where c.cat_id = c_child.id_parent;
        set found = row_count();
    end while;

    select p.id, p.id_category, p.name, p.description, p.price, p.perc_reduction
    from product p
    join cat_tree ct on p.id_category = ct.cat_id
    union
    select p.id, p.id_category, p.name, p.description, p.price, p.perc_reduction
    from product p
    join category c on c.id = p.id_category
    where c.id = param;
   
    drop table cat_tree;

end $$
call generic_products_child_categories(1)$$

INSERT INTO `User` (`id`, `Username`, `User_password`, `rolename`, `email`, `passwordStatus`, `ban`) VALUES
(6, 'client4', 'pass4', 'client', 'client4@yahoo.com', 1, 0),
(7, 'client5', 'pass5', 'client', 'client5@yahoo.com', 1, 0),
(8, 'client6', 'pass6', 'client', 'client6@yahoo.com', 1, 0),
(9, 'client7', 'pass7', 'client', 'client7@yahoo.com', 1, 0),
(10, 'client8', 'pass8', 'client', 'client8@yahoo.com', 1, 0),
(11, 'client9', 'pass9', 'client', 'client9@yahoo.com', 1, 0),
(12, 'client10', 'pass10', 'client', 'client10@yahoo.com', 1, 0),
(13, 'client11', 'pass11', 'client', 'client11@yahoo.com', 1, 0),
(14, 'client12', 'pass12', 'client', 'client12@yahoo.com', 1, 0),
(15, 'client13', 'pass13', 'client', 'client13@yahoo.com', 1, 0),
(16, 'client14', 'pass14', 'client', 'client14@yahoo.com', 1, 0),
(17, 'client15', 'pass15', 'client', 'client15@yahoo.com', 1, 0),
(18, 'client16', 'pass16', 'client', 'client16@yahoo.com', 1, 0),
(19, 'client17', 'pass17', 'client', 'client17@yahoo.com', 1, 0),
(20, 'client18', 'pass18', 'client', 'client18@yahoo.com', 1, 0),
(21, 'client19', 'pass19', 'client', 'client19@yahoo.com', 1, 0),
(22, 'client20', 'pass20', 'client', 'client20@yahoo.com', 1, 0),
(23, 'client21', 'pass21', 'client', 'client21@yahoo.com', 1, 0),
(24, 'client22', 'pass22', 'client', 'client22@yahoo.com', 1, 0),
(25, 'client23', 'pass23', 'client', 'client23@yahoo.com', 1, 0),
(26, 'client2', 'Client2', 'client', 'client2@yahoo.com', 1, 0),
(27, 'client24', 'Pass24', 'client', 'client24@yahoo.com', 1, 0),
(28, 'vanessa', 'Vanessa1', 'client', 'gligor.vanessa@gmail.com', 1, 0),
(29, 'client1', 'Client1', 'client', 'client1@yahoo.com', 1, 0);



INSERT INTO `Country` (`id`, `name`) VALUES
(1, 'Romania'),
(2, 'Great Britain');

INSERT INTO `County` (`id`, `name`, `id_Country`) VALUES
(1, 'Brasov', 1),
(2, 'Sibiu', 1),
(3, 'Timis', 1),
(4, 'Constanta', 1),
(5, 'Ilfov', 1);

INSERT INTO `Locality` (`id`, `name`, `id_County`) VALUES
(1, 'Brasov', 1),
(2, 'Podu-Oltului', 1),
(3, 'Harman', 1),
(4, 'Ghimbav', 1),
(5, 'Sibiu', 2),
(6, 'Timisoara', 3),
(7, 'Constanta', 4),
(8, 'Bucuresti', 5);

INSERT INTO `Adress` (`id`, `id_Locality`, `street`, `number`, `staircase`, `block`, `flat`, `id_Client`) VALUES
(24, 7, 'Harmanului', 99, 'a', 17, 2, 9),
(25, 1, 'Vald Tepes', 8, 'd', 11, 2, 10),
(26, 2, 'Florilor', 88, 'c', 14, 2, 11),
(27, 8, 'Andrei Saguna', 7, 'a', 22, 2, 12),
(28, 1, 'Independentei', 2, 'a', 12, 2, 13),
(29, 3, 'Castanilor', 9, 'b', 3, 2, 14),
(30, 1, 'Garii', 2, 'a', 17, 2, 15),
(31, 5, 'Toamnei', 77, 'c', 18, 2, 16),
(32, 2, 'Morii', 2, 'a', 19, 4, 17),
(33, 2, 'Soarelui', 2, 'b', 1, 5, 18),
(34, 1, 'Scolii', 2, 'c', 2, 6, 19),
(35, 7, 'Carierei', 99, 'a', 17, 2, 20),
(36, 1, 'Vald Tepes', 8, 'd', 11, 2, 21),
(37, 2, 'Eroilor', 88, 'c', 14, 9, 22),
(38, 8, 'Iuliu Maniu', 7, 'a', 22, 2, 23),
(39, 7, 'Harmanului', 99, 'a', 17, 2, 9),
(40, 1, 'Vald Tepes', 8, 'd', 11, 2, 10),
(41, 2, 'Florilor', 88, 'c', 14, 2, 11),
(42, 8, 'Andrei Saguna', 7, 'a', 22, 2, 12),
(43, 1, 'Independentei', 2, 'a', 12, 2, 13),
(44, 3, 'Castanilor', 9, 'b', 3, 2, 14),
(45, 1, 'Garii', 2, 'a', 17, 2, 15),
(46, 5, 'Toamnei', 77, 'c', 18, 2, 16),
(47, 2, 'Morii', 2, 'a', 19, 4, 17),
(48, 2, 'Soarelui', 2, 'b', 1, 5, 18),
(49, 1, 'Scolii', 2, 'c', 2, 6, 19),
(50, 7, 'Carierei', 99, 'a', 17, 2, 20),
(51, 1, 'Vald Tepes', 8, 'd', 11, 2, 21),
(52, 2, 'Eroilor', 88, 'c', 14, 9, 22),
(53, 8, 'Iuliu Maniu', 7, 'a', 22, 2, 23);
