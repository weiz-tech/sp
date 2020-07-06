package tech.weizhang.learn.javacore;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * LamdaLearn Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>7月 6, 2020</pre>
 */
public class LamdaLearnTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testMain() throws Exception {
        //TODO: Test goes here...
    }


    @Test
    public void testGetTestDtos() throws Exception {
        Assert.assertEquals(4,LamdaLearn.getTestDtos(4).size());
    }

} 
