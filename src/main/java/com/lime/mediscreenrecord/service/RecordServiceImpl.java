package com.lime.mediscreenrecord.service;

import com.lime.mediscreenrecord.model.Record;
import com.lime.mediscreenrecord.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public List<Record> findAll() {
        List<Record> records = recordRepository.findAll();
        return records;
    }

    @Override
    public List<Record> findByPatientId(Long patientId) {
        List<Record> recordList = recordRepository.findByPatientId(patientId);
        return recordList;
    }

    @Override
    public Record findById(Long id) {
        Optional<Record> optionalRecord = recordRepository.findById(id);
        if (optionalRecord.isPresent()) {
            Record record = optionalRecord.get();
            return record;
        }
        return null;
    }

    @Override
    public Boolean deleteRecordById(Long id) {
        Boolean deleted = false;
        Optional<Record> record = recordRepository.findById(id);
        if (record.isPresent()) {
            recordRepository.deleteRecordById(id);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public Record createRecord(Long patientId, Record record) {
        record.setId(sequenceGeneratorService.generateSequence(Record.SEQUENCE_NAME));
        record.setPatientId(patientId);
        return recordRepository.save(record);
    }

    @Override
    public Record updateRecord(Long id, Record newRecord) {
        Optional<Record> optionalRecord = recordRepository.findById(id);
        if (optionalRecord.isPresent()) {
            Record record = optionalRecord.get();
            record.setPatientId(newRecord.getPatientId());
            record.setNote(newRecord.getNote());
            final Record updatedRecord = recordRepository.save(record);
            return updatedRecord;
        }
        return null;
    }
}
