<%--
    Document   : Source
    Created on : Nob 15, 2015, 10:35:33 AM
    Author     : Ahasanul Ashid, IBCS, And Abu Taleb, IBCS
--%>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>IBCS ICX BILLING SOLUTION </title>

<!-- CSS Reset -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/html/admin/css/reset.css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/html/themes/base/jquery.ui.all.css"/>
<!-- Fluid 960 Grid System - CSS framework -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/html/admin/css/grid.css" media="screen" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/html/admin/css/styles.css" media="screen" />

<!-- Table sorter stylesheet -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/html/admin/css/tablesorter.css" media="screen" />

<!-- Themes. Below are several color themes. Uncomment the line of your choice to switch to different color. All styles commented out means blue theme. -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/html/admin/css/theme-blue.css" media="screen" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/DataTables-1.9.1/media/css/jquery.dataTables.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/html/ui/jquery-ui-1.8.18.custom.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/highcharts.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/html/ui/jquery.ui.core.js"></script>
<script src="${pageContext.request.contextPath}/resources/html/ui/jquery.ui.dialog.js"></script>
<script src="${pageContext.request.contextPath}/resources/html/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/html/js/JQuery.blockUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/html/js/date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/html/admin/js/jquery.colorbox.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/DataTables-1.9.1/media/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/html/js/jquery.corner.js"></script>

<!-- JQuery tablesorter plugin script-->






<!-- Initiate password strength script -->





<script type="text/javascript">
    function daily_call(){
        var op_name=[];
        var value=[];
        var result = [];



        $.ajax({
                    async: false,
            url: '${pageContext.request.contextPath}/JSON/daily_call_flow.htm',
            dataType: "json",
            success: function(data) {
                $.each(data, function(key, val) {
                    op_name.push(key);
                    value.push(val);

                    //////alert(key);
                })
            }
        })






    var c=0;
    for(c=0;c<100000;c++){


    }
    //////alert("hello");
    //return merge(op_name,value);
    draw(merge(op_name,value));

}




 function daily_call_int_in(){
        var op_name=[];
        var value=[];
        var result = [];



        $.ajax({
                    async: false,
            url: '${pageContext.request.contextPath}/JSON/daily_call_flow_int_in.htm',
            dataType: "json",
            success: function(data) {
                $.each(data, function(key, val) {
                    op_name.push(key);
                    value.push(val);

                    //////alert(key);
                })
            }
        })






    var c=0;
    for(c=0;c<100000;c++){


    }
    //////alert("hello");
    //return merge(op_name,value);
    draw_int_in(merge(op_name,value));

}

function daily_call_int_out(){
        var op_name=[];
        var value=[];
        var result = [];



        $.ajax({
                    async: false,
            url: '${pageContext.request.contextPath}/JSON/daily_call_flow_int_out.htm',
            dataType: "json",
            success: function(data) {
                $.each(data, function(key, val) {
                    op_name.push(key);
                    value.push(val);

                    //////alert(key);
                })
            }
        })






    var c=0;
    for(c=0;c<100000;c++){


    }
    //////alert("hello");
    //return merge(op_name,value);
    draw_int_out(merge(op_name,value));

}



function merge(a, b){
    var length = Math.min(a.length, b.length);
    var x_array=[];
    for (var n = 0; n < length; n++) {
        x_array.push([a[n], b[n]]);
    }
    return x_array;
}
function draw(x){
    //////alert(x.length);


    //var res =[];

    $(".inline").colorbox({inline:true, width:"50%"});


    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container1',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: true
        },
        title: {
            text: 'Daily Call Flow (Domestic)'
        },
        tooltip: {
            formatter: function() {
                return this.point.name + this.percentage +' %';
            }
        },

        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function() {
                      //  alert(this.percentage.toString().slice( 0, 4 ));
                      return  this.point.name + this.percentage.toString().slice( 0, 5 ) +' %';
                    }
                }
            }
        },
        series: [{
                type: 'pie',
                name: 'Daily Call Flow (Domestic)',
                data:x
                /*[
                        ['GP',   45.0],
                        ['AXB',       26.8],

                        ['Bangla Link',    8.5],
                        ['Teletalk',     6.2],
                        ['City Cell',   0.7]
                ]*/
            }]
    });


}








