package com.ttd.auth;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 安全认证
 * @author wolf
 * @since 2016-03-10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentication {

    /**
     * 认证方式
     * @return
     */
    AuthType type() default (AuthType.NONE);
    
    /**
     * 权限编码
     * @return
     */
    String code() default ("");
    
    /**
     * 编码集合条件
     * @return
     */
    AuthCondtion condtion() default (AuthCondtion.AND);
    
    /**
     * 是否需要AuthToken认证（用于openAPI的客户身份认证）
     * @return
     */
    boolean isRequiredAuthToken() default(false);
    
}
