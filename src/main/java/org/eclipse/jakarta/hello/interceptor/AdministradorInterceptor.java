package org.eclipse.jakarta.hello.interceptor;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(servletNames = {"register","borraridioma","borrarusuari","crearentrada","editarentrada"})
public class AdministradorInterceptor implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest; //Casteam servletRequest ja que es de GenericServlet (o sigui, podría ser per multiplataforma, pero noltros especificam que es per http amb aquest cast)
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        HttpSession httpSession = request.getSession();
        String username = (String) httpSession.getAttribute("username");
        String rol = (String) httpSession.getAttribute("rol");

        if (rol != null && username != null && rol.equals("ADMINISTRADOR")) {
            //Patró chain of responsibility
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            System.out.println("Interceptor ADMINISTRADOR Error: No está loggejat o no té el rol requerit");
            response.sendRedirect("login");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
