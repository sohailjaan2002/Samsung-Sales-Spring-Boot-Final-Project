DROP TABLE IF EXISTS order_options;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS options;
DROP TABLE IF EXISTS batteries;
DROP TABLE IF EXISTS processors;
DROP TABLE IF EXISTS colors;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
  customer_pk int unsigned NOT NULL AUTO_INCREMENT,
  customer_id varchar(40) NOT NULL,
  first_name varchar(45) NOT NULL, 
  last_name varchar(45) NOT NULL,
  phone varchar(20),
  PRIMARY KEY (customer_pk)
);

CREATE TABLE models (
  model_pk int unsigned NOT NULL AUTO_INCREMENT,
  model_id enum('GALAXY_S10', 'GALAXY_S20', 'GALAXY_S21', 'GALAXY_S22', 'GALAXY_ZFold', 'GALAXY_A', 'GALAXY_NOTE') NOT NULL,
  type_model varchar(40) NOT NULL,
  storage_in_GB int NOT NULL,
  screen_size int NOT NULL,
  launch_price decimal(9, 2) NOT NULL,
  PRIMARY KEY (model_pk),
  UNIQUE KEY (model_id, type_model, storage_in_GB)
);

CREATE TABLE images (
  image_pk int unsigned NOT NULL AUTO_INCREMENT,
  model_fk int unsigned NOT NULL,
  image_id varchar(40) NOT NULL,
  width int NOT NULL,
  height int NOT NULL,
  mime_type enum('image/jpeg', 'image/png'),
  name varchar(256),
  data mediumblob NOT NULL,
  PRIMARY KEY (image_pk),
  FOREIGN KEY (model_fk) REFERENCES models (model_pk)
);

CREATE TABLE colors (
  color_pk int unsigned NOT NULL AUTO_INCREMENT,
  color_id varchar(30) NOT NULL,
  color varchar(60) NOT NULL,
  price decimal(9, 2) NOT NULL,
  is_exterior boolean NOT NULL,
  PRIMARY KEY (color_pk),
  UNIQUE KEY (color_id)
);

CREATE TABLE processors (
  processor_pk int unsigned NOT NULL AUTO_INCREMENT,
  processor_id varchar(30) NOT NULL,
  size_in_nm decimal(5, 2) NOT NULL,
  name varchar(60) NOT NULL,
  processor_type enum('DUAL', 'QUAD', 'OCTA') NOT NULL,
  speed_in_ghz decimal(7, 2) NOT NULL,
  speed_in_mhz decimal(7, 2) NOT NULL,
  has_start_stop boolean NOT NULL,
  description varchar(500) NOT NULL,
  price decimal(9, 2),
  PRIMARY KEY (processor_pk),
  UNIQUE KEY (processor_id)
);

CREATE TABLE batteries (
  battery_pk int unsigned NOT NULL AUTO_INCREMENT,
  battery_id varchar(30) NOT NULL, 
  battery_capacity varchar(128) NOT NULL,
  manufacturer varchar(70) NOT NULL,
  price decimal(7, 2) NOT NULL,
  warranty_days int NOT NULL,
  PRIMARY KEY (battery_pk),
  UNIQUE KEY (battery_id)
);

CREATE TABLE options (
  option_pk int unsigned NOT NULL AUTO_INCREMENT,
  option_id varchar(30) NOT NULL,
  category enum('RAM', 'DISPLAY', 'PROTECTOR', 'CHARGER', 'CASE', 'HEADPHONE') NOT NULL,
  manufacturer varchar(60) NOT NULL,
  name varchar(60) NOT NULL,
  price decimal(9, 2) NOT NULL,
  PRIMARY KEY (option_pk),
  UNIQUE KEY (option_id)
);

CREATE TABLE orders (
  order_pk int unsigned NOT NULL AUTO_INCREMENT,
  customer_fk int unsigned NOT NULL,
  color_fk int unsigned NOT NULL,
  processor_fk int unsigned NOT NULL,
  battery_fk int unsigned NOT NULL,
  model_fk int unsigned NOT NULL,
  price decimal(9, 2) NOT NULL,
  PRIMARY KEY (order_pk),
  FOREIGN KEY (customer_fk) REFERENCES customers (customer_pk) ON DELETE CASCADE,
  FOREIGN KEY (color_fk) REFERENCES colors (color_pk) ON DELETE CASCADE,
  FOREIGN KEY (processor_fk) REFERENCES processors (processor_pk) ON DELETE CASCADE,
  FOREIGN KEY (battery_fk) REFERENCES batteries (battery_pk) ON DELETE CASCADE,
  FOREIGN KEY (model_fk) REFERENCES models (model_pk) ON DELETE CASCADE
);

CREATE TABLE order_options (
  option_fk int unsigned NOT NULL,
  order_fk int unsigned NOT NULL,
  FOREIGN KEY (option_fk) REFERENCES options (option_pk) ON DELETE CASCADE,
  FOREIGN KEY (order_fk) REFERENCES orders (order_pk) ON DELETE CASCADE
);