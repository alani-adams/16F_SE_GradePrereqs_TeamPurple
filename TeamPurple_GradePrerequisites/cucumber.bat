javac -cp "jars/*;." step_definitions/GradePrerequisitesTest.java implementation/GradePrerequisites.java
java -cp "jars/*;." cucumber.api.cli.Main -p pretty --snippets camelcase -g step_definitions features
java -cp "jars/*;." cucumber.api.cli.Main -p progress --snippets camelcase -g step_definitions features