package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class Encode
 */
@WebFilter("/Encode")
public class Encode implements Filter {

    /**
     * Default constructor. 
     */
    public Encode() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("���ٹ�������ͳһ�����ʽ");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // ����û������URI
        String Path = httpRequest.getServletPath();
		System.out.println("ͳһ������������·����"+Path);
		
		chain.doFilter(request, response);
		System.out.println("ͳһ������ͨ����·����"+Path);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("��ʼ����������ͳһ�����ʽ");
	}

}