function draw_int_in(x_1){
    //////alert(x.length);


    //var res =[];

    $(".inline").colorbox({inline:true, width:"50%"});


    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container1_2',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: true
        },
        title: {
            text: 'Daily Call Flow (International Incoming)'
        },
        tooltip: {
            formatter: function() {
                return this.point.name + this.percentage +' %';
            }
        },

        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function() {
                        return  this.point.name + this.percentage.toString().slice( 0, 5 ) +' %';
                    }
                }
            }
        },
        series: [{
                type: 'pie',
                name: 'Daily Call Flow (International Incoming)',
                data:x_1
                /*[
                        ['GP',   45.0],
                        ['AXB',       26.8],

                        ['Bangla Link',    8.5],
                        ['Teletalk',     6.2],
                        ['City Cell',   0.7]
                ]*/
            }]
    });


}

function draw_int_out(x_1){
    //////alert(x.length);


    //var res =[];

    $(".inline").colorbox({inline:true, width:"50%"});


    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container1_3',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: true
        },
        title: {
            text: 'Daily Call Flow (International OutGoing)'
        },
        tooltip: {
            formatter: function() {
                return this.point.name + this.percentage +' %';
            }
        },

        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function() {
                       return  this.point.name + this.percentage.toString().slice( 0, 5 ) +' %';
                    }
                }
            }
        },
        series: [{
                type: 'pie',
                name: 'Daily Call Flow (International OutGoing)',
                data:x_1
                /*[
                        ['GP',   45.0],
                        ['AXB',       26.8],

                        ['Bangla Link',    8.5],
                        ['Teletalk',     6.2],
                        ['City Cell',   0.7]
                ]*/
            }]
    });


}

function testArray(){
            $.getJSON('${pageContext.request.contextPath}/JSON/testy.htm', function(data) {
        var items = [];

        $.each(data, function(key, val) {
            items.push(Number(val));
            //////alert(val);
        });
        return items;
    });

}


        function modalOn(){

    $.blockUI({ message: '<img width="60" height="40" src="${pageContext.request.contextPath}/resources/html/images/273.gif" />' });
    document.body.style.cursor = "wait";



}

function modalOff(){
    $.unblockUI();
    document.body.style.cursor = "default";



}


function ModalBlock(){

    $("#areaDiv").ajaxStart(function(){
                // $("#all").hide();
        $.blockUI({ message: '<img width="60" height="40" src="${pageContext.request.contextPath}/resources/html/images/273.gif" />' });
        document.body.style.cursor = "wait";
    });

    $("#areaDiv").ajaxStop(function(){
        $.unblockUI();
        document.body.style.cursor = "default";

    });



}



function tableSort(){


    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    modal("#dialog-form","#eForm");
    $(".dataTable").dataTable({
        "bPaginate": true,
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "bFilter": true,
        "bSort": true,
        "bInfo": true,
        "sScrollY": "130%",
        "sScrollX": "100%",

        "bScrollCollapse": true,
        "bAutoWidth": false


    });
}











function normal_tableSort_1(){


    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    modal("#dialog-form","#eForm");
    $(".entryForm_1").dataTable({
        "bPaginate": false,
        "bJQueryUI": true,
        "bFilter": false,
        "bSort": false,
        "bInfo": false,

        "sScrollX": "90%",

        "bScrollCollapse": false,
        "bAutoWidth": false



    });
}



function normal_tableSort(){


    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    modal("#dialog-form","#eForm");
    $(".entryForm").dataTable({
        "bPaginate": false,
        "bJQueryUI": true,
        "bFilter": false,
        "bSort": false,
        "bInfo": false,
             
        "sScrollX": "90%",

        "bScrollCollapse": false,
        "bAutoWidth": false



    });
}

function normal_tableSort5(){


    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    modal("#dialog-form","#eForm");
    $(".entryForm5").dataTable({
        "bPaginate": false,
        "bJQueryUI": true,
        "bFilter": false,
        "bSort": false,
        "bInfo": false,
             
        "sScrollX": "100%",

        "bScrollCollapse": false,
        "bAutoWidth": false



    });
}


function id_tableSort(id){

    $(id).dataTable({
        "bPaginate": true,
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "bFilter": true,
        "bSort": true,
        "bInfo": true,
        "sScrollY": "130%",
        "sScrollX": "100%",

        "bScrollCollapse": true,
        "bAutoWidth": false


    });
}








