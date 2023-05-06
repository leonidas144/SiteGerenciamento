<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang='en'>

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
														<h4 class="sub-title">Pesquisa de Usuários</h4>

														<form class="form-inline">

															
														</form>

														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group mx-sm-3 mb-2">
																<label for="nomeBusca" class="sr-only">Nome</label>
																<input type="text" class="form-control" id="nomeBusca" name="nomeBusca"
																	placeholder="Nome" aria-describedby="basic-addon2" aria-label="nomeBusca">						
																<button class="btn btn-primary mb-2" onclick="buscarUsuario();">Pesquisar</button>
															</div>
															

															<table class="table table-striped"  id="tabelaResultados">
																<thead>
																	<tr>
																		<th scope="col">ID</th>
																		<th scope="col">Nome</th>
																		<th scope="col">E-mail</th>
																		<th scope="col">Visualizar</th>
																	</tr>
																</thead>
																
																<tbody>
																	<tr>
																		${tabelaHTML}
																	</tr>
																															
																</tbody>
																
															</table>
															<div>
																
																</div>
															
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
			
			function buscarUsuario(){
								
				document.getElementById('formUser').method = 'get';
				var nomeBusca = document.getElementById("nomeBusca").value;
				document.getElementById('acao').value = 'buscarUserAjax';
				document.getElementById('formUser').submit();
							
			}
			
			function editarUser(id){
				
				var urlAction = document.getElementById('formUser').action;
				
				window.location.href = urlAction + '?acao=editarUser&id='+id;
				
			}
				
			
			</script>
		
		</body>
		</html>
	