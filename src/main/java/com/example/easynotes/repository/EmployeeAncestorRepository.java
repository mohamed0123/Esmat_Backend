package com.example.easynotes.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.EmployeeAncestor;
import com.example.easynotes.model.EmployeeWorkHourAndSalary;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface EmployeeAncestorRepository extends JpaRepository<EmployeeAncestor, Long> {
	@Query(value = "select max(id) from employee_ancestor where employee_work_hour_and_salary_id=?1", nativeQuery = true)
	Long findMaxIdByEmployeeWorkHourSalary(long employee_work_hour_and_salary_id);

	@Query(value = "select max(id) from employee_ancestor where employee_work_hour_and_salary_id=?1 and id !=?2", nativeQuery = true)
	Long findMaxIdByEmployeeWorkHourSalaryAndNotThisOne(long employee_work_hour_and_salary_id, long recordId);

	List<EmployeeAncestor> findByEmployeeWorkHourSalary(EmployeeWorkHourAndSalary employeeWorkHourAndSalary);

	@Query(value = "select * from employee_ancestor where employee_work_hour_and_salary_id = :employee_work_hour_and_salary_id and  day_Date >= :startDate AND day_Date <= :endDate", nativeQuery = true)
	public List<EmployeeAncestor> getAllBetweenDatesWithHr(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("employee_work_hour_and_salary_id") Long employee_work_hour_and_salary_id);

}