function CommonEdit(a,urlLink){
    //$("#result").hide();
    //////alert($("#edt").val());
    $.ajax({
        type: "POST",
        datatype:"text/html",
        data:{"id":a},
        url:urlLink,

        success: function(msg){
            // $("#areaDiv").append("<div id='editDiv'></div>")
            ////alert(msg);

            $("#edit_dialog-form").html(msg);
            modal("#edit_dialog-form","#edForm");
             $(function() {
             $( ".DatePicker" ).datepicker({dateFormat: 'yy-mm-dd'});
           $( ".ddate" ).datepicker({dateFormat: 'yy-mm-dd'});    
         })
           $( "#edit_dialog-form" ).dialog( "open" );


        },
        error : function (jqXHR,status){
            ////alert(jqXHR.responseText);
        }




    });
}







function CommonEditDynamic(a,urlLink,dialogForm,form){
    //$("#result").hide();
    //////alert($("#edt").val());
    $.ajax({
        type: "POST",
        datatype:"text/html",
        data:{"id":a},
        url:urlLink,

        success: function(msg){
            $("#areaDiv").empty().append(msg);
            modal(dialogForm,form);
               
            $( dialogForm ).dialog( "open" );
               $( ".eDatePicker" ).click(function(){ jQuery( ".eDatePicker" ).datepicker({dateFormat: 'yy-mm-dd'});});
      $( ".ddate" ).datepicker({dateFormat: 'yy-mm-dd'});

        },
        error : function (jqXHR,status){
            ////alert(jqXHR.responseText);
        }




    });
}
















function CommonDeletee (a,urlLink){

    $("#edt").val(a);



    $( "#dialog:ui-dialog" ).dialog( "destroy" );

    $("#dialog-confirm").empty().append('<p><span class="ui-icon ui-icon-////alert" style="float:left; margin:0 7px 20px 0;"></span>These items will be permanently deleted and cannot be recovered. Are you sure?</p>');

    $( "#dialog-confirm" ).dialog({
        resizable: false,
        height:140,
        width:650,
        modal: true,
        buttons: {
            "Delete all items": function() {
                $( this ).dialog( "close" );

                $.ajax({
                    type: "POST",
                    datatype:"text/html",
                    data:{"id":a},
                    url:urlLink,

                    success: function(msg){
                        modalOn();
                        location.reload();
                    },
                    error : function (jqXHR,status){
                        ////alert(jqXHR.responseText);
                    }




                });

            },
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        }
    });








}


function CommonAjax(datavalue1,datavalue2,urlLink,divToAppend){

    //  var b = $("#areaa :selected").val();
    // ////alert(b);
    $.ajax({
        type: "POST",
        datatype:"text/html",
        data:{"id":datavalue1,
            "btrc":datavalue2},
        url:urlLink,

        success: function(msg){
            $(divToAppend).empty().append(msg);
            tableSort();
            modal("#dialog-form","#eForm");


        },
        error : function (jqXHR,status){
            ////alert(jqXHR.responseText);
        }




    });
}





function CommonAjax2(datavalue1,urlLink,divToAppend){

    //  var b = $("#areaa :selected").val();
    // ////alert(b);
    $.ajax({
        type: "POST",
        datatype:"text/html",
        data:{"country":datavalue1
        },
        url:urlLink,

        success: function(msg){
           
            $(divToAppend).empty().append(msg);
             tableSort();
             modal("#dialog-form","#eForm");


        },
        error : function (jqXHR,status){
           alert(jqXHR.responseText);
        }




    });
}





