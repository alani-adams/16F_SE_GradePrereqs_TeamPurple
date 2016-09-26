abstract class
    A java class that cannot be instantiated; Instead, a class must inherit from it.

BannerID
    A nine-digit string that is unique to each student.

Course
    An entity that has a Course Code, and an optional set of prerequisites.
    
Course Code
    The code that can be used to describe a course, such as "IT101".

CRN
    The unique (somewhat) five-digit code that can be used to refer to a specific course section.
    
Student
    An entity that has a Banner ID and an optional set of taken courses.
    
Prerequisite
    Any condition a student is required to satisfy in order to take a course.
    
    ACT Score Prerequisite
        A minimum ACT score prerequisite, either overall or a specific section.
    
    SAT Score Prerequisite
        A minimum SAT score prerequisite, either overall or a specific section.
        
    Compound Prerequisite
        Any prerequisite defined by other prerequisites seperated by logical operators such as and/or and whose order of operations can be denoted by parenteses.
    
    Course Prerequisite
        A course that must be taken as a prerequisite, usually with a minimum grade requirement.
        
