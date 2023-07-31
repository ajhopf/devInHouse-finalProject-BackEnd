-- DROP TABLE TOKEN;
-- DROP TABLE USERS;
-- DROP TABLE CONFIG;
-- DROP TABLE ALERGIES;
-- DROP TABLE APPOINTMENTS;
-- DROP TABLE SPECIAL_CARE;
-- DROP TABLE LOG;
-- DROP TABLE pacients;
-- DROP TABLE addresses;
MERGE INTO USERS U
    USING DUAL
    ON (U.EMAIL = 'admin@gmail.com')
    WHEN NOT MATCHED THEN
        INSERT (NAME, EMAIL, PASSWORD, PHOTO_URL, ROLE)
            VALUES ('Admin', 'admin@gmail.com', '12345', null, 'ROLE_ADMIN');

MERGE INTO USERS U
    USING DUAL
    ON (U.EMAIL = 'doctor@gmail.com')
    WHEN NOT MATCHED THEN
        INSERT (NAME, EMAIL, PASSWORD, PHOTO_URL, ROLE)
            VALUES ('Doctor', 'doctor@gmail.com', '12345', null, 'ROLE_DOCTOR');

MERGE INTO USERS U
    USING DUAL
    ON (U.EMAIL = 'nurse@gmail.com')
    WHEN NOT MATCHED THEN
        INSERT (NAME, EMAIL, PASSWORD, PHOTO_URL, ROLE)
            VALUES ('Nurse', 'nurse@gmail.com', '12345', null, 'ROLE_NURSE');

MERGE INTO CONFIG C
    USING DUAL
    ON (C.KEY = 'systemConfigDefault')
    WHEN NOT MATCHED THEN
        INSERT ("KEY",VALUE) VALUES ('systemConfigDefault',TO_CLOB('{"companyName":"LabMedical","logoUrl":"https://img.favpng.com/5/12/1/hospital-medicine-icon-png-favpng-r1z9JHyesUSmRqNn7WL3xkb7Q.jpg","primaryColor":"#212529","secondaryColor":"#0d6efd","fontColor":"#ffffff"}'));
