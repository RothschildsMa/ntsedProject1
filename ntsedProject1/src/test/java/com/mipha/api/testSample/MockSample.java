package com.mipha.api.testSample;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.mipha.api.dto.SearchRequest;
import com.mipha.api.entity.Employee;
import com.mipha.api.mapper.EmployeeMapper;
import com.mipha.api.service.impl.EmpServiceImpl;


//@ExtendWith(MockitoExtension.class)
public class MockSample {
	
	@InjectMocks
	@Spy
	private EmpServiceImpl mockEmpService;
	
	@Mock
	private EmployeeMapper mockEmployeeMapper;
	
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	public void test1() {
		
		System.out.println(Mockito.mockingDetails(mockEmpService).isSpy());
		when(mockEmpService.getEmpMaxId()).thenReturn(1);
	
		System.out.println(mockEmpService.getEmpMaxId());
		
		verify(mockEmpService, Mockito.times(1)).getEmpMaxId();
		
		assertEquals(1,mockEmpService.getEmpMaxId());
		
	}
	
	//@Test
	public void testAddEmployeeInfo() {
		
		doReturn(new Employee()).when(mockEmployeeMapper).findByName(anyString());

		Employee employee = new Employee();
		employee.setEmpName("mockTest2");
		employee.setGenderId(0);
		employee.setDepartmentId(0);
		
		doReturn(1).when(mockEmployeeMapper).insert(employee);
		
		doNothing().when(mockEmpService).addEmployeeInfo(employee);
		
		mockEmpService.addEmployeeInfo(employee);
		
		Mockito.verify(mockEmpService, Mockito.times(1)).addEmployeeInfo(employee);
		
	}
	
	//@Test
	public void testGetSearchList() {
		
		List<Employee> testList = new ArrayList<>();
		
		Employee employee = new Employee();
		employee.setEmpId(10L);
		employee.setEmpName("テストくん");
		employee.setGenderId(0);
		employee.setDepartmentId(0);
		
		testList.add(employee);
		

		doReturn(testList).when(mockEmployeeMapper).search(any(SearchRequest.class));
	
		doReturn(testList).when(mockEmpService).getSearchList(any(SearchRequest.class));
		
		SearchRequest req = new SearchRequest();
		req.setEmpId(10L);
		req.setEmpName("テストくん");
		
		
		List<Employee> resultList = mockEmpService.getSearchList(req);
		
		
		assertEquals(1, resultList.size());
	}


}
