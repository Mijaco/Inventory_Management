<%@include file="../common/wsHeader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}
</style>
<!-- -------------------------------------------------------------------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Ticket</a> / List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Read Work Flow</h1>
		</div>
		
		<table border="1"><tr><td>
		<table>
		<tr><td>
	1. Contractor & contractor representive create  
	</td></tr>
<tr><td>
	2. Item allocation set for contractor representive
	</td></tr>
	<tr><td>
	3. Transformer repair target setting form submit 
	</td></tr>
</table>
</td></tr>
<tr><td>


		<tr><td>
<b>for Repair works:</b>
</td>
<td>
<b>for preventive maintenance :</b>
</td>
</tr>
<tr><td>
1. 	transformer requisition by contractor  (rayhan.power@gmail.com)<br>
2. 	transformer register by ws officer   (0193)<br>
3. 	job lookup item entry by ws officer  (0193)<br>
4. 	job card issue for per transformer and approval hirarchy (3 person of ws) maintain (then auto fill job no.  in xformer register book)  by contractor<br>
5. 	transformer Repairing materials requisition<br>
6. 	requisition quantity set for each job <br>
7. 	(single/3)phase wise testing transformer with hirarchy(then auto fill test date in xformer register book)<br>
8. 	As-built Report generate<br>
9. 	After repair transformer return to CS<br>
10. materials return if exists materials in hand /unuseble item<br>

</td>
<td>
<table border="1">
		
<tr><td>
1. receive transformer by ws officer<br>
2. transformer register by ws officer<br>
3. job lookup item entry by ws officer<br>
4. job card issue and approval hirarchy maintain (then auto fill job no.  in xformer register book) start by contractor<br>
5. transformer Repairing materials requisition<br>
6. requisition quantity set for each job <br>
7. (single/3)phase wise testing transformer with hirarchy(then auto fill test date in xformer register book) start by contractor<br>
8. As-built Report generate<br>
9. After repair transformer return as gatepass<br>
10. materials return if exists materials in hand /unuseble item<br>
</td></tr>
</table>
</td></tr>

<tr><td>
** CloseOut Report generate (Repair works & preventive maintenance )
</td></tr>
</table>
	
	</div>

	<div class="row" style="background: white;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			
				
				
			
			<!-- --------------------- -->
		</div>

	</div>
</div>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
