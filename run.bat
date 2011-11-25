@echo off

set M2_REPOSITORY=%HOME%\.m2\repository
set JAVA_OPTS=

java %JAVA_OPTS% -cp %M2_REPOSITORY%\commons-codec\commons-codec\1.5\commons-codec-1.5.jar;%M2_REPOSITORY%\com\nativelibs4java\bridj\0.6\bridj-0.6.jar;%M2_REPOSITORY%\jline\jline\1.0\jline-1.0.jar;target\classes org.rejna.p11shell.Shell