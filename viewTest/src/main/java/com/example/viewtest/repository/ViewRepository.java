package com.example.viewtest.repository;

import com.example.viewtest.Entity.View;
import com.example.viewtest.dto.ViewDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ViewRepository extends JpaRepository<View, Long>, JpaSpecificationExecutor<View> {

}
