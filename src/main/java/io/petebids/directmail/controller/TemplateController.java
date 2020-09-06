package io.petebids.directmail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.petebids.directmail.exception.TemplateNotFoundException;
import io.petebids.directmail.model.TemplateDocument;
import io.petebids.directmail.service.TemplateService;

@RestController
public class TemplateController {

	@Autowired
	TemplateService templateService;

	@GetMapping("/api/v1/template/")
	public ResponseEntity<List<TemplateDocument>> get() {
		List<TemplateDocument> templates = new ArrayList<TemplateDocument>();
		templates = templateService.getAll();
		return ResponseEntity.ok(templates);

	}

	@GetMapping("/api/v1/template/{id}")
	public ResponseEntity<TemplateDocument> getById(@PathVariable(value = "id") final String id)
			throws TemplateNotFoundException {
		TemplateDocument template = new TemplateDocument();
		try {
			template = templateService.getByID(id);
		} catch (TemplateNotFoundException e) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No Template by %s", id), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(template);
	}

	@PostMapping("/api/v1/template/")
	public ResponseEntity<TemplateDocument> create(@RequestBody Map<String, String> body) {
		TemplateDocument template = templateService.create(body.get("body"));
		return ResponseEntity.status(HttpStatus.CREATED).body(template);
	}

}
