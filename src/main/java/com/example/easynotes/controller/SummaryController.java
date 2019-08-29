package com.example.easynotes.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.datesObj.SummaryDetailes;
import com.example.easynotes.datesObj.SummaryObj;
import com.example.easynotes.repository.DeceasedRepository;
import com.example.easynotes.repository.ManufacturersOfProductFittingsRepository;
import com.example.easynotes.repository.OtherExpensesRepository;
import com.example.easynotes.repository.PurchasesRepository;
import com.example.easynotes.repository.SalesRepository;

/**
 * Created by Hamada on 07/28/2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/summary")
public class SummaryController {

	@Autowired
	SalesRepository sales;
	@Autowired
	DeceasedRepository deceasedRepository;
	@Autowired
	ManufacturersOfProductFittingsRepository manufacturersOfProductFittingsRepository;
	@Autowired
	OtherExpensesRepository otherExpensesRepository;
	@Autowired
	PurchasesRepository purchasesRepository;

	@PostMapping("/ByHRAndDate")
	public List<SummaryDetailes> getAllSummary(@Valid @RequestBody SummaryObj summaryObj) {
		List<SummaryDetailes> res = new ArrayList<>();
		long daysDifference = ChronoUnit.DAYS.between(LocalDate.parse(summaryObj.getStartDate().toString()),
				LocalDate.parse(summaryObj.getEndDate().toString())) + 1;

		Long sumTotalPriceSales = sales.getSumBetweenDatesWithHr(summaryObj.getStartDate(), summaryObj.getEndDate());

		if (sumTotalPriceSales == null)
			sumTotalPriceSales = 0l;

		res.add(new SummaryDetailes("المبيعات", sumTotalPriceSales, daysDifference));

		Long sumTotalPriceDeceased = deceasedRepository.getSumBetweenDatesWithHr(summaryObj.getStartDate(),
				summaryObj.getEndDate());

		if (sumTotalPriceDeceased == null)
			sumTotalPriceDeceased = 0l;

		res.add(new SummaryDetailes("الهالك", sumTotalPriceDeceased, daysDifference));

		Long sumTotalPriceManufacturersOfProductFittingsRepository = manufacturersOfProductFittingsRepository
				.getSumBetweenDatesWithHr(summaryObj.getStartDate(), summaryObj.getEndDate());

		if (sumTotalPriceManufacturersOfProductFittingsRepository == null)
			sumTotalPriceManufacturersOfProductFittingsRepository = 0l;

		res.add(new SummaryDetailes("مصنعيه تجهيزات المنتج", sumTotalPriceManufacturersOfProductFittingsRepository,
				daysDifference));

		Long sumTotalPriceOtherExpensesRepository = otherExpensesRepository
				.getSumBetweenDatesWithHr(summaryObj.getStartDate(), summaryObj.getEndDate());

		if (sumTotalPriceOtherExpensesRepository == null)
			sumTotalPriceOtherExpensesRepository = 0l;

		res.add(new SummaryDetailes("مصروفات اخرى", sumTotalPriceOtherExpensesRepository, daysDifference));

		Long sumTotalPricePurchasesRepository = purchasesRepository.getSumBetweenDatesWithHr(summaryObj.getStartDate(),
				summaryObj.getEndDate());

		if (sumTotalPricePurchasesRepository == null)
			sumTotalPricePurchasesRepository = 0l;
		res.add(new SummaryDetailes("المشتريات", sumTotalPricePurchasesRepository, daysDifference));

		return res;

	}

}
