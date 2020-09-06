package io.petebids.directmail.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.petebids.directmail.exception.TemplateNotFoundException;
import io.petebids.directmail.model.DmDocument;
import io.petebids.directmail.model.TemplateDocument;
import io.petebids.directmail.repository.DmRepository;

@Service
public class DmService {

	@Autowired
	DmRepository dmRepository;

	@Autowired
	TemplateService templateService;

	@Cacheable
	public DmDocument getById(String id) {

		try {
			Optional<DmDocument> optionalDoc = dmRepository.findById(id);
			DmDocument doc = optionalDoc.get();
			return doc;
		} catch (IllegalArgumentException i) {
			i.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException n) {
			n.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Cacheable
	public List<DmDocument> getAll() {
		List<DmDocument> all;

		try {
			all = dmRepository.findAll();
			return all;
		} catch (Exception e) {

			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings("unchecked")
	public DmDocument create(Map<String, Object> requestBody) throws TemplateNotFoundException {
		String templateId = (String) requestBody.get("templateId"); // TODO add null check
		TemplateDocument template = templateService.getByID(templateId);
		Map<String, String> inputParams;// TODO validations on type & content
		inputParams = Collections.unmodifiableMap((Map<String, String>) requestBody.get("inputParams"));
		String body = buildMessage(inputParams, template.getBody());
		DmDocument doc = new DmDocument();
		doc.setBody(body);
		try {
			doc = dmRepository.save(doc);
		} catch (IllegalArgumentException i) {
			i.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return doc;

	}

	private String buildMessage(Map<String, String> inputParams, String templateString) {
		String message = templateString;
		for (Map.Entry<String, String> entry : inputParams.entrySet()) {
			message = message.replace(entry.getKey(), entry.getValue());
		}
		return message;
	}
}
