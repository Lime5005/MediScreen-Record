package com.lime.mediscreenrecord.repository;

import com.lime.mediscreenrecord.model.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends MongoRepository<Record, Long> {
    List<Record> findByPatientId(Long patientId);
    Optional<Record> findById(Long id);
    void deleteRecordById(Long id);
}
