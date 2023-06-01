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
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPaginas());
			request.getRequestDispatcher("main/pesquisa.jsp").forward(request, response);
						
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("editarUser")) {
			String id = request.getParameter("id");
			
			modelLogin editar = daoUsuarioRepository.editarUsuario(id);
			
			request.setAttribute("msg", "Usuário pronto para edição");
			request.setAttribute("modelLogin", editar);
			request.getRequestDispatcher("main/usuario.jsp").forward(request, response);
			
			
					
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
			String idUser = request.getParameter("id");
			
			modelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(idUser);
			if(modelLogin.getFotoUser() != null && !modelLogin.getFotoUser().isEmpty()) {
				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaoFotoUser());
				response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotoUser().split("\\,")[1]));
			}

			
		
		
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
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String complemento = request.getParameter("complemento");
			String numero = request.getParameter("numero");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			
			
			model.modelLogin modelLogin = new modelLogin();
			modelLogin.setId(id !=null && !id.isEmpty() ? Long.parseLong(id):null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setComplemento(complemento);
			modelLogin.setNumero(numero);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			
			if(ServletFileUpload.isMultipartContent(request)) {
				Part part = request.getPart("fileFoto"); // Pega a foto da tela
				
				if(part.getSize() > 0) {
					byte[] foto = IOUtils.toByteArray(part.getInputStream()); //Converte IMG para byte
					String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
					
					modelLogin.setFotoUser(imagemBase64);
					modelLogin.setExtensaoFotoUser(part.getContentType().split("\\/")[1]);
				}
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
