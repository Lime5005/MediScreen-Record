package com.lime.mediscreenrecord.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.mediscreenrecord.MediscreenRecordApplication;
import com.lime.mediscreenrecord.model.Record;
import com.lime.mediscreenrecord.service.RecordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ MediscreenRecordApplication.class })
public class RecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private RecordService recordServiceMock;

    @BeforeAll
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_Get_Record_By_PatientId_Should_Return_OK() throws Exception {
        Record record1 = new Record(11L, 11L, "A test note1", new Date(), new Date());
        Record record2 = new Record(22L, 11L, "A test note2", new Date(), new Date());
        List<Record> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(recordServiceMock.findByPatientId(11L)).thenReturn(recordList);
        mockMvc.perform(get("/api/records/patient/11")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].note").value("A test note1"))
                .andExpect(jsonPath("$.[1].note").value("A test note2"));
    }

    @Test
    public void test_Get_Null_Records_By_PatientId_Should_Return_OK() throws Exception {
        when(recordServiceMock.findByPatientId(400L)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/records/patient/400")
        ).andExpect(status().isNoContent());
    }

    @Test
    public void test_Get_Record_By_Id_Should_Return_OK() throws Exception {
        Record record = new Record(99L, 99L, "A note for get by id", new Date(), new Date());
        when(recordServiceMock.findById(99L)).thenReturn(record);
        mockMvc.perform(get("/api/records/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.note").value("A note for get by id"));
    }

    @Test
    public void test_Update_Record_By_PatientId_Should_Return_OK() throws Exception {
        Record updatedRecord = new Record(88L, 88L, "A note for update test", new Date(), new Date());
        when(recordServiceMock.updateRecord(88L, updatedRecord)).thenReturn(updatedRecord);
        mockMvc.perform(put("/api/records/update/88")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedRecord))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.note").value("A note for update test"));
    }

    @Test
    public void test_Add_Record_To_Patient_Should_Return_OK() throws Exception {
        Record record = new Record(77L, 77L, "A note for add test", new Date(), new Date());
        when(recordServiceMock.createRecord(77L, record)).thenReturn(record);
        mockMvc.perform(post("/api/records/patient/77/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(record))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.note").value("A note for add test"));
    }

    @Test
    public void test_Delete_Record_By_Id_Should_Return_OK() throws Exception {
        when(recordServiceMock.deleteRecordById(44L)).thenReturn(true);
        mockMvc.perform(delete("/api/records/44")
        ).andExpect(status().isOk());
    }

    @Test
    public void test_Delete_Null_Record_Should_Return_OK() throws Exception {
        when(recordServiceMock.deleteRecordById(404L)).thenReturn(false);
        mockMvc.perform(delete("/api/records/404")
        ).andExpect(status().isBadRequest());
    }


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
