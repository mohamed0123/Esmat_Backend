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

import com.example.easynotes.datesObj.OtherExpensesDatesObj;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.OtherExpenses;
import com.example.easynotes.repository.OtherExpensesRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/otherexpenses")
public class OtherExpensesController {

	@Autowired
	OtherExpensesRepository otherExpensesRepository;

	@GetMapping("/all")
	public List<OtherExpenses> getAll() {
		return otherExpensesRepository.findAll();
	}

	@PostMapping("/byDate")
	public List<OtherExpenses> getAllByHrAndDate(@Valid @RequestBody OtherExpensesDatesObj mfopf) {

		return otherExpensesRepository.getAllBetweenDates(
				mfopf.getStartDate(), mfopf.getEndDate() );
	}
	
	@PostMapping("/create")
	public OtherExpenses create(@Valid @RequestBody OtherExpenses note) {
		return otherExpensesRepository.save(note);
	}

	@GetMapping("/getOne/{id}")
	public OtherExpenses getById(@PathVariable(value = "id") Long noteId) {
		return otherExpensesRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("OtherExpensesRepository", "id", noteId));
	}

	@PutMapping("/update/{id}")
	public OtherExpenses update(@PathVariable(value = "id") Long noteId, @Valid @RequestBody OtherExpenses noteDetails) {

		OtherExpenses note = otherExpensesRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("OtherExpensesRepository", "id", noteId));

		note.setTypeOfPiecesConsumed(noteDetails.getTypeOfPiecesConsumed());
		note.setPrice(noteDetails.getPrice());
		OtherExpenses updatedNote = otherExpensesRepository.save(note);
		return updatedNote;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		OtherExpenses note = otherExpensesRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("OtherExpensesRepository", "id", noteId));

		otherExpensesRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}
