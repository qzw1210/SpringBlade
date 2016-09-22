package com.smallchill.core.beetl;

import java.util.List;

import org.beetl.sql.core.InterceptorContext;
import org.beetl.sql.ext.DebugInterceptor;

import com.smallchill.core.constant.Cst;
import com.smallchill.core.toolbox.kit.DateKit;

/**
 * @title 重写beetlsql输出的sql语句格式
 * @author zhuangqian
 * @email smallchill@163.com
 * @date 2016-1-28下午5:01:02
 * @copyright 2016
 */
public class ReportInterceptor extends DebugInterceptor {
	
	@Override
	public void before(InterceptorContext ctx) {
		if(!Cst.me().isDevMode()){
			return;
		}
		String sqlId = ctx.getSqlId();
		if(this.isDebugEanble(sqlId)){
			ctx.put("debug.time", System.currentTimeMillis());
		}
	
		print(sqlId,ctx.getSql(),ctx.getParas());
		RuntimeException ex = new  RuntimeException();
		StackTraceElement[] traces = ex.getStackTrace();
		boolean found = false ;
		for(StackTraceElement tr:traces){
			if(!found&&tr.getClassName().indexOf("SQLManager")!=-1){
				found = true ;
			}
			if(found&&!tr.getClassName().startsWith("org.beetl.sql.core")&&!tr.getClassName().startsWith("com.sun")){
				//startwith("com.sun"),proxy call,please refer to MapperJava Proxy since beetlsql 2.0
				//found 
				String className = tr.getClassName();
				String mehodName = tr.getMethodName();
				int line = tr.getLineNumber();
				println("location : " + className + "." + mehodName + "(" + tr.getFileName() + ":" + line + ")");
				break ;
			}
		}
		return ;
	}
	
	@Override
	public void after(InterceptorContext ctx) {
		if(!Cst.me().isDevMode()){
			return;
		}
		long time = System.currentTimeMillis();
		long start = (Long)ctx.get("debug.time");
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nBlade beetlsql after ------------------ " + DateKit.getTime() + " -----------------------------\n")
			.append("sqlId	: " + ctx.getSqlId()).append("\n")
			.append("time	: "+(time-start)+"ms").append("\n");
		
		if(ctx.isUpdate()){
			sb.append("成功更新	: [");
			if(ctx.getResult().getClass().isArray()){
				int[] ret = (int[])ctx.getResult();
				for(int i=0;i<ret.length;i++){
					sb.append(ret[i]);
					if(i!=ret.length-1){
						sb.append(",");
					}
				}
			}else{
				sb.append(ctx.getResult());
			}
			sb.append("]");
		}else{
			sb.append("成功返回	: [").append(ctx.getResult()).append("]");
		}
		sb.append("\n")
		.append("-----------------------------------------------------------------------------------------");;
		println(sb.toString());

	}
	
	protected void print(String sqlId,String sql,List<Object> paras){
		StringBuilder sb = new StringBuilder();
		sb.append("\nBlade beetlsql before ----------------- " + DateKit.getTime() + " -----------------------------\n")
			.append("sqlId	: " + sqlId).append("\n")
			.append("sql	: " + sql).append("\n")
			.append("paras	: " + formatParas(paras)).append("\n")
			.append("-----------------------------------------------------------------------------------------");
		println(sb.toString());
	}
	
}
