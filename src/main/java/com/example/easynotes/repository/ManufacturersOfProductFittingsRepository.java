package com.example.easynotes.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.HumanResources;
import com.example.easynotes.model.ManufacturersOfProductFittings;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface ManufacturersOfProductFittingsRepository extends JpaRepository<ManufacturersOfProductFittings, Long> {

	@Query(value = "select max(id) from manf_of_pro_fit where human_resource_id=?1", nativeQuery = true)
	Long findMaxIdByHumanResource(long human_resource_id);
	
	@Query(value = "select max(id) from manf_of_pro_fit where human_resource_id=?1 and id !=?2", nativeQuery = true)
	Long findMaxIdByHumanResourceAndNotThisOne(long human_resource_id  ,long recordId);
	
	List<ManufacturersOfProductFittings> findByHumanResources(HumanResources hr);
	
	@Query(value = "select * from manf_of_pro_fit where human_resource_id = :hrId and  day_Date >= :startDate AND day_Date <= :endDate", nativeQuery = true)
	public List<ManufacturersOfProductFittings> getAllBetweenDatesWithHr(@Param("startDate")Date startDate,@Param("endDate")Date endDate ,@Param("hrId")Long hrId );

	
	@Query(value = "select sum(total_Price) from manf_of_pro_fit where  day_Date >= :startDate AND day_Date <= :endDate", nativeQuery = true)
	public Long getSumBetweenDatesWithHr(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	
}
