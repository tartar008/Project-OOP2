# Hotel Room Booking System

## Introduction

This project demonstrates how to compile and run a Java application that uses the `json-simple` library for JSON handling.

## Prerequisites

- Java Development Kit (JDK)
- `json-simple-1.1.1.jar` library

## Setting the CLASSPATH

### Windows

To set the `CLASSPATH`, you can use the following commands:

```bash
set CLASSPATH=.;classes;json-simple-1.1.1.jar
```
Alternatively, in PowerShell:
```bash
$env:CLASSPATH = ".;classes;json-simple-1.1.1.jar"
```

### Linux
For Linux, you can set the CLASSPATH as follows:
```bash
export CLASSPATH=.:./classes:./json-simple-1.1.1.jar
```

## Compiling the Code
To compile the Java files, run:
```bash
javac -cp .;lib/json-simple-1.1.1.jar;bin -d bin *.java
```

## Running the Application
You can run the application using the following commands:
```bash
java -cp .;lib/json-simple-1.1.1.jar;bin Main
```

## Git Commands
```bash
git add .
git commit -m "commit"
git push -u origin main
git pull
```
