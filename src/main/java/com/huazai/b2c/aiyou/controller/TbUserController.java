package com.huazai.b2c.aiyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huazai.b2c.aiyou.pojo.TbUser;
import com.huazai.b2c.aiyou.repo.AiyouResultData;
import com.huazai.b2c.aiyou.service.TbUserService;

/**
 * 
 * @author HuaZai
 * @contact who.seek.me@java98k.vip
 *          <ul>
 * @description 用户控制层
 *              </ul>
 * @className TbUserController
 * @package com.huazai.b2c.aiyou.controller
 * @createdTime 2017年06月17日
 *
 * @version V1.0.0
 */
@Controller
@RequestMapping(value = "/user")
public class TbUserController
{

	@Autowired
	private TbUserService tbUserService;

	@Description(value = "校验用户数据是否可用")
	@RequestMapping(value = "/check/info/{param}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public AiyouResultData checkInUserData(@PathVariable(value = "param") String param,
			@PathVariable(value = "type") Integer type)
	{
		AiyouResultData resultData = tbUserService.checkUserData(param, type);
		return resultData;
	}

	@Description(value = "用户注册接口")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public AiyouResultData register(TbUser tbUser)
	{
		AiyouResultData resultData = tbUserService.registerInfo(tbUser);
		return resultData;
	}

}
