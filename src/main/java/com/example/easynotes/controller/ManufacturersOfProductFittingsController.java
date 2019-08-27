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

import com.example.easynotes.datesObj.MF_O_P_F;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.HumanResources;
import com.example.easynotes.model.ManufacturersOfProductFittings;
import com.example.easynotes.repository.ManufacturersOfProductFittingsRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/manufacturersOfproductfittings")
public class ManufacturersOfProductFittingsController {

	@Autowired
	ManufacturersOfProductFittingsRepository manufacturersOfProductFittingsRepository;

	@GetMapping("/all")
	public List<ManufacturersOfProductFittings> getAll() {
		return manufacturersOfProductFittingsRepository.findAll();
	}

	@PostMapping("/ByHR")
	public List<ManufacturersOfProductFittings> getAllByHr(@Valid @RequestBody HumanResources hr) {
		return manufacturersOfProductFittingsRepository.findByHumanResources(hr);
	}

	@PostMapping("/ByHRAndDate")
	public List<ManufacturersOfProductFittings> getAllByHrAndDate(@Valid @RequestBody MF_O_P_F mfopf) {

		return manufacturersOfProductFittingsRepository.getAllBetweenDatesWithHr(
				mfopf.getStartDate(), mfopf.getEndDate() , mfopf.getHr().getId());
	}

	@PostMapping("/create")
	public ManufacturersOfProductFittings create(@Valid @RequestBody ManufacturersOfProductFittings note) {

		if (note.getId() == null) {
			Long hrPrevMaxId = manufacturersOfProductFittingsRepository
					.findMaxIdByHumanResource(note.getHumanResources().getId());
			// له = السعر فى الكميه
			note.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());

			if (hrPrevMaxId != null && hrPrevMaxId > 0) {
				ManufacturersOfProductFittings previousOne = manufacturersOfProductFittingsRepository
						.findById(hrPrevMaxId)
						.orElseThrow(() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository",
								"id", hrPrevMaxId));

				note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getAdvanceAccounts());

			} else {
				note.setTotalPrice(note.getPriceAllKillios() - note.getAdvanceAccounts());
			}
		} else {

			Long maxId = manufacturersOfProductFittingsRepository
					.findMaxIdByHumanResource(note.getHumanResources().getId());
			ManufacturersOfProductFittings noteO = null;
			if (maxId == note.getId()) {

				noteO = manufacturersOfProductFittingsRepository.findById(note.getId())
						.orElseThrow(() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository",
								"id", note.getId()));
				Long hrPrevMaxId = manufacturersOfProductFittingsRepository
						.findMaxIdByHumanResourceAndNotThisOne(note.getHumanResources().getId(), note.getId());
				noteO.setPriceInKillo(note.getPriceInKillo());
				// السلف
				noteO.setAdvanceAccounts(note.getAdvanceAccounts());
				noteO.setcategory(note.getcategory());
				noteO.setHumanResources(note.getHumanResources());
				// له = السعر فى الكميه
				noteO.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());
				// الكميه
				noteO.setQuantityInKillo(note.getQuantityInKillo());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					ManufacturersOfProductFittings previousOne = manufacturersOfProductFittingsRepository
							.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository",
									"id", hrPrevMaxId));
					// له - السلف
					noteO.setTotalPrice(
							previousOne.getTotalPrice() + noteO.getPriceAllKillios() - noteO.getAdvanceAccounts());

				} else {
					// له - السلف
					noteO.setTotalPrice(noteO.getPriceAllKillios() - noteO.getAdvanceAccounts());
				}

			} else {

				new ResourceNotFoundException("you can only edit the last record", "id", note.getId());
				return null;
			}
			ManufacturersOfProductFittings updatedNote = manufacturersOfProductFittingsRepository.save(noteO);
			return updatedNote;
		}
		// له - السلف
		return manufacturersOfProductFittingsRepository.save(note);
	}

	@GetMapping("/getOne/{id}")
	public ManufacturersOfProductFittings getById(@PathVariable(value = "id") Long noteId) {
		return manufacturersOfProductFittingsRepository.findById(noteId).orElseThrow(
				() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository", "id", noteId));
	}

	@PutMapping("/update/{id}")
	public ManufacturersOfProductFittings update(@PathVariable(value = "id") Long noteId,
			@Valid @RequestBody ManufacturersOfProductFittings noteDetails) {

		Long maxId = manufacturersOfProductFittingsRepository
				.findMaxIdByHumanResource(noteDetails.getHumanResources().getId());
		ManufacturersOfProductFittings note = null;
		if (maxId != noteId) {

			note = manufacturersOfProductFittingsRepository.findById(noteId).orElseThrow(
					() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository", "id", noteId));
			Long hrPrevMaxId = manufacturersOfProductFittingsRepository
					.findMaxIdByHumanResourceAndNotThisOne(noteDetails.getHumanResources().getId(), noteId);

			// السلف
			note.setAdvanceAccounts(noteDetails.getAdvanceAccounts());
			note.setcategory(noteDetails.getcategory());
			note.setHumanResources(noteDetails.getHumanResources());
			// له = السعر فى الكميه
			note.setPriceAllKillios(noteDetails.getPriceInKillo() * noteDetails.getQuantityInKillo());
			// الكميه
			note.setQuantityInKillo(noteDetails.getQuantityInKillo());

			if (hrPrevMaxId != null && hrPrevMaxId > 0) {
				ManufacturersOfProductFittings previousOne = manufacturersOfProductFittingsRepository
						.findById(hrPrevMaxId)
						.orElseThrow(() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository",
								"id", hrPrevMaxId));
				// له - السلف
				note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getAdvanceAccounts());

			} else {
				// له - السلف
				note.setTotalPrice(note.getPriceAllKillios() - note.getAdvanceAccounts());
			}

		} else {

			new ResourceNotFoundException("you can only edit the last record", "id", noteId);

		}
		ManufacturersOfProductFittings updatedNote = manufacturersOfProductFittingsRepository.save(note);
		return updatedNote;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		ManufacturersOfProductFittings note = manufacturersOfProductFittingsRepository.findById(noteId).orElseThrow(
				() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository", "id", noteId));

		manufacturersOfProductFittingsRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}
