package com.CAT.BuffetAPI.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CAT.BuffetAPI.Entities.Product;
import com.CAT.BuffetAPI.Entities.Product_status;
import com.CAT.BuffetAPI.Entities.Service;
import com.CAT.BuffetAPI.Entities.Service_status;
import com.CAT.BuffetAPI.Entities.Unit;
import com.CAT.BuffetAPI.Entities.User_status;
import com.CAT.BuffetAPI.Services.AuthService;
import com.CAT.BuffetAPI.Services.PrestacionesService;

@RestController
@RequestMapping("/supply-adm")
public class PrestacionController {
	@Autowired
	private PrestacionesService pre;
	
	@Autowired
	private AuthService auth;
	

	@RequestMapping("/product")
	private List<Product> getAllMecha(HttpServletResponse res, @RequestHeader("token") String token,
													@RequestParam (required = false) String brand,
													@RequestParam (required = false) String name,
													@RequestParam (required = false) String product_status,
													@RequestParam (required = false) String deleted)
	{
	
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {

				// Get the all the Users
				HashMap<String,Object> data = new HashMap<>();
				
				if(brand!= null)
				{
					data.put("brand", brand);
				}
				if(name!=null)
				{
					data.put("name", name);
				}
				if(product_status!=null)
				{
					data.put("product_status", product_status);
				}
				if(deleted != null)
				{
					data.put("deleted", deleted);
				}
				else
				{
					data.put("deleted", false);
				}
				
				System.out.println("preAsignacion");
				List<Product> productos = pre.getDataProduct(data);
				System.out.println("postAsignacion");
				if(productos == null){
					// 404 Not Found
					res.setStatus(404);
					return null;
				}

				// 200 OK
				res.setStatus(200);
				return productos;
			
		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	
	}


	@RequestMapping(value="/product/{Id}", method = {RequestMethod.GET})
	private Optional<Product> getSpecificProduct(HttpServletResponse res, @PathVariable("Id") String id, @RequestHeader("token") String token)
	{
		if(id.isEmpty() || token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		// Check for authorization
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<Product> product = pre.getOneProduct(id);

			// If there is no matching User
			if(!product.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}
			
			// 200 OK
			res.setStatus(200);
			return product;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value= "/product/{Id}", method = {RequestMethod.POST})
	private String UpdateProduct(HttpServletResponse res,@PathVariable String Id, @RequestBody Product product,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			String productId = product.getProduct_id();
			Optional<Product> optProduct = pre.getOneProduct(productId);
	
			// If there is no matching User
			if(!optProduct.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}
	
			Product oldProduct = optProduct.get();
			
			product.setCreate_at(oldProduct.getCreate_at());
			product.setUpdate_at(new Date());
			pre.UpdateProducto(product);
	
			// 200 OK
			res.setStatus(200);
			return "producto actualizado exitosamente";
		
		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value = "/product/{Id}/change-status", method = {RequestMethod.POST})
	private String ChangeStatus(HttpServletResponse res, @PathVariable String Id ,@RequestParam("product_status")String product_status,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<Product> producto = pre.getOneProduct(Id);

			// If there is no matching User
			if(!producto.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			Product updatedProduct = producto.get();

		
			updatedProduct.setProduct_status(product_status);
			pre.UpdateProducto(updatedProduct);

			// 200 OK
			res.setStatus(200);
			return "Status actualizado exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value= "/product/{Id}", method = {RequestMethod.DELETE})
	private String DeleteProduct(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<Product> producto = pre.getOneProduct(Id);

			// If there is no matching User
			if(!producto.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			Product delProduct = producto.get();
			
			delProduct.setDeleted(true);
			pre.UpdateProducto(delProduct);

			// 200 OK
			res.setStatus(200);
			return "producto Eliminado Exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

	@RequestMapping(value= "/product/{Id}/restore", method = {RequestMethod.PUT})
	private String RestoreProduct(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<Product> user = pre.getOneProduct(Id);

			// If there is no matching User
			if(!user.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			Product resProduct = user.get();

			if(!resProduct.isDeleted()){
				// 409 Conflict
				res.setStatus(409);
				return "El Producto no está Eliminado";
			}
			
			resProduct.setDeleted(false);
			pre.UpdateProducto(resProduct);

			// 200 OK
			res.setStatus(200);
			return "Producto Restaurado Exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

	//===============================================================================================================================================================================//
	//===============================================================================================================================================================================//
	//===============================================================================================================================================================================//
	//===============================================================================================================================================================================//
	//===============================================================================================================================================================================//
	@RequestMapping("/service")
	private List<Service> getAllServ(HttpServletResponse res, @RequestHeader("token") String token,
													@RequestParam (required = false) String name,
													@RequestParam (required = false) String serv_status,
													@RequestParam (required = false) String deleted)
	{
	
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {

				// Get the all the Users
				HashMap<String,Object> data = new HashMap<>();
				
				
				if(name!=null)
				{
					data.put("name", name);
				}
				if(serv_status!=null)
				{
					data.put("serv_status", serv_status);
				}
				if(deleted != null)
				{
					data.put("deleted", deleted);
				}
				else
				{
					data.put("deleted", false);
				}
				
				System.out.println("preAsignacion");
				List<Service> productos = pre.getDataService(data);
				System.out.println("postAsignacion");
				if(productos == null){
					// 404 Not Found
					res.setStatus(404);
					return null;
				}

				// 200 OK
				res.setStatus(200);
				return productos;
			
		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	
	}


	@RequestMapping(value="/service/{Id}", method = {RequestMethod.GET})
	private Optional<Service> getSpecificServicio(HttpServletResponse res, @PathVariable("Id") String id, @RequestHeader("token") String token)
	{
		if(id.isEmpty() || token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		// Check for authorization
		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<Service> servicio = pre.getOneService(id);

			// If there is no matching User
			if(!servicio.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}
			
			// 200 OK
			res.setStatus(200);
			return servicio;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value= "/service/{Id}", method = {RequestMethod.POST})
	private String UpdateService(HttpServletResponse res,@PathVariable String Id, @RequestBody Service service,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			String service_id = service.getServ_id();
			Optional<Service> optService = pre.getOneService(service_id);
	
			// If there is no matching User
			if(!optService.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}
	
			Service oldService = optService.get();
			
			service.setCreate_at(oldService.getCreate_at());
			service.setUpdate_at(new Date());
			pre.UpdateService(service);
	
			// 200 OK
			res.setStatus(200);
			return "servicio actualizado exitosamente";
		
		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value = "/service/{Id}/change-status", method = {RequestMethod.POST})
	private String ChangeStatusService(HttpServletResponse res, @PathVariable String Id ,@RequestParam("serv_status")String serv_status,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<Service> servicio = pre.getOneService(Id);

			// If there is no matching User
			if(!servicio.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			Service updatedServicio = servicio.get();

		
			updatedServicio.setServ_status(serv_status);
			pre.UpdateService(updatedServicio);

			// 200 OK
			res.setStatus(200);
			return "Status actualizado exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}


	@RequestMapping(value= "/service/{Id}", method = {RequestMethod.DELETE})
	private String DeleteService(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<Service> servicio = pre.getOneService(Id);

			// If there is no matching User
			if(!servicio.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			Service delService = servicio.get();
			
			delService.setDeleted(true);
			pre.UpdateService(delService);

			// 200 OK
			res.setStatus(200);
			return "servicio Eliminado Exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

	@RequestMapping(value= "/service/{Id}/restore", method = {RequestMethod.PUT})
	private String RestoreService(HttpServletResponse res,@PathVariable String Id,@RequestHeader("token") String token)
	{
		if(token.isEmpty()){
			// 400 Bad Request
			res.setStatus(400);
			return null;
		}

		List<String> typesAllowed = new ArrayList<String>();
		typesAllowed.add("ADM");
		if(!auth.Authorize(token, typesAllowed)){
			// 401 Unauthorized
			res.setStatus(401);
			return null;
		}

		try {
			// Get the User
			Optional<Service> user = pre.getOneService(Id);

			// If there is no matching User
			if(!user.isPresent()){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			Service resService = user.get();

			if(!resService.isDeleted()){
				// 409 Conflict
				res.setStatus(409);
				return "El Servicio no está Eliminado";
			}
			
			resService.setDeleted(false);
			pre.UpdateService(resService);

			// 200 OK
			res.setStatus(200);
			return "Servicio Restaurado Exitosamente";

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}

		//===============================================================================================================================================================================//
		//===============================================================================================================================================================================//
		//===============================================================================================================================================================================//
		//===============================================================================================================================================================================//
		//===============================================================================================================================================================================//
	
	@RequestMapping("/serv_status")
	private List<Service_status> getServStatus(HttpServletResponse res){

		try {
			// Get the all the Users
			List<Service_status> typeList = pre.getAllservStatus();

			if(typeList == null){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return typeList;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}
	@RequestMapping("/product-status")
	private List<Product_status> getAllProductStatus(HttpServletResponse res){

		try {
			// Get the all the Users
			List<Product_status> typeList = pre.getAllProductStatus();

			if(typeList == null){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return typeList;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}
	@RequestMapping("/units")
	private List<Unit> getAllStatus(HttpServletResponse res){

		try {
			// Get the all the Users
			List<Unit> typeList = pre.getAllUnits();

			if(typeList == null){
				// 404 Not Found
				res.setStatus(404);
				return null;
			}

			// 200 OK
			res.setStatus(200);
			return typeList;

		} catch (Exception e) {
			// If There was an error connecting to the server
			// 500 Internal Server Error
			res.setStatus(500);
			return null;
		}
	}
}
