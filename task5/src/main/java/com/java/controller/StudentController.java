package com.java.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.java.interceptor.DESUtil;
import com.java.interceptor.MD5Util;
import com.java.pojo.Jns;
import com.java.pojo.Student;
import com.java.pojo.Users;
import com.java.service.JnsService;
import com.java.service.StudentService;
import com.java.service.UsersService;
import com.java.until.Page;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class StudentController {
	Log log=LogFactory.getLog(StudentController.class);
	@Autowired
     StudentService studentService;
	@Autowired
	 JnsService jnsService;
	@Autowired
	UsersService usersService;
	@RequestMapping(value = "/only",method = RequestMethod.POST)
	public ModelAndView Only(HttpServletRequest request) throws IOException {
		String name=request.getParameter("name");
		String pwd=request.getParameter("password");
		long time=System.currentTimeMillis();
		Users u=new Users();
		u.setPassword(pwd);
		u.setPhone(name);
		u.setCreate_at(time);
		Users users=usersService.findByName(name);
		if(users==null){
			String passwordMd5 = MD5Util.stringToMD5(u.getPassword());
			u.setPassword(passwordMd5);
            usersService.add(u);
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setViewName("login");
			return modelAndView;
		}else{
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setViewName("register");
			return modelAndView;
		}
	}
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView Login(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public ModelAndView Register(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("register");
		return modelAndView;
	}
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public ModelAndView Logout(HttpServletResponse response,HttpServletRequest request){
		HttpSession httpSession=request.getSession();
		httpSession.removeAttribute("name");
		Cookie cookie = new Cookie("token",null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		System.out.println(cookie.getName()+cookie.getValue());
		response.addCookie(cookie);
		ModelAndView modelAndView=new ModelAndView("redirect:/home");
		return modelAndView;
	}
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public ModelAndView Home(HttpServletRequest request){
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(Cookie cookie : cookies){
				System.out.println("cookie"+cookie.getName());
				if(cookie.getName().equals("session")){
					System.out.println("session:"+cookie.getName()+cookie.getValue());
					request.getSession().setAttribute("name",cookie.getValue());
				}
				if(!cookie.getName().equals("token") &&cookies.length<3){
						System.out.println("session:"+cookie.getName()+cookie.getValue());
						request.getSession().setAttribute("name",null);
						ModelAndView modelAndView=new ModelAndView();
						modelAndView.setViewName("home");
						return modelAndView;
				}
			}
		}
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("home");
		return modelAndView;
	}
	@RequestMapping(value = "/u/job",method = RequestMethod.GET)
	public String Job(Model model){
		List<Jns> jnt=jnsService.list();
		model.addAttribute("subject",jnt);
		return "job";
	}
	@RequestMapping(value = "/offer",method = RequestMethod.GET)
	public ModelAndView Offer(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("offer");
		return modelAndView;
	}
	@RequestMapping(value = "/about",method = RequestMethod.GET)
	public ModelAndView About(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("about");
		return modelAndView;
	}

	@RequestMapping(value="/student",method=RequestMethod.GET)
	public ModelAndView listStudent(Page page) {
		log.info("查询所有:");
		ModelAndView modelAndView=new ModelAndView();
		PageHelper.offsetPage(page.getStart(), 5);
		List<Student> students=studentService.list();
		int total=(int) new PageInfo<>(students).getTotal();
		page.caculateLast(total);
		modelAndView.addObject("ss", students);
		modelAndView.setViewName("listStudent");
		return modelAndView;
	}
	
	@RequestMapping(value="/student",method=RequestMethod.PUT)
	public ModelAndView addStudent(Student student) {
		studentService.add(student);
		log.info("添加操作："+student);
		ModelAndView modelAndView=new ModelAndView("redirect:/student");
		return modelAndView;
	}
	
	@RequestMapping(value="/student/{id}",method=RequestMethod.DELETE)
	public ModelAndView deleteStudent(Student student) {
		log.info("删除操作:"+student.getId());
		studentService.delete(student.getId());
		ModelAndView modelAndView=new ModelAndView("redirect:/student");
		return modelAndView;	
	}
	
	@RequestMapping(value="/student/{id}",method=RequestMethod.GET)
	public ModelAndView editStudent(Student student) {
		log.info("编辑操作:");
		Student student2= studentService.get(student.getId());
		ModelAndView modelAndView=new ModelAndView("editStudent");
		modelAndView.addObject("c", student2);
		return modelAndView;
	}
	
	@RequestMapping(value="/student/{id}",method=RequestMethod.POST)
	public ModelAndView updateStudent(Student student) {
		log.info("更新操作:"+student);
		studentService.update(student);
		log.debug("更新后的学生信息："+student);
		ModelAndView modelAndView=new ModelAndView("redirect:/student");
		return modelAndView;
	}
	@RequestMapping(value = "/login/validate", method = RequestMethod.POST)
	public ModelAndView valiDate(Users users, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name=request.getParameter("name");
		String pwd=request.getParameter("password");
		//注册时候用，数据库中保存的是加密的字符，
		String passwordMd5 = MD5Util.stringToMD5(pwd);
		users.setPhone(name);
		users.setPassword(passwordMd5);
		users=usersService.checkLogin(users.getPhone(),users.getPassword());
		if(users !=null){
			request.getSession().setAttribute("name",name);
			request.getSession().setAttribute("pwd",pwd);
			Users userAuth1 = usersService.findByName(users.getPhone());
			long id = userAuth1.getId();
			// 使用系统当前时间生成唯一token, 格式为键值对
			String token = id + "=" + System.currentTimeMillis();
			// 加密key 必须8位
			String key = "liuhuan1";
			// 加密
			byte[] bytes = DESUtil.encrypt(token, key);
			System.out.println("加密后的token: " + DESUtil.toHexString(bytes).toUpperCase());
			System.out.println("加密后的Base64 token: " + Base64.encodeBase64String(bytes));
			Cookie cookie1 = new Cookie("session", name);
			cookie1.setMaxAge(6000);
			cookie1.setPath("/");
			response.addCookie(cookie1);
			// 保存到 Cookie 中
			// 使用 Base64.encodeBase64String(bytes)) 报错 RFC6265 Cookie values may not contain control characters . 原因: import org.apache.commons.net.util.Base64 生成的Base64带换行符, 会导致报错 更换为 org.apache.commons.codec.binary.Base64;
			Cookie cookie = new Cookie("token", Base64.encodeBase64String(bytes));
			// Cookie cookie = new Cookie("tonken", key);
			// 设置 Cookie 过期时间 单位为秒
			cookie.setMaxAge(6000);
			// 设置 Cookie 有效路径
			cookie.setPath("/");
			System.out.println("新生成的Cookie-效时间-值: " + cookie.getName() + "-->" + cookie.getMaxAge() + "-->" + cookie.getValue() + cookie.getPath());
			response.addCookie(cookie);

			ModelAndView modelAndView=new ModelAndView("redirect:/u/job");
			return modelAndView;
		}
		ModelAndView modelAndView=new ModelAndView("redirect:/login");
		return modelAndView;
	}
}
