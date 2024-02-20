package com.mipha.api.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.mipha.api.dto.SearchRequest;
import com.mipha.api.entity.Employee;
import com.mipha.api.exception.NameConflictException;
import com.mipha.api.exception.CreateFaildException;
import com.mipha.api.mapper.EmployeeMapper;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmpServiceImplTest {

	@Autowired
	@SpyBean
	private EmpServiceImpl mockEmpService;

	// @SpyBean
	@MockBean
	private EmployeeMapper mockEmployeeMapper;

	@Test
	void testSuccessAddEmployeeInfo() {
		Employee employee = new Employee();
		employee.setEmpName("testSuccess");

		when(mockEmployeeMapper.findByName(anyString())).thenReturn(null);

		when(mockEmployeeMapper.insert(employee)).thenReturn(1);

		mockEmpService.addEmployeeInfo(employee);

		verify(mockEmployeeMapper, Mockito.times(1)).findByName("testSuccess");

		verify(mockEmployeeMapper, Mockito.times(1)).insert(employee);

	}

	@Test
	void testNameConflict() {

		// テストNameConflictException
		Employee existingEmp = new Employee();
		existingEmp.setEmpName("test");

		Employee newEmp = new Employee();
		newEmp.setEmpName("test");

		when(mockEmployeeMapper.findByName(anyString())).thenReturn(existingEmp);

		assertThrows(NameConflictException.class, () -> mockEmpService.addEmployeeInfo(newEmp));

//		try {
//			mockEmpService.addEmployeeInfo(newEmp);
//			fail("失敗");
//		} catch (Exception e) {
//			assertTrue(e instanceof NameConflictException);
//		}

	}

	@Test
	void testCreateFaild() {
		// テストCreateFaildException
		Employee emp = new Employee();

		when(mockEmployeeMapper.findByName(anyString())).thenReturn(null);

		when(mockEmployeeMapper.insert(emp)).thenReturn(0);

		assertThrows(CreateFaildException.class, () -> mockEmpService.addEmployeeInfo(emp));
	}

	@Test
	void testGetAllEmployee() {
		List<Employee> testList = new ArrayList<>();

		Employee employee = new Employee();
		employee.setEmpId(10L);
		employee.setEmpName("テストくん");
		employee.setGenderId(0);
		employee.setDepartmentId(0);

		testList.add(employee);

		when(mockEmployeeMapper.getAll()).thenReturn(testList);

		List<Employee> resultList = mockEmpService.getAllEmployee();

		assertFalse(resultList.isEmpty());

		verify(mockEmployeeMapper, Mockito.times(1)).getAll();

	}

	@Test
	void testGetSearchList() {

		List<Employee> testList = new ArrayList<>();

		Employee employee = new Employee();
		employee.setEmpId(10L);
		employee.setEmpName("テストくん");
		employee.setGenderId(0);
		employee.setDepartmentId(0);

		testList.add(employee);

		doReturn(testList).when(mockEmployeeMapper).search(any(SearchRequest.class));

		// doReturn(testList).when(mockEmpService).getSearchList(any(SearchRequest.class));

		SearchRequest req = new SearchRequest();
		req.setEmpId(10L);
		req.setEmpName("テストくん");

		List<Employee> resultList = mockEmpService.getSearchList(req);

		assertEquals(1, resultList.size());

		verify(mockEmployeeMapper, Mockito.times(1)).search(req);
	}

	@Test
	void testDelete() {

		doNothing().when(mockEmployeeMapper).delete(anyList());

		List<Integer> empIds = new ArrayList<>(Arrays.asList(10001, 10002, 10003));

		mockEmpService.deleteEmployeeByIdList(empIds);

		verify(mockEmployeeMapper, Mockito.times(1)).delete(empIds);
	}

	@Test
	void testGetEmpMaxId() {

		// System.out.println(Mockito.mockingDetails(mockEmpService).isSpy());
		when(mockEmployeeMapper.getMaxId()).thenReturn(1);

		assertEquals(1, mockEmpService.getEmpMaxId());
		
		verify(mockEmployeeMapper, Mockito.times(1)).getMaxId();

	}

	@Test
	void testFindByEmpId() {
		
		when(mockEmployeeMapper.findByEmpId(anyInt())).thenReturn(new Employee());
		
		Employee employee = mockEmpService.findByEmpId(10001);
		
		assertNotNull(employee);
		
		verify(mockEmployeeMapper, Mockito.times(1)).findByEmpId(10001);
		
	}

	@Test
	void testUpdateEmployeeInfo() {

		doNothing().when(mockEmployeeMapper).update(any(Employee.class));
		
		Employee employee = new Employee();
		employee.setEmpId(10L);
		employee.setEmpName("テストくん");
		employee.setGenderId(0);
		employee.setDepartmentId(0);
		
		mockEmpService.updateEmployeeInfo(employee);
		
		verify(mockEmployeeMapper, Mockito.times(1)).update(employee);

	}
	
	@Test
	void testResetData() {
		doNothing().when(mockEmployeeMapper).reset();
		
		mockEmpService.resetData();
		
		verify(mockEmployeeMapper, Mockito.times(1)).reset();
	}

}
