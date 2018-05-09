package com.java.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.how2java.mapper.CategoryMapper;
import com.how2java.pojo.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisTest {

	@Autowired
	private CategoryMapper categoryMapper;

	@Test
	public void testAdd() {
		Category category = new Category();
		category.setId(4);
		category.setName("Category 4");
		categoryMapper.add(category);
	}
	@Test
	public void testUpdate() {
		Category category = new Category();
		category.setId(3);
		category.setName("Category 4");
		categoryMapper.update(category);
	}
	@Test
	public void testDelete() {
	categoryMapper.delete(3);
	}
	@Test
	public void testGet() {
	categoryMapper.get(1);
	}
	@Test
	public void testList() {
		System.out.println(categoryMapper);
		List<Category> cs=categoryMapper.list();
		for (Category c : cs) {
			System.out.println(c.getName());
		}
	}

}
