package xjt.sb.config;

/*@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return Objects.equals(rawPassword.toString(),encodedPassword);
            }
        };
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("pass").roles("ADMIN")
                .and().withUser("x").password("x").roles("USER");
        //auth.inMemoryAuthentication().withUser("admin").password("{MD5}pass").roles("ADMIN");
        //auth.inMemoryAuthentication().withUser("admin").password("{noop}pass").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin/**")
                .hasRole("ADMIN").anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/login")
                .permitAll().and().csrf().disable();
    }
}*/
