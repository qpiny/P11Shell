#!/bin/sh

M2_REPOSITORY=~/.m2/repository
JAVA_OPTS=

case $(uname -i) in
	*64*)
		ARCH=64
		;;
	*)
		ARCH=32
		;;
esac
java ${JAVA_OPTS} -cp \
${M2_REPOSITORY}/commons-codec/commons-codec/1.5/commons-codec-1.5.jar:\
${M2_REPOSITORY}/com/nativelibs4java/bridj/0.6/bridj-0.6.jar:\
${M2_REPOSITORY}/jline/jline/1.0/jline-1.0.jar:\
${M2_REPOSITORY}/org/javatuples/javatuples/1.2/javatuples-1.2.jar:\
target/classes org.rejna.p11shell.Shell $ARCH
