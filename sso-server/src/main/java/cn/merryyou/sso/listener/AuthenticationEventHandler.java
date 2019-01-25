package cn.merryyou.sso.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import java.util.Collection;

/**
 * Created by zhangyinglong on 2019/1/15.
 *
 * 身份验证失败后
 *  1.发布事件：{@link AuthorizationFailureEvent}
 *  2.抛出异常：{@link org.springframework.security.access.AccessDeniedException}
 * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#beforeInvocation(Object)
 *
 */
@Component
@Slf4j
public class AuthenticationEventHandler {

    @EventListener
    public void handlerAuthorizationFailureEvent(AuthorizationFailureEvent authorizationFailureEvent){
//        log.info("AuthorizationFailureEvent{}",authorizationFailureEvent.getClass().getSimpleName());
        System.out.println("AuthorizationFailureEvent "+authorizationFailureEvent.getClass().getSimpleName());
        Authentication authentication = authorizationFailureEvent.getAuthentication();
//        log.info("---" + authentication.toString());
        System.out.println("---" + authentication.toString());
    }

    @EventListener
    public void handlerAuthorizationFailureEvent(AbstractAuthenticationFailureEvent authorizationFailureEvent){
//        log.info("authorizationFailureEvent{}",authorizationFailureEvent.getClass().getSimpleName());
        System.out.println("AbstractAuthenticationFailureEvent "+authorizationFailureEvent.getClass().getSimpleName());
        Authentication authentication = authorizationFailureEvent.getAuthentication();
//        log.info("---" + authentication.toString());
        System.out.println("---" + authentication.toString());
    }
    @EventListener
    public void handlerAuthorizationFailureEvent(EmbeddedServletContainerInitializedEvent authorizationFailureEvent){
//        log.info("authorizationFailureEvent{}",authorizationFailureEvent.getClass().getSimpleName());
        System.out.println("EmbeddedServletContainerInitializedEvent "+authorizationFailureEvent.getClass().getSimpleName());
        EmbeddedServletContainer source = authorizationFailureEvent.getSource();
        log.info("启动成功---" + source.getClass().getCanonicalName());
    }

    /**
     * FrameworkServlet执行结束
     * @param authorizationFailureEvent
     */
    @EventListener
    public void handlerAuthorizationFailureEvent(ServletRequestHandledEvent authorizationFailureEvent){
//        log.info("authorizationFailureEvent{}",authorizationFailureEvent.getClass().getSimpleName());
        System.out.println("ServletRequestHandledEvent "+authorizationFailureEvent.getClass().getSimpleName());

        String requestUrl = authorizationFailureEvent.getRequestUrl();
        String servletName = authorizationFailureEvent.getServletName();
        String method = authorizationFailureEvent.getMethod();
        Class<? extends ServletRequestHandledEvent> aClass = authorizationFailureEvent.getClass();
        String sessionId = authorizationFailureEvent.getSessionId();
        String userName = authorizationFailureEvent.getUserName();
        long processingTimeMillis = authorizationFailureEvent.getProcessingTimeMillis();
        long timestamp = authorizationFailureEvent.getTimestamp();
        Throwable failureCause = authorizationFailureEvent.getFailureCause();

        log.info("FrameworkServlet执行结束--{},{},{},{},{},{},{},{}",aClass.getSimpleName(),method,servletName,requestUrl,sessionId,userName,timestamp,processingTimeMillis);
        if(failureCause!=null){
            log.error("error:",failureCause);
        }
    }

    @EventListener
    /**
     * Client
     * OAuth2ClientAuthenticationProcessingFilter
     */
    public void handlerAuthorizationFailureEvent(AuthenticationSuccessEvent authorizationFailureEvent){
//        log.info("authorizationFailureEvent{}",authorizationFailureEvent.getClass().getSimpleName());
        System.out.println("AuthenticationSuccessEvent "+authorizationFailureEvent.getClass().getSimpleName());

        Authentication authentication = authorizationFailureEvent.getAuthentication();
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.info("验证成功 {} , {}",principal,authorities.toString());
    }

    /**
     * Client
     * AbstractAuthenticationProcessingFilter.successfulAuthentication
     */
    @EventListener
    public void handlerAuthorizationFailureEvent(InteractiveAuthenticationSuccessEvent authorizationFailureEvent){
//        log.info("authorizationFailureEvent{}",authorizationFailureEvent.getClass().getSimpleName());
        System.out.println("InteractiveAuthenticationSuccessEvent "+authorizationFailureEvent.getClass().getSimpleName());

        Authentication authentication = authorizationFailureEvent.getAuthentication();
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.info("验证成功 {} , {}",principal,authorities.toString());
    }

}
