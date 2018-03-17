insert into productcategories (Name,IsActive,status)
select distinct category_name as Name , 1 as isActive, 1 as 'status'
from api_category ac
where category_name not in (select name from productcategories);

insert into productsubcategories(CategoryId,name, label,MetadataFile, CompareMetadataFile )
select distinct b.id CategoryId,child_category name,child_category_name label, 'dummy', 'dummy' from api_category a
inner join productcategories b on a.category_name = b.name
where child_category not in (select name from productsubcategories);

INSERT  INTO products(SubcategoryId,Name,Price,isActive,mspId,InsertedProperties,IsVisited,ImageInserted,Image,IsBestSeller,Popularity,IsExternalUrl,amazonSchemaId,api_product_id)
select distinct  s.id SubcategoryId, product_name name , 0 price, 1 isActive, null mspId, 0 InsertedProperties, 0 IsVisited,
 1 ImageInserted, 'dummy' Image, 0  IsBestSeller, 0 Popularity,0 IsExternalUrl, null amazonSchemaId ,p.product_id
from api_category c
inner join api_product p on c.child_category = p.product_sub_category
inner join productsubcategories s on s.name = p.product_sub_category
where p.product_id not in (select api_product_id from products)


update products p
inner join (select p.api_product_id , min(s.product_price) price from products p
inner join api_product_store s on p.api_product_id = s.product_id
 group by s.product_id ) t on p.api_product_id = t.api_product_id
set p.price =  t.price;

update products p
inner join (select distinct p.api_product_id , s.image_path image from products p
inner join api_product_images s on p.api_product_id = s.product_id
 group by s.product_id  ) t on p.api_product_id = t.api_product_id
set p.image =  t.image;

INSERT IGNORE INTO subcategoryfieldgroups(SubcategoryId,Name)
select  distinct p.SubcategoryId,  s.category name from
(select distinct product_id, category from api_product_sec_specs) s, products p where p.api_product_id = s.product_id

INSERT IGNORE INTO productsubcategoryproperties
(
SubcategoryId,
Label,
Name,
DataType,
Priority,
GroupId,
IsSystemField,
IsAvailableOnCompare,
IsAvailableForUpdate,
IsAvailableForInsert,
GroupName,
IsAvailableOnDetailPage)
select distinct t.SubcategoryId,t.spec_key Label,t.spec_value Name, 'String' DataType,
 1 Priority, p.id GroupId , 0 IsSystemField, 1 IsAvailableOnCompare, 1 IsAvailableForUpdate,
 1 IsAvailableForInsert, p.name GroupName, 1 IsAvailableOnDetailPage from subcategoryfieldgroups p,
 (select distinct s.*, p.SubcategoryId from api_product_sec_specs s , products p
where p.api_product_id = s.product_id ) t
where t.SubcategoryId = p.SubcategoryId and p.name = t.category;


update api_product_sec_specs
set spec_key = replace(spec_key,' (in inches)','');


INSERT IGNORE INTO productspecs
(
ProductId,
category,
property,
value,
columnName,
subCategoryId,
api_product_id)
select distinct p.id as  productid,s.category, s.spec_key property, s.spec_value value, replace(replace(trim(s.spec_key), '  ',' ' ),' ','_') columnName, p.SubcategoryId, p.api_product_id as api_product_id from api_product_sec_specs s , products p
where p.api_product_id = s.product_id;


INSERT IGNORE INTO `productimages`
(
`ProductId`,
`Url`,
`ZoomImageUrl`,
`IsExternalUrl`,
`api_product_id`)
select distinct  p.Id, i.image_path, i.image_path, 1, i.product_id from api_product_images i
inner join products p on i.product_id = p.api_product_id


INSERT INTO `comparetest`.`webstores`
(
`Label`,
`Name`,
`LogoUrl`)
VALUES(
'Flipkart',
'flipkart',
'flipkart.png');

INSERT INTO `comparetest`.`webstores`
(
`Label`,
`Name`,
`LogoUrl`)
VALUES(
'Snapdeal',
'snapdeal',
'snapdeal.png');
INSERT INTO `comparetest`.`webstores`
(
`Label`,
`Name`,
`LogoUrl`)
VALUES(
'Amazon.in',
'amazon',
'amazon.png');

