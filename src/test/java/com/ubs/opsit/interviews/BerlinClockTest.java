package com.ubs.opsit.interviews;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class BerlinClockTest {
    private TimeConverter testClass;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        testClass = new BerlinClock();
    }

    @Test
    public void testNullTime() {
        // check null input time
        String expectedResult = null;
        String testTime = null;
        String result = testClass.convertTime(testTime);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testEmptyTime() {
        // check empty input time
        String expectedResult = null;
        String testTime = "";
        String result = testClass.convertTime(testTime);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testInvalidTime() {
        // test 1: check alphabetical string input
        String expectedResult = null;
        String testTime = "dummy string";
        String result = testClass.convertTime(testTime);
        assertEquals(expectedResult, result);

        // test 2: input time with wrong hour range
        testTime = "53:59:00";
        result = testClass.convertTime(testTime);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testValidTime() {
        // test 1: check time for input time 13:17:01
        String expectedResult = "O\r\n" +
                "RROO\r\n" +
                "RRRO\r\n" +
                "YYROOOOOOOO\r\n" +
                "YYOO";
        String testTime = "13:17:01";
        String result = testClass.convertTime(testTime);
        assertEquals(expectedResult, result);

        // test 2: check time for input time 00:00:00
        expectedResult = "Y\r\n" +
                "OOOO\r\n" +
                "OOOO\r\n" +
                "OOOOOOOOOOO\r\n" +
                "OOOO";
        testTime = "00:00:00";
        result = testClass.convertTime(testTime);
        assertEquals(expectedResult, result);

        // test 3: check time for input time 24:00:00
        expectedResult = "Y\r\n" +
                "RRRR\r\n" +
                "RRRR\r\n" +
                "OOOOOOOOOOO\r\n" +
                "OOOO";
        testTime = "24:00:00";
        result = testClass.convertTime(testTime);
        assertEquals(expectedResult, result);
    }
}