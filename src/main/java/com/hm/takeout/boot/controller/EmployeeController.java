package com.hm.takeout.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hm.takeout.boot.common.R;
import com.hm.takeout.boot.entity.Employee;
import com.hm.takeout.boot.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 登录判断
     * @param request
     * @param employee
     * @return
     */
    @RequestMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //3、如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("登陆失败");
        }
        //4、密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() == 0){
            return R.error("员工已禁用");
        }
        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }


    /**
     * 退出操作
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 添加员工操作
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工{}",employee.toString());
        //设置初始密码  并进行MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        /**
         * 这块不需要在单独赋值，调用MP的自动填充属性为变量赋值，减少代码量
         */
        //获取当前时间  赋值给新增属性
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        //抛出异常的话会在全局异常中接受
        employeeService.save(employee);
        return R.success("员工添加成功");
    }

    /**
     * 分页操作
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        log.info("page:{} pageSize:{} name:{}",page,pageSize,name);
        //创建分页构造工具
        Page pageInfo = new Page<>(page,pageSize);
        //构建分页条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //根据姓名进行模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //根据创造时间进行条件排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 更新操作
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        /**
         * 这块不需要在单独赋值，调用MP的自动填充属性为变量赋值，减少代码量
         */
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(empId);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return  R.success("更新成功！");
    }

    /**
     * 根据id查找员工信息，将员工信息返回给前端修改信息界面
     * PathVariable获取请求地址中的参数
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable("id") Long id){
        log.info("传过来的id{}:",id);
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return R.success(employee);
        }else{
            return R.error("查不到员工信息");
        }
    }
}
