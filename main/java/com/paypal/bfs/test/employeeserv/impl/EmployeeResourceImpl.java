package com.paypal.bfs.test.employeeserv.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.entity.IdempotencyEntity;
import com.paypal.bfs.test.employeeserv.exception.Constants;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.repository.IdempotencyRepository;
import com.sun.el.stream.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	EmployeeRepository employeeRepo;
	@Autowired
	EmployeeEntity employeeEntity;
	@Autowired
	Address address;
	@Autowired
	Employee employee;
	@Autowired
	IdempotencyRepository idempotencyRepository;
	@Autowired
	IdempotencyEntity idempotencyEntity;

	String response = null;

	@Override
	public ResponseEntity<?> employeeGetById(String id) {

		try {
			if (id == null || id == "") {
				response = Constants.generateResponse(HttpStatus.BAD_REQUEST, "Id should not be null or empty");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			employeeEntity = employeeRepo.findByid(Integer.valueOf(id));
		} catch (Exception e) {

		}
		if (employeeEntity == null) {
			try {
				response = Constants.generateResponse(HttpStatus.NOT_FOUND, "Employee Id Not Found");

				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				try {
					response = Constants.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
							HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().toString());
					return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				} catch (Exception e1) {

				}

			}
		}

		employee.setId(employeeEntity.getId());
		employee.setFirstName(employeeEntity.getFirstName());
		employee.setLastName(employeeEntity.getLastName());
		employee.setDob(employeeEntity.getDob());
		address.setCity(employeeEntity.getCity());
		address.setLine1(employeeEntity.getLine1());
		address.setLine2(employeeEntity.getLine2());
		address.setState(employeeEntity.getState());
		address.setCountry(employeeEntity.getCountry());
		address.setZip_code(employeeEntity.getZip_code());
		employee.setAddress(address);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> createEmployee(Employee emp, Long header) {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();

		try {

			int Id = emp.getId();
			String fName = emp.getFirstName();
			String lName = emp.getLastName();
			String dob = emp.getDob();
			
			if (idempotencyRepository.existsById(header)) {

				return new ResponseEntity<>(HttpStatus.OK);

			} else {

				if (fName == "") {
					response = Constants.generateResponse(HttpStatus.BAD_REQUEST, "First Name should not be empty");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
				if (lName == "") {
					response = Constants.generateResponse(HttpStatus.BAD_REQUEST, "Last Name should not be  empty");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
				if (dob == "") {
					response = Constants.generateResponse(HttpStatus.BAD_REQUEST, "Date of Birt should not be  empty");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}

				employeeEntity.setDob(dob);

				employeeEntity.setFirstName(fName);

				employeeEntity.setLastName(lName);
				employeeEntity.setId(Id);

				address = mapper.convertValue(emp.getAddress(), Address.class);

				employeeEntity.setLine1(address.getLine1());
				employeeEntity.setLine2(address.getLine2());
				employeeEntity.setCity(address.getCity());
				employeeEntity.setState(address.getState());
				employeeEntity.setCountry(address.getCountry());
				employeeEntity.setZip_code(address.getZip_code());
				employeeEntity = employeeRepo.save(employeeEntity);

				idempotencyEntity.setIdempotencyKey(header);
				idempotencyRepository.save(idempotencyEntity);

				response = Constants.generateResponse(HttpStatus.CREATED, "Employee created successfully");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				response = Constants.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
						HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().toString());
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (Exception e1) {
				// TODO Auto-generated catch block

			}
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

}
