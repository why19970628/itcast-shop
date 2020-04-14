package com.itheima.store.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.itheima.store.domain.User;

public class MyBeanUtil {
	public static void populate(Object o,Map<String, String[]> map){
		try {
			//希望认识日期类型
			DateConverter dateConverter = new DateConverter();
			dateConverter.setPattern("yyyy-MM-dd");
			ConvertUtils.register(dateConverter, Date.class);
			BeanUtils.populate(o, map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	public static void main(String[] args) {
		Map<String, String[]> map=new HashMap<>();
		map.put("birthday", new String[]{"2018/07/16"});
		User user = new User();
		populate(user, map);
		System.out.println(user);
	}
}
