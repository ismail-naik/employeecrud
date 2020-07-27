package com.paypal.bfs.test.employeeserv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.mockito.junit.jupiter.MockitoExtension;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = EmployeeservApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeservApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port=9090;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
   

	

	

   @InjectMocks
	EmployeeResourceImpl employeeController;
	
	@Mock
	EmployeeRepository employeeRepository;
	
	@Test
	public void testAddEmployee() 
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		//Employee employee = new Employee();
		EmployeeEntity employeeToAdd = new EmployeeEntity();
		//employee.setId(1);
		//when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(employeeToAdd);
		
		System.out.println("inside test add employee");
		Employee emp = new Employee();
		emp.setId(140);
		emp.setFirstName("Shaik");
		emp.setLastName("muneer");
		emp.setDob("09-07-1990");
		Long header = 234123L;
		ResponseEntity<?> responseEntity = employeeController.createEmployee(emp, header);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		System.out.println("Response" + responseEntity.getHeaders());
		//assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/140");
	}
   
	
	  @Test 
	  public void testGetEmployeeById() { 
		  System.out.println("URL" + getRootUrl() + "/v1/bfs/employees/get/140");
		  Employee employee =
	  restTemplate.getForObject(getRootUrl() + "/v1/bfs/employees/get/140",
			  Employee.class); System.out.println("Employee First Name" +
	  employee.getFirstName()); assertNotNull(employee); }
	 
}
