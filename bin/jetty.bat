cd /d %~dp0
cd ..
mvn clean test-compile exec:java -Dexec.mainClass=QuickStartServer -Dexec.classpathScope=test