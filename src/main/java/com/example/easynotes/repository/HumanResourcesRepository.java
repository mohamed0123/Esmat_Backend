package com.example.easynotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.HumanResources;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface HumanResourcesRepository extends JpaRepository<HumanResources, Long> {

	List<HumanResources> findByHumanResourceType(String hrValue);
}
