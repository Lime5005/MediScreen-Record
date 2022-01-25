package com.lime.mediscreenrecord.service;

import com.lime.mediscreenrecord.model.Record;

import java.util.List;

public interface RecordService {
    List<Record> findAll();
    List<Record> findByPatientId(Long patientId);
    Record findById(Long id);
    Boolean deleteRecordById(Long id);
    Record createRecord(Record record);
    Record updateRecord(Long id, Record newRecord);
}
