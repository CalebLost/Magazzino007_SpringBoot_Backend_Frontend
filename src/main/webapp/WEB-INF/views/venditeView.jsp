<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>Vendite List - Spring Boot Web Application Example</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ taglib prefix = "c"   uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="tableurl" value="/resources/DataTables/" />
<c:url var="commonurl" value="/resources/commonjs/" />
<c:url var="jqueryurl" value="/resources/table/vendor/jquery/" />
<c:url var="venditeserviceurl" value="${venditeservice}" />
<link rel="stylesheet" type="text/css" href="${tableurl}datatables.min.css"/>

<script type="text/javascript" language="javascript" src="${jqueryurl}jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${tableurl}datatables.min.js"></script>
<script type="text/javascript" src="${tableurl}datatables.js"></script>
<script type="text/javascript" src="${commonurl}accounting.js"></script>
<script type="text/javascript" src="${commonurl}moment.js"></script>
<script type="text/javascript" class="init">
	
var venditeCurrency = "<c:out value="${venditeservicecurrency}" escapeXml="false"></c:out>";
var slang =               '<c:out value="${venditeGridLabels[0]}"></c:out>';
var sleng =               '<c:out value="${venditeGridLabels[1]}"></c:out>';
var snorecordsfound =     '<c:out value="${venditeGridLabels[2]}"></c:out>';
var snorecordsavailable = '<c:out value="${venditeGridLabels[3]}"></c:out>';
var spager =              '<c:out value="${venditeGridLabels[4]}"></c:out>';
var sfilter =             '<c:out value="${venditeGridLabels[5]}"></c:out>';
var sprevious =           '<c:out value="${venditeGridLabels[6]}"></c:out>';
var snext =               '<c:out value="${venditeGridLabels[7]}"></c:out>';
$(document).ready(function()
		{
	$('#venditeTable').DataTable
	
	(
			{
				'sAjaxSource'   :  "<c:out value="${venditeserviceurl}"></c:out>",
				'sAjaxDataProp' : "",
				"fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
				     oSettings.jqXHR = $.ajax( {
				       "dataType": 'json',
				       "type": "GET",
				       "headers" : {"<c:out value="${venditeserviceauthtokenheader}"></c:out>" : "<c:out value="${venditeserviceauthtokenvalue}"></c:out>"},
				       "url": sSource,
				       "data": aoData,
				       "success": fnCallback,
				       "error": function (e) {
				           console.log(e.message);
				       }
				     })
				},
				"order"         : [[0,"asc"]],
				"aoColumns"     : 
					[
						{ "mData"   : "id"},
						{ "mData"   : "id_prodotto"},
						{ "mData"   : "id_cliente"},
						{ "mData"   : "prezzo",
							"mRender" : function(a,b,c)
							   {
								 if(b === "sort" || b === "type")
								 {
					                    return a;
					            }
							     return accounting.formatMoney(a, venditeCurrency, <c:out value="${venditeservicecurrencyprecision}"></c:out>,"<c:out value="${venditeservicecurrencyseparator1}"></c:out>", "<c:out value="${venditeservicecurrencyseparator2}"></c:out>");
							   }
						},
						{ "mData"   : "quantita"},
						{ "mData"   : "data",
						  "mRender" : function(a,b,c)
						   {
							    if(b === "sort" || b === "type")
							    {
				                   return a;
				                }
							     return moment(a).format("<c:out value="${venditeservicetimeformatter}"></c:out>");
						   }
						 
						}
						
					]
				,
				  "language" :  {
	                    "sSearch" :     slang,
					    "lengthMenu":   sleng,
			            "zeroRecords":  snorecordsfound,
			            "info":         spager,
			            "infoEmpty":    snorecordsavailable,
			            "infoFiltered": sfilter,
			            "paginate"    : 
			                             {
			            	                "previous" : sprevious,
			            	                "next"     : snext
			                             }

					  }
					
			}
			
	);
} );
</script>
</head>
<body>
<jsp:include page="parts/login-operations.jsp"></jsp:include>
<h1 align="center">${venditeTitle}</h1>
</br>
<table id="venditeTable" class="display" style="width:100%">
        <thead><TR>
          <c:forEach items="${venditeHeaders}" var="venditaHeader">
          <TH>
             <c:out value="${venditaHeader}"></c:out>
          </TH>
          </c:forEach>
        </TR></thead>
       <!-- <tbody> -->
        <!--  
  <c:forEach items="${vendite}" var="vendita">
  <TR>
     <TD><c:out value="${vendita.id}"></c:out></TD>
     <TD><c:out value="${vendita.prodotto.id}"></c:out></TD>
     <TD><c:out value="${vendita.cliente.id}"></c:out></TD>
     <TD><fmt:setLocale value="it_IT"/><fmt:formatNumber value="${vendita.prezzo}" type="currency" currencySymbol="&euro;" /></TD>
     <TD><c:out value="${vendita.quantita}"></c:out></TD>
     <TD><c:out value="${vendita.data}"></c:out></TD>
  </c:forEach>
  -->
   <tfoot>
            <tr>
              <c:forEach items="${venditeHeaders}" var="venditaHeader">
                 <TH>
                  <c:out value="${venditaHeader}"></c:out>
                  </TH>
               </c:forEach>
            </tr>
        </tfoot>
 <!--  </tbody> -->
</table>
</body>
</html></html>