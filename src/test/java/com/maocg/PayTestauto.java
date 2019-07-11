package com.maocg;

import com.maocg.core.Gunit;
import com.maocg.core.InterceptorClasses;
import com.maocg.core.Interceptorimpl;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Gunit.class)
@InterceptorClasses(value = {Interceptorimpl.class})
public class PayTestauto {
//    @Before
//    public void setUp() {
//        System.out.println("setup");
//    }

    @Test
    public void test() {
      //  System.out.println("测试用例");
    }

//    @After
//    public void tearDown() {
//        System.out.println("tearDown");
//    }
}
