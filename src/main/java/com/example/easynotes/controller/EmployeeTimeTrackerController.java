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

import com.example.easynotes.datesObj.EmployeeTimeTrackerObj;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.HumanResources;
import com.example.easynotes.model.EmployeeTimeTracker;
import com.example.easynotes.repository.EmployeeTimeTrackerRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/employeetimetracker")
public class EmployeeTimeTrackerController {

	@Autowired
	EmployeeTimeTrackerRepository employeeTimeTracker;

	@GetMapping("/all")
	public List<EmployeeTimeTracker> getAll() {
		return employeeTimeTracker.findAll();
	}

	@PostMapping("/ByHR")
	public List<EmployeeTimeTracker> getAllByHr(@Valid @RequestBody HumanResources hr) {
		return employeeTimeTracker.findByHumanResources(hr);
	}

	@PostMapping("/ByHRAndDate")
	public List<EmployeeTimeTracker> getAllByHrAndDate(
			@Valid @RequestBody EmployeeTimeTrackerObj employeeTimeTrackerObj) {

		return employeeTimeTracker.getAllBetweenDatesWithHr(employeeTimeTrackerObj.getStartDate(),
				employeeTimeTrackerObj.getEndDate(), employeeTimeTrackerObj.getHr().getId());
	}

	@PostMapping("/create")
	public EmployeeTimeTracker create(@Valid @RequestBody EmployeeTimeTracker note) {
		return employeeTimeTracker.save(note);
	}

	@GetMapping("/getOne/{id}")
	public EmployeeTimeTracker getById(@PathVariable(value = "id") Long noteId) {
		return employeeTimeTracker.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeTimeTrackerRepository", "id", noteId));
	}

	@PutMapping("/update/{id}")
	public EmployeeTimeTracker update(@PathVariable(value = "id") Long noteId,
			@Valid @RequestBody EmployeeTimeTracker noteDetails) {
		return employeeTimeTracker.save(noteDetails);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		EmployeeTimeTracker note = employeeTimeTracker.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeTimeTrackerRepository", "id", noteId));

		employeeTimeTracker.delete(note);

		return ResponseEntity.ok().build();
	}
}
