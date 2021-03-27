//package com.hdc.config;
//import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
//import org.apache.shiro.mgt.DefaultSubjectDAO;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * shiro配置类
// */
//@Configuration
//public class ShiroConfig {
//
//    @Bean(name = "securityManager")
//    public DefaultWebSecurityManager securityManager(MyRealm myRealm){
//        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
//        // 设置自定义 realm.
//        securityManager.setRealm(myRealm);
//
//        //关闭session
//        DefaultSubjectDAO subjectDAO=new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator sessionStorageEvaluator=new DefaultSessionStorageEvaluator();
//        sessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
//        securityManager.setSubjectDAO(subjectDAO);
//        return securityManager;
//    }
//
//    /**
//     * 先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
//     */
//    @Bean
//    public ShiroFilterFactoryBean factory(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
//        ShiroFilterFactoryBean factoryBean=new ShiroFilterFactoryBean();
//        factoryBean.setSecurityManager(securityManager);
//        // 添加自己的过滤器并且取名为jwt
//        Map<String, Filter> filterMap=new LinkedHashMap<String, Filter>();
//        //设置我们自定义的JWT过滤器
//        filterMap.put("jwt",new JWTFilter());
//        factoryBean.setFilters(filterMap);
//
//        // 设置无权限时跳转的 url;
//        factoryBean.setUnauthorizedUrl("/system/login");
//        Map<String,String>filterRuleMap=new HashMap<String, String>();
//
//        //token超时或者错误返回的提示不拦截
//        filterRuleMap.put("/system/unauthorized/**","anon");
//        //token为空提示 不拦截
//        filterRuleMap.put("/system/tokenIsNull","anon");
//        // 访问登录不拦截
//        filterRuleMap.put("/system/login","anon");
//        filterRuleMap.put("/system/signing","anon");
//        //放开静态资源
//        filterRuleMap.put("/static/**","anon");
//        filterRuleMap.put("/css/**","anon");
//        filterRuleMap.put("/img/**","anon");
//        // 所有请求通过我们自己的JWT Filter
//        filterRuleMap.put("/**","jwt");
//        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
//        return factoryBean;
//    }
//
//    /**
//     * 添加注解支持，如果不加的话很有可能注解失效
//     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
//
//        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }
//
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//}
