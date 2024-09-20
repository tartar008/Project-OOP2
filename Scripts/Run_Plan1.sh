#!/usr/bin/expect -f

# เปลี่ยนไปยังไดเรกทอรีที่มีไฟล์ Java
cd ../Ignore/Plan1

# แสดงไดเรกทอรีปัจจุบันและไฟล์ในไดเรกทอรี
puts "Current directory: [exec pwd]"
puts "Files in directory:"
exec ls

# ตรวจสอบว่าไฟล์ .java มีอยู่ในไดเรกทอรี
set java_files [glob *.java]
if {[llength $java_files] == 0} {
    puts "No .java files found in the directory"
    exit 1
}

# เคลียร์เนื้อหาของไฟล์ output.txt ก่อนเริ่มการบันทึก
exec sh -c "echo -n > output.txt"

# ตั้งค่าการบันทึกเอาต์พุตไปยังไฟล์
log_file output.txt

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
