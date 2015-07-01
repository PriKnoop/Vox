package services;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import daos.UsuarioDao;
import entitys.Usuario;
import managers.SimpleEntityManager;
 
/**
 * @author Priscila
 *
 * Jun 30, 2015
 */
public class UsuarioService {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("tcc");
    private SimpleEntityManager simpleEntityManager = new SimpleEntityManager(factory);
    private UsuarioDao dao = new UsuarioDao(simpleEntityManager.getEntityManager());

    /*public UsuarioService(SimpleEntityManager simpleEntityManager){
        this.simpleEntityManager = simpleEntityManager;
        dao = new UsuarioDao(simpleEntityManager.getEntityManager());
    }*/
     
    public Usuario adicionar(Usuario usuario){
        try{
    		simpleEntityManager = new SimpleEntityManager(factory);
            dao = new UsuarioDao(simpleEntityManager.getEntityManager());
            simpleEntityManager.beginTransaction();
            dao.adicionar(usuario);
            simpleEntityManager.commitAndClose();
        }
        catch(Exception e){
            e.printStackTrace();
            simpleEntityManager.rollBack();
            simpleEntityManager.close();
        }
        return usuario;
    }
    
    public void atualizar(Usuario usuario){
        try{
        	simpleEntityManager = new SimpleEntityManager(factory);
            dao = new UsuarioDao(simpleEntityManager.getEntityManager());
            simpleEntityManager.beginTransaction();
            dao.atualizar(usuario);
            simpleEntityManager.commitAndClose();
        }catch(Exception e){
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }
    
    public void deletar(Usuario usuario){
        try{
        	//simpleEntityManager = new SimpleEntityManager(factory);
           // dao = new UsuarioDao(simpleEntityManager.getEntityManager());
            simpleEntityManager.beginTransaction();
            dao.deletar(usuario);
            simpleEntityManager.commitAndClose();
        }catch(Exception e){
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
    }
    
    public Usuario encontrarPorId(long usuarioID){
        Usuario usuarioEncontrado = null;
    	try{
    		simpleEntityManager = new SimpleEntityManager(factory);
            dao = new UsuarioDao(simpleEntityManager.getEntityManager());
            simpleEntityManager.beginTransaction();
            usuarioEncontrado = (Usuario) dao.findById(usuarioID);
            simpleEntityManager.commit();
        }catch(Exception e){
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }
        return usuarioEncontrado;
    }
   
    public List<Usuario> encontrarTodos(){
        return dao.findAll();
    }
    
    
    
    
    
    
    
    
    
    
}