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
import com.example.easynotes.model.ManufacturersOfProductFittings;
import com.example.easynotes.repository.HumanResourcesRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/humanresources")
public class HumanResourcesController {

	@Autowired
	HumanResourcesRepository humanResourcesRepository;

	@GetMapping("/all")
	public List<HumanResources> getAll() {
		return humanResourcesRepository.findAll();
	}

	
	
	@GetMapping("/all/{type}")
	public List<HumanResources> getAllByType(@PathVariable(value = "type") String type) {
		return humanResourcesRepository.findByHumanResourceType(type);
	}
	
	@PostMapping("/create")
	public HumanResources create(@Valid @RequestBody HumanResources note) {
		return humanResourcesRepository.save(note);
	}

	@GetMapping("/getOne/{id}")
	public HumanResources getById(@PathVariable(value = "id") Long noteId) {
		return humanResourcesRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("HumanResources", "id", noteId));
	}

	@PutMapping("/update/{id}")
	public HumanResources update(@PathVariable(value = "id") Long noteId,
			@Valid @RequestBody HumanResources noteDetails) {

		HumanResources note = humanResourcesRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("HumanResources", "id", noteId));

		note.setCity(noteDetails.getCity());
		note.setEmail(noteDetails.getEmail());
		note.setFirstContact(noteDetails.getFirstContact());
		note.setHumanResourceType(noteDetails.getHumanResourceType());
		note.setName(noteDetails.getName());
		note.setPhoneNumber(noteDetails.getPhoneNumber());
		note.setGender(noteDetails.getGender());
		note.setIsPermanent(noteDetails.getIsPermanent());
		HumanResources updatedNote = humanResourcesRepository.save(note);
		return updatedNote;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		HumanResources note = humanResourcesRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("HumanResources", "id", noteId));

		humanResourcesRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}
