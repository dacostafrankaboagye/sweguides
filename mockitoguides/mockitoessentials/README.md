- By
  - Sujoy Acharya


# Chap 1 - Exploring Test Doubles
-  Automated unit tests provide an
   extremely effective mechanism for catching regressions, especially when combined
   with test-driven development; it creates a test safety net for the developers.

## working with unit test
- A common understanding of unit testing is the testing of the smallest possible part of
software, such as a single method, a small set of related methods, or a class. 
- In reality, we do not test methods; we test a logical unit and its behavior instead.
- Benefits of Test Automation
    - The side effects of code changes are detected immediately
    - Saves time; no need for immediate regression testing
      -  Manual regression testing is
         tedious and time-consuming, but if you have an automated unit test suite,
         then you can delay the regression testing until the functionality is done.
- Characteristics of a unit test
  - should have a fast test execution
  - A test should not depend on the result of another test or rather test
    execution order
    - Unit test frameworks can execute tests in any order
  - should not depend on database access, file access, or any long running task.
  - should be readable and expressive
- Note
  - You cannot automate a unit test if your API class depends on slow external entities,
    such as data access objects or JNDI lookup. Then, you need test doubles to isolate the
    external dependencies and automate the unit test

## Understanding test doubles
```text
We all know about stunt doubles in movies. A stunt double or dummy is a trained 
replacement used for dangerous action sequences in movies, such as a fight sequence 
on the top of a burning train, jumping from an airplane, and so on, mainly fight 
scenes. Stunt doubles are used to protect the real actors, are used when the actor is 
not available, or when the actor has a contract to not get involved in stunts.
Similarly, sometimes it is not possible to unit test the code because of the 
unavailability of the collaborator objects, or the cost of interaction and instantiation 
of collaborator
```
-  For instance, when the code is dependent on database access, it is
   not possible to unit test the code unless the database is available, or 
- when a piece of
   code needs to send information to a printer and the machine is not connected to a
   LAN
-  The primary reason for using doubles is to isolate the unit you are testing
   from the external dependencies
- Test doubles act as stunt doubles

> Gerard Meszaros coined the term test doubles in his book xUNIT TEST PATTERNS,
Addison-Wesley—this book explores the various test doubles and sets the foundation
for Mockito.

- we create `test doubles` to impersonate collaborators
- Types
  - `Dummy`
  - `Stub`
  - `Mock`
  - `Fake`
  - `Spy`

## using dummy objects
- [./src/main/java/org/example/chap1/testdoubles/dummy](./src/main/java/org/example/chap1/testdoubles/dummy)

```text
In movies, sometimes a double doesn't perform anything; they just appear on the 
screen. One such instance would be standing in a crowded place where the real actor 
cannot go
```
- Likewise a dummy object is passed as a mandatory parameter object

```text
A dummy 
object is not directly used in the test or code under test, but it is required for the 
creation of another object required in the code under test

```
- the usage of dummy objects

```text
We'll create an examination grade system. The program will analyze the 
aggregate of all the subjects and determine the grade of a student

```


```java
public class Student {
    private final String name;
    private final String roleNumber;
    public Student(String name, String roleNumber) {
        this.name = name;
        this.roleNumber = roleNumber;
    }
    // ...
}
public class Marks {
    private final Student student;
    private final String subjectId;
    private final BigDecimal marks;
    public Marks(Student student, String subjectId, BigDecimal marks) {
        this.student = student;
        this.subjectId = subjectId;
        this.marks = marks;
    }
    // ...
}


```
- Note that the Marks constructor accepts a Student object to represent the marks of a student. 
- So, a Student object is needed to create a Marks object


```java
public class Marks {

    private final Student student;
    private final String subjectId;
    private final BigDecimal marks;

    public Marks(Student student, String subjectId, BigDecimal marks) {
        this.student = student;
        this.subjectId = subjectId;
        this.marks = marks;
    }
    //...
}

public class Teacher {

    private BigDecimal calculatePercentage(BigDecimal aggregate, int numberOfSubject){
        //...
    }

    public Grades generateGrade(List<Marks> marksList){
        //...
    }
}

```

- DummyStudent class and extend the Student
- Note that the constructor passes NULL to the super constructor and 
- throws a runtime exception from the getRoleNumber() and getName() methods

```text

Create a JUnit test to verify our assumption that when a student gets more 
than 75 percent (but less than 90 percent) in aggregate, then the teacher 
generates the grade as VeryGood, creates a DummyStudent object, and passes 
it as Student to the Marks constructor
```
- [./src/test/java/org/example/chap1/testdoubles/dummy/TeacherTest.java](./src/test/java/org/example/chap1/testdoubles/dummy/TeacherTest.java)

```java
    @Test
    void when_marks_above_75_returns_very_good() {
        // Student is required for the creation of the marks
        // that is where the dummy comes in
        DummyStudent dummyStudent = new DummyStudent();
        Marks inEnglish = new Marks(dummyStudent,//...);
        Marks inMath = new Marks(dummyStudent,//...);
        Marks inHistory = new Marks(dummyStudent,//...);
        
        //...
    }

```
- Note

