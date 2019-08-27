package com.example.easynotes.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.EmployeeTimeTracker;
import com.example.easynotes.model.HumanResources;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface EmployeeTimeTrackerRepository extends JpaRepository<EmployeeTimeTracker, Long> {
	
	@Query(value = "select max(id) from employee_time_tracker where human_resource_id=?1", nativeQuery = true)
	Long findMaxIdByHumanResource(long employee_time_tracker);

	@Query(value = "select max(id) from employee_time_tracker where human_resource_id=?1 and id !=?2", nativeQuery = true)
	Long findMaxIdByHumanResourceAndNotThisOne(long employee_time_tracker, long recordId);

	List<EmployeeTimeTracker> findByHumanResources(HumanResources hr);

	@Query(value = "select * from employee_time_tracker where human_resource_id = :hrId and  day_Date >= :startDate AND day_Date <= :endDate", nativeQuery = true)
	public List<EmployeeTimeTracker> getAllBetweenDatesWithHr(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("hrId") Long hrId);

}
