<link href='https://fonts.googleapis.com/css?family=Ubuntu+Condensed'
	rel='stylesheet' type='text/css'>


<style>
.user-info {
	max-width: 200px !important;
}
</style>
<c:set var="authenticatedUser" scope="session"
	value="${authenticatedUser}" />
<a data-toggle="dropdown" href="#" class="dropdown-toggle"> <img
	class="nav-user-photo"
	src="${pageContext.request.contextPath}/resources/assets/images/logo36.png"
	alt="Jason's Photo" /> <span class="user-info"> <!-- principal comes from Spring Security -->
		<sec:authentication property="principal" var="anonymousUser" /> <%-- <c:if test="${anonymousUser eq 'anonymousUser' || empty anonymousUser}">

			<%
				response.setHeader("Cache-Control", "no-cache");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", 0);
					//System.out.println("No session 1");
					
					response.sendRedirect(request.getContextPath()
							+ "/auth/login.do");
					//System.out.println("No session 2"+request.getContextPath());
			%>

		</c:if> <c:if test="${anonymousUser ne 'anonymousUser' }">
			<% //System.out.println("Has session"); %>
			<span id="text-invitation">Welcome </span>
			<br />
			<sec:authentication property="principal.username" />

		</c:if> --%> <c:choose>
			<c:when
				test="${anonymousUser eq 'anonymousUser' || empty anonymousUser || empty authenticatedUser.userid}">
				<%
					//response.setHeader("Cache-Control", "no-cache");
							response.setHeader("Cache-Control", "no-cache");
							response.setHeader("Cache-Control", "no-store");
							response.setHeader("Cache-Control", "must-revalidate");
							response.setHeader("Pragma", "no-cache");
							response.setDateHeader("Expires", 0);
							response.sendRedirect(request.getContextPath()
									+ "/auth/login.do");
				%>
			</c:when>

			<c:when test="${anonymousUser eq 'principal'}">
				<%
					response.setHeader("Cache-Control", "no-cache");
							response.setHeader("Pragma", "no-cache");
							response.setDateHeader("Expires", 0);
							response.sendRedirect(request.getContextPath()
									+ "/auth/login.do");
				%>
			</c:when>

			<c:otherwise>
				<c:choose>
					<c:when test="${anonymousUser eq 'principal'}">
						<%
							response.setHeader("Cache-Control", "no-cache");
											response.setHeader("Pragma", "no-cache");
											response.setDateHeader("Expires", 0);
											response.sendRedirect(request.getContextPath()
													+ "/auth/login.do");
						%>
					</c:when>
					<c:otherwise>
						<span id="text-invitation"> ${authenticatedUser.name} </span>
						<br />
						 Id: ${authenticatedUser.userid}
<!-- 						<sec:authentication property="principal.username" /> -->
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

</span> <i class="ace-icon fa fa-caret-down"></i>
</a>