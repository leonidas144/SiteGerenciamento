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
		
		String sql = "INSERT INTO login(login, senha, nome, email)VALUES (?, ?, ?, ?)";
		
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		preparedSql.setString(1, objeto.getLogin());
		preparedSql.setString(2, objeto.getSenha());
		preparedSql.setString(3, objeto.getNome());
		preparedSql.setString(4, objeto.getEmail());
		
		preparedSql.execute();
		
		connection.commit();
				
		}else {
			String sql = "UPDATE login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			
			preparedSql.executeUpdate();
			
			connection.commit();
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
		}
		
		return modelLogin;
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
