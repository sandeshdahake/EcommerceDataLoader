SET SQL_SAFE_UPDATES = 0;
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

--INSERT INTO `users` ( `Type`, `FirstName`, `LastName`, `Email`, `LastLoggedIn`, `Password`) VALUES
--( 'Admin', 'Amit', 'Marode', 'amitmarode@gmail.com', '0000-00-00 00:00:00', 'admin123');


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
