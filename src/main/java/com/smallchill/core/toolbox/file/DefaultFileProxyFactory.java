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
package com.smallchill.core.toolbox.file;

import java.io.File;
import java.util.Date;

import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.system.model.Attach;

public class DefaultFileProxyFactory implements IFileProxy {

	@Override
	public File rename(File f) {
		File dest = new File(path(f));
		f.renameTo(dest);
		return dest;
	}

	@Override
	public String path(File f) {
		StringBuilder newFileName = new StringBuilder()
		.append(getFileDir(Cst.me().getUploadRealPath()))
		.append(System.currentTimeMillis())
		.append(getFileExt(f.getName()));
		return newFileName.toString();
	}

	@Override
	public String virtualPath(File f) {
		StringBuilder newFileName = new StringBuilder()
		.append(getFileDir(Cst.me().getUploadCtxPath()))
		.append(System.currentTimeMillis())
		.append(getFileExt(f.getName()));
		return newFileName.toString();
	}

	@Override
	public Object getFileId(BladeFile bf) {
		Attach attach = new Attach();
		attach.setCreater(Func.toInt(ShiroKit.getUser().getId(), 0));
		attach.setCreatetime(new Date());
		attach.setName(bf.getOriginalFileName());
		attach.setStatus(1);
		attach.setUrl((Cst.me().isRemoteMode() ? bf.getUploadPath() : bf.getUploadVirtualPath()));
		return Blade.create(Attach.class).saveRtStrId(attach);
	}

	/**
	 * 获取文件后缀
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
	}

	/**
	 * 获取文件保存地址
	 * @param saveDir
	 * @return
	 */
	public static String getFileDir(String saveDir) {
		StringBuilder newFileDir = new StringBuilder();
		newFileDir.append(saveDir)
				.append(File.separator).append(DateKit.getDays())
				.append(File.separator);
		return newFileDir.toString();
	}

}
