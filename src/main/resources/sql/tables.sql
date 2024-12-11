CREATE TABLE IF NOT EXISTS customer (
      customer_id SERIAL PRIMARY KEY,
      customer_name VARCHAR(255),
      address VARCHAR(255),
      zipcode VARCHAR(4),
      city VARCHAR(255),
      phone_number VARCHAR(12),
      email VARCHAR(255),
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table IF NOT EXISTS account (
    user_id serial primary key,
    user_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    user_password VARCHAR,
    is_admin boolean DEFAULT false
);
DROP TABLE IF EXISTS carport_material_function CASCADE;
DROP TABLE IF EXISTS material_function CASCADE;
DROP TABLE IF EXISTS carport_material CASCADE;
DROP TABLE IF EXISTS carport_orderlines CASCADE;
DROP TABLE IF EXISTS carport_order CASCADE;

CREATE TYPE OrderStatus AS ENUM(    'UNASSIGNED',
    'ASSIGNED',
    'CALCULATING',
    'OFFER_SENT',
    'OFFER_ACCEPTED',
    'OFFER_REJECTED',
    'PAYMENT_REQUESTED',
    'PAID',
    'COMPLETED',
    'ERROR');

CREATE TABLE carport_order (
       order_id SERIAL PRIMARY KEY,
       customer_id INT NOT NULL,
       sales_id INT NULL DEFAULT NULL,
       carport_width INT NOT NULL,
       carport_length INT NOT NULL,
       carport_height INT NOT NULL,
       carport_shed BOOLEAN NOT NULL,
       shed_width INT NULL,
       shed_length INT NULL,
       carport_roof VARCHAR NOT NULL,
       is_paid BOOLEAN DEFAULT false,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       order_status OrderStatus DEFAULT 'UNASSIGNED',
       CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,
       CONSTRAINT fk_sales FOREIGN KEY (sales_id) REFERENCES account(user_id) ON DELETE SET NULL
);
CREATE TABLE carport_material (
       material_id SERIAL PRIMARY KEY,
       material_name VARCHAR(255),
       width int,
       height int,
       length int,
       fog_id varchar(64),
       fog_price int
);
CREATE TABLE material_function (
    function_id SERIAL PRIMARY KEY,
    description VARCHAR(255)
);
CREATE TABLE carport_material_function (
   material_id INT NOT NULL,
   function_id INT NOT NULL,
   PRIMARY KEY (material_id, function_id),
   CONSTRAINT fk_material FOREIGN KEY (material_id) REFERENCES carport_material(material_id) ON DELETE CASCADE,
   CONSTRAINT fk_function FOREIGN KEY (function_id) REFERENCES material_function(function_id) ON DELETE CASCADE
);

CREATE TABLE carport_orderlines (
    line_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    material_id INT NOT NULL,
    quantity INT NOT NULL,
    description INT NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES carport_order(order_id) ON DELETE CASCADE,
    CONSTRAINT fk_material FOREIGN KEY (material_id) REFERENCES carport_material(material_id) ON DELETE CASCADE,
    CONSTRAINT fk_function FOREIGN KEY (description) REFERENCES material_function(function_id) ON DELETE RESTRICT
);
