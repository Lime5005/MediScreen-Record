package com.lime.mediscreenrecord.service;

import com.lime.mediscreenrecord.MediscreenRecordApplication;
import com.lime.mediscreenrecord.model.Record;
import com.lime.mediscreenrecord.repository.RecordRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={ MediscreenRecordApplication.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecordServiceTest {
    private Long id = null;
    private Long patientId = null;

    private Record record = new Record();
    private Record recordToDelete = new Record();
    private Long idToDelete = null;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @BeforeAll
    public void init() {
        patientId = 11L;
        record.setNote("A init note");
        recordService.createRecord(patientId, record);
        id = record.getId();

        recordToDelete.setPatientId(404L);
        recordToDelete.setNote("Record to delete");
        recordService.createRecord(404L, recordToDelete);
        idToDelete = recordToDelete.getId();

    }

    @AfterAll
    public void clean() {
        recordRepository.deleteRecordById(id);
    }

    @Test
    @Order(1)
    public void test_Find_All_Record_Should_Exist() {
        List<Record> all = recordService.findAll();
        assertTrue(all.size() > 0);
    }

    @Test
    @Order(2)
    public void test_Get_Record_By_Id_Should_Exist() {
        Record byId = recordService.findById(id);
        assertNotNull(byId);
    }

    @Test
    @Order(3)
    public void test_Get_Records_By_PatientId_Should_Exist() {
        List<Record> byPatientId = recordService.findByPatientId(patientId);
        assertTrue(byPatientId.size() > 0);
    }

    @Test
    @Order(4)
    public void test_Update_Record_By_Id_Should_OK() {
        Long id = record.getId();
        Record foundRecord = recordService.findById(id);
        foundRecord.setNote("Update note");
        Record newRecord = recordService.updateRecord(id, foundRecord);
        assertEquals("Update note", newRecord.getNote());
    }

    @Test
    @Order(5)
    public void test_Delete_Record_By_Id_Should_OK() {
        Boolean aBoolean = recordService.deleteRecordById(idToDelete);
        assertTrue(aBoolean);
    }

}