function modal(divname,form){


    $.each($('#eForm').serializeArray(), function(i, field) {
        var values1 = {};
        values1[field.name] = field.value;

        $('input[name='+field.name+']').removeClass('ui-state-error');
     //   $('input[name='+field.name+']').removeClass( "ui-state-highlight" );
        // $('input[name='+field.name+']').after("<span class=''> &nbsp &nbsp blank field not allowed</span>");




    });
    var flag=true;
    var reflag=true;
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    $(divname).dialog({
        autoOpen: false,
        height: 300,
        width: '50%',
        modal: true,
        buttons: {
            "Submit": function() {
                // ////alert("hello");
                var flag=validation(form);

                if(flag==true && reflag==true){
                    $(form).submit();
                }
                else {}////alert("validation required");
            },
            Cancel: function() {
                // $("#dialog-form").dialog( "close" );
                if(form=='#edForm'){
                    $("#edit_dialog-form").dialog( "close" );

                    $( "#dialog:ui-dialog" ).dialog( "close" );
                    //   location.reload();
                }
                else {$("#dialog-form").dialog( "close" );
                    $( "#dialog:ui-dialog" ).dialog( "close" );}
                //   $("#edit_dialog-form").dialog("destroy");
            }
        },
        close: function() {
            //$("#editDiv").empty();

        }
    });


    $('#add').bind('click', function() {

        //////alert("hello");


        modal("#dialog-form","#eForm");
        $( "#dialog-form" ).dialog( "open" );



    });





}






function Secondmodal(divname,form){
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    $(divname).dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Submit": function() {
                ////alert("hello");
                $(form).submit();

                allFields.removeClass( "ui-state-error" );



            },
            Cancel: function() {
                // $("#dialog-form").dialog( "close" );
                if(form=='#d_edForm'){
                    $("#d_edit_dialog-form").dialog( "close" );
                    $( "#dialog:ui-dialog" ).empty();
                }
                else $("#d_dialog-form").dialog( "close" );
                $( "#dialog:ui-dialog" ).empty();
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });


    $('#d_add').bind('click', function() {

        //////alert("hello");


        modal("#d_dialog-form","#d_eForm");
        $( "#d_dialog-form" ).dialog( "open" );



    });





}
































function Dmodal(divname,form,IsEdit,edit_dialog_form,dialog_form,btnId,addForm){
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    $(divname).dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Submit": function() {
                ////alert("hello");
                $(form).submit();

                allFields.removeClass( "ui-state-error" );



            },
            Cancel: function() {
                // $("#dialog-form").dialog( "close" );
                    
                if(IsEdit=='y'){

                    $(edit_dialog_form).dialog( "close" );
                    $("#editDiv").empty();
                }
                else {
                    validateCallRate();
                    $(dialog_form).dialog( "close" );
                    $("#editDiv").empty();
                }


            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });


    $(btnId).bind('click', function() {

        //////alert("hello");


        modal(dialog_form,addForm);
        $(dialog_form).dialog( "open" );



    });





}


























function ModalDelete(){







}

function validateCallRate(){
    var bFlag=true;

    ////alert($('input[name=entry_date]').val());

    

}
function trunkIdCheck(a){
    //$("#result").hide();
    //////alert($("#edt").val());
var rt;
  $.ajax({
        type: "POST",
        datatype:"text/html",
        data:{"id":a},
        url:"${pageContext.request.contextPath}/IFW/trunk_trunkIdCheck.htm",

        success: function(msg){
       //alert(msg);
       rt=msg;
       return rt;
        },
        error : function (jqXHR,status){
            ////alert(jqXHR.responseText);
        }




    });

}

