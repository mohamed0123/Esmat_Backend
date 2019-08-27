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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.datesObj.EmployeeAncestorObj;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.EmployeeAncestor;
import com.example.easynotes.model.EmployeeWorkHourAndSalary;
import com.example.easynotes.repository.EmployeeAncestorRepository;

/**
 * Created by Hamada on 07/28/2019.
 *  سلف الموظف
 */
@CrossOrigin
@RestController
@RequestMapping("/employeeancestor")
public class EmployeeAncestorController {

	@Autowired
	EmployeeAncestorRepository employeeAncestor;

	@GetMapping("/all")
	public List<EmployeeAncestor> getAll() {
		return employeeAncestor.findAll();
	}

	@PostMapping("/ByHR")
	public List<EmployeeAncestor> getAllByHr(@Valid @RequestBody EmployeeWorkHourAndSalary employeeWorkHourAndSalary) {
		return employeeAncestor.findByEmployeeWorkHourSalary(employeeWorkHourAndSalary);
	}

	@PostMapping("/getAllByEmployeeAncestorDate")
	public List<EmployeeAncestor> getAllByHrAndDate(@Valid @RequestBody EmployeeAncestorObj employeeAncestorObj) {

		return employeeAncestor.getAllBetweenDatesWithHr(employeeAncestorObj.getStartDate(),
				employeeAncestorObj.getEndDate(), employeeAncestorObj.getEmployeeWorkHourAndSalary().getId());
	}

	@PostMapping("/create")
	public EmployeeAncestor create(@Valid @RequestBody EmployeeAncestor note) {
 		return employeeAncestor.save(note);
	}

	@GetMapping("/getOne/{id}")
	public EmployeeAncestor getById(@PathVariable(value = "id") Long noteId) {
		return employeeAncestor.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeAncestorRepository", "id", noteId));
	}
 
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		EmployeeAncestor note = employeeAncestor.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeAncestorRepository", "id", noteId));

		employeeAncestor.delete(note);

		return ResponseEntity.ok().build();
	}
}
