package cn.merryyou.soo.client.listener;

import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by zhangyinglong on 2019/1/15.
 *
 * 身份验证失败后
 *  1.发布事件：{@link org.springframework.security.access.event.AuthorizationFailureEvent}
 *  2.抛出异常：{@link org.springframework.security.access.AccessDeniedException}
 * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#beforeInvocation(Object)
 *
 */
@Component
public class AuthenticationEventHandler {

    @EventListener
    public void handlerAuthorizationFailureEvent(AuthorizationFailureEvent authorizationFailureEvent){
        Authentication authentication = authorizationFailureEvent.getAuthentication();
        System.out.println(authentication.toString());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getAuthorities());
    }

}
