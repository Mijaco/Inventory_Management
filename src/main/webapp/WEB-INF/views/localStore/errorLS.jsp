<%@include file="../common/lsHeader.jsp"%>

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
			<img style="width: 100%"
				src="${pageContext.request.contextPath}/resources/assets/images/404.png"
				alt="" />
			<p class="error">
				<strong>Massage: </strong>${errorMsg}.</p>
		</div>
	</div>
</div>

<%@include file="../common/ibcsFooter.jsp"%>