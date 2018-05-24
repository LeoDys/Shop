package com.leo.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.leo.domain.User;
import com.leo.service.UserService;
import com.leo.tool.CommonUtil;
import com.leo.tool.MailUtils;

/**
 * 注册的业务逻辑
 */
public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		/**
		 * 获得表单数据
		 */

		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			ConvertUtils.register(new Converter() {
				public Object convert(Class arg0, Object arg1) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = null;
					try {
						date = format.parse(arg1.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date;
				}
			}, java.util.Date.class);
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		user.setUid(CommonUtil.getUUID());
		user.setTelephone(null);
		user.setState(0);
		String activeCode = CommonUtil.getUUID();
		user.setCode(activeCode);

		UserService userService = new UserService();
		boolean register = userService.register(user);
		if (register) {
			// 成功
			// 进行用户激活的操作
			String emailMsg = "点击激活<a href='http://localhost:8080/ShopGo/active?activeCode="+activeCode+"'>"
					+"http://localhost:8080/ShopGo/active?activeCode=" + activeCode + 
					"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		} else {
			// 失败页面
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}