package com.woowacamp.soolsool.core.statistics.repository;

import org.springframework.stereotype.Component;

@Component
public interface StatisticsRepository extends StatisticsJpaRepository, StatisticsRedis {

}
