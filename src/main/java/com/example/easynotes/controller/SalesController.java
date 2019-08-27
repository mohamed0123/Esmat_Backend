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

import com.example.easynotes.datesObj.SalesObj;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.HumanResources;
import com.example.easynotes.model.Sales;
import com.example.easynotes.repository.SalesRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/sales")
public class SalesController {

	@Autowired
	SalesRepository sales;

	@GetMapping("/all")
	public List<Sales> getAll() {
		return sales.findAll();
	}

	@PostMapping("/ByHR")
	public List<Sales> getAllByHr(@Valid @RequestBody HumanResources hr) {
		return sales.findByHumanResources(hr);
	}

	@PostMapping("/ByHRAndDate")
	public List<Sales> getAllByHrAndDate(@Valid @RequestBody SalesObj salesObj) {

		return sales.getAllBetweenDatesWithHr(
				salesObj.getStartDate(), salesObj.getEndDate() , salesObj.getHr().getId());
	}

	@PostMapping("/create")
	public Sales create(@Valid @RequestBody Sales note) {

		if (note.getId() == null) {
			Long hrPrevMaxId = sales
					.findMaxIdByHumanResource(note.getHumanResources().getId());
			// صادر السعر الكلى للمنتج  = السعر فى الكميه
			note.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());

			if (hrPrevMaxId != null && hrPrevMaxId > 0) {
				Sales previousOne = sales
						.findById(hrPrevMaxId)
						.orElseThrow(() -> new ResourceNotFoundException("SalesRepository",
								"id", hrPrevMaxId));

				note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getIncomingMoney());

			} else {
				note.setTotalPrice(note.getPriceAllKillios() - note.getIncomingMoney());
			}
		} else {

			Long maxId = sales
					.findMaxIdByHumanResource(note.getHumanResources().getId());
			Sales noteO = null;
			if (maxId == note.getId()) {

				noteO = sales.findById(note.getId())
						.orElseThrow(() -> new ResourceNotFoundException("SalesRepository",
								"id", note.getId()));
				Long hrPrevMaxId = sales
						.findMaxIdByHumanResourceAndNotThisOne(note.getHumanResources().getId(), note.getId());

				noteO.setPriceInKillo(note.getPriceInKillo());
				// وارد الفلوس اللى دفعها
				noteO.setIncomingMoney(note.getIncomingMoney());
				noteO.setcategory(note.getcategory());
				noteO.setHumanResources(note.getHumanResources());
				// صادر السعر الكلى للمنتج  = السعر فى الكميه
				noteO.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());
				// الكميه
				noteO.setQuantityInKillo(note.getQuantityInKillo());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					Sales previousOne = sales
							.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("SalesRepository",
									"id", hrPrevMaxId));
					// صادر السعر الكلى للمنتج  - وارد الفلوس اللى دفعها
					noteO.setTotalPrice(
							previousOne.getTotalPrice() + noteO.getPriceAllKillios() - noteO.getIncomingMoney());

				} else {
					// صادر السعر الكلى للمنتج  - وارد الفلوس اللى دفعها
					noteO.setTotalPrice(noteO.getPriceAllKillios() - noteO.getIncomingMoney());
				}

			} else {

				new ResourceNotFoundException("you can only edit the last record", "id", note.getId());
				return null;
			}
			Sales updatedNote = sales.save(noteO);
			return updatedNote;
		}
		// صادر السعر الكلى للمنتج  - وارد الفلوس اللى دفعها
		return sales.save(note);
	}

	@GetMapping("/getOne/{id}")
	public Sales getById(@PathVariable(value = "id") Long noteId) {
		return sales.findById(noteId).orElseThrow(
				() -> new ResourceNotFoundException("SalesRepository", "id", noteId));
	}

	@PutMapping("/update/{id}")
	public Sales update(@PathVariable(value = "id") Long noteId,
			@Valid @RequestBody Sales noteDetails) {

		Long maxId = sales
				.findMaxIdByHumanResource(noteDetails.getHumanResources().getId());
		Sales note = null;
		if (maxId != noteId) {

			note = sales.findById(noteId).orElseThrow(
					() -> new ResourceNotFoundException("SalesRepository", "id", noteId));
			Long hrPrevMaxId = sales
					.findMaxIdByHumanResourceAndNotThisOne(noteDetails.getHumanResources().getId(), noteId);

			// وارد الفلوس اللى دفعها
			note.setIncomingMoney(noteDetails.getIncomingMoney());
			note.setcategory(noteDetails.getcategory());
			note.setHumanResources(noteDetails.getHumanResources());
			// صادر السعر الكلى للمنتج  = السعر فى الكميه
			note.setPriceAllKillios(noteDetails.getPriceInKillo() * noteDetails.getQuantityInKillo());
			// الكميه
			note.setQuantityInKillo(noteDetails.getQuantityInKillo());

			if (hrPrevMaxId != null && hrPrevMaxId > 0) {
				Sales previousOne = sales
						.findById(hrPrevMaxId)
						.orElseThrow(() -> new ResourceNotFoundException("SalesRepository",
								"id", hrPrevMaxId));
				// صادر السعر الكلى للمنتج  - وارد الفلوس اللى دفعها
				note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getIncomingMoney());

			} else {
				// صادر السعر الكلى للمنتج  - وارد الفلوس اللى دفعها
				note.setTotalPrice(note.getPriceAllKillios() - note.getIncomingMoney());
			}

		} else {

			new ResourceNotFoundException("you can only edit the last record", "id", noteId);

		}
		Sales updatedNote = sales.save(note);
		return updatedNote;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		Sales note = sales.findById(noteId).orElseThrow(
				() -> new ResourceNotFoundException("SalesRepository", "id", noteId));

		sales.delete(note);

		return ResponseEntity.ok().build();
	}
}
