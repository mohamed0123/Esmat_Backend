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
		return purchases.findAll();
	}

	@PostMapping("/ByHR")
	public List<Purchases> getAllByHr(@Valid @RequestBody HumanResources hr) {
		return purchases.findByHumanResources(hr);
	}

	@PostMapping("/ByHRAndDate")
	public List<Purchases> getAllByHrAndDate(@Valid @RequestBody PurchasesObj purchasesObj) {

		return purchases.getAllBetweenDatesWithHr(
				purchasesObj.getStartDate(), purchasesObj.getEndDate() , purchasesObj.getHr().getId());
	}

	@PostMapping("/create")
	public Purchases create(@Valid @RequestBody Purchases note) {

		if (note.getId() == null) {
			Long hrPrevMaxId = purchases
					.findMaxIdByHumanResource(note.getHumanResources().getId());
			// له = السعر فى الكميه
			note.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());

			if (hrPrevMaxId != null && hrPrevMaxId > 0) {
				Purchases previousOne = purchases
						.findById(hrPrevMaxId)
						.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository",
								"id", hrPrevMaxId));

				note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getPayments());

			} else {
				note.setTotalPrice(note.getPriceAllKillios() - note.getPayments());
			}
		} else {

			Long maxId = purchases
					.findMaxIdByHumanResource(note.getHumanResources().getId());
			Purchases noteO = null;
			if (maxId == note.getId()) {

				noteO = purchases.findById(note.getId())
						.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository",
								"id", note.getId()));
				Long hrPrevMaxId = purchases
						.findMaxIdByHumanResourceAndNotThisOne(note.getHumanResources().getId(), note.getId());
				noteO.setPriceInKillo(note.getPriceInKillo());
				// مدفوعات
				noteO.setPayments(note.getPayments());
				noteO.setcategory(note.getcategory());
				noteO.setHumanResources(note.getHumanResources());
				// له = السعر فى الكميه
				noteO.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());
				// الكميه
				noteO.setQuantityInKillo(note.getQuantityInKillo());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					Purchases previousOne = purchases
							.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository",
									"id", hrPrevMaxId));
					// له - مدفوعات
					noteO.setTotalPrice(
							previousOne.getTotalPrice() + noteO.getPriceAllKillios() - noteO.getPayments());

				} else {
					// له - مدفوعات
					noteO.setTotalPrice(noteO.getPriceAllKillios() - noteO.getPayments());
				}

			} else {

				new ResourceNotFoundException("you can only edit the last record", "id", note.getId());
				return null;
			}
			Purchases updatedNote = purchases.save(noteO);
			return updatedNote;
		}
		// له - مدفوعات
		return purchases.save(note);
	}

	@GetMapping("/getOne/{id}")
	public Purchases getById(@PathVariable(value = "id") Long noteId) {
		return purchases.findById(noteId).orElseThrow(
				() -> new ResourceNotFoundException("PurchasesRepository", "id", noteId));
	}

	@PutMapping("/update/{id}")
	public Purchases update(@PathVariable(value = "id") Long noteId,
			@Valid @RequestBody Purchases noteDetails) {

		Long maxId = purchases
				.findMaxIdByHumanResource(noteDetails.getHumanResources().getId());
		Purchases note = null;
		if (maxId != noteId) {

			note = purchases.findById(noteId).orElseThrow(
					() -> new ResourceNotFoundException("PurchasesRepository", "id", noteId));
			Long hrPrevMaxId = purchases
					.findMaxIdByHumanResourceAndNotThisOne(noteDetails.getHumanResources().getId(), noteId);

			// مدفوعات
			note.setPayments(noteDetails.getPayments());
			note.setcategory(noteDetails.getcategory());
			note.setHumanResources(noteDetails.getHumanResources());
			// له = السعر فى الكميه
			note.setPriceAllKillios(noteDetails.getPriceInKillo() * noteDetails.getQuantityInKillo());
			// الكميه
			note.setQuantityInKillo(noteDetails.getQuantityInKillo());

			if (hrPrevMaxId != null && hrPrevMaxId > 0) {
				Purchases previousOne = purchases
						.findById(hrPrevMaxId)
						.orElseThrow(() -> new ResourceNotFoundException("PurchasesRepository",
								"id", hrPrevMaxId));
				// له - مدفوعات
				note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getPayments());

			} else {
				// له - مدفوعات
				note.setTotalPrice(note.getPriceAllKillios() - note.getPayments());
			}

		} else {

			new ResourceNotFoundException("you can only edit the last record", "id", noteId);

		}
		Purchases updatedNote = purchases.save(note);
		return updatedNote;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		Purchases note = purchases.findById(noteId).orElseThrow(
				() -> new ResourceNotFoundException("PurchasesRepository", "id", noteId));

		purchases.delete(note);

		return ResponseEntity.ok().build();
	}
}
