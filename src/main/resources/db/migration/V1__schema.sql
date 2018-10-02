CREATE SEQUENCE users_seq;

CREATE TABLE users (
  id bigint NOT NULL DEFAULT NEXTVAL ('users_seq'),
  name varchar(40) NOT NULL,
  username varchar(15) NOT NULL,
  email varchar(40) NOT NULL,
  password varchar(100) NOT NULL,
  created_at timestamp(0) DEFAULT NULL,
  updated_at timestamp(0) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_users_username UNIQUE  (username),
  CONSTRAINT uk_users_email UNIQUE  (email)
) ;


CREATE SEQUENCE roles_seq;

CREATE TABLE roles (
  id bigint NOT NULL DEFAULT NEXTVAL ('roles_seq'),
  name varchar(60) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_roles_name UNIQUE  (name)
)  ;

ALTER SEQUENCE roles_seq RESTART WITH 4;


CREATE TABLE user_roles (
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (user_id,role_id),
  CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ;

CREATE SEQUENCE positions_seq;

CREATE TABLE positions (
  id bigint NOT NULL DEFAULT NEXTVAL ('positions_seq'),
  latitude varchar(100),
  longitude varchar  NOT NULL,
  created_at timestamp(0) DEFAULT NULL,
  updated_at timestamp(0) DEFAULT NULL,


  PRIMARY KEY (id)
) ;

CREATE SEQUENCE addresses_seq;

CREATE TABLE addresses (
  id bigint NOT NULL DEFAULT NEXTVAL ('addresses_seq'),
  address_One_line varchar(100),
  city varchar(100) NOT NULL,
  state varchar(100)  NOT NULL,
  zip varchar(100) NOT NULL,
  position_id bigint NOT NULL,
  created_at timestamp(0) DEFAULT NULL,
  updated_at timestamp(0) DEFAULT NULL,

  PRIMARY KEY (id),
  CONSTRAINT fk_address_position_id FOREIGN KEY (position_id) REFERENCES positions (id)
) ;

CREATE SEQUENCE parkings_seq;

CREATE TABLE parkings (
  id bigint NOT NULL DEFAULT NEXTVAL ('parkings_seq'),
  number varchar(10),
  size varchar(10)  NOT NULL,
  price decimal NOT NULL,
  comment varchar(100) NOT NULL,
  date date NOT NULL,
  time_start timestamp(0) NOT NULL,
  time_end timestamp(0) NOT NULL,
  recurrence varchar(20) NOT NULL,
  address_id bigint NOT NULL,
  user_id bigint NOT NULL,
  created_at timestamp(0) DEFAULT NULL,
  updated_at timestamp(0) DEFAULT NULL,

  PRIMARY KEY (id),
  CONSTRAINT fk_parking_address_id FOREIGN KEY (address_id) REFERENCES addresses (id),
  CONSTRAINT fk_parking_user_id FOREIGN KEY (user_id) REFERENCES users (id)
) ;


CREATE INDEX fk_user_roles_role_id ON user_roles (role_id);

