package io.petebids.directmail.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.petebids.directmail.model.TemplateDocument;

public interface TemplateRepository extends MongoRepository<TemplateDocument, String> {
	
	public boolean existsByBody(String body);
	
	public Optional<TemplateDocument> findByBody(String body);
	

}
