#!/usr/bin/expect -f

# เคลียร์เนื้อหาของไฟล์ output.txt ก่อนเริ่มการบันทึก
exec sh -c "echo -n > output.txt"

# ตั้งค่าการบันทึกเอาต์พุตไปยังไฟล์
log_file output.txt

# ตั้งค่า CLASSPATH
exec sh -c "export CLASSPATH=.:./classes:./json-simple-1.1.1.jar"

# คอมไพล์ไฟล์ Java ด้วยตัวเลือก -Xlint:unchecked
# ใช้ glob เพื่อค้นหาไฟล์ .java
set java_files [glob *.java]
if {[llength $java_files] == 0} {
    puts "No .java files found in the directory"
    exit 1
}

# คอมไพล์ไฟล์ .java ทีละไฟล์
foreach file $java_files {
    exec javac -Xlint:unchecked -d classes $file
}

# ตรวจสอบการคอมไพล์
set class_files [glob classes/*.class]
if {[llength $class_files] == 0} {
    puts "Compilation failed or no .class files found"
    exit 1
}

# เริ่มต้นโปรแกรม Java
spawn java -cp .:./classes:./json-simple-1.1.1.jar Main

# รอข้อความแรกที่โปรแกรมแสดงและให้ input
expect "Choose an option:"
send "1\r"

# รอข้อความใหม่และให้ input
expect "Choose an option:"
send "3\r"

# รอข้อความใหม่และให้ input
expect "Please enter the room number you want to book:"
send "101\r"

# รอข้อความใหม่และให้ input
expect "Choose an option:"
send "4\r"

# รอให้โปรแกรมเสร็จสิ้น
expect eof

# ปิดการบันทึก
log_file
