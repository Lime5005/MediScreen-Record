package com.lime.mediscreenrecord.controller;

import com.lime.mediscreenrecord.exception.ResourceNotFoundException;
import com.lime.mediscreenrecord.model.Record;
import com.lime.mediscreenrecord.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecordController {
    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/records")
    public List<Record> getAllRecords() {
        return recordService.findAll();
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<Record> findRecordById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Record record = recordService.findById(id);
        if (record == null) throw new ResourceNotFoundException("Record not found with id: " + id);
        return ResponseEntity.ok().body(record);
    }

    @PostMapping("/record/add")
    public ResponseEntity<Record> createRecord(@Valid @RequestBody Record record) {
        Record newRecord = recordService.createRecord(record);
        return ResponseEntity.ok(newRecord);
    }

    @PutMapping("/records/update/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable(value = "id") Long id, @Valid @RequestBody Record recordDetails) throws ResourceNotFoundException {
        Record record = recordService.updateRecord(id, recordDetails);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/records/{id}")
    public ResponseEntity<Boolean> deleteRecord(@PathVariable(value = "id") Long id)  throws ResourceNotFoundException {
        Boolean deleted = recordService.deleteRecordById(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Record not found with id: " + id);
        }
        return ResponseEntity.ok().body(true);
    }

}
