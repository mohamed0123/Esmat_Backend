package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.HumanResources;
import com.example.easynotes.model.EmployeeWorkHourAndSalary;
import com.example.easynotes.repository.EmployeeWorkHourAndSalaryRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/employeeworkhourandsalary")
public class EmployeeWorkHourAndSalaryController {

	@Autowired
	EmployeeWorkHourAndSalaryRepository employeeWorkHourAndSalary;

	@GetMapping("/all")
	public List<EmployeeWorkHourAndSalary> getAll() {
		return employeeWorkHourAndSalary.findAll();
	}

	@PostMapping("/ByHR")
	public List<EmployeeWorkHourAndSalary> getAllByHr(@Valid @RequestBody HumanResources hr) {
		return employeeWorkHourAndSalary.findByHumanResources(hr);
	}

	@PostMapping("/create")
	public EmployeeWorkHourAndSalary create(@Valid @RequestBody EmployeeWorkHourAndSalary note) {

		return employeeWorkHourAndSalary.save(note);
	}

	@GetMapping("/getOne/{id}")
	public EmployeeWorkHourAndSalary getById(@PathVariable(value = "id") Long noteId) {
		return employeeWorkHourAndSalary.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeWorkHourAndSalaryRepository", "id", noteId));
	}

	@PutMapping("/update/{id}")
	public EmployeeWorkHourAndSalary update(@PathVariable(value = "id") Long noteId,
			@Valid @RequestBody EmployeeWorkHourAndSalary noteDetails) {
		EmployeeWorkHourAndSalary updatedNote = employeeWorkHourAndSalary.save(noteDetails);
		return updatedNote;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		EmployeeWorkHourAndSalary note = employeeWorkHourAndSalary.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeWorkHourAndSalaryRepository", "id", noteId));

		employeeWorkHourAndSalary.delete(note);

		return ResponseEntity.ok().build();
	}
}
