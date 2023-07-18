package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.LogRequest;
import com.example.labmedical.repository.LogRepository;
import com.example.labmedical.repository.model.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class LogServiceTest {

    @Autowired
    private LogService logServiceAutowired;
    @InjectMocks
    private LogService logService;
    @Mock
    private LogRepository logRepository;
    @Autowired
    private ObjectMapper om;
    @Nested
    @DisplayName("Tests of success method")
    class successMethodTests {
        @Test
        @DisplayName("When description has more tha 64 caracters, it should return ConstraintViolationException")
        @Transactional
        void test1() {
            var description = "5tg0Ee33GXqny1my8YCGE4u7oC2XIPI3Z509djL7MSw1G4WcMzbyyMxpB6D87f83S";
            assertThrows(ConstraintViolationException.class, () -> {
                logServiceAutowired.success(description);
            });
        }

        @Test
        @DisplayName("When description has more tha 8 caracters, it should return ConstraintViolationException")
        @Transactional
        void test2() {
            var description = "S";
            assertThrows(ConstraintViolationException.class, () -> {
                logServiceAutowired.success(description);
            });
        }

        @Test
        @DisplayName("When description has the right amount of caracters, it should return a Log with LogType as SUCCESS and logOrigin as BACK_END")
        @Transactional
        void test3() {
            var description = "Correct Description";
            var result = logServiceAutowired.success(description);
            assertEquals(result.getLogType(), Log.LogType.SUCCESS);
            assertEquals(result.getDescription(), description);
            assertEquals(result.getLogOrigin(), Log.LogOrigin.BACK_END);
            assertNotNull(result.getCreatedDate());
            assertNotNull(result.getId());
        }
    }

    @Nested
    @DisplayName("Tests of info method")
    class infoMethodTests {
        @Test
        @DisplayName("When description has more tha 64 caracters, it should return ConstraintViolationException")
        @Transactional
        void test1() {
            var description = "5tg0Ee33GXqny1my8YCGE4u7oC2XIPI3Z509djL7MSw1G4WcMzbyyMxpB6D87f83S";
            assertThrows(ConstraintViolationException.class, () -> {
                logServiceAutowired.info(description);
            });
        }

        @Test
        @DisplayName("When description has more tha 8 caracters, it should return ConstraintViolationException")
        @Transactional
        void test2() {
            var description = "S";
            assertThrows(ConstraintViolationException.class, () -> {
                logServiceAutowired.info(description);
            });
        }

        @Test
        @DisplayName("When description has the right amount of caracters, it should return a Log with LogType as INFO and logOrigin as BACK_END")
        @Transactional
        void test3() {
            var description = "Correct Description";
            var result = logServiceAutowired.info(description);
            assertEquals(result.getLogType(), Log.LogType.INFO);
            assertEquals(result.getDescription(), description);
            assertEquals(result.getLogOrigin(), Log.LogOrigin.BACK_END);
            assertNotNull(result.getCreatedDate());
            assertNotNull(result.getId());
        }
    }

    @Nested
    @DisplayName("Tests of error method")
    class errorMethodTests {
        @Test
        @DisplayName("When description has more tha 64 caracters, it should return ConstraintViolationException")
        @Transactional
        void test1() {
            var description = "5tg0Ee33GXqny1my8YCGE4u7oC2XIPI3Z509djL7MSw1G4WcMzbyyMxpB6D87f83S";
            assertThrows(ConstraintViolationException.class, () -> {
                logServiceAutowired.error(description);
            });
        }

        @Test
        @DisplayName("When description has more tha 8 caracters, it should return ConstraintViolationException")
        @Transactional
        void test2() {
            var description = "S";
            assertThrows(ConstraintViolationException.class, () -> {
                logServiceAutowired.error(description);
            });
        }

        @Test
        @DisplayName("When description has the right amount of caracters, it should return a Log with LogType as ERROR and logOrigin as BACK_END")
        @Transactional
        void test3() {
            var description = "Correct Description";
            var result = logServiceAutowired.error(description);
            assertEquals(result.getLogType(), Log.LogType.ERROR);
            assertEquals(result.getDescription(), description);
            assertEquals(result.getLogOrigin(), Log.LogOrigin.BACK_END);
            assertNotNull(result.getCreatedDate());
            assertNotNull(result.getId());
        }
    }

    @Nested
    @DisplayName("Tests of logFromFront method")
    class logFromFrontMethodTests {
        @Test
        @DisplayName("When description has more tha 64 caracters, it should return ConstraintViolationException")
        @Transactional
        void test1() {
            var description = "5tg0Ee33GXqny1my8YCGE4u7oC2XIPI3Z509djL7MSw1G4WcMzbyyMxpB6D87f83S";
            var logRequest = LogRequest.builder()
                    .logType(Log.LogType.SUCCESS)
                    .description(description)
                    .build();
            assertThrows(ConstraintViolationException.class, () -> {
                logServiceAutowired.logFromFront(logRequest);
            });
        }

        @Test
        @DisplayName("When description has more tha 8 caracters, it should return ConstraintViolationException")
        @Transactional
        void test2() {
            var description = "S";
            var logRequest = LogRequest.builder()
                    .logType(Log.LogType.SUCCESS)
                    .description(description)
                    .build();
            assertThrows(ConstraintViolationException.class, () -> {
                logServiceAutowired.logFromFront(logRequest);
            });
        }

        @Test
        @DisplayName("When description has the right amount of caracters and LogType is SUCCESS, it should return a Log with LogType as SUCCESS and logOrigin as FRONT_END")
        @Transactional
        void test3() {
            var description = "Correct Description";
            var logRequest = LogRequest.builder()
                    .logType(Log.LogType.SUCCESS)
                    .description(description)
                    .build();
            var result = logServiceAutowired.logFromFront(logRequest);
            assertEquals(result.getLogType(), Log.LogType.SUCCESS);
            assertEquals(result.getDescription(), description);
            assertEquals(result.getLogOrigin(), Log.LogOrigin.FRONT_END);
            assertNotNull(result.getCreatedDate());
            assertNotNull(result.getId());
        }

        @Test
        @DisplayName("When description has the right amount of caracters and LogType is INFO, it should return a Log with LogType as INFO and logOrigin as FRONT_END")
        @Transactional
        void test4() {
            var description = "Correct Description";
            var logRequest = LogRequest.builder()
                    .logType(Log.LogType.INFO)
                    .description(description)
                    .build();
            var result = logServiceAutowired.logFromFront(logRequest);
            assertEquals(result.getLogType(), Log.LogType.INFO);
            assertEquals(result.getDescription(), description);
            assertEquals(result.getLogOrigin(), Log.LogOrigin.FRONT_END);
            assertNotNull(result.getCreatedDate());
            assertNotNull(result.getId());
        }

        @Test
        @DisplayName("When description has the right amount of caracters and LogType is ERROR, it should return a Log with LogType as ERROR and logOrigin as FRONT_END")
        @Transactional
        void test5() {
            var description = "Correct Description";
            var logRequest = LogRequest.builder()
                    .logType(Log.LogType.ERROR)
                    .description(description)
                    .build();
            var result = logServiceAutowired.logFromFront(logRequest);
            assertEquals(result.getLogType(), Log.LogType.ERROR);
            assertEquals(result.getDescription(), description);
            assertEquals(result.getLogOrigin(), Log.LogOrigin.FRONT_END);
            assertNotNull(result.getCreatedDate());
            assertNotNull(result.getId());
        }
    }

    @Nested
    @DisplayName("Tests of getAll method")
    class getAllMethodTests {

        @Test
        @DisplayName("When asked for page one and ten results per page, it should return one page and 10 results")
        void test1() {
            var logList = new ArrayList<Log>(100);
            for (long i = 0; i < 100; i++) {
                logList.add(Log.builder()
                        .id(i)
                        .logOrigin(Log.LogOrigin.BACK_END)
                        .description("Log description " + i)
                        .logType(Log.LogType.SUCCESS)
                        .build());
            }
            var pageNumber = 0;
            var pageSize = 10;
            var totalElements = logList.size();
            var fromIndex = pageNumber * pageSize;
            var toIndex = Math.min(fromIndex + pageSize, totalElements);
            var logSublist = logList.subList(fromIndex, toIndex);
            var logPage = new PageImpl<>(logSublist, PageRequest.of(pageNumber, pageSize), totalElements);

            when(logRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("id").descending())))
                    .thenReturn(logPage);

            var result = logService.getAll(pageNumber, pageSize);

            assertEquals(100, result.getTotalElements());
            assertEquals(10, result.getTotalPages());
            assertEquals(10, result.getContent().size());
            assertEquals(0L, result.getContent().get(0).getId());
            assertEquals("Log description 0", result.getContent().get(0).getDescription());
        }

    }
}
