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

import com.example.easynotes.datesObj.DeceasedDatesObj;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Deceased;
import com.example.easynotes.repository.DeceasedRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/deceased")
public class DeceasedController {

	@Autowired
	DeceasedRepository deceasedRepository;

	@GetMapping("/all")
	public List<Deceased> getAll() {
		return deceasedRepository.findAll();
	}

	@PostMapping("/byDate")
	public List<Deceased> getAllByHrAndDate(@Valid @RequestBody DeceasedDatesObj mfopf) {

		return deceasedRepository.getAllBetweenDates(
				mfopf.getStartDate(), mfopf.getEndDate() );
	}
	
	@PostMapping("/create")
	public Deceased create(@Valid @RequestBody Deceased note) {
		return deceasedRepository.save(note);
	}

	@GetMapping("/getOne/{id}")
	public Deceased getById(@PathVariable(value = "id") Long noteId) {
		return deceasedRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("DeceasedRepository", "id", noteId));
	}

	@PutMapping("/update/{id}")
	public Deceased update(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Deceased noteDetails) {

		Deceased note = deceasedRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("DeceasedRepository", "id", noteId));

		note.setTypeOfPiecesConsumed(noteDetails.getTypeOfPiecesConsumed());
		note.setPrice(noteDetails.getPrice());
		Deceased updatedNote = deceasedRepository.save(note);
		return updatedNote;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		Deceased note = deceasedRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("DeceasedRepository", "id", noteId));

		deceasedRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}
