package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionDataBase;
import model.modelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		
		connection = ConnectionDataBase.getConnection();
		
	}
	
	public modelLogin gravarUsuario(modelLogin objeto) throws Exception {
		
		if(objeto.isNew()) {
		
		String sql = "INSERT INTO login(login, senha, nome, email, perfil, sexo, cep, logradouro, complemento, numero, bairro, localidade, uf)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, objeto.getLogin());
		preparedSql.setString(2, objeto.getSenha());
		preparedSql.setString(3, objeto.getNome());
		preparedSql.setString(4, objeto.getEmail());
		preparedSql.setString(5, objeto.getPerfil());
		preparedSql.setString(6, objeto.getSexo());
		preparedSql.setString(7, objeto.getCep());
		preparedSql.setString(8, objeto.getLogradouro());
		preparedSql.setString(9, objeto.getComplemento());
		preparedSql.setString(10, objeto.getNumero());
		preparedSql.setString(11, objeto.getBairro());
		preparedSql.setString(12, objeto.getLocalidade());
		preparedSql.setString(13, objeto.getUf());
		
		preparedSql.execute();
		
		connection.commit();
				
		if(objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
			sql = "UPDATE LOGIN SET fotouser = ?,extensaofotouser = ? WHERE LOGIN = ?";
			preparedSql = connection.prepareStatement(sql);
			
			preparedSql.setString(1, objeto.getFotoUser());
			preparedSql.setString(2, objeto.getExtensaoFotoUser());
			preparedSql.setString(3, objeto.getLogin());
			
			preparedSql.execute();
			
			connection.commit();
		}
		
		}else {
			String sql = "UPDATE login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, complemento=?, numero=?, bairro=?, localidade=?, uf=?  WHERE id = "+objeto.getId()+";";
			
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setString(5, objeto.getPerfil());
			preparedSql.setString(6, objeto.getSexo());
			preparedSql.setString(7, objeto.getCep());
			preparedSql.setString(8, objeto.getLogradouro());
			preparedSql.setString(9, objeto.getComplemento());
			preparedSql.setString(10, objeto.getNumero());
			preparedSql.setString(11, objeto.getBairro());
			preparedSql.setString(12, objeto.getLocalidade());
			preparedSql.setString(13, objeto.getUf());
			
			preparedSql.executeUpdate();
			
			connection.commit();
			
			if(objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "UPDATE LOGIN SET fotouser = ?,extensaofotouser = ? WHERE id = ?";
				preparedSql = connection.prepareStatement(sql);
				
				preparedSql.setString(1, objeto.getFotoUser());
				preparedSql.setString(2, objeto.getExtensaoFotoUser());
				preparedSql.setLong(3, objeto.getId());
				
				preparedSql.execute();
				
				connection.commit();
			}
		}
		
		return this.consultaUsuario(objeto.getLogin());
		
		
	}
	
	public List<modelLogin> consultaUsuarioList(String nome) throws Exception{
		
		List<modelLogin> retorno = new ArrayList<modelLogin>();
		
		String sql = "SELECT * FROM login WHERE useradmin is false and UPPER(nome) LIKE UPPER (?) ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			modelLogin modelLogin = new modelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	
	public List<modelLogin> consultaUsuarioListPaginada(String nome, Integer offset) throws Exception{
			
			List<modelLogin> retorno = new ArrayList<modelLogin>();
			
			String sql = "SELECT * FROM login WHERE useradmin is false and UPPER(nome) LIKE UPPER (?) ORDER BY nome OFFSET" + offset + "LIMIT 10";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + nome + "%");
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				modelLogin modelLogin = new modelLogin();
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				
				
				retorno.add(modelLogin);
			}
			
			return retorno;
		}
	

	
	public modelLogin consultaUsuario(String login) throws Exception {
		
		modelLogin modelLogin = new modelLogin();
		
		String sql ="SELECT * FROM login WHERE useradmin is false and UPPER(login) = UPPER ('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setComplemento(resultado.getString("complemento"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
						
		}
		
		return modelLogin;
	}
	
	public modelLogin consultaUsuarioId(String id) throws Exception {
			
			modelLogin modelLogin = new modelLogin();
			
			String sql ="SELECT * FROM login WHERE id=? AND useradmin is false";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(id));
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setSenha(resultado.getString("senha"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				modelLogin.setFotoUser(resultado.getString("fotouser"));
				modelLogin.setExtensaoFotoUser(resultado.getString("extensaofotouser"));
				modelLogin.setCep(resultado.getString("cep"));
				modelLogin.setLogradouro(resultado.getString("logradouro"));
				modelLogin.setComplemento(resultado.getString("complemento"));
				modelLogin.setNumero(resultado.getString("numero"));
				modelLogin.setBairro(resultado.getString("bairro"));
				modelLogin.setLocalidade(resultado.getString("localidade"));
				modelLogin.setUf(resultado.getString("uf"));
				
			}
			
			return modelLogin;
		}
			
	public modelLogin editarUsuario(String id) throws Exception {
		
		modelLogin modelLogin = new modelLogin();
		
		String sql ="SELECT * FROM login WHERE id = ? and useradmin is false ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setComplemento(resultado.getString("complemento"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
		}
		
		return modelLogin;
	}
	
	public int totalPaginas()throws Exception {
		
		String sql = "SELECT COUNT(1) AS total FROM login WHERE useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porPagina = 10.0;
		
		Double pagina = cadastros / porPagina;
		
		Double resto = pagina % 2;
		if(resto > 0) {
			pagina++;
		}
		return pagina.intValue();
	}
	
	
	public boolean validaLogin(String login) throws Exception{
		String sql = "SELECT COUNT(1) > 0 AS EXISTE FROM login WHERE UPPER(login) = UPPER('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		return resultado.getBoolean("existe");
	}
	
	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM login WHERE id = ? and useradmin is false";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setLong(1, Long.parseLong(idUser));
		
		prepareSql.executeUpdate();
		
		connection.commit();
		
		
	}
	

}
