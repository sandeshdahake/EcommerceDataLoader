SET SQL_SAFE_UPDATES = 0;
update api_category
set category_name = replace(category_name,'&','')
WHERE category_name like '%&%'
and category_name not in (select name from productcategories);

update api_category
set category_name = replace(category_name,',',' ')
WHERE category_name like '%,%'
and category_name not in (select name from productcategories);