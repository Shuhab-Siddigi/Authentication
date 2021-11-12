
build:
	javac -d bin/ src/*.java

clean-build: clean build

server: build
	java -cp bin/ Server

client: build
	java -cp bin/ Client

clean:
	rm -r bin/*.class
