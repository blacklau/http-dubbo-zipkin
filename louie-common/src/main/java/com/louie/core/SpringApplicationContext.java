/*
 * Copyright (c) 2013, Rainbow and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software;Designed and Developed mainly by many Chinese 
 * opensource volunteers. you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License version 2 only, as published by the
 * Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 *
 */
package com.louie.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringApplicationContext.context = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext()
			throws BeansException {
		return context;
	}

	public static Object getBean(String beanId) {
		if (beanId == null || beanId.length() == 0) {
			return null;
		}
		Object object = null;
		object = context.getBean(beanId);
		return object;
	}
	
	/*
	public static Object getController(String controllerName) {
		 Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(ApiController.class);
		 for(String s : beansWithAnnotation.keySet()) {
			 System.out.println(s);
		 }
		 return null;
	}
	*/
	
	public static  <T> T getBean(Class<T> clazz) {
		if (clazz == null ) {
			return null;
		}
		return context.<T>getBean(clazz);
	}
	
	/**
	 * 从spring容器中获取指定的bean
	 * 
	 * @param name
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return context.getBean(name, requiredType);
	}

	public static void removeBean(String beanId){
		if (beanId == null || beanId.isEmpty()) {
			return ;
		}
		ConfigurableApplicationContext applicationContexts = (ConfigurableApplicationContext)context;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContexts.getBeanFactory();
		beanFactory.removeBeanDefinition(beanId);
	}
	
	public static void removeBeans(String... beanIds){
		if(beanIds == null || beanIds.length == 0){
			return;
		}
		ConfigurableApplicationContext applicationContexts = (ConfigurableApplicationContext)context;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContexts.getBeanFactory();
		for(String beanId : beanIds){
			if(beanId != null && !beanId.isEmpty()){
				if(beanFactory.isBeanNameInUse(beanId)){
					beanFactory.removeBeanDefinition(beanId);
				}
			}
		}
	}
	
	
}