package meteorologicalmonitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//解决跨域问题
//因为浏览器中同源策略，前后端名不能不一致
@Configuration
public class CorsConfig {
    // 设置允许跨域请求
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOriginPatterns("*") // 允许所有域
                        .allowedMethods("*") // 允许任何method
                        .allowedHeaders("*") // 允许任何请求头
                        .allowCredentials(false) // 不允许证书、cookie
                        .exposedHeaders(HttpHeaders.SET_COOKIE)//允许客户端访问 Set-Cookie 头
                        .maxAge(3600L); // maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
            }
        };
    }
}
