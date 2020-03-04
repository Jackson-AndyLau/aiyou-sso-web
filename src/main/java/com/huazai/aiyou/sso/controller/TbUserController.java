package com.huazai.aiyou.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huazai.aiyou.common.response.AiyouResultData;
import com.huazai.aiyou.common.utils.CookieUtils;
import com.huazai.aiyou.common.utils.JsonUtils;
import com.huazai.aiyou.sso.pojo.TbUser;
import com.huazai.aiyou.sso.service.TbUserService;

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

	@Description(value = "根据Token获取用户信息")
	@RequestMapping(value = "/token/{token}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Object getUserInfoByToken(@PathVariable(value = "token") String token, String callback)
	{
		AiyouResultData resultData = tbUserService.getUserInfoByToken(token);
		// 是否为跨域请求
		if (StringUtils.isNotEmpty(callback))
		{
			// (4.1版本以前的写法)
			String string = callback + "(" + JsonUtils.objectToJson(resultData) + ")";
			// （4.1版本后支持这种写法）
			MappingJacksonValue value = new MappingJacksonValue(resultData);
			value.setJsonpFunction(callback);
			return value;
		}
		return resultData;
	}

	@Description(value = "退出登录")
	@RequestMapping(value = "/login_out", method = RequestMethod.POST)
	@ResponseBody
	public AiyouResultData loginOut(String token)
	{
		AiyouResultData resultData = tbUserService.loginOut(token);

		return resultData;
	}

}
