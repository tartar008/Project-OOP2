## COMMAND COMPILE AND RUN


# Main.java

javac -cp lib/json-simple-1.1.1.jar -d bin src/bookings/*.java src/customers/*.java src/management/*.java src/rooms/*.java Main.java MainManager.java

java -cp "bin;lib/json-simple-1.1.1.jar" MainManager



# TestMain.java

javac -cp lib/json-simple-1.1.1.jar -d bin src/bookings/*.java src/customers/*.java src/management/*.java src/rooms/*.java TestMain.java 

java -cp "bin;lib/json-simple-1.1.1.jar" TestMain

