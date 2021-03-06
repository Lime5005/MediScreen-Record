package com.lime.mediscreenrecord.controller;

import com.lime.mediscreenrecord.model.Record;
import com.lime.mediscreenrecord.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@Api(tags = "Record's data API")
@RequestMapping("/api")
public class RecordController {
    Logger logger = LoggerFactory.getLogger(RecordController.class);
    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/records")
    @ApiOperation("Get all Records from database")
    public List<Record> getAllRecords() {
        return recordService.findAll();
    }

    @GetMapping("/records/patient/{patientId}")
    @ApiOperation("Get all Records by one Patient's ID")
    public ResponseEntity<List<Record>> getOnePatientRecords(@PathVariable(value = "patientId") Long patientId) {
        List<Record> recordList = recordService.findByPatientId(patientId);
        if (recordList.isEmpty()) {
            logger.error("Failed to find any Record with PatientId: " + patientId);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
        logger.info("Queried Record(s) with PatientId: " + patientId);
        return ResponseEntity.ok(recordList);
    }

    @GetMapping("/records/{id}")
    @ApiOperation("Find one Record by record's ID")
    public ResponseEntity<?> findRecordById(@PathVariable(value = "id") Long id) {
        Record record = recordService.findById(id);
        if (record == null) {
            logger.error("Failed to find Record with id: " + id);
            return new ResponseEntity<>("Failed to find Record with id: " + id, HttpStatus.NOT_FOUND);
        }
        logger.info("Queried Record with id: " + id);
        return ResponseEntity.ok().body(record);
    }

    @PostMapping("/records/patient/{patientId}/add")
    @ApiOperation("Create a new Record by one patient's ID")
    public ResponseEntity<?> createRecord(@PathVariable(value = "patientId") Long patientId, @Valid @RequestBody Record record) {
        Record newRecord = recordService.createRecord(patientId, record);
        if (newRecord == null) {
            logger.error("Failed to create Record with PatientId: " + patientId);
            return new ResponseEntity<>("Failed to create Record with PatientId: " + patientId, HttpStatus.BAD_REQUEST);
        }
        logger.info("New Record created with PatientId: " + patientId);
        return ResponseEntity.ok(newRecord);
    }

    @PutMapping("/records/update/{id}")
    @ApiOperation("Update a Record by record's ID")
    public ResponseEntity<?> updateRecord(@PathVariable(value = "id") Long id, @Valid @RequestBody Record recordDetails) {
        Record record = recordService.updateRecord(id, recordDetails);
        if (record == null) {
            logger.error("Failed to update Record with id: " + id);
            return new ResponseEntity<>("Failed to update Record with id: " + id, HttpStatus.BAD_REQUEST);
        }
        logger.info("Updated Record with id: " + id);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/records/{id}")
    @ApiOperation("Delete a Record by record's ID")
    public ResponseEntity<?> deleteRecord(@PathVariable(value = "id") Long id) {
        Boolean deleted = recordService.deleteRecordById(id);
        if (!deleted) {
            logger.error("Failed to delete Record with id: " + id);
            return new ResponseEntity<>("Failed to delete Record with id: " + id, HttpStatus.BAD_REQUEST);
        }
        logger.info("Deleted Record with id " + id);
        return ResponseEntity.ok().body(true);
    }

}
