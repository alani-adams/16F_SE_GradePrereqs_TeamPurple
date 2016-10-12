#Author: Sebastian Snyder, Alani Peters, Cole Spears
Feature: Test Feature

Scenario Outline: Student Take Course
	Given A student with BannerID "<banner>"
	Then the student "<allowed>" allowed to be enrolled in "<courseCode>"

    Examples: CSV file
    | banner    | courseCode | allowed  |
    | 51        | BIBL101    | is       |
    | 3093      | MPIN411    | is       |
    | 734       | BIOL113    | is not   |
    | 734       | PEAC340    | is       |
    | 835       | PEAC100    | is       |
    | 835       | ACCT311    | is not   |
    | 333175    | BIOL471    | is not   |
    | 333175    | BIOL340    | is       |
    | 480764    | BIOL354    | is       |
    | 480764    | PSYC370    | is not   |
    | 837148    | BIBL101    | is       |
    | 837982    | CORE110    | is       |
    
    | 59039     | THEA425    | is not   |
    | 59039     | PEAC234    | is       |
    | 59039     | FREN112    | is       |
    | 59039     | THEA137    | is       |
    | 59039     | MPIM221    | is       |
    | 59039     | COMS345    | is       |
    
    Examples: Classification Prerequisites
    | banner    | crn       | allowed  |
    | 438032    | COMP485   | is       |
    | 443188    | COMP485   | is not   |
    | 438032    | COMP330   | is       |
    | 463387    | COMP330   | is not   |
    | 472854    | COMP330   | is       |







