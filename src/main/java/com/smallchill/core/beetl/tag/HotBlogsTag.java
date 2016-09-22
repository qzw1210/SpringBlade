package com.smallchill.core.beetl.tag;

import org.beetl.core.GeneralVarTagBinding;

public class HotBlogsTag extends GeneralVarTagBinding{

	@Override
	public void render() {
		
		//Map<String, Object> paras= (Map<String, Object>) this.args[1];
		/*List<Notice> notices=Notice.dao.findAll();
		if(null!=notices){
			for(Notice notice:notices){
				this.binds(notice);
				this.doBodyRender();
			}
		}*/
	}

}
