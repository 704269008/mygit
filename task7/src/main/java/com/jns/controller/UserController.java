package com.jns.controller;

import com.aliyun.oss.OSSClient;
import com.jns.entity.Users;
import com.jns.service.UsersService;
import com.jns.utils.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("")
public class UserController {

    public int count=0;
    //借助slf4j记录日志
    Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    UsersService usersService;


    //指定请求的实际地址，请求的method类型
    //注册页面
    @RequestMapping(value = "/register" , method = RequestMethod.GET)
    public String toRegister(){
        return  "register";
    }


    //登录页面
    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public String toLogin(){
        return  "login";
    }


    //登录以及是否需要验证码登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(String number,Users users, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies=request.getCookies();
        request.getSession().setAttribute("email", users.getPhone());
        if(number!=null){
            for(Cookie cookie:cookies) {
                if (cookie.getName().equals("code")) {
                    if (cookie.getValue().equals(number)) {
                        request.getSession().setAttribute("email", null);
                        return loginCheck(users, response,request);
                    } else {
                            JOptionPane.showMessageDialog(null, "验证码错误");
                    }
                }
            }
            return new ModelAndView("redirect:/login");
        }
        return loginCheck(users,response,request);
    }


    //登录验证及数据加密
    public ModelAndView loginCheck(Users users, HttpServletResponse response,HttpServletRequest request) throws Exception {
        HttpSession session=request.getSession();
        //防止空指针
        if (users != null) {
            //注册时候用，数据库中保存的是加密的字符。
            //使用md5对用户密码进行加密
            String passwordMd5 = MD5Util.stringToMD5(users.getPassword());
            Users users1=new Users();
            users1.setPhone(users.getPhone());
            users1.setPassword(passwordMd5);
            //声明一个新的用户来接受返回的用户数据
            Users u1=usersService.getByPhone(users1.getPhone());
            Users user = usersService.checkLogin(users1.getPhone(), users1.getPassword());
            if (user != null) {
                managerCookie("num", null, 6000, response);
                //为了记录用户信息，把登录成功的用户信息保存在服务端的session中
                //通过用户phone，找到这个用户，并根据部分信息生成一个token，以便识别用户登录状态
                Users userAuth1 = usersService.getByPhone(users.getPhone());
                long id = userAuth1.getId();
                // 使用系统当前时间生成唯一token, 格式为键值对
                String token = id + "=" + System.currentTimeMillis();
                // 加密key 必须8位
                String key = "liuhuan1";
                // DESUtil加密
                byte[] bytes = DESUtil.encrypt(token, key);
                //调用方法来管理cookie
                if(userAuth1.getName()!=null){
                    session.setAttribute("name",userAuth1.getName());
                }else{
                    session.setAttribute("name",userAuth1.getPhone());
                }
                // 使用  org.apache.commons.codec.binary.Base64;
                //把加密后的token放入cookie中，以便储存登录状态
                String value=Base64.encodeBase64String(bytes);
                //session存储token
                session.setAttribute("token",value);
                //cookie存储token
                loginCookie("token",value,6000,response);
                //cookie设置sessionid
                loginCookie("JSESSIONID",session.getId(),6000,response);
                //重定向，跳转到首页home
                //cookie存储验证码
                managerCookie("code", null, 0, response);
                return new ModelAndView("redirect:/home");
            } else {
                count += 1;
                managerCookie("num", String.valueOf(count), 6000, response);
                if(u1==null && !users.getPhone().isEmpty()){
                    JOptionPane.showMessageDialog(null,"账号不存在！");
                }
            }
        }
        //如果用户未登录，跳转到登录页面
        return new ModelAndView("redirect:/login");
    }


    //中文加密cookie存值
    private void managerCookie(String name, String value, int maxAge,HttpServletResponse response) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(name, value);
        if(value!=null) {
            String valueEncoder = URLEncoder.encode(value, "utf-8");
            cookie = new Cookie(name, valueEncoder);
        }
            //cookie的生命周期以及路径
            cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            //保存cookie
            response.addCookie(cookie);
    }


    //原始cookie
    private void loginCookie(String name, String value, int maxAge,HttpServletResponse response) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(name, value);
        //cookie的生命周期以及路径
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        //保存cookie
        response.addCookie(cookie);
    }


    //检测账号是否存在
    @RequestMapping(value = "/check")
    public void checkRegister(HttpServletRequest request) throws IOException {

        String phone=request.getParameter("phoneOrEmail1");
        Users users=usersService.getByPhone(phone);
        if(users!=null){
            JOptionPane.showMessageDialog(null,"此账号已存在");
        }
    }


    //注册以及判断用户phone重复
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(Users users,String number,HttpServletRequest request) throws IOException{
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies) {
            if (cookie.getName().equals("code")) {
                if (cookie.getValue().equals(number)) {
                    //防止空指针
                    if (users != null) {
                        //新创建一个用户对象，设置它的注册时间。
                        Users u = new Users();
                        u.setPassword(users.getPassword());
                        u.setPhone(users.getPhone());
                        u.setCreate_at(users.getCreate_at());
                        //判断注册的phone是否重复。
                        Users user = usersService.getByPhone(users.getPhone());
                        if (user == null) {
                            //使用MD5对注册的用户密码进行加密。
                            String passwordMd5 = MD5Util.stringToMD5(u.getPassword());
                            u.setPassword(passwordMd5);
                            //保存注册用户的信息
                            usersService.add(u);
                            //跳转到用户登录页面
                            ModelAndView modelAndView = new ModelAndView();
                            modelAndView.setViewName("redirect:/login");
                            return modelAndView;
                        }
                    }
                }else {
                    if (number != null) {
                        JOptionPane.showMessageDialog(null, "验证码错误");
                    }
                }
            }
        }
        //若用户的phone存在，请换一个phone再注册
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }


    //注销
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
        //清除cookie中的数据
        managerCookie("JSESSIONID", null,0,response);
        managerCookie("token", null,0,response);
        request.getSession().setAttribute("token",null);
        request.getSession().setAttribute("name",null);
        //返回首页
        return  new ModelAndView("redirect:/home");
    }


    //用户个人信息页面
    @RequestMapping(value = "/personal" , method = RequestMethod.GET)
    public String toPersonal(Model model,HttpServletRequest request) throws UnsupportedEncodingException {
           HttpSession session=request.getSession();
           String name= (String) session.getAttribute("name");
        if(name!=null) {
            Users users = usersService.getByName(URLDecoder.decode(name, "utf-8"));
            if (users == null) {
                logger.info("phone" + URLDecoder.decode(name, "utf-8"));
                users = usersService.getByPhone(URLDecoder.decode(name, "utf-8"));
            }
            model.addAttribute("c", users);
            return "personal";
        }
            return "register";
    }


    //修改个人资料
    @RequestMapping(value = "/personal" , method = RequestMethod.POST)
    public ModelAndView personal(Users users, @RequestParam(value = "upload")MultipartFile multipartFile, HttpServletResponse response,HttpServletRequest request) throws IOException {
        OSSClient ossClient=OssDemo.getOssClient();
        //上传图片到服务器，返回图片路径
        String root=OssDemo.sendServer(multipartFile,request);
        //本地到第三方API
        File file=new File(root);
        //bucket名    自定义上传文件的名字，   文件在本地的具体路径，\ 用 \\代替
        if(file.getName().endsWith(".jpg")||file.getName().endsWith(".jpeg")
                || file.getName().endsWith(".bmp")|| file.getName().endsWith(".gif")
                || file.getName().endsWith(".png")){
            //上传至第三方
            Map map = OssDemo.uploadFile2OSS(ossClient, file);
            System.out.println("md5Key" + map.get("md5Key"));
            String url = OssDemo.getUrl(ossClient,(String) map.get("key"));
            System.out.println("url" + url);
            users.setImg(url);
            //清楚服务器存放的图片。
            file.delete();
            usersService.update(users);
        }else {
            usersService.updateOther(users);
        }
        request.getSession().setAttribute("img",users.getImg());
        request.getSession().setAttribute("name",users.getName());
        return new ModelAndView("redirect:/home");
    }


    //发送验证码
    @RequestMapping(value = "/send")
    public void sendCode(HttpServletResponse response,HttpServletRequest request){
        String phone;
        if(request.getParameter("phoneOrEmail")==null){
           phone= (String) request.getSession().getAttribute("email");
        }else{
        phone=request.getParameter("phoneOrEmail");
        }
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("code"))
                try {
                    managerCookie("code",null,0,response);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
        }
        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = pattern.matcher(phone);
        //随机码
        Random random=new Random();
        String code="";
        for(int i=0;i<6;i++){
            //6位随机数字组合的字符串
            int num=random.nextInt(10);
            code+=num;
        }
        if (matcher.matches()) {
            //code随机码，"1"为限制一分钟内，不能再次发送验证码
            String[] datas=new String[]{code,"1"};
            SMS sms=new SMS();
            sms.sendTemplateSMS(phone,"1",datas);
        }else{
            String[] datas=new String[]{"http://api.sendcloud.net/apiv2/mail/send","Does_test_P9bt0O","oLoiKbJ6m1Jv8MRU","sendcloud@sendcloud.org","李丽华",phone,"来自SendCloud的第一封邮件！",code};
            Email email=new Email();
            try {
                email.params(datas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            managerCookie("code",code,60,response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
