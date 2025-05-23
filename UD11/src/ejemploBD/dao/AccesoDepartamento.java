package ejemploBD.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ejemploBD.config.ConfigSQLite;
import ejemploBD.config.ConfigMySql;
import ejemploBD.excepciones.BDException;
import ejemploBD.modelo.Departamento;
import entrada.Teclado;

public class AccesoDepartamento {

	/**
	 * Consulta los departamentos de la base de datos personal.db
	 *  con la misma ubicaci�n y ordenados por nombre de forma ascendente.
	 * @param ubicacion
	 * @return
	 * @throws BDException
	 */
	public static List<Departamento> consultarDepartamentos(String ubicacion) throws BDException{

		List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexi�n a la bd
			conexion = ConfigSQLite.abrirConexion();

			String query = "SELECT * FROM departamento WHERE ubicacion = ? ORDER BY nombre";

			ps = conexion.prepareStatement(query);
			// Al primer interrogante
			ps.setString(1, ubicacion);

			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				Departamento departamento = new Departamento(resultados.getInt("codigo"),
						resultados.getString("nombre"), resultados.getString("ubicacion"));
				listaDepartamentos.add(departamento);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}		
		finally {
			if (conexion!=null) {
				ConfigMySql.cerrarConexion(conexion);
			}
		}
		return listaDepartamentos;
	}

	/**
	 * Consulta los departamentos de la base de datos
	 * con la misma ubicaci�n y ordenados por nombre de forma ascendente.
	 * @param ubicacion
	 * @return
	 * @throws BDException
	 */
	public static List<Departamento> consultarDepartamentos2(String ubicacion) throws BDException  {

		List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
		Connection conexion = null;

		try {
			// Conexi�n a la bd			
			conexion = ConfigSQLite.abrirConexion();
			String query = "SELECT * FROM departamento WHERE ubicacion = '" + ubicacion + "' order by nombre";
			
			Statement sentencia = conexion.createStatement();			
			ResultSet resultados = sentencia.executeQuery(query);
			
			while (resultados.next()) {
				Departamento departamento = new Departamento(resultados.getInt("codigo"),
						resultados.getString("nombre"), resultados.getString("ubicacion"));
				listaDepartamentos.add(departamento);
			}			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}		
		finally {
			if (conexion!=null) {
				ConfigSQLite.cerrarConexion(conexion);
			}
		}
		return listaDepartamentos;
	}
	//Consultar departamento por nombre
	public static boolean existeDepartamentoNombre(String nombreDepartamento) throws BDException{

		Departamento departamento =null;
		PreparedStatement ps = null;
		Connection conexion = null;
		ResultSet resultados;
		boolean existe = false;

		try {
			// Conexi�n a la bd			
			conexion = ConfigSQLite.abrirConexion();
			String query = "SELECT * FROM departamento WHERE nombre = ? " ;
			
			ps = conexion.prepareStatement(query);			
			ps.setString(1, nombreDepartamento);
			
			resultados = ps.executeQuery();
			existe = resultados.next();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}		
		finally {
			if (conexion!=null) {
				ConfigSQLite.cerrarConexion(conexion);
			}
		}
		return existe;
	}
	
	// Consultar el departamento por c�digo
	public static Departamento consultarDepartamento(int codigoDepartamento) throws BDException{

		Departamento departamento =null;
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexi�n a la bd			
			conexion = ConfigSQLite.abrirConexion();
			String query = "SELECT * FROM departamento WHERE codigo = ? " ;
			
			ps = conexion.prepareStatement(query);			
			ps.setInt(1, codigoDepartamento);
			
			ResultSet resultados = ps.executeQuery();
			
			if (resultados.next()) {
				departamento = new Departamento (resultados.getInt("codigo"),
						resultados.getString("nombre"),resultados.getString("ubicacion"));
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}		
		finally {
			if (conexion!=null) {
				ConfigSQLite.cerrarConexion(conexion);
			}
		}
		return departamento;
	}
	
	
	// Modificar la ubicaci�n de un departamento
	public static boolean modificarDepartamento (String ubicacion,int codDepartamento) throws BDException {
		PreparedStatement ps = null;
		Connection conexion = null;
		int resultados = 0;
		try {
			// Conexi�n a la bd			
			conexion = ConfigSQLite.abrirConexion();
			String query = "UPDATE departamento SET ubicacion = ? WHERE codigo = ?" ;
			
			ps = (PreparedStatement) conexion.createStatement();			
			ps.setString(1, ubicacion);
			ps.setInt(2, codDepartamento);
			
			resultados = ps.executeUpdate(query);
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}		
		finally {
			if (conexion!=null) {
				ConfigSQLite.cerrarConexion(conexion);
			}
		}
		return resultados > 0;
		
	}	
	// Borrar un departamento por c�digo
	public static boolean eliminarDepartamento (int codigo) throws BDException {
		PreparedStatement ps = null;
		Connection conexion = null;
		int resultados = 0;
		try {
			// Conexi�n a la bd			
			conexion = ConfigSQLite.abrirConexion();
			String query = "DELETE departamento WHERE codigo = ?" ;
			
			ps = (PreparedStatement) conexion.createStatement();			
			ps.setInt(1, codigo);
			
			resultados = ps.executeUpdate(query);
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}		
		finally {
			if (conexion!=null) {
				ConfigSQLite.cerrarConexion(conexion);
			}
		}
		return resultados == 1;
	}
	public static boolean agregarDepartamento(String nombre,String ubicacion) throws BDException {
		List<Departamento> listaDepartamentos = new ArrayList<>();
		PreparedStatement ps = null;
		Connection conexion = null;
		int resultados = 0;
		try {
			// Conexi�n a la bd			
			conexion = ConfigSQLite.abrirConexion();
			String query = "INSERT INTO departamento (nombre,ubicacion) VALUES = (?,?)" ;
			
			ps = (PreparedStatement) conexion.createStatement();			
			ps.setString(1, nombre);
			ps.setString(2, ubicacion);
			
			resultados = ps.executeUpdate(query);
			
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}		
		finally {
			if (conexion!=null) {
				ConfigSQLite.cerrarConexion(conexion);
			}
		}
		return resultados == 1;
		
	}
	// Consultar todos los departamentos ordenados por nombre
	public static List<Departamento> listarDepartamentos () throws BDException{
		List<Departamento> listaDepartamentos = new ArrayList<>();
		Statement ps = null;
		Connection conexion = null;
		try {
			// Conexi�n a la bd			
			conexion = ConfigSQLite.abrirConexion();
			String query = "SELECT * FROM departamento ORDER BY nombre ASC" ;
			
			ps =  conexion.createStatement();
			ResultSet resultados = ps.executeQuery(query);
			while (resultados.next()) {
				Departamento departamento = new Departamento(resultados.getInt("codigo"),
						resultados.getString("nombre"), resultados.getString("ubicacion"));
				listaDepartamentos.add(departamento);
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}		
		finally {
			if (conexion!=null) {
				ConfigSQLite.cerrarConexion(conexion);
			}
		}
		return listaDepartamentos;
	}
	

	
}
