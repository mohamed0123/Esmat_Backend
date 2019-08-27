package com.example.easynotes.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.OtherExpenses;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface OtherExpensesRepository extends JpaRepository<OtherExpenses, Long> {

	
	
	
	@Query(value = "select * from other_expenses where  day_Date >= :startDate AND day_Date <= :endDate", nativeQuery = true)
	public List<OtherExpenses> getAllBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate );

}
