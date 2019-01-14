<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>Vendite List - Spring Boot Web Application Example</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
 .prodottiGrid table 
 { 
 border-collapse: collapse; 
 text-align: left; 
 width: 100%; 
 } 
 .prodottiGrid
  {
  font: normal 12px/150% Arial, Helvetica, sans-serif; 
  background: #fff; 
  overflow: hidden;
   border: 1px solid #006699;
    -webkit-border-radius: 3px;
     -moz-border-radius: 3px; 
     border-radius: 3px; 
     }
 .prodottiGrid table td, 
 .prodottiGrid table th { padding: 3px 10px; }
 .prodottiGrid table thead th {background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #006699), color-stop(1, #00557F) );background:-moz-linear-gradient( center top, #006699 5%, #00557F 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#006699', endColorstr='#00557F');background-color:#006699; color:#FFFFFF; font-size: 15px; font-weight: bold; border-left: 1px solid #0070A8; } 
 .prodottiGrid table thead th:first-child { border: none; }
 .prodottiGrid table tbody td { color: #00496B; border-left: 1px solid #E1EEF4;font-size: 12px;font-weight: normal; }
 .prodottiGrid table tbody .alt td { background: #E1EEF4; color: #00496B; }
 .prodottiGrid table tbody td:first-child { border-left: none; }
 .prodottiGrid table tbody tr:last-child td { border-bottom: none; }
 .prodottiGrid table tfoot tr:first-child {background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #006699), color-stop(1, #00557F) );background:-moz-linear-gradient( center top, #006699 5%, #00557F 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#006699', endColorstr='#00557F');background-color:#006699; color:#FFFFFF; font-size: 15px; font-weight: bold; border-left: 1px solid #0070A8; } 
 .prodottiGrid table tfoot td div { border-top: 1px solid #006699;background: #E1EEF4;} 
 .prodottiGrid table tfoot td { padding: 0; font-size: 12px }
 .prodottiGrid table tfoot td div{ padding: 2px; }
 .prodottiGrid table tfoot td ul { margin: 0; padding:0; list-style: none; text-align: right; }
 .prodottiGrid table tfoot  li { display: inline; }
 .prodottiGrid table tfoot li a { text-decoration: none; display: inline-block;  padding: 2px 8px; margin: 1px;color: #FFFFFF;border: 1px solid #006699;-webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #006699), color-stop(1, #00557F) );background:-moz-linear-gradient( center top, #006699 5%, #00557F 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#006699', endColorstr='#00557F');background-color:#006699; }
 .prodottiGrid table tfoot ul li.active { background-color: #006699; color: #00496B; }
 .prodottiGrid table tfoot ul a:hover { text-decoration: none;border-color: #006699; color: #FFFFFF; background: none; background-color:#00557F;}
  div.dhtmlx_window_active, div.dhx_modal_cover_dv { position: fixed !important; }
</style>
<%@ taglib prefix = "c"   uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"  uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


</head>
<body>
<jsp:include page="parts/login-operations.jsp"></jsp:include>
<h1 align="center">${prodottiTitle}</h1>
</br>
<DIV class="prodottiGrid">
<table>
<thead><TR>
          <c:forEach items="${prodottiHeaders}" var="prodottoHeader">
          <TH>
             <c:out value="${prodottoHeader}"></c:out>
          </TH>
          </c:forEach>
        </TR>
</thead>
<tfoot>
<TR>
          <c:forEach items="${prodottiHeaders}" var="prodottoHeader">
          <TH>
             <c:out value="${prodottoHeader}"></c:out>
          </TH>
          </c:forEach>
</TR>
<tr><td colspan="<c:out value="${prodottiPager.colonne}"></c:out>"><div id="paging">
<ul><li>
<c:url value="${prodottiPager.pathPrecedente}" var="urlprecedente"></c:url>
<a href="<c:out value="${urlprecedente}"></c:out>"><span><c:out value="${prodottiPager.precedente}"></c:out></span></a></li>
  <c:forEach items="${prodottiPager.pagine}" var="page">
     <c:url value="${page.path}" var="urlpath"></c:url>
     <li <c:if test="${page.status != null}"> class="<c:out value="${page.status}"></c:out>" </c:if>><a href="<c:out value="${urlpath}"></c:out>" <c:if test="${page.status != null}"> class="<c:out value="${page.status}"></c:out>" </c:if> >
     <span><c:out value="${page.id}"></c:out></span></a></li>
  </c:forEach>
 <li>
 <c:url value="${prodottiPager.pathSucessivo}" var="urlsucessivo"></c:url>
 <a href="<c:out value="${urlsucessivo}"></c:out>"><span><c:out value="${prodottiPager.sucessivo}"></c:out></span></a></li></ul>
 </div>
 </tr>
</tfoot>
<tbody>
<c:forEach items="${prodotti}" var="prodotto">
  <TR>
     <TD><c:out value="${prodotto.id}"></c:out></TD>
     <TD><c:out value="${prodotto.nome}"></c:out></TD>
     <TD><c:out value="${prodotto.descrizione}"></c:out></TD>
     <TD><c:out value="${prodotto.quantita}"></c:out></TD>
     <TD><fmt:setLocale value="it_IT"/><fmt:formatNumber value="${prodotto.prezzo}" type="currency" currencySymbol="&euro;" /></TD>
  </c:forEach>
</tbody>
</table>
</DIV>
</body>
</html>


