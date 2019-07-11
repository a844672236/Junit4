package com.maocg;

import static org.junit.Assert.assertTrue;

import com.maocg.core.Gunit;
import com.maocg.core.InterceptorClasses;
import com.maocg.core.Interceptorimpl;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit test for simple App.
 */
@RunWith(Gunit.class)
@InterceptorClasses(value = {Interceptorimpl.class} )
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
