
CREATE TABLE productcategories (
  Id int(20) NOT NULL AUTO_INCREMENT,
  Name varchar(200) NOT NULL,
  IsActive tinyint(1) DEFAULT '1',
  status int(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (Id)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- ALTER TABLE productcategories
-- ADD UNIQUE INDEX category_id_unique (api_category_id ASC);


CREATE TABLE productsubcategories (
  Id int(11) NOT NULL AUTO_INCREMENT,
  CategoryId int(11) NOT NULL,
  Name varchar(200) NOT NULL,
  Label varchar(500) NOT NULL,
  MetadataFile varchar(500) NOT NULL,
  CompareMetadataFile varchar(500) NOT NULL,
  amazonSchemaId int(11) DEFAULT NULL,
  api_sub_category varchar(255) ,
  PRIMARY KEY (Id),
  KEY CategoryId (CategoryId),
  CONSTRAINT Subcategory Master FOREIGN KEY (CategoryId) REFERENCES productcategories (Id)
) ;
-- ALTER TABLE productsubcategories
-- ADD UNIQUE INDEX api_sub_category_unique (api_sub_category ASC);

CREATE TABLE products (
  Id int(10) NOT NULL AUTO_INCREMENT,
  SubcategoryId int(11) NOT NULL,
  Name varchar(600) NOT NULL,
  Price decimal(10,2) NOT NULL,
  isActive tinyint(1) NOT NULL DEFAULT '1',
  mspId varchar(20) DEFAULT NULL,
  InsertedProperties int(11) NOT NULL,
  IsVisited tinyint(1) NOT NULL DEFAULT '0',
  ImageInserted tinyint(4) NOT NULL,
  Image varchar(1000) NOT NULL,
  IsBestSeller tinyint(4) NOT NULL,
  Popularity int(11) NOT NULL,
  IsExternalUrl tinyint(1) DEFAULT '0',
  amazonSchemaId int(11) DEFAULT NULL,
  api_product_id varchar(255) NOT NULL,
  PRIMARY KEY (Id),
  KEY SubcategoryId (SubcategoryId)
) ;
ALTER TABLE `products`
ADD INDEX `api_product_id` (`api_product_id` ASC);

-- ALTER TABLE products ADD UNIQUE (api_product_id);




CREATE TABLE productsubcategoryproperties (
  Id int(11) NOT NULL AUTO_INCREMENT,
  SubcategoryId int(11) NOT NULL,
  Label varchar(200) NOT NULL,
  Name varchar(200) NOT NULL,
  DataType varchar(200) NOT NULL,
  Priority int(11) DEFAULT NULL,
  GroupId int(11) NOT NULL,
  IsSystemField tinyint(1) NOT NULL,
  IsAvailableOnCompare tinyint(1) NOT NULL,
  IsAvailableForUpdate tinyint(1) NOT NULL,
  IsAvailableForInsert tinyint(1) NOT NULL,
  GroupName varchar(500) DEFAULT NULL,
  IsAvailableOnDetailPage tinyint(1) NOT NULL DEFAULT '1',
  api_sub_category varchar(255) ,
  PRIMARY KEY (Id),
  KEY SuncategoryId (SubcategoryId),
  KEY Name (Name),
  CONSTRAINT ParentSubcategoryProp FOREIGN KEY (SubcategoryId) REFERENCES productsubcategories (Id) ON DELETE NO ACTION ON UPDATE CASCADE
)

ALTER TABLE `productsubcategoryproperties`
ADD UNIQUE INDEX `unique` (`SubcategoryId` ASC, `Label` ASC, `GroupId` ASC);


CREATE TABLE subcategoryfieldgroups (
  Id int(11) NOT NULL AUTO_INCREMENT,
  SubcategoryId int(11) NOT NULL,
  Name varchar(500) NOT NULL,
  PRIMARY KEY (Id)
) ENGINE=InnoDB AUTO_INCREMENT=1205 DEFAULT CHARSET=latin1;

ALTER TABLE subcategoryfieldgroups
ADD UNIQUE INDEX api_sub_category_field_unique (SubcategoryId , Name);


CREATE TABLE subcategoryhotproperties (
  Id int(11) NOT NULL AUTO_INCREMENT,
  SubcategoryId int(11) NOT NULL,
  PropertyName varchar(500) NOT NULL,
  Priority int(11) NOT NULL,
  AppendText varchar(500) NOT NULL,
  PrependText varchar(500) NOT NULL,
  api_sub_category varchar(255) ,
  PRIMARY KEY (Id),
  KEY SubcategoryId (SubcategoryId)
) ENGINE=InnoDB AUTO_INCREMENT=1526 DEFAULT CHARSET=latin1;



CREATE TABLE productspecs (
  id int(11) NOT NULL AUTO_INCREMENT,
  ProductId int(11) NOT NULL,
  category varchar(600) NOT NULL,
  property varchar(500) NOT NULL,
  value text NOT NULL,
  columnName varchar(500) NOT NULL,
  subCategoryId int(11) NOT NULL,
  api_product_id varchar(255) NULL,
  PRIMARY KEY (id),
  KEY ProductSpecs_products_Id_fk (ProductId),
  CONSTRAINT ProductSpecs_products_Id_fk FOREIGN KEY (ProductId) REFERENCES products (Id)
) ENGINE=InnoDB AUTO_INCREMENT=526653 DEFAULT CHARSET=latin1;

ALTER TABLE productspecs
ADD UNIQUE INDEX unique (category ASC, property ASC, api_product_id ASC);

CREATE TABLE productimages (
  Id int(11) NOT NULL AUTO_INCREMENT,
  ProductId int(11) NOT NULL,
  Url varchar(500) NOT NULL,
  ZoomImageUrl varchar(500) NOT NULL,
  IsExternalUrl tinyint(1) DEFAULT '0',
  api_product_id varchar(255) NULL,
  PRIMARY KEY (Id),
  KEY ProductId (ProductId),
  CONSTRAINT ParentProductId FOREIGN KEY (ProductId) REFERENCES products (Id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=77055 DEFAULT CHARSET=latin1;

ALTER TABLE `productimages`
ADD UNIQUE INDEX `unique` (`api_product_id` ASC, `Url` ASC);

CREATE TABLE productwebstores (
  Id int(11) NOT NULL AUTO_INCREMENT,
  ProductId int(11) NOT NULL,
  WebstoreLabel varchar(500) NOT NULL,
  WebstoreName varchar(500) NOT NULL,
  WebstoreProductId varchar(500) NOT NULL,
  PRIMARY KEY (Id)
) ENGINE=InnoDB AUTO_INCREMENT=84961 DEFAULT CHARSET=latin1;

CREATE TABLE reviews (
  Id int(11) NOT NULL AUTO_INCREMENT,
  ProductId int(11) NOT NULL,
  Review text NOT NULL,
  Title varchar(500) NOT NULL,
  PRIMARY KEY (Id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE webstores (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Label varchar(500) NOT NULL,
  Name varchar(500) NOT NULL,
  LogoUrl varchar(500) NOT NULL,
  PRIMARY KEY (Id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE filters (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(100) NOT NULL,
  Type varchar(20) NOT NULL,
  ParentSubcategoryId int(10) NOT NULL,
  ProductPropertyName varchar(200) NOT NULL,
  Index int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (Id),
  KEY ParentSubcategoryId (ParentSubcategoryId),
  KEY ProductPropertyName (ProductPropertyName)
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=latin1;

CREATE TABLE filterdetails (
  Id int(11) NOT NULL AUTO_INCREMENT,
  FilterId int(11) NOT NULL,
  FilterMinValue varchar(500) NOT NULL,
  FilterMaxValue varchar(500) NOT NULL,
  Priority int(11) DEFAULT NULL,
  Value varchar(500) NOT NULL,
  PRIMARY KEY (Id)
) ENGINE=InnoDB AUTO_INCREMENT=2549 DEFAULT CHARSET=latin1;


