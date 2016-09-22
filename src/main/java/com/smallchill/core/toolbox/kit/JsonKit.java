package com.smallchill.core.toolbox.kit;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class JsonKit {
	
	public static String toJson(Object object) {
		return JSONObject.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
	}
	
	public static JSONObject parse(String text) {
		return JSONObject.parseObject(text);
	}
	
	public static <T> T parse(String text, Class<T> clazz) {
		return JSONObject.parseObject(text, clazz);
	}
	
}

