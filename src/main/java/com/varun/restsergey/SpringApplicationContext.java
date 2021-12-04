package com.varun.restsergey;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.varun.restsergey.service.UserService;

@Controller
public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext CONTEXT;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT = applicationContext;
	}

	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}
}
