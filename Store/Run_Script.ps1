# เคลียร์เนื้อหาของไฟล์ output.txt
Clear-Content output.txt

# เริ่มต้นบันทึกผลลัพธ์ไปยัง output.txt
Start-Transcript -Path "output.txt"

# ตั้งค่า CLASSPATH
$env:CLASSPATH = ".;.\classes;.\json-simple-1.1.1.jar"
Write-Host "CLASSPATH set to: $env:CLASSPATH"

# คอมไพล์ไฟล์ Java
$javaFiles = Get-ChildItem -Filter *.java
if ($javaFiles.Count -eq 0) {
    Write-Host "No .java files found in the directory"
    Stop-Transcript
    exit 1
}

foreach ($file in $javaFiles) {
    Write-Host "Compiling: $file"
    $compileResult = & javac -Xlint:unchecked -d classes $file.FullName
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Compilation failed for $file"
        Stop-Transcript
        exit 1
    } else {
        Write-Host "Compiled: $file"
    }
}

# ตรวจสอบการคอมไพล์
$classFiles = Get-ChildItem -Path classes -Filter *.class
if ($classFiles.Count -eq 0) {
    Write-Host "Compilation failed or no .class files found"
    Stop-Transcript
    exit 1
}

# เริ่มต้นโปรแกรม Java
Write-Host "Running Java program..."
$javaResult = & java -cp ".;.\classes;.\json-simple-1.1.1.jar" Main
if ($LASTEXITCODE -ne 0) {
    Write-Host "Java program failed to run."
    Write-Host "Error: $javaResult"
    Stop-Transcript
    exit 1
} else {
    Write-Host "Java program ran successfully."
}

# ปิดการบันทึก
Stop-Transcript
