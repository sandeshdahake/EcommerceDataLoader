drop table api_category;

CREATE TABLE api_category (
    ID int NOT NULL AUTO_INCREMENT,
    category_id varchar(255) NOT NULL,
    category varchar(255) NOT NULL,
    category_name varchar(255) NOT NULL,
    sub_category varchar(255) NOT NULL,
    sub_category_name varchar(255) ,
    child_category varchar(255) ,
	child_category_name varchar(255) ,
    child_property varchar(255) ,
    can_compare varchar(255) ,
	created_at varchar(255) ,
	updated_at varchar(255) ,
    isLoaded int NOT NULL,
    PRIMARY KEY (ID)
);
ALTER TABLE api_category
ADD UNIQUE INDEX childcat ( child_category ASC);


--truncate api_product;
--truncate api_product_color;
--truncate api_product_images;
--truncate api_product_store;
--truncate api_product_main_specs;
--truncate api_product_sec_specs;

CREATE TABLE api_product (
    ID int NOT NULL AUTO_INCREMENT,
    product_id varchar(255) NOT NULL,
    product_name varchar(1000) NOT NULL,
    product_model varchar(255) NULL,
    product_brand varchar(255)  NULL,
    product_ratings varchar(255)  NULL,
    product_category varchar(255) ,
    product_sub_category varchar(255) ,
	is_available BOOLEAN ,
	is_comparable BOOLEAN ,
    spec_available BOOLEAN ,
	review_available varchar(255) ,
    PRIMARY KEY (ID)
);

CREATE TABLE api_product_color (
    ID int NOT NULL AUTO_INCREMENT,
    product_id varchar(255) NOT NULL,
    color varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE api_product_images (
    ID int NOT NULL AUTO_INCREMENT,
    product_id varchar(255) NOT NULL,
    image_path varchar(700) NOT NULL,
    PRIMARY KEY (ID)
);

ALTER TABLE api_product modify COLUMN product_name varchar(1000) NULL

ALTER TABLE api_product ADD UNIQUE (product_id);


CREATE TABLE api_product_store (
    ID int NOT NULL AUTO_INCREMENT,
    product_id varchar(255) NOT NULL,
    product_store varchar(255) NOT NULL,
    product_store_logo varchar(700) NOT NULL,
    product_store_url varchar(6535) NOT NULL,
    product_price varchar(255),
    product_offer varchar(255),
    product_color varchar(255),
    product_delivery varchar(255),
	product_delivery_cost varchar(255),
    is_emi varchar(255),
    is_cod varchar(255),
    return_time varchar(255),
    PRIMARY KEY (ID)
);


CREATE TABLE api_product_main_specs (
    ID int NOT NULL AUTO_INCREMENT,
    product_id varchar(255) NOT NULL,
    main_specs varchar(700) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE api_product_sec_specs (
    ID int NOT NULL AUTO_INCREMENT,
    product_id varchar(255) NOT NULL,
    category varchar(700) NOT NULL,
	spec_key varchar(700) NOT NULL,
    spec_value varchar(700) NOT NULL,
    PRIMARY KEY (ID)
);
ALTER TABLE `api_load`.`api_product_sec_specs`
ADD INDEX `category` (`category` ASC);

ALTER TABLE api_product_sec_specs CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE api_product CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


CREATE TABLE `api_filters` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ProductSubcategoryName` varchar(200) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `ParentSubcategoryId` int(10) NOT NULL,
  `Index` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `unique` (`ProductSubcategoryName`,`Name`,`Type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `API_filterdetails` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FilterId` int(11) NOT NULL,
  `FilterMinValue` varchar(500) NOT NULL,
  `FilterMaxValue` varchar(500) NOT NULL,
  `Priority` int(11) DEFAULT NULL,
  `Value` varchar(500) NOT NULL,
  PRIMARY KEY (`Id`)
)

CREATE TABLE `api_counter` (
  `create_date` VARCHAR(100) NOT NULL,
  `api_count` INT NOT NULL,
  PRIMARY KEY (`create_date`));

