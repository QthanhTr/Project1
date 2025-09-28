# Database Schema Design

This document describes the MySQL database design for a hospital management system. The database contains 4 main tables: Doctor, Patient, Appointment, and MedicalRecord.

---

## Doctor
| Field Name    | Data Type     | Key |
|---------------|--------------|-----|
| doctor_id     | INT          | PK  |
| name          | VARCHAR(100) |     |
| specialization| VARCHAR(50)  |     |

---

## Patient
| Field Name     | Data Type     | Key |
|----------------|--------------|-----|
| patient_id     | INT          | PK  |
| name           | VARCHAR(100) |     |
| date_of_birth  | DATE         |     |

---

## Appointment
| Field Name       | Data Type     | Key                        |
|-----------------|--------------|----------------------------|
| appointment_id   | INT          | PK                         |
| doctor_id        | INT          | FK → Doctor(doctor_id)    |
| patient_id       | INT          | FK → Patient(patient_id)  |
| appointment_date | DATETIME     |                            |

---

## MedicalRecord
| Field Name  | Data Type     | Key                        |
|------------|--------------|----------------------------|
| record_id   | INT          | PK                         |
| patient_id  | INT          | FK → Patient(patient_id)  |
| doctor_id   | INT          | FK → Doctor(doctor_id)    |
| diagnosis   | TEXT         |                            |
| treatment   | TEXT         |                            |
