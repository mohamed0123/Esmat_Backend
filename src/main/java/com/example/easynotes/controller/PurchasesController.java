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

import com.example.easynotes.datesObj.PurchasesObj;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.HumanResources;
import com.example.easynotes.model.Purchases;
import com.example.easynotes.repository.PurchasesRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/purchases")
public class PurchasesController {

	@Autowired
	PurchasesRepository purchases;

	@GetMapping("/all")
	public List<Purchases> getAll() {
		return purchases.findAllByOrderByIdAsc();
	}

	@PostMapping("/ByHR")
	public List<Purchases> getAllByHr(@Valid @RequestBody HumanResources hr) {
		return purchases.findByHumanResources(hr);
	}

	@PostMapping("/ByHRAndDate")
	public List<Purchases> getAllByHrAndDate(@Valid @RequestBody PurchasesObj purchasesObj) {

		return purchases.getAllBetweenDatesWithHr(purchasesObj.getStartDate(), purchasesObj.getEndDate(),
				purchasesObj.getHr().getId());
	}

	public void insertNew(Purchases note) {

		Long hrPrevMaxId = purchases.findMaxIdByHumanResource(note.getHumanResources().getId());
		// له = السعر فى الكميه
		note.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());

		if (hrPrevMaxId != null && hrPrevMaxId > 0) {
			Purchases previousOne = purchases.findById(hrPrevMaxId)
					.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository", "id", hrPrevMaxId));

			note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getPayments());

		} else {
			note.setTotalPrice(note.getPriceAllKillios() - note.getPayments());
		}

		purchases.save(note);
	}

	public void update(Purchases note) {

		List<Purchases> editableList = purchases.getListToEdit(note.getId(), note.getHumanResources().getId());

		Purchases currentNote = null;
		for (int i = 0; i < editableList.size(); i++) {

			currentNote = editableList.get(i);

			if (currentNote.getId() == note.getId()) {
				Long hrPrevMaxId = purchases.findMaxPreviousId(note.getId(), note.getHumanResources().getId());

				currentNote.setPriceInKillo(note.getPriceInKillo());
				// مدفوعات
				currentNote.setPayments(note.getPayments());
				currentNote.setcategory(note.getcategory());
				currentNote.setHumanResources(note.getHumanResources());
				// له = السعر فى الكميه
				currentNote.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());
				// الكميه
				currentNote.setQuantityInKillo(note.getQuantityInKillo());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					Purchases previousOne = purchases.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository", "id", hrPrevMaxId));
					// له - مدفوعات
					currentNote.setTotalPrice(
							previousOne.getTotalPrice() + currentNote.getPriceAllKillios() - currentNote.getPayments());

				} else {
					// له - مدفوعات
					currentNote.setTotalPrice(currentNote.getPriceAllKillios() - currentNote.getPayments());
				}
				purchases.save(currentNote);
			} else {
				Long hrPrevMaxId = purchases.findMaxPreviousId(currentNote.getId(),
						currentNote.getHumanResources().getId());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					Purchases previousOne = purchases.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository", "id", hrPrevMaxId));
					// له - مدفوعات
					currentNote.setTotalPrice(
							previousOne.getTotalPrice() + currentNote.getPriceAllKillios() - currentNote.getPayments());

				} else {
					// له - مدفوعات
					currentNote.setTotalPrice(currentNote.getPriceAllKillios() - currentNote.getPayments());
				}

				purchases.save(currentNote);

			}
		}
	}

	@PostMapping("/create")
	public Purchases create(@Valid @RequestBody Purchases note) {

		if (note.getId() == null) {
			insertNew(note);
		} else {

			update(note);
		}

		return purchases.findById(note.getId())
				.orElseThrow(() -> new ResourceNotFoundException("purchases", "id", note.getId()));
	}

	@GetMapping("/getOne/{id}")
	public Purchases getById(@PathVariable(value = "id") Long noteId) {
		return purchases.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository", "id", noteId));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		Purchases note = purchases.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository", "id", noteId));

		purchases.delete(note);

		return ResponseEntity.ok().build();
	}
}
