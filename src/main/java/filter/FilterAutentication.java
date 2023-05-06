package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.ConnectionDataBase;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/main/*"})
public class FilterAutentication extends HttpFilter implements Filter {
       
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private static Connection connection;

	public FilterAutentication() {
       
    }

	/*Encerra os processos quando o servidor é parado - Mata a conexão com o banco*/
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/*Intercepta as requisições e fornece as respostas no sistema - Tudo passa por aqui*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
			try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			String urlParaAutenticar = req.getServletPath();
					
			if (usuarioLogado == null && !urlParaAutenticar.contains("/main/ServletLogin2")) {
				
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url =" + urlParaAutenticar);
				request.setAttribute("msg", "Realize o login");
				redireciona.forward(request, response);
				return;
			
			}else {
				chain.doFilter(request, response);	
			}
			
			connection.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*Inicia o processo de conexão com o banco*/
	public void init(FilterConfig fConfig) throws ServletException {
		connection = ConnectionDataBase.getConnection();
		
	}

}
