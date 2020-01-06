package com.yanyi.controller;

import com.yanyi.dao.CourseDAO;
import com.yanyi.entity.Course;
import com.yanyi.entity.CourseList;
import com.yanyi.entity.CourseMap;
import com.yanyi.entity.CourseSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ResponseBody 可以将业务方法的返回值返回到客户端，而不需要jsp页面
 */
@Controller
public class DataBindController {
    //因为添加课程的时候需要使用CourseDao @Autowired注解完成自动装载
    //这样就会创建CourseDAO对象，同时注入到DataBindController类中
    @Autowired
    private CourseDAO courseDAO;

    @RequestMapping(value = "/baseType")
    @ResponseBody
    //将Http请求中的参数绑定到id形参 需要使用@RequestParam注解 这样的话就会将url的id取出来绑定到形参中
    public String baseType(@RequestParam(value = "id") int id ){
        return "id:"+id;
    }
    @RequestMapping(value = "/packageType")
    @ResponseBody
    public String packageType(@RequestParam(value = "id")Integer id){
        return "id:"+id;
    }
    @RequestMapping(value = "/arrayType")
    @ResponseBody
    public String arrayType(@RequestParam(value = "names")String[] names){
        //这时候传入的是数组，这里需要将数组进行遍历,然后进行拼接（使用StringBuffer），最后进行输出
        StringBuffer stringBuffer = new StringBuffer();
        for (String name : names){
            stringBuffer.append(name).append(" ");
        }
        return stringBuffer.toString();
    }
    //向其中添加课程，需要前端传递来的课程对象，添加完毕后，将课程信息返回给前端
    //因为需要页面所以需要ModelAndView
    @RequestMapping(value = "/pojoType")
    public ModelAndView pojoType(Course course){
        //进行添加操作,即具体业务逻辑
        System.out.println(course);
        courseDAO.add(course);
        //进行响应
        ModelAndView modelAndView = new ModelAndView();
        //添加模型数据
        modelAndView.addObject("courses",courseDAO.getAll());
        //添加视图信息
        modelAndView.setViewName("index");
        return modelAndView;
    }
    //在使用List是无法使用，因为是无法完成绑定的，这时需要一个包装类，
    @RequestMapping(value = "/listType")
    public ModelAndView listType(CourseList courseList){
        for (Course courses :courseList.getCourses()){
            courseDAO.add(courses);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("courses",courseDAO.getAll());
        return modelAndView;
    }
    @RequestMapping(value = "/mapType")
    public ModelAndView mapType(CourseMap courseMap){
        for (String key : courseMap.getCourses().keySet()){
            Course course = courseMap.getCourses().get(key);
            courseDAO.add(course);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("course",courseDAO.getAll());
        return modelAndView;
    }
    @RequestMapping(value = "/setType")
    public ModelAndView setType(CourseSet courseSet){
        for (Course course : courseSet.getCourses()){
            courseDAO.add(course);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("course",courseDAO.getAll());
        return modelAndView;
    }
    @RequestMapping(value = "/jsonType")
    @ResponseBody
    public Course jsonType(@RequestBody Course course){
        course.setPrice(course.getPrice()+199);
        return course;
    }
}
