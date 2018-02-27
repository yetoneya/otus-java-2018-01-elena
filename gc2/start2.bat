
java -Xms128m -Xmx128m -XX:MaxMetaspaceSize=256m -XX:+UseParallelOldGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+ScavengeBeforeFullGC -XX:+CMSScavengeBeforeRemark -XX:+UseParallelGC -verbose:gc -Xloggc:./logs2/gc2.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/ -jar target/gc2.jar >jmv.out