# เคลียร์เนื้อหาของไฟล์ output.txt ก่อนเริ่มการบันทึก
Set-Content output.txt ""

# ตั้งค่า CLASSPATH
$env:CLASSPATH = ".;./classes;./json-simple-1.1.1.jar"

# คอมไพล์ไฟล์ Java ด้วยตัวเลือก -Xlint:unchecked
$javaFiles = Get-ChildItem -Filter *.java
if ($javaFiles.Count -eq 0) {
    Write-Host "No .java files found in the directory"
    exit 1
}

# คอมไพล์ไฟล์ .java ทีละไฟล์
foreach ($file in $javaFiles) {
    # คอมไพล์ไฟล์และบันทึกผลลัพธ์ลงใน output.txt
    javac -Xlint:unchecked -d classes $file.FullName 2>> output.txt
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Compilation failed for $file"
        exit 1
    }
}

# ตรวจสอบการคอมไพล์
$classFiles = Get-ChildItem -Path classes -Filter *.class
if ($classFiles.Count -eq 0) {
    Write-Host "No .class files found"
    exit 1
}

# เริ่มต้นโปรแกรม Java และบันทึกผลลัพธ์ลงในไฟล์ output.txt
$process = Start-Process -FilePath java -ArgumentList "-cp .;./classes;./json-simple-1.1.1.jar Main" -RedirectStandardOutput output.txt -PassThru -Wait

# รอให้โปรแกรมเสร็จสิ้น
$process.WaitForExit()
