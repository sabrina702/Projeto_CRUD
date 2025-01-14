package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.Seller;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;
import model.dao.SellerDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/serllers", "/sellers/form","/seller/Insert", "/seller/update", "/seller/delete"})

public class SellersControler extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		switch (action) {
		case "/crud-manager/seller/form": {
			loadComponies(req);
			req.setAttribute("acao", "insert");
			ControllerUtil.forward(req, resp, "/form-seller.jsp");	
			break;
		}
		case "/crud-manager/seller/update": {
			loadSeller(req);
			loadComponies(req);
			req.setAttribute("acao", "update");
			ControllerUtil.forward(req, resp, "/form-seller.jsp");	
			break;
		}
		
		default:
			listSelles(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/sellers.jsp");
		}
	}
	private void loadSeller(HttpServletRequest req) {
		String sellerIdStr = req.getParameter("sellerID");
		int sellerId = Integer.parseInt(sellerIdStr);
		
		SellerDAO dao = DAOFactory.createDAO(SellerDAO.class);
		
		Seller seller = new Seller(0);
		
		try {
			seller = dao.findById(sellerId);
		} catch (Exception e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar vendedor para edição");
		}
		req.setAttribute("vendedorEdit", seller);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		 switch (action) {
		case "/crud-manager/seller/insert": {
			insertSeller(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/sellers");
			break;
		}
		case "/crud-manager/seller/update": {
			updateSeller(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/sellers");
			break;
		}
		case "/crud-manager/seller/delete": {
			String sellerIdStr = req.getParameter("id");
			String sellerName = req.getParameter("entityName");
			int sellerId = Integer.parseInt(sellerIdStr);
			
			SellerDAO dao = DAOFactory.createDAO(SellerDAO.class);
			try {
				if(dao.delete(new Seller(sellerId))){
					ControllerUtil.sucessMessage(req,"Vendedor"+sellerName+"excluído com sucesso");
				}else {
					ControllerUtil.sucessMessage(req,"Vendedor"+sellerName+"não pode ser excluído");
				}
			} catch (ModelException e) {
				ControllerUtil.sucessMessage(req,e.getMessage());
			}
			break;
		}
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
	
	private void insertSeller(HttpServletRequest req) {
		Seller seller = createSeller(req, 0);

		SellerDAO dao = DAOFactory.createDAO(SellerDAO.class);

		try {
			if (dao.save(seller))
				ControllerUtil.sucessMessage(req, "Vendedor " + seller.getName() + " salvo com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Vendedor " + seller.getName() + " não pode ser salvo.");
		} catch (ModelException e) {
			e.printStackTrace(); // log
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateSeller(HttpServletRequest req) {
		String sellerIdStr = req.getParameter("seller_id");
		int sellerId = Integer.parseInt(sellerIdStr);

		Seller seller = createSeller(req, sellerId);

		SellerDAO dao = DAOFactory.createDAO(SellerDAO.class);

		try {
			if (dao.update(seller))
				ControllerUtil.sucessMessage(req, "Vendedor " + seller.getName() + " alterado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Vendedor " + seller.getName() + " não pode ser alterado.");
		} catch (ModelException e) {
			e.printStackTrace(); // log
			ControllerUtil.errorMessage(req, e.getMessage());
		}

	}

	private Seller createSeller(HttpServletRequest req, int sellerId) {

		String sellerName = req.getParameter("seller_name");
		String sellerEmail = req.getParameter("seller_email");
		String sellerFone = req.getParameter("seller_fone");
		String sellerCompany = req.getParameter("seller_company");
		int sellerCompanyId = Integer.parseInt(sellerCompany);

		Seller seller;
		if (sellerId == 0) {
			seller = new Seller();
		} else {
			seller = new Seller(sellerId);
		}
		seller.setName(sellerName);
		seller.setEmail(sellerEmail);
		seller.setFone(sellerFone);
		seller.setCompany(new Company(sellerCompanyId));

		return seller;
	}
	
	private void loadComponies(HttpServletRequest req) {
		List<Company> companies = new ArrayList<>();
		 CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		 try {
				companies = dao.listAll();
			} catch(ModelException e) {
				ControllerUtil.errorMessage(req, "Erro ao carregar empresas");
			}
		 req.setAttribute("empresas", companies);
	}

	private void listSelles(HttpServletRequest req) {
		SellerDAO dao = DAOFactory.createDAO(SellerDAO.class);
		
		List<Seller> sellers = new ArrayList<Seller>();
		
		try {
			sellers = dao.listAll();
		} catch(ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar dados dos vendedores");
		}
		
		req.setAttribute("vendedores", sellers);
	}
}
