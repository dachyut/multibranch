package code.myorg;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
	
	@BeforeClass
    public static void beforeClass() {
        System.out.println("Before Class");
    }
 
    @Before
    public void before() {
        System.out.println("Before Test Case");
    }
 
     
    @After
    public void after() {
        System.out.println("After Test Case");
    }
 
    @AfterClass
    public static void afterClass() {
        System.out.println("After Class");
    }
	
    @Test
    public void shouldAnswerWithTruetest()
    {
        assertTrue( true );
    }
    
    @Test
	public void test1() {
    	App ob = new App();
		assertEquals(8, ob.add(4, 4));
	}
    
    @Test
	public void test2() {
    	App ob = new App();
		assertEquals(2, ob.divide(10, 5));
	}
    
    @Test
	public void test3() {
    	App ob = new App();
		assertEquals(40, ob.multiply(10, 4));
	}
    
//    @Test
//    public void test4() {
//        String obj1 = "junit";
//        String obj2 = "junit";
//        String obj3 = "test";
//        String obj4 = "test";
//        String obj5 = null;
//        int var1 = 1;
//        int var2 = 2;
//        int[] arithmetic1 = { 1, 2, 3 };
//        int[] arithmetic2 = { 1, 2, 3 };
// 
//        assertEquals(obj1, obj2);
// 
//        assertSame(obj3, obj4);
// 
//        assertNotSame(obj2, obj1);
// 
//        assertNotNull(obj1);
// 
//        assertNull(obj5);
// 
//        assertTrue(obj5,false);
// 
//        assertArrayEquals(arithmetic1, arithmetic2);
//    }
//
//
//
//
}
