package com.lsxy.framework.web.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsxy.framework.config.SystemConfig;
import com.lsxy.framework.core.security.SecurityUser;
import com.lsxy.framework.core.utils.BeanUtils;
import com.lsxy.framework.core.utils.StringUtil;


/**
 * @author tantyou
 */
@SuppressWarnings("unchecked")
public class WebUtils {
	private static Log logger = LogFactory.getLog(WebUtils.class);

	private WebUtils() {
	}
	
	/**
	 *  向客户端输出json字符串
	 * @param response
	 * @param json
	 */
	public static void outputJson(HttpServletRequest request,HttpServletResponse response,String json){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		try {
			logger.debug("请求响应 ["+getRemoteAddress(request)+"]["+request.getSession().getId()+"]:"+json);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  向客户端输出json字符串
	 * @param response
	 * @param json
	 */
	@Deprecated
	public static void outputJson(HttpServletResponse response,String json){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		try {
			logger.debug("response output :"+json);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 重载Spring WebUtils中的函数,作用如函数名所示 加入泛型转换,改变输入参数为request 而不是session
	 *
	 * @param name  session中变量名称
	 * @param clazz session中变量的类型
	 */
	public static <T> T getOrCreateSessionAttribute(HttpServletRequest request, String name, Class<T> clazz) {
		return (T) org.springframework.web.util.WebUtils.getOrCreateSessionAttribute(request.getSession(), name, clazz);
	}
	
	/**
	 * 取得request参数值,根据是否需要转码配置进行相应的转码工作
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getRequestParam(HttpServletRequest request,String name){
		return getRequestParam(request, name, null);
	}
	/**
	 * 取得request参数值,根据是否需要转码配置进行相应的转码工作
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getRequestParam(HttpServletRequest request,String name,String defaultValue){
		String method = request.getMethod().trim().toLowerCase();
		String ret = request.getParameter(name);
		if(StringUtil.isNotEmpty(ret)){
			if(method.equals("get")){ 
				String encode = SystemConfig.getProperty("system.params.encode");
				if(StringUtil.isNotEmpty(encode) && encode.toLowerCase().equals("true")){
					String charset = SystemConfig.getProperty("system.params.encode.charset");
					try {
						if (ret != null && !ret.equals("")) {
							byte[] byteStr = ret.getBytes("ISO-8859-1");
							ret = new String(byteStr,charset);
						}
					} catch (Exception e) {
					}
				}
			}
		}else{
			ret = defaultValue;
		}
		return ret;
	}
	/**
	 * 得到request的queryString
	 * @param request
	 * @return
	 */
	public static String getQueryString(HttpServletRequest request){
		String ret = request.getQueryString();
		if(StringUtil.isNotEmpty(ret)){
			String encode = SystemConfig.getProperty("JTEAP_SYSTEM_REQUEST_ENCODE");
			if(StringUtil.isNotEmpty(encode) && encode.toLowerCase().equals("true")){
				String charset = SystemConfig.getProperty("JTEAP_SYSTEM_REQUEST_ENCODE_CHARSET");
				try {
					if (ret != null && !ret.equals("")) {
						byte[] byteStr = ret.getBytes("ISO-8859-1");
						ret = new String(byteStr,charset);
					}
				} catch (Exception e) {
				}
				
			}
		}
		return ret;
	}
	
	/**
	 * 输出请求参数方法
	 * @param request
	 */
	public static void logRequestParams(HttpServletRequest request){
		if(!logger.isDebugEnabled()){
			return;
		}
		//过滤不想看到的日志
		String url = request.getRequestURI();
		String excludes[] = SystemConfig.getProperty("log.exclude.url","").split(",");
		for (int i = 0; i < excludes.length; i++) {
			if(StringUtil.isNotEmpty(excludes[i]) && url.indexOf(excludes[i])>=0){
				return;
			}
		}
		
		logger.debug("Http请求 ["+getRemoteAddress(request)+"]["+request.getSession().getId()+"]"+request.getRequestURL()+" 参数如下：");
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String paramName = params.nextElement();
			String value = request.getParameter(paramName);
			logger.debug(paramName+":"+value);
		}
	}

	
	/**
	 * 根据请求参数，将参数值设置到指定bean对象的对应的属性中
	 * @param request
	 */
	public static void setRequestFormValuesToBean(HttpServletRequest request,
			Object object) {
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String paramName = params.nextElement();
			String value = getRequestParam(request, paramName);
			Field field = null;
			try {
				field = BeanUtils.getDeclaredField(object.getClass(), paramName);
			} catch (NoSuchFieldException e) {
			}
			if(field == null){
				continue;
			}else{
				try {
					BeanUtils.setProperty(object, paramName,value);
				} catch (Exception e) {
					logger.error("为对象"+object+"设置属性"+paramName+"时出现异常，该值为"+value);
					e.printStackTrace();
				}
			}
			
		}
		
	}
	

	/**
	 * 获取调用者ip地址
	 * @param request
	 * @return
	 */
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取mac地址
	 * @param ip
	 * @return
	 */
	 public String getMACAddress(String ip) {
	        String str = "";
	        String macAddress = "";
	        try {
	            Process p = Runtime.getRuntime().exec("nbtstat -a " + ip);
	            InputStreamReader ir = new InputStreamReader(p.getInputStream());
	            LineNumberReader input = new LineNumberReader(ir);
	            for (int i = 1; i < 100; i++) {
	                str = input.readLine();
	                if (str != null) {
	                    //if (str.indexOf("MAC Address") > 1) {
	                    if (str.indexOf("MAC") > 1) {
	                        macAddress = str.substring(
	                                str.indexOf("=") + 2, str.length());
	                        break;
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace(System.out);
	        }
	        return macAddress;
	    }

	 /**
	  * 使用请求对象构建一个环境变量映射表
	  * @param request
	  * @return
	  * 映射表中包括：
	  * 1.参数
		2.当前用户  currentUser
		3.ip
		4.dt
		4.session attribute
		5.request attribute
		 * 
	  */
	 
	public static Map<String,Object> buildContextMap(HttpServletRequest request) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(request != null){
			resultMap.putAll(request.getParameterMap());
			Enumeration<String> enums = request.getAttributeNames();
			while(enums.hasMoreElements()){
				String attrName = enums.nextElement();
				resultMap.put(attrName, request.getAttribute(attrName));
			}
			
			enums = request.getSession().getAttributeNames();
			while(enums.hasMoreElements()){
				String attrName = enums.nextElement();
				resultMap.put(attrName, request.getSession().getAttribute(attrName));
			}
			String ip = WebUtils.getRemoteAddress(request);
			resultMap.put("ip", ip);
			
			SecurityUser su = (SecurityUser) request.getSession().getAttribute("currentUser");
	        if(su == null){
	        	su = new SecurityUser("0", "Anonymouse", "匿名", "null","null");
	        }
	        resultMap.put("currentUser", su);
	        resultMap.put("dt", new Date());
		}
		return resultMap;
	}
	
	/**
	 * 根据cookie名字获取cookie value
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request,String name){
		Cookie[] cookies = request.getCookies();
		String value = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(name)){
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}
	
	

	
	/**
	 * 设置cookie通用
	 * @param name
	 * @param value
	 */
	public static Cookie createCookie(String name,String value,int expiry){
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
		cookie.setDomain(SystemConfig.getProperty("system.sso.domain",".hesyun.com"));
		if(expiry > 0)
			cookie.setMaxAge(expiry);
		return cookie;
	}
	
	/**
	 * 创建cookie
	 * @param name
	 * @param value
	 * @return
	 */
	public static Cookie createCookie(String name,String value){
		return createCookie(name, value, -1);
	}

	/**
	 * 得到cookie对象
	 * 主要用于删除cookie使用
	 * 需要重新设置对应的 path和 domain才能正确删除对应cookie
	 * @param string
	 */
	public static Cookie getCookieObj(HttpServletRequest request,String string) {
        Cookie[] allCookies = request.getCookies();
        int i = 0;
        if(allCookies != null){
            for(i = 0;i < allCookies.length;i++){
                Cookie temp = allCookies[i];
                if(temp.getName().equals(string)){
                	return temp;
                }
            }
        }
        return null;
	}
}