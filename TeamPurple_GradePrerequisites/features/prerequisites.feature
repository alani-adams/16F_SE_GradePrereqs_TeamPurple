#Author: Sebastian Snyder, Alani Peters, Cole Spears
Feature: Test Feature

Scenario Outline: Student Take Course
	Given A student with BannerID "<banner>"
	Then the student "<allowed>" allowed to be enrolled in "<crn>"

    Examples: CSV file
    | banner    | crn       | allowed  |
    | 51        | 15000     | is       |
    | 3093      | 15013     | is not   |
    | 3093      | 14296     | is       |