```text

Note that a DummyStudent object is created and passed to all the three Marks
objects, as the Marks constructor needs a Student object. This dummyStudent
object is not used in the Teacher class or test method, but it is necessary for 
the Marks object
```

## working with stubs
- delivers indirect inputs to the caller when the stub's methods are called
-  Stubs are programmed only for the test scope
-  Stubs may record other information such as
   how many times they are invoked and so on
- think of scenarios that are not easy to imitate
  - subs helps use to simulate these conditions
-  Stubs can also be programmed to return a
   hardcoded result
- e.g.  demonstrate stubbing

```text

- The service implementation class
  delegates the Students Object Creation Task
  to the StudentDAO object
  
 - If anything 
goes wrong in the data access layer, then the DAO throws an 
SQLException error

- The implementation class catches the 
exceptions and sets the error message to the response object.

```

- now the question is 
  - How can you test the SQLException condition?
    - Create a stub object
      and throw an exception
    - Whenever the create method is invoked
      on the stubbed DAO, the DAO throws an exception

```text

The following 
ConnectionTimedOutStudentDAOStub class implements the StudentDAO
interface and throws an SQLException error from the create() method
```

```java
public class ConnectionTimedOutStudentDAOStub implements StudentDAO{
    @Override
    public String create(String name, String className) throws SQLException {
        throw new SQLException("DB Connection timed out");
    }
}
```

-  Test the SQLException condition

```java
@Test
void  when_connection_times_out_then_the_student_is_not_saved() {
    studentService = new StudentServiceImpl(
            new ConnectionTimedOutStudentDAOStub()
    );
    String classNine = "IX";
    String johnSmith = "john Smith";
    CreateStudentResponse response = studentService.create(johnSmith, classNine);
    assertFalse(response.isSuccess());
}
```

- what have we done here

```text
The error condition is stubbed and passed into the service implementation 
object.

When the service implementation invokes the create() method on 
the stubbed DAO, it throws an SQLException error.

```

- Stubs are very handy to impersonate error conditions and external dependencies
  - (you can achieve the same thing with a mock; this is just one approach)


```text
Suppose 
you need to test a code that looks up a JNDI resource and asks the resource to return 
some value. You cannot look up a JNDI resource from a JUnit test; you can stub the 
JNDI lookup code and return a stubbed object that will give you a hardcoded value.

```

## Exploring a test spy
- a spy object spies on real object
```test

 A spy is a variation of a stub, but 
instead of only setting the expectation, a spy records the method calls made to the 
collaborator. A spy can act as an indirect output of the unit under test and can also 
act as an audit log
```
- e.g.
- [./src/main/java/org/example/chap1/testdoubles/spy](./src/main/java/org/example/chap1/testdoubles/spy)

```text

The StudentService class contains a map of the course names and students. 
The enrollToCourse method looks up the map; if no student is enrolled, 
then it creates a collection of students, adds the student to the collection, and 
puts the collection back in the map. If a student has previously enrolled for 
the course, then the map already contains a Student collection. So, it just 
adds the new student to the collection.students list.
```
```java
public class StudentService {
    private Map<String, List<Student>> studentCouseMap = new HashMap<>();  // course-name : list of students
    public void enrollToCourse(String studentName, Student student){
        List<Student> list = studentCouseMap.get(studentName);
        if(list == null){
            list = new ArrayList<>();
        }
        if(!list.contains(student)){
            list.add(student);
        }
        studentCouseMap.put(studentName, list);
    }
}

```

```text

- The enrollToCourse method is a void method and doesn't return a
response. To verify that the enrollToCourse method was invoked with a 
specific set of parameters, we can create a spy object. 

- The service will write to 
the spy log, and the spy will act as an indirect output for verification

- Create 
a spy object to register method invocations
```

- - [./src/main/java/org/example/chap1/testdoubles/spy/MethodInvocation.java](./src/main/java/org/example/chap1/testdoubles/spy/MethodInvocation.java)

```text

The MethodInvocation class represents a method invocation: the method 
name, a parameter list, and a return value. Suppose a sum() method is 
invoked with two numbers and the method returns the sum of two numbers, 
then the MethodInvocation class will contain a method name as sum, a 
parameter list that will include the two numbers, and a return value that 
will contain the sum of the two numbers.
```

- The following is the spy object snippet. It has a registerCall
  method to log a method call instance

```text
 Modify the StudentService class to set a spy and log every method 
invocation to the spy object:


```

- [./src/main/java/org/example/chap1/testdoubles/spy/StudentService.java](./src/main/java/org/example/chap1/testdoubles/spy/StudentService.java)

```text

 a test to examine the method invocation and arguments. The following 
JUnit test uses the spy object and verifies the method invocation
```
































# Chap 2 - Socializing with Mockito
# Chap 3 - Accelerating Mockito
# Chap 4 - Behavior-Driven Development with Mockito
# Chap 5 - Unit Testing the Legacy code with Mockito
# Chap 6 - Developing SOA with Mockito
# Chap 7 - Unit Testing GWT Code with Mockito