<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
	<%@include file="base-head.jsp"%>
	<title>CRUD Manager - inserir Vendedor</title>
</head>

<body>
	<%@include file="nav-menu.jsp"%>
	
	<div id="container" class="container-fluid">
		<h3 class="page-header">${acao eq "insert" ? logica ? "Adicionar" : "Editar"}Vendedor</h3>
		
	<div class="row">
		<form action="${pageContext.request.contextPath}/seller/${acao}" method="POST">
		
		<input type="hidden" name="sellerId" value="${vendedorEdit.getId()}">
		
			<div class="form-group col-md-6">
					<label for="content">Nome</label>
						<input type="text" class="form-control" id="seller_name" name="seller_name" 
							   autofocus="autofocus" placeholder="Nome do vendedor" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o nome do vendedor.')"
							   oninput="setCustomValidity('')"
							   value="${vendedorEdit.getName()}">
			</div>
			
			<div class="form-group col-md-6">
					<label for="content">Email</label>
						<input type="email" class="form-control" id="seller_email" name="seller_email" 
							   autofocus="autofocus" placeholder="Email do vendedor" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o Email do vendedor.')"
							   oninput="setCustomValidity('')"
							   value="${vendedorEdit.getEmail()}">
							   
			</div>
			
			<div class="form-group col-md-6">
					<label for="content">Telefone</label>
						<input type="number" class="form-control" id="seller_fone" name="seller_fone" 
							   autofocus="autofocus" placeholder="fone do vendedor" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o telefone do vendedor.')"
							   oninput="setCustomValidity('')"
							   value="${vendedorEdit.getFone()}">
			</div>
			
			<div class="form-group col-md-6">
					<label for="seller_company">Empresa</label> <select
						id="seller_company" class="form-control selectpicker"
						name="seller_company" required
						oninvalid="this.setCustomValidity('Por favor, informe a Empresa.')"
						oninput="setCustomValidity('')">
						<option value=""disabled ${notemptysellerToEdit ? "" : "selected"}>Selecione
							uma empresa</option>
						<c:forEach var="company" items="${companies}">
							<option value="${company.getId()}"
								${sellerToEdit.getCompany().getId() eq company.getId() ? "selected" : ""}>
								${company.getName()}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<hr />

			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					<a href="${pageContext.request.contextPath}/sellers"
						class="btn btn-default">Cancelar</a>
					<button type="submit" class="btn btn-primary">${action eq "insert" ? "Criar" : "Editar"}
						Vendedor</button>
				</div>
			</div>

		</form>
	</div>

	<!-- IMPORTAR OS SCRIPTS -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

</body>
</html>