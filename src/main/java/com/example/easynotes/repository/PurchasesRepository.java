package com.example.easynotes.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.HumanResources;
import com.example.easynotes.model.Purchases;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface PurchasesRepository extends JpaRepository<Purchases, Long> {

	@Query(value = "select max(id) from purchases where human_resource_id=?1", nativeQuery = true)
	Long findMaxIdByHumanResource(long human_resource_id);

	List<Purchases> findByHumanResources(HumanResources hr);

	@Query(value = "select * from purchases where human_resource_id = :hrId and  day_Date >= :startDate AND day_Date <= :endDate  order by id", nativeQuery = true)
	public List<Purchases> getAllBetweenDatesWithHr(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("hrId") Long hrId);

	@Query(value = "select sum(total_Price) from purchases where  day_Date >= :startDate AND day_Date <= :endDate", nativeQuery = true)
	public Long getSumBetweenDatesWithHr(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "select max(id) from purchases where id < ?1 and human_resource_id = ?2", nativeQuery = true)
	Long findMaxPreviousId(long editId, long hrId);

	@Query(value = "select * from purchases where id >= :editId and human_resource_id = :hrId order by id", nativeQuery = true)
	public List<Purchases> getListToEdit(@Param("editId") Long editId, @Param("hrId") long hrId);

	public List<Purchases> findAllByOrderByIdAsc();

}
