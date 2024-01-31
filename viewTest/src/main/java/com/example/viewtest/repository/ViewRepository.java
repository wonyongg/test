package com.example.viewtest.repository;

import com.example.viewtest.Entity.View;
import com.example.viewtest.Entity.ViewCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ViewRepository extends JpaRepository<View, Long>, JpaSpecificationExecutor<View> {

    Optional<View> findByMemberId(Long id);
    Optional<View> findByCompositeKey(ViewCompositeKey viewCompositeKey);
}
