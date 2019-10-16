
1.参考资料：SpringBoot从入门到精通教程 https://blog.csdn.net/hemin1003/article/details/82038244；
progress：
1.Maven启动srpingboot和测试正常：
  run: mvn spring-boot:run
  test: mvn test;
2:简单的controller并回复json数据；
3.controller读取配置文件并注入；
4.数据库操作，JPA自动更新数据表；
5.遇到问题：StudentRepository无法注入：
增加注解：@SpringBootTest(classes=FristsbaApplication.class)
FristsbaApplication是主程序入口，包括SpringApplication.run(FristsbaApplication.class, args);
6:springboot + mybatis 多数据源最简解决方案：导致repository无法注入，暂时未验证
7：Sprignboot + Spring Shiro:访问API前必须先登录(/login),然后才能访问/students;
 7.1:login成功后，默认展示index页面
 7.2:shiro 标签；需要注入ShiroDialect，详见ShiroConfig
8：Springboot Restful + JWT验证 + Role：
  8.1:自定义UserLoginToken 注解，包含role和reqired 验证；
  8.2：自定义AuthenticationInterceptor并注入，拦截未验证的请求；
  8.3：UserLoginToken支持role 验证，详见JWTUserController;
  8.4:UserLoginToken支持用户只能访问自己的数据，即/api/user/{userid}/xxxx
9:Springboot + mybatis + 多数据源：
 9.1:新增数据源配置：stock.datasource.url/username/password;
 9.2:新增DefaultDataSourceConfig和StockDataSourceConfig，特别注意PACKAGE和MAPPER_LOCATION常量；
 9。3：default datasource需要增加Primary，不然会影响JPA的datasource；   
 10：Springboot + shiro + JWT综合校验：
   10.1：Shiro Realm通过Token获取roles，避免重复读取数据库；
   10.2：Shiro验证是可通过Token或者username+password校验；
   10.3：特殊的验证，可自定义注解，例如UserLoginToken结合Jwt Filter来实现验证；
   10.4:AuthenticationInterceptor有自动刷新token并从Response返回的过程；
   10.4:ai/login登录后，下次访问将获取到的token置入header，可通过JWTFilter验证并授权；
     重点关注ShiroConfig将JWTFilter植入FilterChain;
    