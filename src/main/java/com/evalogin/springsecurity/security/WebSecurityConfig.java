package com.evalogin.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/user/**").permitAll()
                // 그 외 어떤 요청이든 '인증'
                .anyRequest().authenticated()
                .and()
                // [로그인 기능]
                .formLogin()
                // 로그인 View 제공 (GET /user/login)
                .loginPage("/user/login")
                // 로그인 처리 (POST /user/login)
                .loginProcessingUrl("/user/login")
                // 로그인 처리 후 성공 시 URL
                .defaultSuccessUrl("/")
                // 로그인 처리 후 실패 시 URL
                .failureUrl("/user/login?error")
                .permitAll()
                .and()
                // [로그아웃 기능]
                .logout()
                // 로그아웃 요청 처리 URL
                .logoutUrl("/user/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                // "접근 불가" 페이지 URL 설정
                .accessDeniedPage("/forbidden.html");
    }
}

// (springboot 2.7이상)

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
//public class WebSecurityConfig {
//
//    @Bean
//    public BCryptPasswordEncoder encodePassword() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
//        return (web) -> web.ignoring()
//                .antMatchers("/h2-console/**");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // CSRF protection 을 비활성화
//        http.csrf().disable();
//
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        // image 폴더를 login 없이 허용
//                        .antMatchers("/images/**").permitAll()
//                        // css 폴더를 login 없이 허용
//                        .antMatchers("/css/**").permitAll()
//                        // 회원 관리 처리 API 전부를 login 없이 허용
//                        .antMatchers("/user/**").permitAll()
//                        // 어떤 요청이든 '인증'
//                        .anyRequest().authenticated()
//                )
//                // [로그인 기능]
//                .formLogin()
//                // 로그인 View 제공 (GET /user/login)
//                .loginPage("/user/login")
//                // 로그인 처리 (POST /user/login)
//                .loginProcessingUrl("/user/login")
//                // 로그인 처리 후 성공 시 URL
//                .defaultSuccessUrl("/")
//                // 로그인 처리 후 실패 시 URL
//                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                // [로그아웃 기능]
//                .logout()
//                // 로그아웃 처리 URL
//                .logoutUrl("/user/logout")
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                // "접근 불가" 페이지 URL 설정
//                .accessDeniedPage("/forbidden.html");
//
//        return http.build();
//    }
//}




//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//
//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public BCryptPasswordEncoder encodePassword() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void configure(WebSecurity web) {
//        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
//        web
//                .ignoring()
//                .antMatchers("/h2-console/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
//        http.csrf()
//                .ignoringAntMatchers("/user/**")
//                .ignoringAntMatchers("/api/products/**");
//
//        http.authorizeRequests()
//                // image 폴더를 login 없이 허용
//                .antMatchers("/images/**").permitAll()
//                // css 폴더를 login 없이 허용
//                .antMatchers("/css/**").permitAll()
//                // 회원 관리 처리 API 전부를 login 없이 허용
//                .antMatchers("/user/**").permitAll()
//                // 그 외 어떤 요청이든 '인증'
//                .anyRequest().authenticated()
//                .and()
//                // [로그인 기능]
//                .formLogin()
//                // 로그인 View 제공 (GET /user/login)
//                .loginPage("/user/login")
//                // 로그인 처리 (POST /user/login)
//                .loginProcessingUrl("/user/login")
//                // 로그인 처리 후 성공 시 URL
//                .defaultSuccessUrl("/")
//                // 로그인 처리 후 실패 시 URL
//                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                // [로그아웃 기능]
//                .logout()
//                // 로그아웃 처리 URL
//                .logoutUrl("/user/logout")
//                .permitAll();
//    }
//}


// (springboot 2.7이상)

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//public class WebSecurityConfig {
//
//    @Bean
//    public BCryptPasswordEncoder encodePassword() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
//        return (web) -> web.ignoring()
//                .antMatchers("/h2-console/**");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
//        http.csrf()
//                .ignoringAntMatchers("/user/**");
//
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        // image 폴더를 login 없이 허용
//                        .antMatchers("/images/**").permitAll()
//                        // css 폴더를 login 없이 허용
//                        .antMatchers("/css/**").permitAll()
//                        // 회원 관리 처리 API 전부를 login 없이 허용
//                        .antMatchers("/user/**").permitAll()
//                        // 어떤 요청이든 '인증'
//                        .anyRequest().authenticated()
//                )
//                // [로그인 기능]
//                .formLogin()
//                // 로그인 View 제공 (GET /user/login)
//                .loginPage("/user/login")
//                // 로그인 처리 (POST /user/login)
//                .loginProcessingUrl("/user/login")
//                // 로그인 처리 후 성공 시 URL
//                .defaultSuccessUrl("/")
//                // 로그인 처리 후 실패 시 URL
//                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                // [로그아웃 기능]
//                .logout()
//                // 로그아웃 처리 URL
//                .logoutUrl("/user/logout")
//                .permitAll();
//
//        return http.build();
//    }
//}



//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    // spring에서 권고하고 있는 암호화 알고리즘. 세큐리티 5.7 이상에서도 같은 사용법이다.
//    @Bean
//    public BCryptPasswordEncoder encodePassword() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void configure(WebSecurity web) {
//        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
//        web
//                .ignoring()
//                .antMatchers("/h2-console/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
//        http.csrf()
//                .ignoringAntMatchers("/user/**");
//
//        http.authorizeRequests()
//                // image 폴더를 login 없이 허용
//                .antMatchers("/images/**").permitAll()
//                // css 폴더를 login 없이 허용
//                .antMatchers("/css/**").permitAll()
//                // 회원 관리 처리 API 전부를 login 없이 허용
//                .antMatchers("/user/**").permitAll()
//                // 그 외 어떤 요청이든 '인증'
//                .anyRequest().authenticated()
//                .and()
//                // 로그인 기능
//                .formLogin()
//                // 로그인 처리 (POST /user/login)
//                .loginProcessingUrl("/user/login")
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                // 로그아웃 기능
//                .logout()
//                .permitAll();
//    }
//}

// Security 5.7 이상  CSRF, FrameOptions 무시 예제 코드

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//public class WebSecurityConfig {
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//				// h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
//        return (web) -> web.ignoring()
//                .antMatchers("/h2-console/**");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//				// 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
//        http.csrf()
//                .ignoringAntMatchers("/user/**");
//
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        // image 폴더를 login 없이 허용
//                        .antMatchers("/images/**").permitAll()
//                        // css 폴더를 login 없이 허용
//                        .antMatchers("/css/**").permitAll()
//                        // 회원 관리 처리 API 전부를 login 없이 허용
//                        .antMatchers("/user/**").permitAll()
//                        // 어떤 요청이든 '인증'
//                        .anyRequest().authenticated()
//                )
//                // 로그인 기능 허용
//                .formLogin()
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                //로그아웃 기능 허용
//                .logout()
//                .permitAll();
//
//        return http.build();
//    }
//}



// Security 5.7 이하 정적 파일 허용 예제

//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                // image 폴더를 login 없이 허용
//                .antMatchers("/images/**").permitAll()
//                // css 폴더를 login 없이 허용
//                .antMatchers("/css/**").permitAll()
//                // 어떤 요청이든 '인증'
//                .anyRequest().authenticated()
//                .and()
//                // 로그인 기능 허용
//                .formLogin()
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                // 로그아웃 기능 허용
//                .logout()
//                .permitAll();
//    }
//}



// Boot Security 2.7이상, 5.7 이상 버전에서의 세큐리티 정적 파일 허용 코드

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//												// image 폴더를 login 없이 허용
//                        .antMatchers("/images/**").permitAll()
//												// css 폴더를 login 없이 허용
//                        .antMatchers("/css/**").permitAll()
//                        // 어떤 요청이든 '인증'
//                        .anyRequest().authenticated()
//                )
//                // 로그인 기능 허용
//                .formLogin()
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/user/login?error")
//                .permitAll()
//                .and()
//                //로그아웃 기능 허용
//                .logout()
//                .permitAll();
//
//        return http.build();
//    }
//}


// 기본 로그인 부 설명

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin() // 로그인 기능작동
//                .loginPage("/user/login") // 사용자 정의 페이지 경로
//                .defaultSuccessUrl("/") // 로그인 성공 시 이동 페이지 경로
//                .failureUrl("/user/login?error") // 로그인 실패 시 이동 페이지 경로
//                .permitAll();
//    }
//}

// < Spring Secutiry 5.7 미만 버전일 경우 (Spring Boot 2.7 미만일 경우)

//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                // 어떤 요청이든 '인증'
//                .anyRequest().authenticated()
//                .and()
//                // 로그인 기능 허용
//                .formLogin()
//                .defaultSuccessUrl("/") // 로그인 성공시 이동 페이지 경로
//                .permitAll()
//                .and()
//                // 로그아웃 기능 허용
//                .logout()
//                .permitAll();
//    }
//}

// < Spring Secutiry 5.7 이상 버전일 경우 (Spring Boot 2.7 이상일 경우)

// 이제는 상속받지 않고 filterChain을 Bean으로 등록해 사용한다

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
//public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        // 어떤 요청이든 '인증'
//                        .anyRequest().authenticated()
//                )
//                // 로그인 기능 허용
//                .formLogin()
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                //로그아웃 기능 허용
//                .logout()
//                .permitAll();
//
//        return http.build();
//    }
//}