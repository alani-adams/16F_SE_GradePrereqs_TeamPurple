#Author: Sebastian Snyder, Alani Peters, Cole Spears
Feature: Test Feature

Background: Prereqs

	#Prereqs
	Given "CS115" with a CRN of "10457" with a prereq of "NA"
	Given "CS116" with a CRN of "10916" with a prereq of "CS115"
	Given "CS120" with a CRN of "10859" with a prereq of "NA"
	Given "CS130" with a CRN of "10852" with a prereq of "CS120"
	Given "CS311" with a CRN of "10850" with a prereq of "CS130"
	
	#Student Info
	Given "000481289" has taken "10457" and recieved a "A"
	Given "000481199" has taken "10852" and recieved a "B"
	Given "000321267" has taken "10859" and recieved a "F"

Scenario Outline: Student Take Course
	Given A student with BannerID <banner>
	Then the student <allowed> allowed to be enrolled in <crn>

	Examples:
	| banner      | crn     | allowed  |
	| "000481289" | "10916" | "is"     |
	| "000481199" | "10850" | "is"     |
	| "000321267" | "10850" | "is not" |