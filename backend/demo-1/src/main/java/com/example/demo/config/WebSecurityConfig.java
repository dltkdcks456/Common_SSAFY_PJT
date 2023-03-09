// package com.example.demo.config;

// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http
//                 .cors()
//                 .and()
//                 .csrf()
//                 .disable()
//                 .authorizeRequests()
//                 .antMatchers("/ws-stomp/info"
//                         ).permitAll()
//                 .antMatchers("/ws-stomp/**"
//                 )
//                 .permitAll();
//     }
// }
