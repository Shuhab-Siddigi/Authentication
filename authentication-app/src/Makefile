build:
	javac -d ../bin/ *.java

client: build
	java -cp ../bin Client

server: build
	java -cp ../bin ApplicationServer

dataBase:
	java -cp "../lib/sqlite-jdbc-3.36.0.3.jar:" dataBase.java
