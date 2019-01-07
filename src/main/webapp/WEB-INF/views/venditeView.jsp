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
<c:url var="venditeservice" value="${venditeService}" />
<link rel="stylesheet" type="text/css" href="${tableurl}datatables.min.css"/>

<script type="text/javascript" language="javascript" src="${jqueryurl}jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${tableurl}datatables.min.js"></script>
<script type="text/javascript" src="${tableurl}datatables.js"></script>
<script type="text/javascript" src="${commonurl}accounting.js"></script>
<script type="text/javascript" src="${commonurl}moment.js"></script>
<script type="text/javascript" class="init">
	

$(document).ready(function()
		{
	$('#venditeTable').DataTable
	
	(
			{
				'sAjaxSource'   :  "<c:out value="${venditeservice}"></c:out>",
				'sAjaxDataProp' : "",
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
							     return accounting.formatMoney(a, "&euro;", 2, ".", ",");
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
							     return moment(a).format("YYYY-MM-DD HH:mm:ss");
						   }
						 
						}
						
					]
					
			}
			
	);
} );
</script>
</head>
<body>
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