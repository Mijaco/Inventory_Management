<%@include file="../common/homeHeader.jsp"%>

<style type="text/css">
.error {
	font-size: 16px;
	color: white;
	font-style: italic;
}
</style>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="col-md-offset-3 col-md-6">
		<div class="">
			<img style="width: 100%; margin-top: 30px;" 
				src="${pageContext.request.contextPath}/resources/assets/images/accessDenied.png"
				alt="" />			
		</div>
	</div>
</div>

<%@include file="../common/ibcsFooter.jsp"%>