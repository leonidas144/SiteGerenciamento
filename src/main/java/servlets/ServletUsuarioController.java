package servlets;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.modelLogin;


@MultipartConfig
public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
    public ServletUsuarioController() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
			
			String idUser = request.getParameter("id");
			
			daoUsuarioRepository.deletarUser(idUser);
			
			request.setAttribute("msg", "Excluído com sucesso!");
			
			request.getRequestDispatcher("main/usuario.jsp").forward(request, response);
		
		}
		else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
			
			String nomeBusca = request.getParameter("nomeBusca");
									
			List<modelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca);
						
			 String tabelaHTML = "";

			 for (modelLogin valor : dadosJsonUser) {
			      tabelaHTML += "<tr><td>" + valor.getId() + "</td>"
			      + "<td>" + valor.getNome() + "</td>"
			      + "<td>" + valor.getEmail() + "</td>"
			      + "<td><button type= 'button'class='btn btn-info' onclick='editarUser("+valor.getId()+");'>Editar</button></td></tr>";
			 }
			 			
			request.setAttribute("tabelaHTML", tabelaHTML);
			
			request.getRequestDispatcher("main/pesquisa.jsp").forward(request, response);
						
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("editarUser")) {
			String id = request.getParameter("id");
			
			modelLogin editar = daoUsuarioRepository.editarUsuario(id);
			
			request.setAttribute("msg", "Usuário pronto para edição");
			request.setAttribute("modelLogin", editar);
			request.getRequestDispatcher("main/usuario.jsp").forward(request, response);
			
			
					
		}else {
			request.getRequestDispatcher("main/usuario.jsp").forward(request, response);
		}
	
		}catch (Exception e){
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			String msg = "Já existe esse usuário, informe outro login!";
			
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			
			
			model.modelLogin modelLogin = new modelLogin();
			modelLogin.setId(id !=null && !id.isEmpty() ? Long.parseLong(id):null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			
			if(ServletFileUpload.isMultipartContent(request)) {
				Part part = request.getPart("fileFoto"); // Pega a foto da tela
				byte[] foto = IOUtils.toByteArray(part.getInputStream()); //Converte IMG para byte
				String imagemBase64 = new Base64().encodeBase64String(foto);
				System.out.println(imagemBase64);
			}
			
			if(daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe esse usuário, informe outro login!";
			}else {
				if(modelLogin.isNew()) {
					msg = "Gravado com sucesso!";
				}else {
					msg = "Atualizado com sucesso!";
				}
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin);
			}
						
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			RequestDispatcher redireciona = request.getRequestDispatcher("main/usuario.jsp");
			redireciona.forward(request, response);
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("error.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
