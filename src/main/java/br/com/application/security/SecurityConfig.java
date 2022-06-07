package br.com.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public AppUserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/Login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());
		
		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
		jsfDeniedEntry.setContextRelative(true);
		
		http.csrf().disable()
			.headers().frameOptions().sameOrigin()
			.and()
			
		.authorizeRequests()
			.antMatchers("/Login.xhtml", "/Erro.xhtml", "/javax.faces.resource/**").permitAll()
			.antMatchers("/Home.xhtml", "/AcessoNegado.xhtml", "/dialogos/**").authenticated()
			.antMatchers("/usuarios/CadastroUsuario.xhtml").hasRole("CADASTRAR_USUARIO")
			.antMatchers("/usuarios/PesquisaUsuarios.xhtml").hasRole("PESQUISAR_USUARIOS")
			.antMatchers("/clientes/CadastroCliente.xhtml").hasRole("CADASTRAR_CLIENTES")
			.antMatchers("/clientes/PesquisaClientes.xhtml").hasRole("PESQUISAR_CLIENTES")
			.antMatchers("/relatorios/Relatorios.xhtml").hasRole("GERENCIAR_RELATORIOS")
			.and()
		
		.formLogin()
			.loginPage("/Login.xhtml")
			.failureUrl("/Login.xhtml?invalid=true")
			.and()
		
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
		
		.exceptionHandling()
			.accessDeniedPage("/AcessoNegado.xhtml")
			.authenticationEntryPoint(jsfLoginEntry)
			.accessDeniedHandler(jsfDeniedEntry);
	}
	
}