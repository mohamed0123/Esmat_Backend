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
		return sales.findAllByOrderByIdAsc();
	}

	@PostMapping("/previoustotalpriceforhr")
	public Long getPreviousTotalPriceForHr(@Valid @RequestBody HumanResources hr) {

		Long previousTotalPrice = 0L;
		Long hrPrevMaxId = sales.findMaxIdByHumanResource(hr.getId());
		if (hrPrevMaxId != null)
			previousTotalPrice = sales.findPreviousTotalPriceForHumanResource(hrPrevMaxId);
		return previousTotalPrice;
	}

	@PostMapping("/ByHR")
	public List<Sales> getAllByHr(@Valid @RequestBody HumanResources hr) {
		return sales.findByHumanResources(hr);
	}

	@PostMapping("/ByHRAndDate")
	public List<Sales> getAllByHrAndDate(@Valid @RequestBody SalesObj salesObj) {

		return sales.getAllBetweenDatesWithHr(salesObj.getStartDate(), salesObj.getEndDate(), salesObj.getHr().getId());
	}

	public void insertNew(Sales note) {

		Long hrPrevMaxId = sales.findMaxIdByHumanResource(note.getHumanResources().getId());
		// صادر السعر الكلى للمنتج = السعر فى الكميه
		note.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());

		if (hrPrevMaxId != null && hrPrevMaxId > 0) {
			Sales previousOne = sales.findById(hrPrevMaxId)
					.orElseThrow(() -> new ResourceNotFoundException("SalesRepository", "id", hrPrevMaxId));

			note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getIncomingMoney());

		} else {
			note.setTotalPrice(note.getPriceAllKillios() - note.getIncomingMoney());
		}

		sales.save(note);
	}

	public void update(Sales note) {

		List<Sales> editableList = sales.getListToEdit(note.getId(), note.getHumanResources().getId());

		Sales currentNote = null;
		for (int i = 0; i < editableList.size(); i++) {

			currentNote = editableList.get(i);

			if (currentNote.getId() == note.getId()) {
				Long hrPrevMaxId = sales.findMaxPreviousId(note.getId(), note.getHumanResources().getId());

				currentNote.setPriceInKillo(note.getPriceInKillo());
				// وارد الفلوس اللى دفعها
				currentNote.setIncomingMoney(note.getIncomingMoney());
				currentNote.setcategory(note.getcategory());
				currentNote.setHumanResources(note.getHumanResources());
				// صادر السعر الكلى للمنتج = السعر فى الكميه
				currentNote.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());
				// الكميه
				currentNote.setQuantityInKillo(note.getQuantityInKillo());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					Sales previousOne = sales.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("SalesRepository", "id", hrPrevMaxId));
					// صادر السعر الكلى للمنتج - وارد الفلوس اللى دفعها
					currentNote.setTotalPrice(previousOne.getTotalPrice() + currentNote.getPriceAllKillios()
							- currentNote.getIncomingMoney());

				} else {
					// صادر السعر الكلى للمنتج - وارد الفلوس اللى دفعها
					currentNote.setTotalPrice(currentNote.getPriceAllKillios() - currentNote.getIncomingMoney());
				}
				sales.save(currentNote);
			} else {
				Long hrPrevMaxId = sales.findMaxPreviousId(currentNote.getId(),
						currentNote.getHumanResources().getId());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					Sales previousOne = sales.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("SalesRepository", "id", hrPrevMaxId));
					// صادر السعر الكلى للمنتج - وارد الفلوس اللى دفعها
					currentNote.setTotalPrice(previousOne.getTotalPrice() + currentNote.getPriceAllKillios()
							- currentNote.getIncomingMoney());

				} else {
					// صادر السعر الكلى للمنتج - وارد الفلوس اللى دفعها
					currentNote.setTotalPrice(currentNote.getPriceAllKillios() - currentNote.getIncomingMoney());
				}

				sales.save(currentNote);

			}
		}
	}

	@PostMapping("/create")
	public Sales create(@Valid @RequestBody Sales note) {

		if (note.getId() == null) {
			insertNew(note);
		} else {
			update(note);
		}

		return sales.findById(note.getId())
				.orElseThrow(() -> new ResourceNotFoundException("SalesRepository", "id", note.getId()));
	}

	@GetMapping("/getOne/{id}")
	public Sales getById(@PathVariable(value = "id") Long noteId) {
		return sales.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("SalesRepository", "id", noteId));
	}
 

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		Sales note = sales.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("SalesRepository", "id", noteId));

		sales.delete(note);

		return ResponseEntity.ok().build();
	}
}
