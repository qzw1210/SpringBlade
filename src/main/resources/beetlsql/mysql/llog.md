sourceList
===
select * from tfw_login_log

list
===
select l.*,
	u.NAME USERNAME,
	(CASE WHEN l.SUCCEED=1 THEN '成功' else '失败' end) SUCCEEDNAME 
from 
	TFW_LOGIN_LOG l 
	LEFT JOIN TFW_USER u on l.USERID=u.ID