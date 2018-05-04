SET SQL_SAFE_UPDATES = 0;

update `api_product_sec_specs`
set column_name = substring(replace(replace(trim(replace(spec_key, '\n' ,'')), '  ',' ' ),' ','_'), 1,60) ,
column_name_flag = 1
where column_name is null;

update api_product_sec_specs
set column_name = replace(column_name,' (in inches)','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'\r','')
where column_name_flag = 1;


update api_product_sec_specs
set column_name = replace(column_name,'-','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'|','')
where column_name_flag = 1;


update api_product_sec_specs
set column_name = replace(column_name,'Primary','Primary_value')
where column_name= 'Primary'
and column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'Type','type_value')
where column_name = 'Type'
and column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'(','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,')','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'/','_')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'.','_')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'\\','_')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'&','_')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,':','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'*','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'\^','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'%','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'$','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'#','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'\?','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'@','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'!','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'~','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'"','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'"','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'>','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'<','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'}','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'{','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'[','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,']','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'"','')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = TRIM(CHAR(9) FROM TRIM(column_name))
where column_name_flag = 1;


update api_product_sec_specs
set column_name = TRIM(TRAILING '_' FROM column_name)
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'___','_')
where column_name_flag = 1;

update api_product_sec_specs
set column_name = replace(column_name,'__','_')
where column_name_flag = 1;

update api_product_sec_specs
set column_name_flag = 2
where column_name_flag = 1;

update api_category
set category_name = replace(category_name,'&','')
WHERE category_name like '%&%'
and category_name not in (select name from productcategories);

update api_category
set category_name = replace(category_name,',',' ')
WHERE category_name like '%,%'
and category_name not in (select name from productcategories);

insert into productcategories (Name,IsActive,status)
select distinct category_name as Name , 1 as isActive, 1 as 'status'
from api_category ac
where category_name not in (select name from productcategories);

--update `productcategories` set name = replace(name, '&', '') WHERE name like '%&%';
--update `productcategories` set name = replace(name, ',', ' ') WHERE name like '%,%';
insert into productsubcategories(CategoryId,name, label,MetadataFile, CompareMetadataFile )
select distinct b.id CategoryId,child_category as name,child_category_name label, 'dummy', 'dummy' from api_category a
inner join productcategories b on a.category_name = b.name
where child_category not in (select name from productsubcategories);

INSERT  INTO products(SubcategoryId,Name,Price,isActive,mspId,InsertedProperties,IsVisited,ImageInserted,Image,IsBestSeller,Popularity,IsExternalUrl,amazonSchemaId,api_product_id)
select distinct  s.id SubcategoryId, product_name as name , 0 price, 1 isActive, null mspId, 0 InsertedProperties, 0 IsVisited,
 1 ImageInserted, 'dummy' Image, 0  IsBestSeller, 0 Popularity,0 IsExternalUrl, null amazonSchemaId ,p.product_id
from api_category c
inner join api_product p on c.child_category = p.product_sub_category
inner join productsubcategories s on s.name = p.product_sub_category
where p.product_id not in (select api_product_id from products);


update products p
inner join (select p.api_product_id , min(s.product_price) price from products p
inner join api_product_store s on p.api_product_id = s.product_id
 group by s.product_id ) t on p.api_product_id = t.api_product_id
set p.price =  t.price;


-- for updating products table with downloaded image
update products p
inner join (select distinct p.api_product_id , s.Image_local_path image from products p
inner join api_product_images s on p.api_product_id = s.product_id
where p.image = 'dummy'
 group by s.product_id  ) t on p.api_product_id = t.api_product_id
set p.image =  t.image , IsExternalUrl=0;
where p.image = 'dummy'

update products set isActive = 0 where price = 0 or price is null;



INSERT IGNORE INTO subcategoryfieldgroups(SubcategoryId,Name)
select  distinct p.SubcategoryId,  s.category as name from
(select distinct product_id, category from api_product_sec_specs) s, products p where p.api_product_id = s.product_id;
--and (p.SubcategoryId,  s.category) not in (select distinct SubcategoryId , Name from subcategoryfieldgroups)
 ;

INSERT IGNORE INTO productspecs
(
ProductId,
category,
property,
value,
columnName,
subCategoryId,
api_product_id)
select distinct p.id as  productid,s.category, s.spec_key property, s.spec_value as value, column_name as columnName, p.SubcategoryId, p.api_product_id as api_product_id from api_product_sec_specs s , products p
where p.api_product_id = s.product_id  and trim(s.column_name) != '';
--and  ( s.category , s.spec_key , s.product_id ) not in (select distinct category , property , api_product_id  from  productspecs);




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
select distinct t.SubcategoryId,t.spec_key Label,t.column_name as Name, 'String' DataType,
 1 Priority, p.id GroupId , 0 IsSystemField, 1 IsAvailableOnCompare, 1 IsAvailableForUpdate,
 1 IsAvailableForInsert, p.name GroupName, 1 IsAvailableOnDetailPage from subcategoryfieldgroups p,
 (select distinct s.*, p.SubcategoryId from api_product_sec_specs s , products p
where p.api_product_id = s.product_id and trim(s.column_name) != '') t
where t.SubcategoryId = p.SubcategoryId and p.name = t.category ;



