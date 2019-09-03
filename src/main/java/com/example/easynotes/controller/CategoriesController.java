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

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Categories;
import com.example.easynotes.repository.CategoriesRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoriesController {

	@Autowired
	CategoriesRepository categoriesRepository;

	@GetMapping("/all")
	public List<Categories> getAll() {
		return categoriesRepository.findAll();
	}

	@PostMapping("/create")
	public Categories create(@Valid @RequestBody Categories note) {
		return categoriesRepository.save(note);
	}

	@GetMapping("/getOne/{id}")
	public Categories getById(@PathVariable(value = "id") Long noteId) {
		return categoriesRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("CategoriesRepository", "id", noteId));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		Categories note = categoriesRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("CategoriesRepository", "id", noteId));

		categoriesRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}
