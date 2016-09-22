package com.smallchill.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.constant.Const;

@Controller
@RequestMapping("/error")
public class ErrorController extends BladeController {

	@RequestMapping("/error400")
	public ModelAndView error400(){
		ModelAndView view = new ModelAndView(Const.error400Path);
		return view;
	}
	
	@RequestMapping("/error401")
	public ModelAndView error401(){
		ModelAndView view = new ModelAndView(Const.error401Path);
		return view;
	}
	
	@RequestMapping("/error404")
	public ModelAndView error404(){
		ModelAndView view = new ModelAndView(Const.error404Path);
		return view;
	}
	
	@RequestMapping("/error500")
	public ModelAndView error500(){
		ModelAndView view = new ModelAndView(Const.error500Path);
		return view;
	}
}
