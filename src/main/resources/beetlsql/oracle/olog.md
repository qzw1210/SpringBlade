sourceList
===
select * from tfw_operation_log

list
===
select l.*,
	u.NAME USERNAME,
	(CASE WHEN l.SUCCEED=1 THEN '成功' else '失败' end) SUCCEEDNAME 
from 
	TFW_OPERATION_LOG l 
	LEFT JOIN TFW_USER u on l.USERID=u.ID