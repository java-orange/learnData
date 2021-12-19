FROM centos:7
#RUN yum install -y vim
RUN ["yum","install","-y","vim"]
EXPOSE 5672
EXPOSE 15672
WORKDIR /data
WORKDIR bb
ENV BASE_DIR /data/bb
#COPY aa.txt /data/bb
ADD apache-tomcat-8.5.61.tar.gz $BASE_DIR
RUN mv apache-tomcat-8.5.61 tomcat
WORKDIR tomcat
VOLUME $BASE_DIR/tomcat/webapps
#ENTRYPOINT ls /data
#CMD /data/bb 
ENTRYPOINT ["ls"]
CMD ["/data/bb"]

