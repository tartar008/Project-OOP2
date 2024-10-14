## COMMAND COMPILE AND RUN

## Main.java

```

javac -cp lib/json-simple-1.1.1.jar -d bin src/bookings/*.java src/customers/*.java src/management/*.java src/rooms/*.java Main.java Main.java

java -cp "bin;lib/json-simple-1.1.1.jar" Main

```

## การใช้ set CLASSPATH ของ 

### Command Prompt

```
set CLASSPATH=.;lib/json-simple-1.1.1.jar;bin
```

### PowerShell

```
$env:CLASSPATH=".;lib/json-simple-1.1.1.jar;bin"
```

### รันคำสั่ง javac และ java

```

javac -d bin src/bookings/*.java src/customers/*.java src/management/*.java src/rooms/*.java Main.java
java Main

```