INSERT IGNORE INTO `productimages`
(
`ProductId`,
`Url`,
`ZoomImageUrl`,
`IsExternalUrl`,
`api_product_id`)
select distinct  p.Id, i.image_local_path, i.image_local_path, 0, i.product_id from api_product_images i
inner join products p on i.product_id = p.api_product_id;


-- for updating productImage table with downloaded image
/*
update productimages p
inner join (select  p.api_product_id , s.Image_local_path image , s.image_path from productimages p
inner join api_product_images s on p.api_product_id = s.product_id
and s.image_path  = p.Url) t on p.api_product_id = t.api_product_id and t.image_path  = p.Url
set p.Url =  t.image ,p.ZoomImageUrl =  t.image , IsExternalUrl=0;
*/

insert ignore into productwebstores (ProductId, WebstoreLabel,WebstoreName,WebstoreProductId,api_product_id)
select distinct p.id , w.name, w.name, getwebStoreProductId( w.name , s.product_store_url ),p.api_product_id from api_product_store s
inner join products p on p.api_product_id = s.product_id
inner join  webstores w on w.label = s.product_store
where p.id not in (select distinct ProductId from productwebstores);

INSERT IGNORE INTO `filters`
(
`Name`,
`Type`,
`ParentSubcategoryId`,
`ProductPropertyName`,
`Index`,
api_filter_id
)
select f.name, case when f.type='slider' then 'Range' else 'Fixed' end , c.Id,f.Name, 1, f.id  from api_filters f
inner join productsubcategories c on f.ProductSubcategoryName = c.Name
where f.name in ('Brand')
 order by f.id asc;

INSERT IGNORE INTO `filters`
(
`Name`,
`Type`,
`ParentSubcategoryId`,
`ProductPropertyName`,
`Index`,
api_filter_id
)
select f.name, case when f.type='slider' then 'Range' else 'Fixed' end , c.Id,'price', 1, f.id  from api_filters f
inner join productsubcategories c on f.ProductSubcategoryName = c.Name
where f.name in ('Price')
order by f.id asc;

INSERT IGNORE INTO `filterdetails`
(
`FilterId`,
`FilterMinValue`,
`FilterMaxValue`,
`Priority`,
`Value`)
select f.Id, fd.FilterMinValue, fd.FilterMaxValue, fd.Priority, fd.Value from API_filterdetails fd inner join filters f on fd.FilterId = f.api_filter_id;

insert into userratings (ProductId , UserId,Rating)
SELECT id , 3,'3.00' FROM `products`
where id not in (SELECT distinct ProductId FROM `userratings`);

INSERT INTO `users` ( `Type`, `FirstName`, `LastName`, `Email`, `LastLoggedIn`, `Password`) VALUES
( 'Admin', 'Amit', 'Marode', 'amitmarode@gmail.com', '0000-00-00 00:00:00', 'admin123');


set @num := 0;
set @subCategoryId := '';
 insert into subcategoryhotproperties (SubcategoryId, PropertyName, Priority)
select  subCategoryId , columnName , row_number from (
Select   subCategoryId , category , columnName ,  @num := IF( @subCategoryId= subCategoryId, @num +1, 1 ) AS row_number,
  @subCategoryId := subCategoryId AS dummy
  from (
SELECT distinct subCategoryId , category , columnName
From productspecs
where category like '%General F%' and subCategoryId not in (select distinct SubcategoryId from subcategoryhotproperties)
      ) as d
    )as t where row_number <= 5
ORDER BY `t`.`row_number`  DESC;



-- below query has to be generated for each subcategory

set @num := 0;
set @productId := '';
update products p1
  inner join (
SELECT product_id , Image_local_path from (
select  product_id , Image_local_path  ,
@num := IF( @productId= product_id, @num +1, 1 ) AS row_number,
  @productId := product_id AS dummyProductId
from api_product_images
) d  , products p  where p.api_product_id = d.product_id and row_number =1 and p.image = 'dummy')
d1 on p1.api_product_id = d1.product_id
set p1.image =  d1.Image_local_path , IsExternalUrl=0
where p1.image = 'dummy';




update productimages p
inner join (select  p.api_product_id , s.Image_local_path image , s.image_path from productimages p
inner join api_product_images s on p.api_product_id = s.product_id
and s.image_path  = p.Url) t on p.api_product_id = t.api_product_id and t.image_path  = p.Url
set p.Url =  t.image ,p.ZoomImageUrl =  t.image , IsExternalUrl=0;




update mobiles m inner join products p on p.id=m.ProductId and  m.Brand is null
set m.Brand = IFNULL(m.Brand , substring(p.name, 1,position(' ' IN p.name) - 1) );



SET @updateBrand_SQL = '';
SELECT
 GROUP_CONCAT(DISTINCT
   CONCAT(
       'update  `',Name,'`m inner join products p on p.id=m.ProductId and  m.Brand is null set m.Brand = IFNULL(m.Brand , substring(p.name, 1,position(' ' IN p.name) - 1) );'

   )
 )
into @updateBrand_SQL from
(

 SELECT distinct sub.Name as `Name`
 FROM productspecs ps , productsubcategories as sub
where ps.subCategoryId=subCategoryIdIn and ps.subCategoryId = sub.id
and ps.subCategoryId in ( select distinct SubCategoryId
from  productsubcategoryproperties WHERE name = 'Brand' or name ='brand' )
) d;
SELECT @updateBrand_SQL;
