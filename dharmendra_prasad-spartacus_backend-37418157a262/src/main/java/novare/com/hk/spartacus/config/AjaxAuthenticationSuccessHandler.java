package novare.com.hk.spartacus.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import novare.com.hk.spartacus.domain.Users;
import novare.com.hk.spartacus.model.AccessDetailDTO;
import novare.com.hk.spartacus.service.wireless.AccessService;
import novare.com.hk.spartacus.service.wireless.UserManagementService;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private AccessService accessService;

	@Autowired
	private UserManagementService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String email = authentication.getName();
		Users user = userService.findUserByEmail(email);
		AccessDetailDTO authDetail = accessService.accessDetails(user.getUserID());

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), authDetail);
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
