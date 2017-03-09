import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;



/**
 * Created by anton on 3/1/2017.
 */
public class TestInstructor {
    private IInstructor instructor;
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.student.registerForClass("Anton","Class",2017);

    }



    @Test
    //test addHomework for correctness
    public void TestAddHomework(){
        this.instructor.addHomework("Instructor","Class",2017,"HW1","lol");
        assertTrue(this.instructor.homeworkExists("Class",2017,"HW1"));
        this.instructor.addHomework("Instructor","Class",2017,"HW2","lol");
        assertTrue(this.instructor.homeworkExists("Class",2017,"HW2"));
        this.admin.createClass("Class2", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor","Class2",2017,"HW1","lol");
        assertTrue(this.instructor.homeworkExists("Class2",2017,"HW1"));
    }

    @Test
    //test AddHomework for a professor who was not assigned to the class
    public void TestAddHomework1(){
        this.instructor.addHomework("Instructor1","Class",2017,"HW1","lol");
        assertFalse(this.instructor.homeworkExists("Class",2017,"HW1"));
    }

    @Test
    //test homeworkExists for homework that doesn't exist
    public void TestHomeworkExists(){
        this.instructor.addHomework("Instructor","Class",2017,"HW1","lol");
        assertFalse(this.instructor.homeworkExists("Class",2017,"HW2"));
    }

    @Test
    //test homeworkExists for a class that doesn't exist namewise
    public void TestHomeworkExists1(){
        this.instructor.addHomework("Instructor","Class2",2017,"HW1","lol");
        assertFalse(this.instructor.homeworkExists("Class2",2017,"HW1"));
    }

    @Test
    //test homeworkExists for a class that doesn't exist yearwise
    public void TestHomeworkExists2(){
        this.instructor.addHomework("Instructor","Class2",2018,"HW1","lol");
        assertFalse(this.instructor.homeworkExists("Class2",2017,"HW1"));
    }

    @Test
    //test AssignGrade for correctness
    public void TestAssignGrade(){
        this.instructor.addHomework("Instructor","Class",2017,"HW1","lol");
        this.student.submitHomework("Anton","HW1","lol","Class",2017);
        this.instructor.assignGrade("Instructor","Class",2017,"HW1","Anton",92);
        assertEquals(java.util.Optional.of(92),this.instructor.getGrade("Class",2017,"HW1","Anton"));
    }

    @Test
    //test AssignGrade for a student that's not registered for the class
    public void TestAssignGrade1(){
        this.instructor.addHomework("Instructor","Class",2017,"HW1","lol");
        this.student.submitHomework("Missingno","HW1","lol","Class",2017);
        this.instructor.assignGrade("Instructor","Class",2017,"HW1","Missingno",92);
        assertNull(this.instructor.getGrade("Class",2017,"HW1","Missingno"));
    }

    @Test
    //test AssignGrade for a different instructor of the same class from the same year
    public void TestAssignGrade2(){
        this.instructor.addHomework("Instructor1","Class",2017,"HW1","lol");
        this.student.submitHomework("Anton","HW1","lol","Class",2017);
        this.instructor.assignGrade("Instructor","Class",2017,"HW1","Anton",92);
        assertNull(this.instructor.getGrade("Class",2017,"HW1","Anton"));
    }
    @Test
    //test AssignGrade for a same instructor and class name but different year
    public void TestAssignGrade3(){
        this.instructor.addHomework("Instructor","Class",2018,"HW1","lol");
        this.student.submitHomework("Anton","HW1","lol","Class",2017);
        this.instructor.assignGrade("Instructor","Class",2017,"HW1","Anton",92);
        assertNull(this.instructor.getGrade("Class",2017,"HW1","Anton"));
    }




}
