package com.smallchill.core.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Paras;
import com.smallchill.core.toolbox.file.BladeFile;
import com.smallchill.core.toolbox.file.FileRender;
import com.smallchill.core.toolbox.file.UploadFileUtils;
import com.smallchill.core.toolbox.kit.PathKit;

@Controller
@RequestMapping("/uploadify")
public class UploadifyController extends BladeController {
	
	@ResponseBody
	@RequestMapping("/upload")
	public Paras upload(@RequestParam("imgFile") MultipartFile file) {
		Paras rd = Paras.create();
		if (null == file) {
			rd.set("error", 1);
			rd.set("message", "请选择要上传的图片");
			return rd;
		}
		String originalFileName = file.getOriginalFilename();
		String dir = getParameter("dir", "image");
		// 测试后缀
		boolean ok = UploadFileUtils.testExt(dir, originalFileName);
		if (!ok) {
			rd.set("error", 1);
			rd.set("message", "上传文件的类型不允许");
			return rd;
		}
		BladeFile bf = getFile(file);
		bf.transfer();
		Object fileId = bf.getFileId();	
		String url = "/uploadify/renderFile/" + fileId;
		rd.set("error", 0);
		rd.set("fileId", fileId);
		rd.set("url", Cst.me().getContextPath() + url);
		rd.set("fileName", originalFileName);
		return rd;	
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/renderFile/{id}")
	public void renderFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
		Map<String, Object> file = Db.findById("TFW_ATTACH", id);
		String url = file.get("URL").toString();
		File f = new File((Cst.me().isRemoteMode() ? "" : PathKit.getWebRootPath()) + url);
		FileRender.init(request, response, f).render();
	}
	
}
