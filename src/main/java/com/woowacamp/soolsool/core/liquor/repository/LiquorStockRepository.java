package com.woowacamp.soolsool.core.liquor.repository;

import com.woowacamp.soolsool.core.liquor.domain.LiquorStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquorStockRepository extends JpaRepository<LiquorStock, Long> {

}
