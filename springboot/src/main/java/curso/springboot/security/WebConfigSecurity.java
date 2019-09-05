package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplUserDetailService implUserDetailService;
	
	@Override //Configura as solicitaçoes de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable() //Desativa as configuraçoes padrão de memoria
			.authorizeRequests() //Permitir restringir acessos
			.antMatchers(HttpMethod.GET, "/").permitAll() //Qualquer usuario pode acessar a pagina inicial, sem estar logado.
			.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN") //Qualquer usuario pode acessar a pagina inicial, sem estar logado.
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.permitAll() // Permite qualqer usuario
			.and()
		.logout() //Mapeia URL de logout e invalida o usuario autenticado
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override //Cria autenticacao do usuario com DB ou Mémoria!
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implUserDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
		
		/*auth.inMemoryAuthentication()
		.passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("mauricio")
		.password("123")
		.roles("ADMIN");*/
	}
	
	@Override //Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}
}
