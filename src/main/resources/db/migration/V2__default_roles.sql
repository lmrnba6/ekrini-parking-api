INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO users(email, "name","password", username) VALUES('1@1.ca','1111','$2a$10$eSWwc/bX8kHqVRr4/mf6HO5XYRAOIF8s.v0Im1xGTBikgzQCNfdL.','111111');
INSERT INTO user_roles(user_id, role_id) VALUES(1,4);


