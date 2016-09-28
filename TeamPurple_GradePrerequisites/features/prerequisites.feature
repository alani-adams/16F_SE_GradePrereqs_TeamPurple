#Author: Sebastian Snyder, Alani Peters, Cole Spears
Feature: Test Feature

Background: Prereqs

	#Prereqs
	Given "CS115" has no prerequisite
	Given "CS116" has a prerequisite of "CS115"
	Given "CS120" has a prerequisite of "MATH124 or MATH185 or MATH$P124 or MATH109 or MATH$P109 or CS115"
	Given "CS130" has a prerequisite of "CS120"
	Given "CS230" has a prerequisite of "CS130"
	Given "CS311" has a prerequisite of "CS130"
	Given "CS315" has a prerequisite of "(IT220 and CS116) or CS120"
	Given "CS330" has a prerequisite of "CS116 or CS120"
	Given "CS332" has a prerequisite of "CS130 and MATH227"
	Given "CS341" has a prerequisite of "CS120 and MATH186"
	Given "CS374" has a prerequisite of "CS230"

	Given "IT220" has a prerequisite of "CS115 or CS120"

	Given "MATH109" has a prerequisite of "ACAD102 or MATH$P109 or MATH$P121 or MATW019"
	Given "MATH120" has a prerequisite of "ACAD104 or ACT Math 20 or SAT Math 500 or MATH$P120 or MATW019"
	Given "MATH124" has a prerequisite of "MATH109 or MATH$P124 or MATH121"
	Given "MATH130" has a prerequisite of "ACAD102 or ACT Math 20 or SAT Math 500 or MATH$P120"
	Given "MATH185" has a prerequisite of "MATH124 or MATH$P185"
	Given "MATH186" has a prerequisite of "MATH185"
	Given "MATH187" has a prerequisite of "MATH131 or MATH185"
	Given "MATH227" has a prerequisite of "MATH185 or CS120"
	Given "MATH237" has a prerequisite of "MATH120 or MATH123 or (MATW020 and MATW129)"
	Given "MATH238" has a prerequisite of "MATH237 or MATH147"
	Given "MATH286" has a prerequisite of "MATH186"
	Given "MATH334" has a prerequisite of "MATH127 or MATH227 or MATH130"
	Given "MATH341" has a prerequisite of "CS120 and MATH186"
	Given "MATH361" has a prerequisite of "MATH186"
	Given "MATH410" has a prerequisite of "MATH309"
	Given "MATH418" has a prerequisite of "MATH309 or MATH311"
	Given "MATH497" has a prerequisite of "MATH397"
	
	#Courses
	Given "CS115" with a CRN of "10457"
	Given "CS116" with a CRN of "10916"
	Given "CS120" with a CRN of "10859"
	Given "CS130" with a CRN of "10852"
	Given "CS311" with a CRN of "10850"
	Given "CS315" with a CRN of "10910"
	Given "CS330" with a CRN of "10880"
	Given "CS332" with a CRN of "10851"
	Given "CS341" with a CRN of "11129"
	Given "CS374" with a CRN of "10853"

	Given "IT220" with a CRN of "10844"

	Given "MATH109" with a CRN of "10325"
	Given "MATH120" with a CRN of "11128"
	Given "MATH124" with a CRN of "10330"
	Given "MATH130" with a CRN of "10333"
	Given "MATH185" with a CRN of "10345"
	Given "MATH186" with a CRN of "10344"
	Given "MATH187" with a CRN of "10335"
	Given "MATH227" with a CRN of "10336"
	Given "MATH237" with a CRN of "10337"
	Given "MATH238" with a CRN of "10338"
	Given "MATH286" with a CRN of "10339"
	Given "MATH334" with a CRN of "11130"
	Given "MATH341" with a CRN of "10340"
	Given "MATH361" with a CRN of "11341"
	Given "MATH410" with a CRN of "10375"
	Given "MATH418" with a CRN of "11298"
	Given "MATH497" with a CRN of "10347"
	
	#Student Info
	Given "000481289" has taken "10457" and received a "A"
	Given "000481199" has taken "10859" and received a "B"
	Given "000321267" has taken "10859" and received a "F"

	#Case with 2 "and" prereqs, passed both
	Given "000111111" has taken "10852" and received a "A"
	Given "000111111" has taken "10336" and received a "A"

	#Case with 2 "and" prereqs, fails one
	Given "000222222" has taken "10344" and received a "A"
	Given "000222222" has taken "10859" and received a "F"

	#Case with Math Placement Test
	Given "000333333" has taken course "MATH$P120" and received a "A"

	#Case with ACT Math, meets threshold
	Given "000444444" has a "ACT Math" score of "25"

	#Case with ACT Math, doesn't meet threshold
	Given "000555555" has a "ACT Math" score of "19"

	#Case with SAT Math, meets threshold
	Given "000666666" has a "SAT Math" score of "800"

	#Case with SAT Math, doesn't meet threshold
	Given "000777777" has a "SAT Math" score of "40"

	#Case with logical or
	Given "000888888" has taken "10325" and received a "A"
	Given "000999999" has taken course "MATH$P124" and received a "A"
	Given "000123456" has taken "10457" and received a "B"
	Given "000234567" has taken "10325" and received a "B"
	Given "000234567" has taken course "MATH$P124" and received a "A"

	#Case with compound and/or
	Given "111111111" has taken "10844" and received a "A"
	Given "111111111" has taken "10916" and received a "B"
	Given "111111111" has taken "10859" and received a "A"
	Given "222222222" has taken "10844" and received a "A"
	Given "222222222" has taken "10916" and received a "C"
	Given "333333333" has taken "10859" and received a "A"

Scenario Outline: Student Take Course
	Given A student with BannerID <banner>
	Then the student <allowed> allowed to be enrolled in <crn>

	Examples:
	| banner      | crn     | allowed  |
	| "000481289" | "10916" | "is"     |
	| "000481199" | "10852" | "is"     |
	| "000321267" | "10457" | "is"     |
	| "000321267" | "10852" | "is not" |
	| "000321267" | "10850" | "is not" |
	| "000111111" | "10851" | "is"     |
	| "000222222" | "11129" | "is not" |
	| "000333333" | "11128" | "is"     |
	| "000444444" | "10333" | "is"     |
	| "000555555" | "10333" | "is not" |
	| "000666666" | "11128" | "is"     |
	| "000777777" | "10333" | "is not" |
	| "000888888" | "10330" | "is" 	   |
	| "000999999" | "10330" | "is"     |
	| "000123456" | "10330" | "is not" |
	| "000234567" | "10330" | "is"     |
	| "111111111" | "10457" | "is"     |
	| "222222222" | "10457" | "is"     |
	| "333333333" | "10457" | "is"     |

