package com.mipha.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mipha.api.dto.JsonResponse;
import com.mipha.api.dto.SearchRequest;
import com.mipha.api.entity.Employee;
import com.mipha.api.service.IEmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/mipha/api/info")
@RequiredArgsConstructor
public class InfoController {

	private final IEmployeeService employeeService;
	

	@RequestMapping(value = "/getAll")
	public JsonResponse<List<Employee>> getList() {
		return new JsonResponse<>(200, employeeService.getAllEmployee());
	}

	@RequestMapping(value = "/search")
	public JsonResponse<List<Employee>> search(SearchRequest searchRequest) {
		return new JsonResponse<>(200, employeeService.getSearchList(searchRequest));
	}
	
	@RequestMapping(value = "/delete")
	public JsonResponse<Void> delete(@RequestBody List<Integer> empIds) {

		employeeService.deleteEmployeeByIdList(empIds);
		return new JsonResponse<>(200, "削除成功");
	}
	
	@RequestMapping(value = "/reset")
	public JsonResponse<Void> reset() {
		employeeService.resetData();
		return new JsonResponse<>(200, "リセット");
	}

}
