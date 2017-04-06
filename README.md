订单详情的查询演示系统的调用链跟踪,各工程作用，louie-webapi向外部提供http调用，返回json；louie-order模拟订单系统，查询订单详情；louie-account模拟账户系统，查询账户信息，调用链如图：
1、下载zipkin并运行
  wget -O zipkin.jar 'https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec'
  java -jar zipkin.jar
  
2、下载本项目并安装
  mvn install
  
3、将生成的三个war包部署到Tomcat

4、http调用
   http://localhost:8080/louie-webapi/service.do?service=order.customer.orderInfo&data={"id":89}
   
   打开zipkin ui，http://localhost:9411/，查看调用链信息， 
   ![跟踪信息](https://github.com/blacklau/http-dubbo-zipkin/blob/master/trace-info.png)
   
    span信息，带请求参数
    ![span信息,带请求参数](https://github.com/blacklau/http-dubbo-zipkin/blob/master/request-params.png)
   
