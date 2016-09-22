sourceList
===
select * from tfw_attach

list
===
select a.*,
	CONCAT('/uploadify/renderFile/',a.ID) ATTACHURL,
	dic.name STATUSNAME,
	u.name CREATERNAME 
from 
	(select ID,CODE,NAME,STATUS,CREATER,CREATETIME from tfw_attach) a 
	left join (select * from tfw_dict where code=902) dic on a.status=dic.num 
	left join tfw_user u on a.creater=u.id