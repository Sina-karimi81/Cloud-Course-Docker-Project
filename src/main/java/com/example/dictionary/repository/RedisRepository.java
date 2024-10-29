package com.example.dictionary.repository;

import com.example.dictionary.entity.DefinitionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<DefinitionEntity, String> {
}
