javac -d app/classes/java/main/org/example src/main/java/org/example/*.java
jar --create --file app/libs/Heapsort.jar --main-class=org.example.Heapsort -C app/classes/java/main/org/example .
javadoc -d build/docs/javadoc src/main/java/org/example/*.java
java -jar app/libs/Heapsort.jar
pause