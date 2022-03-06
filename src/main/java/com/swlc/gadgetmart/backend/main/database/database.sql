drop
DATABASE gadgetMart;

CREATE
DATABASE gadgetMart;

USE gadgetMart;


CREATE TABLE userLogin
(
    userId  int          NOT NULL AUTO_INCREMENT,
    name     VARCHAR(500) NOT NULL,
    userType VARCHAR(10)  NOT NULL,
    userName VARCHAR(150) NOT NULL,
    password VARCHAR(100) NOT NULL,
    address  VARCHAR(150) NOT NULL,
    contact  VARCHAR(11)  NOT NULL,
    email    VARCHAR(150) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE (userName)
)ENGINE=InnoDB;

insert into userlogin
values (0, 'Alex ', 'Administrator', 'admin', '12345', 'Kalutara', '0775071012', 'test@gmail.com');

CREATE TABLE orders
(
    orderId      int(10) NOT NULL AUTO_INCREMENT,
    userId       int(10) NOT NULL,
    address       VARCHAR(220) NOT NULL,
    contact       VARCHAR(11)  NOT NULL,
    totalCost     double       NOT NULL,
    status        VARCHAR(11)  NOT NULL DEFAULT 'PENDING',
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (userId) REFERENCES userLogin (userId) on update cascade on delete cascade
) ENGINE=INNODB;

CREATE TABLE orderDetail
(
    orderDetailId int(10) NOT NULL AUTO_INCREMENT,
    orderId        int(10) NOT NULL,
    name            VARCHAR(225) NOT NULL,
    description     VARCHAR(225) NOT NULL,
    image           VARCHAR(225) NOT NULL,
    price           DOUBLE       NOT NULL,
    deliveryCost    DOUBLE       NOT NULL,
    brand           VARCHAR(225) NOT NULL,
    category        VARCHAR(225) NOT NULL,
    discount        int(10) NOT NULL,
    shop            VARCHAR(225) NOT NULL,
    soldOut         BOOL         NOT NULL,
    warranty        VARCHAR(225) NOT NULL,
    qty             int (10) NOT NULL,
    CONSTRAINT PRIMARY KEY (orderDetailId),
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES orders (orderId) on update cascade on delete cascade
) ENGINE=INNODB;



