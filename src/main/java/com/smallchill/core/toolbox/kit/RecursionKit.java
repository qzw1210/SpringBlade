/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.core.toolbox.kit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.smallchill.core.toolbox.Func;

public class RecursionKit {
	
	public static List<Map<String, Object>> findChild(List<Map<String, Object>> list, String pId, List<Map<String, Object>> linkedList, String m_pId, String m_id) {
		if (Func.isOneEmpty(list, pId)) {
			return null;
		}
		String[] idArr = pId.split(",");
		for (Iterator<Map<String, Object>> it = list.iterator(); it.hasNext();) {
			Map<String, Object> map = it.next();
			for (String idStr : idArr) {
				if (Func.toStr(map.get(m_pId)) != "0" && idStr.equals(Func.toStr(map.get(m_pId)))) {
					recursionFn(list, map, linkedList, m_pId, m_id);
				}
				if (Func.toStr(map.get(m_id)).equals(idStr)) {
					linkedList.add(map);
				}
			}
		}
		return linkedList;
	}
	
	public static List<Map<String, Object>> findChildWithOutSelf(List<Map<String, Object>> list, String pId, List<Map<String, Object>> linkedList, String m_pId, String m_id) {
		if (Func.isOneEmpty(list, pId)) {
			return null;
		}
		String[] idArr = pId.split(",");
		for (Iterator<Map<String, Object>> it = list.iterator(); it.hasNext();) {
			Map<String, Object> map = it.next();
			for (String idStr : idArr) {
				if (Func.toStr(map.get(m_pId)) != "0" && idStr.equals(Func.toStr(map.get(m_pId)))) {
					recursionFn(list, map, linkedList, m_pId, m_id);
				}
			}
		}
		return linkedList;
	}

	private static void recursionFn(List<Map<String, Object>> list, Map<String, Object> map, List<Map<String, Object>> linkedList, String m_pId, String m_id) {
		List<Map<String, Object>> childList = getChildList(list, map, m_pId, m_id);// 得到子节点列表
		if (childList.size() > 0) {// 判断是否有子节点
			linkedList.add(map);
			Iterator<Map<String, Object>> it = childList.iterator();
			while (it.hasNext()) {
				Map<String, Object> m = (Map<String, Object>) it.next();
				recursionFn(list, m, linkedList, m_pId, m_id);
			}
		} else {
			linkedList.add(map);
		}
	}

	private static List<Map<String, Object>> getChildList(List<Map<String, Object>> list, Map<String, Object> map, String m_pId, String m_id) {
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		Iterator<Map<String, Object>> it = list.iterator();
		while (it.hasNext()) {
			Map<String, Object> m = it.next();
			if (Func.equals(m.get(m_pId), map.get(m_id))) {
				childList.add(m);
			}
		}
		return childList;
	}
}
