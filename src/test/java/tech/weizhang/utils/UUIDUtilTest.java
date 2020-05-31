package tech.weizhang.utils; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertEquals;

/** 
* UUIDUtil Tester. 
* 
* @author weizhang
* @since <pre>5月 31, 2020</pre> 
* @version 1.0 
*/ 
public class UUIDUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: get() 
* 
*/ 
@Test
public void testGet() throws Exception {
    assertEquals(32,UUIDUtil.get().length());
} 


} 
