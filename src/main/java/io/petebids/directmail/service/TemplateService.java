package io.petebids.directmail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.petebids.directmail.exception.TemplateNotFoundException;
import io.petebids.directmail.model.TemplateDocument;
import io.petebids.directmail.repository.TemplateRepository;

@Service
public class TemplateService {

	@Autowired
	TemplateRepository templateRepository;
	
	
	public TemplateDocument getByID(String id) throws TemplateNotFoundException {
		return templateRepository.findById(id)
				.orElseThrow(()-> new TemplateNotFoundException());
	}
	
	public TemplateDocument create(String body) {
		TemplateDocument template = new TemplateDocument();
		//template = templateRepository.findByBody(body).get();//TODO this whole block is cooked, needs idempotent save or return the existing
		template.setBody(body);
		template = templateRepository.save(template);
		return template;
	}
	public List<TemplateDocument> getAll(){
		return templateRepository.findAll();
	}

}
