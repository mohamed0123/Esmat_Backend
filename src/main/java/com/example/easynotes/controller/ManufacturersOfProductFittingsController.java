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
		return manufacturersOfProductFittingsRepository.findAllByOrderByIdAsc();
	}

	@PostMapping("/ByHR")
	public List<ManufacturersOfProductFittings> getAllByHr(@Valid @RequestBody HumanResources hr) {
		return manufacturersOfProductFittingsRepository.findByHumanResources(hr);
	}

	@PostMapping("/ByHRAndDate")
	public List<ManufacturersOfProductFittings> getAllByHrAndDate(@Valid @RequestBody MF_O_P_F mfopf) {

		return manufacturersOfProductFittingsRepository.getAllBetweenDatesWithHr(mfopf.getStartDate(),
				mfopf.getEndDate(), mfopf.getHr().getId());
	}

	public void insertNew(ManufacturersOfProductFittings note) {

		Long hrPrevMaxId = manufacturersOfProductFittingsRepository
				.findMaxIdByHumanResource(note.getHumanResources().getId());
		// له = السعر فى الكميه
		note.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());

		if (hrPrevMaxId != null && hrPrevMaxId > 0) {
			ManufacturersOfProductFittings previousOne = manufacturersOfProductFittingsRepository.findById(hrPrevMaxId)
					.orElseThrow(() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository", "id",
							hrPrevMaxId));

			note.setTotalPrice(previousOne.getTotalPrice() + note.getPriceAllKillios() - note.getAdvanceAccounts());

		} else {
			note.setTotalPrice(note.getPriceAllKillios() - note.getAdvanceAccounts());
		}
		manufacturersOfProductFittingsRepository.save(note);
	}

	public void update(ManufacturersOfProductFittings note) {

		List<ManufacturersOfProductFittings> editableList = manufacturersOfProductFittingsRepository
				.getListToEdit(note.getId(), note.getHumanResources().getId());

		ManufacturersOfProductFittings currentNote = null;
		for (int i = 0; i < editableList.size(); i++) {

			currentNote = editableList.get(i);

			if (currentNote.getId() == note.getId()) {
				Long hrPrevMaxId = manufacturersOfProductFittingsRepository.findMaxPreviousId(note.getId(),
						note.getHumanResources().getId());

				currentNote.setPriceInKillo(note.getPriceInKillo());
				// السلف
				currentNote.setAdvanceAccounts(note.getAdvanceAccounts());
				currentNote.setcategory(note.getcategory());
				currentNote.setHumanResources(note.getHumanResources());
				// له = السعر فى الكميه
				currentNote.setPriceAllKillios(note.getPriceInKillo() * note.getQuantityInKillo());
				// الكميه
				currentNote.setQuantityInKillo(note.getQuantityInKillo());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					ManufacturersOfProductFittings previousOne = manufacturersOfProductFittingsRepository
							.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository",
									"id", hrPrevMaxId));
					// له - السلف
					currentNote.setTotalPrice(previousOne.getTotalPrice() + currentNote.getPriceAllKillios()
							- currentNote.getAdvanceAccounts());

				} else {
					// له - السلف
					currentNote.setTotalPrice(currentNote.getPriceAllKillios() - currentNote.getAdvanceAccounts());
				}
				manufacturersOfProductFittingsRepository.save(currentNote);
			} else {
				Long hrPrevMaxId = manufacturersOfProductFittingsRepository.findMaxPreviousId(currentNote.getId(),
						currentNote.getHumanResources().getId());

				if (hrPrevMaxId != null && hrPrevMaxId > 0) {
					ManufacturersOfProductFittings previousOne = manufacturersOfProductFittingsRepository
							.findById(hrPrevMaxId)
							.orElseThrow(() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository",
									"id", hrPrevMaxId));
					// صادر السعر الكلى للمنتج - وارد الفلوس اللى دفعها
					currentNote.setTotalPrice(previousOne.getTotalPrice() + currentNote.getPriceAllKillios()
							- currentNote.getAdvanceAccounts());

				} else {
					// له - السلف
					currentNote.setTotalPrice(currentNote.getPriceAllKillios() - currentNote.getAdvanceAccounts());
				}

				manufacturersOfProductFittingsRepository.save(currentNote);

			}
		}
	}

	@PostMapping("/create")
	public ManufacturersOfProductFittings create(@Valid @RequestBody ManufacturersOfProductFittings note) {

		if (note.getId() == null) {
			insertNew(note);
		} else {

			update(note);
		}
		// له - السلف
		return manufacturersOfProductFittingsRepository.findById(note.getId()).orElseThrow(
				() -> new ResourceNotFoundException("manufacturersOfProductFittingsRepository", "id", note.getId()));
	}

	@GetMapping("/getOne/{id}")
	public ManufacturersOfProductFittings getById(@PathVariable(value = "id") Long noteId) {
		return manufacturersOfProductFittingsRepository.findById(noteId).orElseThrow(
				() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository", "id", noteId));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long noteId) {
		ManufacturersOfProductFittings note = manufacturersOfProductFittingsRepository.findById(noteId).orElseThrow(
				() -> new ResourceNotFoundException("ManufacturersOfProductFittingsRepository", "id", noteId));

		manufacturersOfProductFittingsRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}
