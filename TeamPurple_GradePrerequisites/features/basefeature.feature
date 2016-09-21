#Author: Sebastian Snyder, Alani Peters, Cole Spears
Feature: Test Feature

Scenario Outline:
	Given <banner> and <crn>
	Then the student is <allowed> to be enrolled in the course

Examples:
| banner      | crn     | allowed      |
| "000481289" | "10961" | "allowed"    |
| "000481199" | "10880" | "allowed"    |
| "000321267" | "10338" | "disallowed" |
