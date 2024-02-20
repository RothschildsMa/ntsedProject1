package com.mipha.api.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mipha.api.dto.SearchRequest;
import com.mipha.api.entity.Employee;
import com.mipha.api.exception.CreateFaildException;
import com.mipha.api.exception.NameConflictException;
import com.mipha.api.mapper.EmployeeMapper;
import com.mipha.api.service.IEmployeeService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class EmpServiceImpl implements IEmployeeService{
	
	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public void addEmployeeInfo(Employee employee) {
		Employee emp = employeeMapper.findByName(employee.getEmpName());
		
		if(emp != null) {
			throw new NameConflictException("名前は既に存在します。");
		}
		
		employee.setPassword("a123");
		employee.setCreateUser("Admin");
		employee.setUpdateUser("Admin");
		

		Integer num = employeeMapper.insert(employee);
		
		if(num != 1) {
			throw new CreateFaildException("ユーザー情報の作成は失敗しました。");
		}	
	}

	@Override
	public List<Employee> getAllEmployee() {
		
		return employeeMapper.getAll();
	}

	@Override
	public List<Employee> getSearchList(SearchRequest searchRequest) {
		
		return employeeMapper.search(searchRequest);
	}

	@Override
	public void deleteEmployeeByIdList(List<Integer> empIds) {
		employeeMapper.delete(empIds);
		
	}

	@Override
	public Integer getEmpMaxId() {
		System.out.println("spytest");
		TestStaticSample.sapmpleMethod("test");
		return employeeMapper.getMaxId();
	}

	@Override
	public Employee findByEmpId(Integer empId) {
		return employeeMapper.findByEmpId(empId);
	}

	@Override
	public void updateEmployeeInfo(Employee employee) {
		employeeMapper.update(employee);
	}

	@Override
	public void resetData() {
		employeeMapper.reset();
		
	}


}
