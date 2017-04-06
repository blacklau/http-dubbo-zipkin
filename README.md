1、下载zipkin并运行
  wget -O zipkin.jar 'https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec'
  java -jar zipkin.jar
2、下载本项目并安装
  mvn install
3、将生成的三个war包部署到Tomcat

4、http调用
   http://localhost:8080/louie-webapi/service.do?service=order.customer.orderInfo&data={"id":89}
   
