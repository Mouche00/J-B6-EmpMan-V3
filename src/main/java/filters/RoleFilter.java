package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebFilter("/*")
public class RoleFilter implements Filter {

    private static final Map<String, List<String>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/admin", List.of("admin"));
        protectedUrls.put("/recruiter", List.of("recruiter"));
        protectedUrls.put("/employee", List.of("admin", "recruiter", "employee"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String uri = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        for (Map.Entry<String, List<String>> entry : protectedUrls.entrySet()) {
            String urlPrefix = entry.getKey();
            List<String> allowedRoles = entry.getValue();

            if (uri.startsWith(urlPrefix)) {
                if (session == null || session.getAttribute("loggedUser") == null) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
                    return;
                }

                String userRole = (String) session.getAttribute("role");
                if (!allowedRoles.contains(userRole)) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/access-denied.jsp");
                    return;
                }
                break;
            }
        }

        chain.doFilter(request, response);
    }
}