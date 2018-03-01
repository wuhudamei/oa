#!/bin/sh
# 本脚本通过Jenkins调用，如ssh work@10.10.1.2 'bash -x -s' < bin/deploy.sh
# 这种远程执行脚本的方式，属于非交互式Shell，不会触发诸如~/.bash_profile之类文件的载入
export JAVA_HOME=/home/work/jdk1.6.0_45
DIR=/home/work/amway-mall
WAR_NAME=amway-mall-1.0-SNAPSHOT.war
cd ${DIR}

ps -ef | grep ${DIR} | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{print $2}' | xargs kill -9 2>/dev/null

[ -e ${WAR_NAME} ] && mv ${WAR_NAME} ${WAR_NAME}.bak
curl -O http://192.168.1.6:7080/job/amway-mall-test/lastSuccessfulBuild/artifact/target/${WAR_NAME}
rm -rf tomcat-7.0.55/webapps/ROOT tomcat-7.0.55/webapps/ROOT.war
cp ${WAR_NAME} tomcat-7.0.55/webapps/ROOT.war
# 工作目录为tomcat目录
cd tomcat-7.0.55
bin/startup.sh