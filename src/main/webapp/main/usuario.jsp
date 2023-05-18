
<%@page import="model.modelLogin"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

	
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>


<body>
	<!-- Pre-loader start -->
	<jsp:include page="theme-loader.jsp"></jsp:include>
	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navBar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="NavBarMainMenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->

						<jsp:include page="pageHeader.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cadastro de Usuários</h4>
														<form class="form-material" enctype="multipart/form-data"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span class="form-bar"></span>
																<label class="float-label">ID</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome</label>
															</div>
															
															<div class="form-group form-default form-static-label">
															<select class="form-control"
																aria-label="Default select example" name="perfil" >
																<option disabled="disabled" selected="selected" >[Selecione o Perfil]</option>
																
																<option value="admin" <% 
																
																
																modelLogin modelLogin = (modelLogin) request.getAttribute("modelLogin");
																
																
																if (modelLogin != null && modelLogin.getPerfil().equals("admin")) {
																		out.print(" ");
																		 out.print("selected=\"selected\"");
																		out.print(" ");
																} %> >Admin</option>
																																														
																<option value="auxiliar" <% 
																	modelLogin = (modelLogin) request.getAttribute("modelLogin");
																				
																	if (modelLogin != null && modelLogin.getPerfil().equals("auxiliar")) {
																		out.print(" ");
																		 out.print("selected=\"selected\"");
																		out.print(" ");
																} %>>Auxiliar</option>
																											
															</select>
															<span class="form-bar"></span> <label class="float-label">Função</label>
															</div>
																													
															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	value="${modelLogin.email}"> <span
																	class="form-bar"></span> <label class="float-label">E-mail</label>
															</div>
																													
															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	value="${modelLogin.login}"> <span
																	class="form-bar"></span> <label class="float-label">Login</label>
															</div>
														
															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	value="${modelLogin.senha}"> <span
																	class="form-bar"></span> <label class="float-label">Senha</label>
															</div>
															
															 <div class="form-group form-default form-static-label">
                                                             <input type="radio" name="sexo" checked="checked" value="masculino"<%
                                                            
	                                                             modelLogin = (modelLogin) request.getAttribute("modelLogin");
	                                                                 
	                                                             if (modelLogin != null && modelLogin.getSexo().equals("masculino")) {
																		out.print(" ");
																		 out.print("checked=\"checked\"");
																		out.print(" ");
																}
	                                                             
	                                                             %>>Masculino</>
                                                             
                                                             <input type="radio" name="sexo" value="feminino" <%
                                                            
                                                             modelLogin = (modelLogin) request.getAttribute("modelLogin");
                                                                 
                                                             if (modelLogin != null && modelLogin.getSexo().equals("feminino")) {
																	out.print(" ");
																	 out.print("checked=\"checked\"");
																	out.print(" ");
															}
                                                             
                                                             %> >Feminino</>
                                                            </div>
                                                            
                                                            <div class="form-group form-default input-group mb-4">
                                                            	<div class="input-group-prepend">
                                                            		<img alt="imageUser" src="" id="fotoembase64" width="70px">
                                                            	</div>
                                                            	<input type="file" accept="image/*" onchange="vizualizarImg('fotoembase64','fileFoto')" id="fileFoto" name="fileFoto" class="form-control-file" style="margin-top: 15px; margin-left: 5px">
                                                            
                                                            
                                                            </div>

															<button type="button" class="btn btn-secondary waves-effect waves-light" onclick="limparForm();">Novo</button>
															<button class="btn btn-success waves-effect waves-light">Salvar</button>
															<button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDelete();">Excluir</button>
														</form>


													</div>

												</div>
											</div>
											<span>${msg}</span>
										</div>


									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Required Jquery -->
	<jsp:include page="JavaScript.jsp"></jsp:include>

	<script type="text/javascript">
		
		function limparForm() {
			var elements = document.getElementById("formUser").elements;
			for (p = 0; p < elements.length; p++) {
				elements[p].value = "";
			}
		}

		function criarDelete() {

			if (confirm("Deseja realmente excluir os dados?")) {
				document.getElementById("formUser").method = "get";
				document.getElementById("acao").value = "deletar";
				document.getElementById("formUser").submit();
			}

		}
		
		
		function vizualizarImg(fotoembase64, fileFoto){
			
			var preview = document.getElementById(fotoembase64); // campo IMG HTML
			var fileUser = document.getElementById(fileFoto).files[0];
			var reader = new FileReader();
			
			reader.onloadend = function() {
				
				preview.src = reader.result; // carrega foto na tela
				
			};
			
			if (fileUser){
				reader.readAsDataURL(fileUser); //Preview da IMG
						
			}else{
				preview.src = " ";
			}
			
		}
		
	</script>
</body>

</html>
