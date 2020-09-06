package io.petebids.directmail.repository;

import io.petebids.directmail.model.DmDocument;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DmRepository extends MongoRepository<DmDocument, String> {

}
