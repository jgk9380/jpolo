package web;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class SessionCheckFilter implements Filter {
    private FilterConfig _filterConfig = null;
    private String errorPage;
    private String[] loginIgnorePages;
    Boolean loginIgnore;
  

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
        String sloginIgnorePages = filterConfig.getInitParameter("loginIgnorePages");
        //取得错误页面
        errorPage = filterConfig.getInitParameter("error");
        loginIgnorePages = sloginIgnorePages.split(",");
        loginIgnore = Boolean.parseBoolean(filterConfig.getInitParameter("loginIgnore"));
    }

    public void destroy() {
        _filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                                     ServletException {      
        if (true == loginIgnore) {
            chain.doFilter(request, response);
            return;
        }
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        boolean flag = false;
        //            System.out.println("req.getRequestURI()=" + req.getRequestURI());
        //            System.out.println("req.getContextPath()=" + req.getContextPath());
        /*begin 参数文件中的不必须登录文件,防止形成死循环*/
        if (req.getRequestURI() != null) {
            for (int i = 0; i < loginIgnorePages.length; i++) {
                String url = req.getContextPath() + loginIgnorePages[i];
                if (req.getRequestURI().startsWith(url)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                chain.doFilter(request, response);
                return;
            }
        }

        ILoginUser currentUser = (ILoginUser) session.getAttribute("loginUser");
       // Object currentUser1 = (Object) JSFUtils.resolveExpression("#{sl.loginUser}");
       // System.out.println("currentUser=" + currentUser);
       // System.out.println("currentUser1=" + currentUser1);
        RequestDispatcher rd = req.getRequestDispatcher("/faces/login.jsf");
        if (null == currentUser||!currentUser.isLogined()) {    
            rd.forward(request, response);
        } else {    
            chain.doFilter(request, response);
        }

    }
}

