package com.how2java.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.how2java.mapper.CategoryMapper;
import com.how2java.pojo.Category;

public class implement {
	static ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
	static CategoryMapper categoryMapper=(CategoryMapper) context.getBean("categoryMapper");
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Category category=new Category();
   //add(category);//添加数据
   //update(category);//修改数据
    //delete();//删除数据
    //getId();//id搜索数据
    show();//搜索所有数据
	}
	private static void update(Category category) {
		// TODO 自动生成的方法存根
		category.setId(4);
		category.setName("category4");
		categoryMapper.update(category);
	}
	private static void delete() {
		// TODO 自动生成的方法存根
		categoryMapper.delete(4);
	}
	private static void getId() {
		// TODO 自动生成的方法存根
		categoryMapper.get(2);
	}
	private static void show() {
		// TODO 自动生成的方法存根
		List<Category> cs=categoryMapper.list();
		for (Category c : cs) {
			System.out.println(c);
		}
	}
	private static void add(Category category) {
		// TODO 自动生成的方法存根
		category.setId(4);
		category.setName("Category 4");
		categoryMapper.add(category);
	}

}
