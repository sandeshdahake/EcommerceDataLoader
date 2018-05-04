SET SQL_SAFE_UPDATES = 0;

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