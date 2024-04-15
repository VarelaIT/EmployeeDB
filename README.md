# EmployeeDB

First Java web application.

The goal of this application is creating a system were you can store, view, update and delete employees form the database.

## Using:
* Jakarta Servlets
* JSP
* PostgreSQL
* Apache Tomcat
* Apache Maven


## Bug Log

* 15-Apr-2024 13:26:53.752 WARNING [Catalina-utility-1] org.apache.catalina.loader.WebappClassLoaderBase.clearReferencesJdbc The web application [EmployeeDB] registered the JDBC     driver [org.postgresql.Driver] but failed to unregister it when the web application was stopped. To prevent a memory leak, the JDBC Driver has been forcibly unregistered.

* 15-Apr-2024 13:58:19.024 INFO [main] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/home/uriel/bin/apache-tomcat-10.1.20/webapps/docs] has finished in [20] ms

* 15-Apr-2024 13:58:19.037 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in [1064] milliseconds

* 15-Apr-2024 13:58:19.040 SEVERE [main] org.apache.catalina.core.StandardServer.await Failed to create server shutdown socket on address [localhost] and port [8005] (base port [8005] and offset [0])
  java.net.BindException: Address already in use
  at java.base/sun.nio.ch.Net.bind0(Native Method)
  at java.base/sun.nio.ch.Net.bind(Net.java:565)
  at java.base/sun.nio.ch.Net.bind(Net.java:554)
  at java.base/sun.nio.ch.NioSocketImpl.bind(NioSocketImpl.java:636)
  at java.base/java.net.ServerSocket.bind(ServerSocket.java:391)
  at java.base/java.net.ServerSocket.<init>(ServerSocket.java:278)
  at org.apache.catalina.core.StandardServer.await(StandardServer.java:577)
  at org.apache.catalina.startup.Catalina.await(Catalina.java:847)
  at org.apache.catalina.startup.Catalina.start(Catalina.java:795)
  at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
  at java.base/java.lang.reflect.Method.invoke(Method.java:580)
  at org.apache.catalina.startup.Bootstrap.start(Bootstrap.java:345)
  at org.apache.catalina.startup.Bootstrap.main(Bootstrap.java:473)

* 15-Apr-2024 13:58:19.041 INFO [main] org.apache.coyote.AbstractProtocol.pause Pausing ProtocolHandler ["http-nio-8080"]
