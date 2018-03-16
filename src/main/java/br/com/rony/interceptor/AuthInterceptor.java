/**
 * 
 */
package br.com.rony.interceptor;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.annotations.RequiredLogin;
import br.com.rony.controller.LoginController;
import br.com.rony.dao.AuthDao;
import br.com.rony.model.Auth;
import br.com.rony.util.Message;

/**
 * @author Rony
 *
 */
@Intercepts
public class AuthInterceptor {

	@Inject
	private HttpServletRequest request;

	@Inject
	private ControllerMethod method;

	@Inject
	private AuthDao authDao;

	@Inject
	private Result result;

	public AuthInterceptor() {
	}

	/**
	 * Método que intercepta as requisições e verifica se existe um usuário logado
	 * 
	 * @throws InterceptionException
	 */
	@BeforeCall
	public void intercept() throws InterceptionException {

		try {
			// verificando se o controlador necessita realizar login para acesso
			if (method.getController().getType().isAnnotationPresent(RequiredLogin.class)) {

				if (request.getHeader("Authorization") != null) {
					String access_token = request.getHeader("Authorization").substring(7);

					// verificando se foi enviado o token de acesso
					if (access_token != null && !access_token.isEmpty()) {
						Auth auth = authDao.findAuthByAccessToken(access_token);

						// caso não exista autenticação ou esteja expirada será retornado acesso negado
						if (auth == null || auth.getExpires_at().before(new Date())) {
							result.redirectTo(LoginController.class).accessoNegado();
						}
					}
				} else {
					result.redirectTo(LoginController.class).accessoNegado();
				}
			}
		} catch (Exception e) {
			result.use(Results.json()).withoutRoot().from(new Message("", "error", "Problema Interno!")).serialize();
		}

	}

}
