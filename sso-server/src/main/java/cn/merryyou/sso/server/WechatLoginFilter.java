package cn.merryyou.sso.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @Description:验证用户名密码正确后，生成一个token，并将token返回给客户端 该类继承自AbstractAuthenticationProcessingFilter，
 *                                                 重写了其中的2个方法attemptAuthentication:接收并解析用户凭证。
 *                                                 successfulAuthentication:用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 *                                                 可参考:{@link UsernamePasswordAuthenticationFilter}
 *
 * @Package: com.txmd.security.filter
 * @author: zyl
 * @date: 2018年6月26日 下午12:03:44
 */
@Slf4j
//@Component
public class WechatLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 只接受POST方式
     */
    private boolean postOnly = true;

    @Autowired
    private ObjectMapper mapper;

    public WechatLoginFilter() {
        super(new AntPathRequestMatcher("/authentication/wechat", "POST"));// 指定拦截的url和请求方式
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, JsonParseException, JsonMappingException, IOException {
        // request.setAttribute("RequestURL", getCurrent(request, response));
        String openId = (String) request.getSession().getAttribute("openId");
        log.info("preHandle wTokenId : " + openId);
        String code = request.getParameter("code");
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
            openId, null);
        /**
         * 通过 {@link ProvierMagaer} 调用{@link JwtLoginProvider}
         */
        return this.getAuthenticationManager().authenticate(authRequest);

    }


    // 原请求信息的缓存及恢复
    private RequestCache requestCache = new HttpSessionRequestCache();
    // 用于重定向
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // 原请求信息的缓存及恢复
        SavedRequest savedRequest = requestCache.getRequest(req, res);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(req, res, targetUrl);
        }
    }

}
