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
    | 734       | 14105     | is not   |
    | 734       | 15412     | is       |
    | 835       | 14166     | is       |
    | 835       | 14202     | is       |
    | 333175    | 30322     | is not   |
    | 333175    | 21411     | is       |
    | 480764    | 14130     | is       |
    | 480764    | 20534     | is not   |
    | 480764    | 30238     | is not   |
    | 837148    | 14976     | is       |
    | 837982    | 14787     | is       |
    | 837982    | 14911     | is       |





