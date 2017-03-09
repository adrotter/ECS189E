import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by anton on 3/1/2017.
 */
public class TestAdmin {
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    @Test
    //a valid class
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    //test if year is less than current year
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    //test if a class exists without creating it
    public void testClassExists() {
        assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    //test outcome of creation with negative year
    public void testMakeClass3() {
        this.admin.createClass("Test", -1, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", -1));
    }
    @Test
    //test having the same professor teach up to 3 classes.  1 and 2 should pass, 3 should fail.
    public void testMakeClass4() {
        this.admin.createClass("Class1", 2017, "Instructor", 15);
        this.admin.createClass("Class2", 2017, "Instructor", 15);
        this.admin.createClass("Class3", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Class1", 2017));
        assertTrue(this.admin.classExists("Class2", 2017));
        assertFalse(this.admin.classExists("Class3", 2017));
    }
    @Test
    //test outcome of creation with a capacity less than 1
    public void testMakeClass5() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", -1));
    }
    @Test
    //test having the same class name and year.  duplicate should not appear
    public void testMakeClass6() {
        this.admin.createClass("Class1", 2017, "Instructor1", 15);
        assertTrue(this.admin.classExists("Class1", 2017));
        this.admin.createClass("Class1", 2017, "Instructor2", 15);
        assertEquals("Instructor1", this.admin.getClassInstructor("Class1",2017));
    }

    @Test
    //test changeCapacity for correctness.
    public void testChangeCapacity()
    {
        //make a valid class
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test",2017,16);
        assertEquals(16,this.admin.getClassCapacity("Test",2017));
    }

    @Test
    //test changeCapacity to see if capacity changes if given a lower number than enrolled number.
    public void testChangeCapacity2()
    {
        //make a valid class
        this.admin.createClass("Test", 2017, "Instructor", 3);
        this.student.registerForClass("1","Test",2017);
        this.student.registerForClass("2","Test",2017);
        this.student.registerForClass("3","Test",2017);
        this.admin.changeCapacity("Test",2017,2);
        assertEquals(3,this.admin.getClassCapacity("Test",2017));
    }
}
