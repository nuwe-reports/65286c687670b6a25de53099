package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;


    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */

    @Nested
    class DoctorTest {

        @Test
        void testGetId() {
            // Arrange
            Doctor doctor = new Doctor();
            doctor.setId(1L);
            // Act
            long actualId = doctor.getId();

            // Assert
            assertThat(actualId).isEqualTo(1L);
        }

        @Test
        void testSetId() {
            // Arrange
            Doctor doctor = new Doctor();

            // Act
            doctor.setId(2L);

            // Assert
            assertThat(doctor.getId()).isEqualTo(2L);
        }

        @Test
        void testConstructor() {
            // Arrange
            String firstName = "John";
            String lastName = "Doe";
            int age = 30;
            String email = "john.doe@example.com";

            // Act
            Doctor doctor = new Doctor(firstName, lastName, age, email);

            // Assert
            assertThat(doctor.getFirstName()).isEqualTo(firstName);
            assertThat(doctor.getLastName()).isEqualTo(lastName);
            assertThat(doctor.getAge()).isEqualTo(age);
            assertThat(doctor.getEmail()).isEqualTo(email);
        }
    }

    @Nested
    class PatientTest {

        @Test
        void testGetId() {
            // Arrange
            Patient patient = new Patient();
            patient.setId(1L);

            // Act
            long actualId = patient.getId();

            // Assert
            assertThat(actualId).isEqualTo(1L);
        }

        @Test
        void testSetId() {
            // Arrange
            Patient patient = new Patient();

            // Act
            patient.setId(2L);

            // Assert
            assertThat(patient.getId()).isEqualTo(2L);
        }

        @Test
        void testConstructor() {
            // Arrange
            String firstName = "Jane";
            String lastName = "Doe";
            int age = 25;
            String email = "jane.doe@example.com";

            // Act
            Patient patient = new Patient(firstName, lastName, age, email);

            // Assert
            assertThat(patient.getFirstName()).isEqualTo(firstName);
            assertThat(patient.getLastName()).isEqualTo(lastName);
            assertThat(patient.getAge()).isEqualTo(age);
            assertThat(patient.getEmail()).isEqualTo(email);
        }
    }

    @Nested
    class RoomTest {

        @Test
        void testGetRoomName() {
            // Arrange
            Room room = new Room("Room1");

            // Act
            String actualRoomName = room.getRoomName();

            // Assert
            assertThat(actualRoomName).isEqualTo("Room1");
        }

        @Test
        void testConstructor() {
            // Arrange
            String roomName = "Room2";

            // Act
            Room room = new Room(roomName);

            // Assert
            assertThat(room.getRoomName()).isEqualTo(roomName);
        }
    }


    @Nested
    class AppointmentTest {

        @Test
        void testGetId() {
            // Arrange
            Appointment appointment = new Appointment();
            appointment.setId(1L);

            // Act
            long actualId = appointment.getId();

            // Assert
            assertThat(actualId).isEqualTo(1L);
        }

        @Test
        void testSetId() {
            // Arrange
            Appointment appointment = new Appointment();

            // Act
            appointment.setId(2L);

            // Assert
            assertThat(appointment.getId()).isEqualTo(2L);
        }

        @Test
        void testGetStartsAt() {
            // Arrange
            LocalDateTime startsAt = LocalDateTime.of(2023, 1, 1, 12, 0);
            Appointment appointment = new Appointment();
            appointment.setStartsAt(startsAt);

            // Act
            LocalDateTime actualStartsAt = appointment.getStartsAt();

            // Assert
            assertThat(actualStartsAt).isEqualTo(startsAt);
        }

        @Test
        void testSetStartsAt() {
            // Arrange
            LocalDateTime startsAt = LocalDateTime.of(2023, 1, 1, 12, 0);
            Appointment appointment = new Appointment();

            // Act
            appointment.setStartsAt(startsAt);

            // Assert
            assertThat(appointment.getStartsAt()).isEqualTo(startsAt);
        }

        @Test
        void testGetFinishesAt() {
            // Arrange
            LocalDateTime finishesAt = LocalDateTime.of(2023, 1, 1, 13, 0);
            Appointment appointment = new Appointment();
            appointment.setFinishesAt(finishesAt);

            // Act
            LocalDateTime actualFinishesAt = appointment.getFinishesAt();

            // Assert
            assertThat(actualFinishesAt).isEqualTo(finishesAt);
        }

        @Test
        void testSetFinishesAt() {
            // Arrange
            LocalDateTime finishesAt = LocalDateTime.of(2023, 1, 1, 13, 0);
            Appointment appointment = new Appointment();

            // Act
            appointment.setFinishesAt(finishesAt);

            // Assert
            assertThat(appointment.getFinishesAt()).isEqualTo(finishesAt);
        }

        @Test
        void testOverlaps() {
            // Arrange
            LocalDateTime startsAt1 = LocalDateTime.of(2023, 1, 1, 12, 0);
            LocalDateTime finishesAt1 = LocalDateTime.of(2023, 1, 1, 13, 0);
            Room room1 = new Room("Room1");
            Appointment appointment1 = new Appointment(null, null, room1, startsAt1, finishesAt1);

            LocalDateTime startsAt2 = LocalDateTime.of(2023, 1, 1, 12, 30);
            LocalDateTime finishesAt2 = LocalDateTime.of(2023, 1, 1, 13, 30);
            Room room2 = new Room("Room1");
            Appointment appointment2 = new Appointment(null, null, room2, startsAt2, finishesAt2);

            // Act
            boolean overlaps = appointment1.overlaps(appointment2);

            // Assert
            assertThat(overlaps).isTrue();
        }
    }

}
