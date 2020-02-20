package com.huazai.b2c.aiyou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huazai.b2c.aiyou.pojo.TbUser;
import com.huazai.b2c.aiyou.repo.AiyouResultData;
import com.huazai.b2c.aiyou.service.TbUserService;
import com.huazai.b2c.aiyou.utils.CookieUtils;

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
	
	@Value(value = "${AIYOU_TB_USER_COOKIE_TOKEN_KEY}")
	private String AIYOU_TB_USER_COOKIE_TOKEN_KEY;

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

	@Description(value = "用户登录接口")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AiyouResultData login(String username, String password, HttpServletRequest request,
			HttpServletResponse response)
	{
		// 调用Service登录接口
		AiyouResultData resultData = tbUserService.login(username, password);
		// 获取 Token 并写入本地 cookie（需要跨域）
		String token = resultData.getData().toString();
		CookieUtils.setCookie(request, response, AIYOU_TB_USER_COOKIE_TOKEN_KEY, token);
		return resultData;
	}

}
