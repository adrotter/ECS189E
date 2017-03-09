import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by anton on 3/5/2017.
 */

public class TestStudent {
    private IInstructor instructor;
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
        this.admin.createClass("Class", 2017, "Instructor", 2);


    }

    @Test
    //test registerStudent for correctness
    public void TestRegisterStudent(){
        this.student.registerForClass("1","Class",2017);
        assertTrue(this.student.isRegisteredFor("1","Class",2017));

    }
    @Test
    //test registerStudent to see if student registers if max capacity is reached
    public void TestRegisterStudent1(){
        this.student.registerForClass("1","Class",2017);
        assertTrue(this.student.isRegisteredFor("1","Class",2017));
        this.student.registerForClass("2","Class",2017);
        assertTrue(this.student.isRegisteredFor("2","Class",2017));
        this.student.registerForClass("3","Class",2017);
        assertFalse(this.student.isRegisteredFor("3","Class",2017));

    }
    @Test
    //test registerStudent to see if student registering multiple times increases enrollment count
    public void TestRegisterStudent2(){
        this.student.registerForClass("1","Class",2017);
        this.student.registerForClass("1","Class",2017);
        this.student.registerForClass("1","Class",2017);
        this.student.registerForClass("2","Class",2017);
        assertTrue(this.student.isRegisteredFor("1","Class",2017));
        assertTrue(this.student.isRegisteredFor("2","Class",2017));
    }
    @Test
    //test registerStudent to see if registering for a class that doesn't exist works
    public void TestRegisterStudent3(){
        this.student.registerForClass("1","Class1",2017);
        assertFalse(this.student.isRegisteredFor("1","Class1",2017));
    }
    @Test
    //test dropClass for correctness
    public void TestDropClass(){
        this.student.registerForClass("1","Class",2017);
        this.student.dropClass("1","Class",2017);
        assertFalse(this.student.isRegisteredFor("1","Class1",2017));
    }

    @Test
    //test dropClass for multiple drops of the same student. class capacity is 2
    public void TestDropClass1(){
        this.student.registerForClass("1","Class",2017);
        this.student.dropClass("1","Class",2017);
        this.student.dropClass("1","Class",2017);
        this.student.dropClass("1","Class",2017);
        this.student.dropClass("1","Class",2017);
        this.student.registerForClass("2","Class",2017);
        this.student.registerForClass("3","Class",2017);
        assertFalse(this.student.isRegisteredFor("1","Class1",2017));
        assertTrue(this.student.isRegisteredFor("2","Class1",2017));
        assertTrue(this.student.isRegisteredFor("3","Class1",2017));

    }
    @Test
    //test submitHomework for correctness
    public void TestSubmitHomework(){
        this.student.registerForClass("1","Class",2017);
        this.instructor.addHomework("Instructor","Class",2017,"HW1","lol");
        this.student.submitHomework("1","HW1","Lol","Class",2017);
        assertTrue(student.hasSubmitted("1","HW1","Class",2017));
    }
    @Test
    //test submitHomework for when a student did not submit
    public void TestSubmitHomework1(){
        this.student.registerForClass("1","Class",2017);
        this.instructor.addHomework("Instructor","Class",2017,"HW1","lol");
        assertFalse(student.hasSubmitted("1","HW1","Class",2017));
    }
    @Test
    //test submitHomework for when the homework never existed
    public void TestSubmitHomework2(){
        this.student.registerForClass("1","Class",2017);
        this.student.submitHomework("1","HW1","Lol","Class",2017);
        assertFalse(student.hasSubmitted("1","HW1","Class",2017));
    }
    @Test
    //test submitHomework for when the class does not exist
    public void TestSubmitHomework3(){
        this.student.registerForClass("1","Class1",2017);
        this.instructor.addHomework("Instructor","Class1",2017,"HW1","lol");
        this.student.submitHomework("1","HW1","Lol","Class1",2017);
        assertFalse(student.hasSubmitted("1","HW1","Class1",2017));
    }
    @Test
    //test submitHomework for when the class is taught by same instructor but in a different year
    public void TestSubmitHomework4(){
        this.admin.createClass("Class", 2018, "Instructor", 2);
        this.student.registerForClass("1","Class",2017);
        this.instructor.addHomework("Instructor","Class",2018,"HW1","lol");
        this.student.submitHomework("1","HW1","Lol","Class",2018);
        assertFalse(student.hasSubmitted("1","HW1","Class1",2018));
    }

}
