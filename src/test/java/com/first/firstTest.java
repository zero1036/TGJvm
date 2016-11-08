package com.first;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by linzc on 2016/10/27.
 */
public class FirstTest {
    @InjectMocks
    private First firstTest;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFirst() throws Exception {
        assertEquals(firstTest.GetTarget(), 2);
    }
}
