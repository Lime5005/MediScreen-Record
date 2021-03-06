package com.lime.mediscreenrecord.proxy;

import com.lime.mediscreenrecord.model.Record;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "mediscreen-record", url = "${record.serviceUrl}")
public interface RecordFeignProxy {

    @GetMapping("/api/records/patient/{patientId}")
    ResponseEntity<List<Record>> getOnePatientRecords(@PathVariable(value = "patientId") Long patientId);
}
