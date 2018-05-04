SET SQL_SAFE_UPDATES = 0;
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

