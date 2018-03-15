/**
 * 
 */
package br.com.rony.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;

/**
 * @author Rony
 *
 */
@Intercepts
public class AuthInterceptor {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public AuthInterceptor() {
	}

	@Inject
	public AuthInterceptor(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	@BeforeCall
    public void intercept() throws InterceptionException {
		String access_token = request.getHeader("Authorization").substring(7);
        System.out.println(access_token);
    }

}
