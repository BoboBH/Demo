
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