function validation(form)
{   var flag=true;
    var reflag=true;
    var extraFlag=true;
    var values = {};
    $.each($(form).serializeArray(), function(i, field) {

        values[field.name] = field.value;
        var numericReg = /^[0-9]+.?[0-9]*$/;
        var dateReg=/^[0-9]{2,4}[-/][0-9]{2,2}[-/][0-9]{2,4}$/;
        var wholeNumReg=/^([0-9]+)$/
        var fractionReg=/^([0-9]*[.?]?[0-9]+)$/



if(!$('input[name='+field.name+']').hasClass("escape")){
       if( $('input[name='+field.name+']').hasClass("fractionnumber")){
           
           if(!fractionReg.test(field.value)){
             //  $("input[name="+field.name+"]").addClass("ui-state-highlight");
             $('input[name='+field.name+']').addClass('ui-state-error');
             $('input[name='+field.name+']').val("Please Input Fraction Value");
                //$('input[name='+field.name+']').addClass( "ui-state-highlight" );
               //  $('input[name='+field.name+']').after("<span id='espan' class=''> &nbsp &nbsp numeric field required</span>");
                flag=false;
                reflag=false;
            }
            else {reflag==true;
                $('input[name='+field.name+']').removeClass('ui-state-error');
            //    $('input[name='+field.name+']').removeClass( "ui-state-highlight" );
              //  $('#espan').empty();
            }
        }



  else if( $('input[name='+field.name+']').hasClass("null_allowed")){
     // //alert("got it");
    
    if($('input[name='+field.name+']').val()==null||$('input[name='+field.name+']').val()==''||$('input[name='+field.name+']').val()==' '){    
    $('input[name='+field.name+']').remove();
    }
    else {}

//      $("#vt").remove();

      
  }

else if( $('input[name='+field.name+']').hasClass("trunkid")){
  
                $.ajax({
        type: "POST",
        datatype:"text/html",
        data:{"id":$('input[name='+field.name+']').val()},
        url:"${pageContext.request.contextPath}/IFW/trunk_trunkIdCheck.htm",
  success: function(msg){
      
        if(msg.toString()=='true'){
          flag=false;
          reflag=false;
          extraFlag=false;
          $('input[name='+field.name+']').val("Trunk Id Has To Be Unique");
        $('input[name='+field.name+']').addClass('ui-state-error');
       }
        if(msg.toString()=='false'){
         reflag=true;
         extraFlag=true;
        $('input[name='+field.name+']').removeClass('ui-state-error');
       }
        },
        error : function (jqXHR,status){
            ////alert(jqXHR.responseText);
        }
     




    });

}

else if( $('input[name='+field.name+']').hasClass("areacode")){

                $.ajax({
        type: "POST",
        datatype:"text/html",
        data:{"id":$('input[name='+field.name+']').val()},
        url:"${pageContext.request.contextPath}/IFWOutRate/areacode_check.htm",
  success: function(msg){

        if(msg.toString()=='true'){
          flag=false;
          extraFlag=false;
          reflag=false;
          $('input[name='+field.name+']').val("Area Code Has To Be Unique");
        $('input[name='+field.name+']').addClass('ui-state-error');
       }
        if(msg.toString()=='false'){
         reflag=true;
         extraFlag=true;
        $('input[name='+field.name+']').removeClass('ui-state-error');
       }
        },
        error : function (jqXHR,status){
            ////alert(jqXHR.responseText);
        }





    });

}

else if( $('input[name='+field.name+']').hasClass("opcode")){

                $.ajax({
        type: "POST",
        datatype:"text/html",
        data:{"id":$('input[name='+field.name+']').val().toUpperCase()},
        url:"${pageContext.request.contextPath}/Account/opcode_check.htm",
  success: function(msg){
              
        if(msg.toString()=='true'){
          flag=false;
          reflag=false;
          extraFlag=false;
          $('input[name='+field.name+']').val("Operator  Code Has To Be Unique");
        $('input[name='+field.name+']').addClass('ui-state-error');
       }
        if(msg.toString()=='false'){
         reflag=true;
         extraFlag=true;
        $('input[name='+field.name+']').removeClass('ui-state-error');
       }
        },
        error : function (jqXHR,status){
            ////alert(jqXHR.responseText);
        }





    });

}
  else if( $('input[name='+field.name+']').hasClass("wholenumber")){
            if(!wholeNumReg.test(field.value)){
                //////alert("date error");
                $('input[name='+field.name+']').addClass('ui-state-error');
                $('input[name='+field.name+']').val("Please Input Whole Number");
             //  $('input[name='+field.name+']').append(" <p>eror</p>");
              //  $('input[name='+field.name+']').addClass( "ui-state-highlight" );
                // $('input[name='+field.name+']').after("<span class=''> &nbsp &nbsp blank field not allowed</span>");
                flag=false;
                reflag=false;

            }
            else {reflag==true;
                $('input[name='+field.name+']').removeClass('ui-state-error');
              //  $('input[name='+field.name+']').removeClass( "ui-state-highlight" );
            }
        }
      else if( $('input[name='+field.name+']').hasClass("datepicker")|| $('input[name='+field.name+']').hasClass("DatePicker")){
            if(!dateReg.test(field.value)){
                //////alert("date error");
                $('input[name='+field.name+']').addClass('ui-state-error');
                $('input[name='+field.name+']').va("Date Entry Required");
               // $('input[name='+field.name+']').addClass( "ui-state-highlight" );
                // $('input[name='+field.name+']').after("<span class=''> &nbsp &nbsp blank field not allowed</span>");
                flag=false;
                reflag=false;
            }
            else {reflag==true;
                $('input[name='+field.name+']').removeClass('ui-state-error');
            //    $('input[name='+field.name+']').removeClass( "ui-state-highlight" );
            }
        }


else if( $('input[name='+field.name+']').hasClass("confirm")){
            if((field.value)!=$('input[name=password]').val()){
                //////alert("date error");
                $('input[name='+field.name+']').addClass('ui-state-error');
                $('input[name='+field.name+']').val("Password Mismatch");
               // $('input[name='+field.name+']').addClass( "ui-state-highlight" );
                // $('input[name='+field.name+']').after("<span class=''> &nbsp &nbsp blank field not allowed</span>");
                flag=false;
                reflag=false;
            }
            else {
               // alert("ok");
                 reflag=true;
         //extraFlag=true;
                $('input[name='+field.name+']').removeClass('ui-state-error');
            //    $('input[name='+field.name+']').removeClass( "ui-state-highlight" );
            }
        }


        else{



            if(field.value==''||field.value.length==0||field.value==null||field.value==' '){

          
           //  //alert("general empty for"+field.value);
               $('input[name='+field.name+']').addClass('ui-state-error');
               $('input[name='+field.name+']').val("This Field Cant Be Empty");
                //$('input[name='+field.name+']').addClass( "ui-state-highlight" );
                // $('input[name='+field.name+']').after("<span class=''> &nbsp &nbsp blank field not allowed</span>");
                flag=false;
                reflag=false;
            }

            else{ reflag==true;
                $('input[name='+field.name+']').removeClass('ui-state-error');
              //  $('input[name='+field.name+']').removeClass( "ui-state-highlight" );
            }}
     
        //////alert(flag);
}
else {
 reflag==true;
                $('input[name='+field.name+']').removeClass('ui-state-error');

}


    });
 //  //alert(flag);
  //alert(flag+" "+reflag)
  if(flag==true && reflag==true && extraFlag==true){return true}
    else return false;
}
function UserEdit(){
    //$("#result").hide();
    //////alert($("#edt").val());
    //alert("works");
    $.ajax({
        type: "POST",
        datatype:"text/html",
        url:"${pageContext.request.contextPath}/Misc/useredit.htm",

        success: function(msg){
            // $("#areaDiv").append("<div id='editDiv'></div>")
            ////alert(msg);

            $("#user_edit_dialog-form").html(msg);
            tmodal("#user_edit_dialog-form","#user_edForm");
            
           $( "#user_edit_dialog-form" ).dialog( "open" );


        },
        error : function (jqXHR,status){
            ////alert(jqXHR.responseText);
        }




    });
}












