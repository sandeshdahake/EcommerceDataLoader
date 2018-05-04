SET SQL_SAFE_UPDATES = 0;

update `api_product_sec_specs`
set column_name = substring(replace(replace(trim(replace(spec_key, '\n' ,'')), '  ',' ' ),' ','_'), 1,60),
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