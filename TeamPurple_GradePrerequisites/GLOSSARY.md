**abstract class**  
    A java class that cannot be instantiated; Instead, its functionality must be inherited.

**BannerID**  
    A nine-digit string that is unique to each student.

**Course**  
    An entity that has a Course Code, and an optional set of prerequisites.
    
**Course Code**  
    The code that can be used to describe a course, such as "IT101".
    
**Course Domain**  
    The word prefix portion of the course code (the "IT" in "IT101.)
    
**Course Number**  
    The three digit number unique to each course within each domain (the "101" in "IT101".)
    
**CRN**  
    The unique (somewhat) five-digit code that can be used to refer to a specific section of a course.
    
**Placement Test Code**
    A course code whose coure domain has a "$P" suffix, i.e. "MATH$P120".
    This code refers to a placement test rather than a course.
    
**Prerequisite**  
    Any condition a student is required to satisfy in order to take a course.
 >    
 > **ACT Score Prerequisite**  
 >     A minimum ACT score prerequisite, either overall or a specific section.
 >         
 > **Compound Prerequisite**  
 >     Any prerequisite defined by other prerequisites seperated by logical operators such as and/or and whose order of operations can be denoted by parentheses.
 >     
 > **Course Prerequisite**  
 >     A course that must be taken as a prerequisite, usually with a minimum grade requirement.
 >     
 > **SAT Score Prerequisite**  
 >     A minimum SAT score prerequisite, either overall or a specific section.
    
**Student**  
    An entity that has a Banner ID and an optional set of taken courses.
