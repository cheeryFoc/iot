package com.hxk.demo.config;

import com.hxk.demo.utils.security.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 监听 @CreateBy @LastModifiedBy 自动注入用户名
 *
 * @author cheer
 */
@Configuration
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if (userLogin.isPresent()) {
            return userLogin;
        }
        return Optional.of("system");
    }
}
