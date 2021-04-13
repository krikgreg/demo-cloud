CREATE TABLE products (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  code varchar(20) NOT NULL,
  name varchar(100) NOT NULL,
  description varchar(200) DEFAULT NULL,
  price decimal(17,2) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE products ADD CONSTRAINT UK_product_code UNIQUE (code);

INSERT INTO products(code, name, description, price) values ('bmw1', 'bmw 1', 'bmw 1', 10000);
INSERT INTO products(code, name, description, price) values ('bmw3', 'bmw 3', 'bmw 3', 20000);
INSERT INTO products(code, name, description, price) values ('bmw5', 'bmw 5', 'bmw 5', 30000);
INSERT INTO products(code, name, description, price) values ('audiA1', 'audi A1', 'audi A1', 10000);
INSERT INTO products(code, name, description, price) values ('audiA3', 'audi A3', 'audi A3', 20000);
INSERT INTO products(code, name, description, price) values ('audiA4', 'audi A4', 'audi A4', 30000);
INSERT INTO products(code, name, description, price) values ('renaultClio', 'renault Clio', 'renault Clio', 10000);
INSERT INTO products(code, name, description, price) values ('renaultMegane', 'renault Megane', 'renault Megane', 20000);
INSERT INTO products(code, name, description, price) values ('renaultCapture', 'renault Capture', 'renault Capture', 30000);

CREATE TABLE categories (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  code varchar(20) NOT NULL UNIQUE,
  name varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO categories(code, name) values ('bmw', 'BMW');
INSERT INTO categories(code, name) values ('audi', 'Audi');
INSERT INTO categories(code, name) values ('renault', 'Renault');

CREATE TABLE products2categories (
  product_code varchar(20) NOT NULL,
  category_code varchar(20) NOT NULL,
  primary key (product_code, category_code),
  FOREIGN KEY (product_code) REFERENCES products(code),
  FOREIGN KEY (category_code) REFERENCES categories(code)
);

INSERT INTO products2categories(category_code, product_code) values ('bmw', 'bmw1');
INSERT INTO products2categories(category_code, product_code) values ('bmw', 'bmw3');
INSERT INTO products2categories(category_code, product_code) values ('bmw', 'bmw5');
INSERT INTO products2categories(category_code, product_code) values ('audi', 'audiA1');
INSERT INTO products2categories(category_code, product_code) values ('audi', 'audiA3');
INSERT INTO products2categories(category_code, product_code) values ('audi', 'audiA4');
INSERT INTO products2categories(category_code, product_code) values ('renault', 'renaultClio');
INSERT INTO products2categories(category_code, product_code) values ('renault', 'renaultMegane');
INSERT INTO products2categories(category_code, product_code) values ('renault', 'renaultCapture');

