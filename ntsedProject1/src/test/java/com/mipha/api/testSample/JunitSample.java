package com.mipha.api.testSample;


import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mipha.api.entity.Employee;
import com.mipha.api.mapper.EmployeeMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JunitSample {

	@Autowired
	private EmployeeMapper employeeMapper;

	//@Test
	public void insertTest() {
		Employee employee = new Employee();
		employee.setEmpName("mockTest1");
		employee.setPassword("123");
		employee.setGenderId(0);
		employee.setDepartmentId(0);
		System.out.println(employeeMapper.insert(employee));

	}

	@Test
	public void search() {
		Employee employee = employeeMapper.findByName("岸田文雄");
		System.out.println(employee);
		assertEquals(10000,employee.getEmpId());
	}

	//@Test
	public void delete() {
		employeeMapper.delete(Arrays.asList(1,10000,10001));
		
	}

}
