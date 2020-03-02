package com.bobo.shirosample.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.bobo.shirosample.auth.CustomRealm;
import com.bobo.shirosample.auth.JwtFilter;
import com.bobo.shirosample.service.UserService;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {

	/*@Bean
    public SecurityManager securityManager(UserService userService) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(new CustomRealm(userService));
        return securityManager;
    }
 */
	@Bean
    public ShiroFilterFactoryBean shirFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
 
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
 
        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/ajaxLogin", "anon");
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
 
        filterChainDefinitionMap.put("/add", "perms[权限添加]");
        filterChainDefinitionMap.put("/students/**", "authc");
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        filterChainDefinitionMap.put("/guest/**", "roles[guest]");
        filterChainDefinitionMap.put("/ops/**", "roles[ops]");
        filterChainDefinitionMap.put("/api/shiro/admin/**", "roles[admin]");
        
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/students/**", "jwt");
 
        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "anon");
 
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
	
	  /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     * 
     * @return
     */
    @Bean
    public CustomRealm getShiroAuthorizingRealm(UserService userService) {
    	CustomRealm userRealm=new CustomRealm(userService);
    		//userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        userRealm.setCachingEnabled(false);
    	 	return  userRealm;
       
    }
    @Bean(name="credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
       HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
       hashedCredentialsMatcher.setHashAlgorithmName("md5");
       hashedCredentialsMatcher.setHashIterations(2);
       //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
       hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
 
       return hashedCredentialsMatcher;
    }
    
    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    
//    @Bean
//    public ShiroDialect shiroDialect() {
//        return new ShiroDialect();
//    }
    
}
