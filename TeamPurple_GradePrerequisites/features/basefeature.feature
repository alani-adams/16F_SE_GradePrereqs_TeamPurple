#Author: Sebastian Snyder, Alani Peters, Cole Spears
Feature: Test Feature

Background: Prereqs
	Given "CS115" with a CRN of "10457" with a prereq of "NA"
	Given "CS116" with a CRN of "10916" with a prereq of "CS115"
	Given "CS120" with a CRN of "10859" with a prereq of "NA"
	Given "CS130" with a CRN of "10852" with a prereq of "CS120"
	Given "CS311" with a CRN of "10850" with a prereq of "CS130"
	
Background: Student Previous Classes
	Given "000481289" has taken "10457" which is "CS115" and recieved a "A"
	Given "000481199" has taken "10852" which is "CS130" and recieved a "B"
	Given "000321267" has taken "10859" which is "CS120" and recieved a "F"

Scenario Outline: Student Take Course
	Given <banner> and <crn>
	Then the student is <allowed> to be enrolled in the course

	Examples:
	| banner      | crn     | allowed      |
	| "000481289" | "10916" | "allowed"    |
	| "000481199" | "10850" | "allowed"    |
	| "000321267" | "10850" | "disallowed" |
