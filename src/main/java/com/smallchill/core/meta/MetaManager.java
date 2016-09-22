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
package com.smallchill.core.meta;

import java.util.Map;

import com.smallchill.core.constant.ConstCurd;
import com.smallchill.core.interfaces.IMeta;

public abstract class MetaManager extends MetaTool implements IMeta,ConstCurd{

	public Class<? extends MetaIntercept> intercept() {
		return null;
	}

	public String paraPerfix() {
		return null;
	}

	public Map<String, Object> switchMap() {
		return null;
	}

}
