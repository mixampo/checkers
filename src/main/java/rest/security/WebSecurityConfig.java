package rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                //csrf might need to be enabled for websocket security to work
                .csrf()
                .and()
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
//                .antMatchers("/signup").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.DELETE, "/account").authenticated()
                .antMatchers(HttpMethod.POST, "/account").permitAll()
                .antMatchers(HttpMethod.PUT, "/account").authenticated()
                .antMatchers(HttpMethod.GET, "/account").authenticated()
                .antMatchers(HttpMethod.GET, "/account/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/leaderboard").authenticated()
                .antMatchers(HttpMethod.POST, "/leaderboard").authenticated()
                .antMatchers(HttpMethod.PUT, "/leaderboard").authenticated()
                .antMatchers(HttpMethod.GET, "/csrf").permitAll()
                .antMatchers("/start").permitAll()
                .antMatchers("/topic").permitAll()
                .antMatchers("/app").permitAll()
                //.anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/authenticate")
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        //@formatter:on
    }
}

