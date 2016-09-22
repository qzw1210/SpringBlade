package com.smallchill.platform.mapper;

import java.util.List;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.annotatoin.SqlStatementType;
import org.beetl.sql.core.mapper.BaseMapper;

import com.smallchill.platform.model.Notice;

public interface NoticeMapper extends BaseMapper<Notice>{

	public Notice findById(@Param("id") Integer id);
	
	@Sql("select * from tb_tfw_tzgg")
	public List<Notice> selectAll();
	
	@Sql(value=" update tb_tfw_tzgg set f_vc_bt = ? where f_it_xl = ? ", type=SqlStatementType.UPDATE)
	public void updateTitle(String f_vc_bt, int id);
	
}
