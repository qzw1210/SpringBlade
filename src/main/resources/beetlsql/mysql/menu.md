list
===
select 
	m.*,d.name as DIC_STATUS 
from tfw_menu m 
	left join (select num,name from tfw_dict where code=902) d on m.status=d.num