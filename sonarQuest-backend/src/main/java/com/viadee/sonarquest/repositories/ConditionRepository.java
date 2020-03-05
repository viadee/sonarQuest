package com.viadee.sonarquest.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.Condition;
import com.viadee.sonarquest.entities.QualityGateRaid;

@Transactional
public interface ConditionRepository extends CrudRepository<Condition, Long>{

    List<Condition> findAll();
    Condition findByMetricKeyAndQualityGateRaid(String metricKey, QualityGateRaid raid);
    
}
