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
import io.petebids.directmail.model.DmDocument;
import io.petebids.directmail.service.DmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class DmController {

	@Autowired
	DmService dmService;

	@Operation(summary = "Create a direct mail message")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = DmDocument.class)) }),
			@ApiResponse(responseCode = "400", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseStatusException.class)) }) })
	@PostMapping("/api/v1/directmail")
	public ResponseEntity<DmDocument> create(@RequestBody Map<String, Object> requestBody)
			throws TemplateNotFoundException {
		DmDocument doc = new DmDocument();
		try {
			doc = dmService.create(requestBody);
		} catch (TemplateNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(doc);

	}

	@GetMapping("/api/v1/directmail")
	public ResponseEntity<List<DmDocument>> getAll() {
		List<DmDocument> all = new ArrayList<DmDocument>();
		all = dmService.getAll();
		return ResponseEntity.ok(all);
	}

	@GetMapping("/api/v1/directmail/{id}")
	public ResponseEntity<DmDocument> getById(@PathVariable(value = "id") final String id) {
		DmDocument doc = new DmDocument();
		doc = dmService.getById(id);
		return ResponseEntity.ok(doc);

	}

}
