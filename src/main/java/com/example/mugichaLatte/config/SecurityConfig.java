package com.example.mugichaLatte.config;

import com.example.mugichaLatte.filter.LoginFilter;
import com.example.mugichaLatte.filter.AdminFilter;
import com.example.mugichaLatte.filter.ApproverFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private LoginFilter loginFilter;

    @Autowired
    private AdminFilter adminFilter;

    @Autowired
    private ApproverFilter approverFilter;

    @Bean
    //フィルターの並びとルールをまとめたもの
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //今回はSpringSecurityのものではなく、独自のログイン画面を使っているのでCSRFを無効化しないとはじかれる
        //Springをimportした時点でこの設定が必要になる。
        http.csrf(csrf -> csrf.disable());

        //★ログイン不要な画面
        //リクエストのアクセス権限設定をまとめる場所
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**").permitAll()
                .anyRequest().authenticated()
        );


        //UsernamePasswordAuthenticationFilter.classに関しては今回上の文で無効化してるので発動しないが、基準として使っている。
        //.classをつけるとクラスそのものを表すオブジェクトになる
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(adminFilter, LoginFilter.class);
        http.addFilterAfter(approverFilter, LoginFilter.class);

        return http.build();
    }

}
