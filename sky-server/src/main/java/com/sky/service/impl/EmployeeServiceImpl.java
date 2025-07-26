package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        password = password.toUpperCase();
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    /**
     * 新增员工
     * @param employeeDTO
     */
    public void save(EmployeeDTO employee) {
        Employee employee2 = new Employee();

        //对象属性拷贝,从前面的拷贝到后面的
        BeanUtils.copyProperties(employee, employee2);

        //设置账号状态
        employee2.setStatus(StatusConstant.ENABLE);

        //设置密码
        employee2.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置创建时间和修改时间
        employee2.setCreateTime(LocalDateTime.now());
        employee2.setUpdateTime(LocalDateTime.now());

        //设置当前记录的创建人id和修改人id
        // TODO  后续需要改为当前登陆用户的id
        employee2.setCreateUser(BaseContext.getCurrentId());
        employee2.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.inert(employee2);
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // select * from employee limit 0,10
        // 开始分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> records = page.getResult();
        PageResult pageResult = new PageResult(total, records);
        return pageResult;
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void startPrStop(Integer status, Long id){
        // update employee set status = ? where id = ?
        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);
        // 使用builder创建对象
//        Employee employee = Employee.builder().
//                status(status).
//                id(id).build();
        employeeMapper.update(employee );
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    public Employee getByID(Long id){
        Employee employee = employeeMapper.getByID(id);
//       处理密码
        employee.setPassword("****");
        return employee;
    }

    @Override
    public void editEmployee(EmployeeDTO employee) {
        Employee employee1 = new Employee();
        BeanUtils.copyProperties(employee, employee1);
        employee1.setUpdateTime(LocalDateTime.now());
        employee1.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee1);
    }
}
