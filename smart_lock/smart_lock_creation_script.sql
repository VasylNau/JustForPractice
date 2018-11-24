CREATE DATABASE smart_lock;

USE smart_lock;

CREATE TABLE user (
	id INT NOT NULL,
	name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50),
    language VARCHAR(20),
    deleted BIT(1),
    active BIT(1),
    locked BIT(1),
    PRIMARY KEY (id)
);

CREATE TABLE role (
	id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE user_role (
	id INT NOT NULL,
    user_id INT NOT NULL,
	role_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE fingerprint (
	id INT NOT NULL,
	fingerprint BLOB NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE `lock`(
	id INT NOT NULL,
    name VARCHAR(255),
    description VARCHAR(1024),
    active BIT(1),
    deleted BIT(1),
    PRIMARY KEY (id)
);


CREATE TABLE access_level (
	id INT NOT NULL,
	name VARCHAR (50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_lock (
	id INT NOT NULL,
	user_id INT NOT NULL,
    lock_id INT NOT NULL,
    access_level_id	INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (lock_id) REFERENCES `lock`(id),
    FOREIGN KEY (access_level_id) REFERENCES access_level(id)
);

CREATE TABLE unlocking_history (
	id INT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
	ip_address VARCHAR(11),
    user_id INT NOT NULL,
    lock_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (lock_id) REFERENCES `lock`(id)
);


