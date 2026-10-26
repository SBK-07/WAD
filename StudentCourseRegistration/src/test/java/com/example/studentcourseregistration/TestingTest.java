package com.example.studentcourseregistration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCourses() throws Exception {

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk());
    }

    @Test
    public void testExample() throws Exception
    {
        mockMvc.perform(get("/students/all/")).andExpect(status().isOk());
    }

    @Test
    public void invalidGetCourses() throws Exception {

        mockMvc.perform(get("/api/course"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRegisterCourse() throws Exception {

        String registrationJson = """
            {
                "studentId": "S101",
                "courseId": "C101"
            }
            """;

        mockMvc.perform(
                        post("/api/register")
                                .contentType("application/json")
                                .content(registrationJson)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterCourseWithMissingValue() throws Exception {

        String registrationJson = """
            {
                "studentId": "S101"
            }
            """;

        mockMvc.perform(
                        post("/api/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(registrationJson)
                )
                .andExpect(status().isBadRequest());
    }


}