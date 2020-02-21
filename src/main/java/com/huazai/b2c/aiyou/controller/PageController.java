package com.huazai.b2c.aiyou.controller;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author HuaZai
 * @contact who.seek.me@java98k.vip
 *          <ul>
 * @description 专门做页面跳转的controller
 *              </ul>
 * @className PageController
 * @package com.huazai.b2c.aiyou.controller
 * @createdTime 2017年06月17日
 *
 * @version V1.0.0
 */
@Controller
@RequestMapping(value = "/page")
public class PageController
{

	@Description(value = "页面跳转接口")
	@RequestMapping(value = "/{page}")
	public String forwardPage(@PathVariable(value = "page") String page)
	{

		return page;
	}

}
