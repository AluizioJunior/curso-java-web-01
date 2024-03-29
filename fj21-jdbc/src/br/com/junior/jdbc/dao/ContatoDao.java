package br.com.junior.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.junior.jdbc.ConnectionFactory;
import br.com.junior.jdbc.modelo.Contato;

//Gerencia a conex�o e insere Contatos no banco de dados,
public class ContatoDao {

	private Connection connection;
	
	public ContatoDao() throws ClassNotFoundException{
		this.connection = new ConnectionFactory().getConnection();
	}
	
	//Adiciona um contato
	public void adiciona(Contato contato){
		String sql = "insert into contatos"+"(nome,email,endereco,dataNascimento)"+"values(?,?,?,?)";
		
		try {
			
			//pre pared statement para inser��o
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			//seta os valores
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
			
			//executa
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	//Lista os contatos
	public List<Contato> getLista() throws DAOException{
		try {
			List<Contato>contatos = new ArrayList<Contato>();
			PreparedStatement stmt = this.connection.prepareStatement("select * from contatos");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Contato contato = new Contato();
				contato.setId(rs.getLong("id"));
				contato.setNome(rs.getString("nome"));
				contato.setEmail(rs.getString("email"));
				contato.setEndereco(rs.getString("endereco"));
				
				//montando a data atrav�s do Calendar
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("dataNascimento"));
				contato.setDataNascimento(data);
				//adicionando objeto � lista
				contatos.add(contato);				
			}
			rs.close();
			stmt.close();
			return contatos;
			
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	//Altera um registro
	public void altera(Contato contato){
		String sql = "update contatos set nome=?, email=?, endereco=?, dataNascimento=? where id=?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
			stmt.setLong(5, contato.getId());
			stmt.execute();
			stmt.close();
			System.out.println("Dados alterados com sucesso!!!");
			
		} catch (SQLException e) {
			System.out.println("N�o foi poss�vel salvar as altera��es!!!");
			throw new RuntimeException(e);
			
		}
	}
	
	//Remove um registro
	public void remove(Contato contato){
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from contatos where id=?");
			stmt.setLong(1, contato.getId());
			stmt.execute();
			stmt.close();
			System.out.println("Removido com sucesso!!!");
		} catch (SQLException e) {
			System.out.println("Erro ao remover registro!!!");
			throw new RuntimeException(e);
		}
	}
	
}
