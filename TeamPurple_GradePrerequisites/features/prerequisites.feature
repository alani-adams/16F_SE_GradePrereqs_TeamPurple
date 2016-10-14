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
    | 480764    | PSYC370    | is       |
    | 837148    | BIBL101    | is       |
    | 837982    | CORE110    | is       |
    
    | 59039     | THEA425    | is not   |
    | 59039     | PEAC234    | is       |
    | 59039     | FREN112    | is       |
    | 59039     | THEA137    | is       |
    | 59039     | MPIM221    | is       |
    | 59039     | COMS345    | is       |
    | 6502      | MATH124    | is       |
    | 6502      | CORE115    | is       |
    | 9198      | MATW20     | is       |
    | 9198      | MKTG342    | is not   |
    
     Examples: Classification Prerequisites
    | banner    | courseCode | allowed  |
    | 438032    | COMP485    | is       |
    | 443188    | COMP485    | is not   |
    | 438032    | COMP330    | is       |
    | 463387    | COMP330    | is not   |

    Examples: Grade Prerequisites
    | banner    | courseCode | allowed  |
    | 206198    | PSYC351    | is not   |
    | 255771    | BIOL222    | is not   |
    | 255771    | BIOL351    | is not   |
    | 173956    | CHEM134    | is not   |
    | 173956    | CHEM131    | is not   |
    | 173956    | BIOL351    | is not   |

    Examples: Compound Prerequites
    | banner    | courseCode | allowed  |
    | 471637    | ANSC343    | is not   |
    | 173956    | BIOL312    | is not   |

    Examples: Enroll in a class already taken
    | banner    | courseCode | allowed  |
    | 54870     | BIBL211    | is       |
    | 723355    | PSYC682    | is       |
    
