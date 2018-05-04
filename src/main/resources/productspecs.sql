SET SQL_SAFE_UPDATES = 0;
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
