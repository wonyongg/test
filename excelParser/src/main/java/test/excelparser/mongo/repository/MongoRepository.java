package test.excelparser.mongo.repository;

import test.excelparser.mongo.entity.MongoEntity;

public interface MongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<MongoEntity, String> {
}
