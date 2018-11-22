package com.FizzBuzz;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FizzBuzzTest {
    private FizzBuzz fb;

    @Before
    public void init(){
        fb = new FizzBuzz();
    }

    @Test
    public void test(){
        assertEquals("1", fb.call(1));
        assertEquals("11", fb.call(11));
        assertEquals("1129", fb.call(1129));
    }

    @Test
    public void test_multiple_3(){
        assertEquals("Fizz", fb.call(3));
        assertEquals("Fizz", fb.call(9));
        assertEquals("Fizz", fb.call(333));
    }

    @Test
    public void test_multiple_5(){
        assertEquals("Buzz", fb.call(5));
        assertEquals("Buzz", fb.call(55));
        assertEquals("Buzz", fb.call(575));
    }

    @Test
    public void test_multiple_shared(){
        assertEquals("FizzBuzz", fb.call(15));
        assertEquals("FizzBuzz", fb.call(90));
        assertEquals("FizzBuzz", fb.call(330));
    }
}