function tmodal(divname,form){


    $.each($(form).serializeArray(), function(i, field) {
        var values1 = {};
        values1[field.name] = field.value;

        $('input[name='+field.name+']').removeClass('ui-state-error');
     //   $('input[name='+field.name+']').removeClass( "ui-state-highlight" );
        // $('input[name='+field.name+']').after("<span class=''> &nbsp &nbsp blank field not allowed</span>");




    });
    var flag=true;
    var reflag=true;
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    $(divname).dialog({
        autoOpen: false,
        height: 300,
        width: '50%',
        modal: true,
        buttons: {
            "Submit": function() {
                // ////alert("hello");
                var flag=validation(form);

                if(flag==true && reflag==true){
                    $(form).submit();
                }
                else {}////alert("validation required");
            },
            Cancel: function() {
                // $("#dialog-form").dialog( "close" );
                if(form=='#user_edForm'){
                    $("#user_edit_dialog-form").dialog( "close" );

                    $( "#dialog:ui-dialog" ).dialog( "close" );
                    //   location.reload();
                }
                else {$("#dialog-form").dialog( "close" );
                    $( "#dialog:ui-dialog" ).dialog( "close" );}
                //   $("#edit_dialog-form").dialog("destroy");
            }
        },
        close: function() {
            //$("#editDiv").empty();
        }
    });


    $('#add').bind('click', function() {
    	modal("#dialog-form","#eForm");
        $( "#dialog-form" ).dialog( "open" );
    });





}
</script>