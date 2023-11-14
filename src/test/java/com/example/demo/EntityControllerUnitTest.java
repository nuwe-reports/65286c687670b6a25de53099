
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDoctors_ReturnsListOfDoctors() throws Exception {
        // Arrange
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("John", "Doe", 35, "john.doe@example.com"));
        when(doctorRepository.findAll()).thenReturn(doctors);

        // Act & Assert
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(doctors.size()));
    }

    @Test
    void getDoctorById_ValidId_ReturnsDoctor() throws Exception {
        // Arrange
        long doctorId = 1L;
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        doctor.setId(doctorId);
        // Act & Assert
        mockMvc.perform(get("/api/doctors/{id}", doctorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(doctorId));
    }

    @Test
    void getDoctorById_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        long invalidDoctorId = 100L;
        when(doctorRepository.findById(invalidDoctorId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/doctors/{id}", invalidDoctorId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDoctor_ValidDoctor_ReturnsCreatedDoctor() throws Exception {
        // Arrange
        Doctor doctorToCreate = new Doctor("John", "Doe", 35, "john.doe@example.com");
        Doctor createdDoctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        when(doctorRepository.save(any(Doctor.class))).thenReturn(createdDoctor);

        // Act & Assert
        mockMvc.perform(post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdDoctor.getId()));
    }

    @Test
    void deleteDoctor_ValidId_ReturnsOk() throws Exception {
        // Arrange
        long doctorId = 1L;
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(new Doctor()));

        // Act & Assert
        mockMvc.perform(delete("/api/doctors/{id}", doctorId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDoctor_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        long invalidDoctorId = 100L;
        when(doctorRepository.findById(invalidDoctorId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/doctors/{id}", invalidDoctorId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAllDoctors_ReturnsOk() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }

}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getAllPatients_ReturnsListOfPatients() throws Exception {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Jane", "Doe", 25, "jane.doe@example.com"));
        when(patientRepository.findAll()).thenReturn(patients);

        // Act & Assert
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(patients.size()));
    }

    @Test
    void getPatientById_ValidId_ReturnsPatient() throws Exception {
        // Arrange
        long patientId = 1L;
        Patient patient = new Patient("Jane", "Doe", 25, "jane.doe@example.com");
        patient.setId(patientId);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Act & Assert
        mockMvc.perform(get("/api/patients/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(patientId));
    }

    @Test
    void getPatientById_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        long invalidPatientId = 100L;
        when(patientRepository.findById(invalidPatientId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/patients/{id}", invalidPatientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPatient_ValidPatient_ReturnsCreatedPatient() throws Exception {
        // Arrange
        Patient patientToCreate = new Patient("Jane", "Doe", 25, "jane.doe@example.com");
        Patient createdPatient = new Patient("Jane", "Doe", 25, "jane.doe@example.com");
        when(patientRepository.save(any(Patient.class))).thenReturn(createdPatient);

        // Act & Assert
        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdPatient.getId()));
    }

    @Test
    void deletePatient_ValidId_ReturnsOk() throws Exception {
        // Arrange
        long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(new Patient()));

        // Act & Assert
        mockMvc.perform(delete("/api/patients/{id}", patientId))
                .andExpect(status().isOk());
    }

    @Test
    void deletePatient_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        long invalidPatientId = 100L;
        when(patientRepository.findById(invalidPatientId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/patients/{id}", invalidPatientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAllPatients_ReturnsOk() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }
}




@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllRooms_ReturnsListOfRooms() throws Exception {
        // Arrange
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Room1"));
        when(roomRepository.findAll()).thenReturn(rooms);

        // Act & Assert
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(rooms.size()));
    }

    @Test
    void getRoomByRoomName_ValidRoomName_ReturnsRoom() throws Exception {
        // Arrange
        String roomName = "Room1";
        Room room = new Room(roomName);
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.of(room));

        // Act & Assert
        mockMvc.perform(get("/api/rooms/{roomName}", roomName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomName").value(roomName));
    }

    @Test
    void getRoomByRoomName_InvalidRoomName_ReturnsNotFound() throws Exception {
        // Arrange
        String invalidRoomName = "NonExistentRoom";
        when(roomRepository.findByRoomName(invalidRoomName)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/rooms/{roomName}", invalidRoomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRoom_ValidRoom_ReturnsCreatedRoom() throws Exception {
        // Arrange
        Room roomToCreate = new Room("Room1");
        Room createdRoom = new Room("Room1");
        when(roomRepository.save(any(Room.class))).thenReturn(createdRoom);

        // Act & Assert
        mockMvc.perform(post("/api/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomName").value(createdRoom.getRoomName()));
    }

    @Test
    void deleteRoom_ValidRoomName_ReturnsOk() throws Exception {
        // Arrange
        String roomName = "Room1";
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.of(new Room()));

        // Act & Assert
        mockMvc.perform(delete("/api/rooms/{roomName}", roomName))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRoom_InvalidRoomName_ReturnsNotFound() throws Exception {
        // Arrange
        String invalidRoomName = "NonExistentRoom";
        when(roomRepository.findByRoomName(invalidRoomName)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(delete("/api/rooms/{roomName}", invalidRoomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAllRooms_ReturnsOk() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }
}
