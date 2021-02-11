package com.tistory.jhun91.inflearnthejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study() {
        Study study = new Study(-10);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다"),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 함")
        );
    }

    @Test
    void create_new_study_again() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
    }

    @Test
    void create_study_again() {
        //assertTimeout 은 Duration 의 시간이 넘어가도 종료될때까지 기다려야 한다.
        //assertTimeoutPreemptively 를 사용하면 시간이 넘어가면 즉시 종료된다.
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
        //TODO ThreadLocal 살펴보기!
    }

    @Test
    void assumeTrueTest() {
        String test_env = "LOCAL";  //System.getenv("TEST_ENV");
        //System.out.println(test_env);
        //assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

        assumingThat("OPER".equalsIgnoreCase(test_env), () -> {
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void assumeAnnotationTest() {

    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach");
    }
}