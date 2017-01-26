<%@include file="../../common/pdHeader.jsp"%>
<style>
.ubuntu {
	font-family: 'Ubuntu Condensed', sans-serif;
}
</style>
<!-- -------------------End of Header-------------------------- -->

<%-- <c:if test="${!empty msg}"><font color="red">${msg}</font></c:if> --%>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue ubuntu">
			<em>Welcome to ${department.deptName} </em>
		</h1>
	</div>

	<%-- <div class="container">
		<div class="row">
			<div class="col-sm-4  col-sm-offset-4"
				style="background-color: white; border: 2px dashed red; border-radius: 10px; margin-top: 10vh; min-height: 30vh; display: flex; align-items: center;">
				<div class="col-sm-12 center">
					<h1 class="red center italicized ubuntu"  >
						<em>Pending Task</em>
					</h1>

					<a href="${pageContext.request.contextPath}/ls/requisitionList.do"
						class="btn btn-primary"
						style="display: block; margin-bottom: 10px; border-radius: 8px;">S&amp;D Item
						Requisition Tray <span class="badge badge-important white bold">${requisitionTray}</span>
					</a> 					
				</div>
			</div>
		</div>
	</div> --%> 

</div>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>