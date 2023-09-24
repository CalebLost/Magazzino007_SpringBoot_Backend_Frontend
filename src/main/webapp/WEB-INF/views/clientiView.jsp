<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>Clienti List - Spring Boot Web Application Example</title>
	<meta http-equiv="Content-Type" 
  content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ taglib prefix = "c"    uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn"   uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt"  uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "sec"  uri="http://www.springframework.org/security/tags" %>

<c:url var="tableurl"   value="/resources/DataTables/" />
<c:url var="modalsandbuttonsurl"   value="/resources/modalsandbuttons/" />
<c:url var="jqueryurl"  value="/resources/table/vendor/jquery/" />

<link rel="stylesheet" type="text/css" href="${tableurl}datatables.min.css"/>
<link rel="stylesheet" type="text/css" href="${modalsandbuttonsurl}modalsandbuttons.css"/>
<script type="text/javascript" language="javascript" src="${jqueryurl}jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${tableurl}datatables.min.js"></script>
<script type="text/javascript" class="init">

var slang =               '<c:out value="${clientiGridLabels[0]}"></c:out>';
var sleng =               '<c:out value="${clientiGridLabels[1]}"></c:out>';
var snorecordsfound =     '<c:out value="${clientiGridLabels[2]}"></c:out>';
var snorecordsavailable = '<c:out value="${clientiGridLabels[3]}"></c:out>';
var spager =              '<c:out value="${clientiGridLabels[4]}"></c:out>';
var sfilter =             '<c:out value="${clientiGridLabels[5]}"></c:out>';
var sprevious =           '<c:out value="${clientiGridLabels[6]}"></c:out>';
var snext =               '<c:out value="${clientiGridLabels[7]}"></c:out>';
$(document).ready(function() {
	$('#clientiGrid').DataTable({
		      "columnDefs": [
			    {
			        "targets": -1,
			        "className": 'dt-head-left'
			    }
			  ],
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
			} );
} );


</script>

	
</head>
<body>
<jsp:include page="parts/login-operations.jsp"></jsp:include>
<h1 align="center">${clientiTitle}</h1>
</br>
<table id="clientiGrid" class="display" style="width:100%">
        <thead><TR>
          <c:forEach items="${clientiHeaders}" var="clienteHeader">
          <TH>
             <c:out value="${clienteHeader}"></c:out>
          </TH>
          </c:forEach>
        </TR></thead>
        <tbody><TR><TD></TD><TD></TD><Td></Td><td></td><Td>
          
          
          <DIV class="buttonsTable"><DIV class="buttonsRow">
            <c:forEach items="${clientiCommands}" var="comando">
            <c:choose>
          <c:when test="${comando.type == 'post'}">
            <div class ="buttonsColumn">
          <sec:authorize access="hasRole('ADMIN')">
            <a href="#<c:out value="${comando.type}"></c:out>Modal<c:out value="${cliente.id}"></c:out>" class="hrefButtonBlue"><c:out value="${comando.nome}"></c:out></a>
             <form:errors path="*" />
             <form accept-charset="utf-8" id="cliente" action="<c:out value="${comandourl}"></c:out>" onSubmit="return true;" method="post" >
              <input type="hidden" name="method_" id="method_" value="<c:out value="${comando.type}"></c:out>"/>  
              <div id="<c:out value="${comando.type}"></c:out>Modal<c:out value="${cliente.id}"></c:out>" class="modalDialog">
          <div>
         <a href="#close" title="Close" class="close">X</a>
           <h2><c:out value="${comando.nome}"></c:out></h2>
           <TABLE>
            <c:forEach items="${clienteFields}" var="clienteField"  varStatus="cStatus">
            <TR><c:choose>
             
               <c:when test="${clienteField.name == 'nome'}"><TD><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><input type="text" name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.nome}"></c:out>" /></TD></c:when>
               <c:when test="${clienteField.name == 'telefono'}"><TD><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><input type="text" name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.telefono}"></c:out>" /></TD></c:when>
               <c:when test="${clienteField.name == 'indirizzo'}"><TD><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><input type="text" name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.indirizzo}"></c:out>" /></TD></c:when>
               </c:choose></TR>
           </c:forEach>
           </TABLE>
               <p><BUTTON type="submit" class="hrefButtonGreen"><c:out value="${comando.conferma}"></c:out></BUTTON><A HREF="#close" class="hrefButtonGreen"><c:out value="${comando.annulla}"></c:out></A></p>
           </div>
            </div>
           </form>
           </sec:authorize>
          </div>
         </c:when> 
         </c:choose>
            </c:forEach>
        </div> </div>
          </Td></TR>
  <c:forEach items="${clienti}" var="cliente"  varStatus="pStatus">
  <TR>
     <TD><c:out value="${cliente.id}"></c:out></TD>
     <TD><c:out value="${cliente.nome}"></c:out></TD>
     <TD><c:out value="${cliente.indirizzo}"></c:out></TD>
     <TD><c:out value="${cliente.telefono}"></c:out></TD>
     <TD><DIV class="buttonsTable"><DIV class="buttonsRow">
      <c:forEach items="${clientiCommands}" var="comando">
      <div class ="buttonsColumn">
      <c:url var="comandourl" value="${comando.azione}"></c:url>
      <c:choose>
      <c:when test="${comando.type == 'get'}">
       <sec:authorize access="hasRole('USER')">
        <A HREF="<c:out value="${comandourl}${cliente.id}"></c:out>" class="hrefButtonGreen"><c:out value="${comando.nome}"></c:out></A>
      </sec:authorize>
      </c:when>
      <c:when test="${comando.type == 'delete'}">
       <sec:authorize access="hasRole('ADMIN')">
        <a href="#<c:out value="${comando.type}"></c:out>Modal<c:out value="${cliente.id}"></c:out>" class="hrefButtonRed"><c:out value="${comando.nome}"></c:out></a>
         <form accept-charset="utf-8" id="cliente" action="<c:out value="${comandourl}${cliente.id}"></c:out>" onSubmit="return true;" method="post" >
          <input type="hidden" name="method_" id="method_" value="<c:out value="${comando.type}"></c:out>"/>  
          <div id="<c:out value="${comando.type}"></c:out>Modal<c:out value="${cliente.id}"></c:out>" class="modalDialog">
	    <div>
		 <a href="#close" title="Close" class="close">X</a>
		   <h2><c:out value="${comando.nome}"></c:out></h2>
		    <TABLE>
		    <c:forEach items="${clienteFields}" var="clienteField"  varStatus="cStatus">
		    <TR><c:choose>
		       <c:when test="${clienteField.name == 'id'}"><TD><input type="hidden"  name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.id}"></c:out>"/><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><label name="<c:out value="${clienteField.name}" ></c:out>"><c:out value="${cliente.id}"></c:out></label></TD></c:when>
		       <c:when test="${clienteField.name == 'nome'}"><TD><input type="hidden"  name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.nome}"></c:out>"/><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><label  name="<c:out value="${clienteField.name}" ></c:out>"><c:out value="${cliente.nome}"></c:out></label></TD></c:when>
		       <c:when test="${clienteField.name == 'telefono'}"><TD><input type="hidden"  name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.telefono}"></c:out>"/><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><label  name="<c:out value="${clienteField.name}" ></c:out>"><c:out value="${cliente.telefono}"></c:out></label></TD></c:when>
		       <c:when test="${clienteField.name == 'indirizzo'}"><TD><input type="hidden"  name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.indirizzo}"></c:out>"/><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><label  name="<c:out value="${clienteField.name}" ></c:out>"><c:out value="${cliente.indirizzo}"></c:out></label></TD></c:when>
		       </c:choose></TR>
		   </c:forEach>
		   </TABLE>
           <p><button type="submit" class="hrefButtonGreen"><c:out value="${comando.conferma}"></c:out></button><A HREF="#close" class="hrefButtonGreen"><c:out value="${comando.annulla}"></c:out></A></p>
	     </div>
        </div>
       </form>
        </sec:authorize>
      </c:when>
      <c:when test="${comando.type == 'put'}">
        <sec:authorize access="hasRole('ADMIN')">
       <a href="#<c:out value="${comando.type}"></c:out>Modal<c:out value="${cliente.id}"></c:out>" class="hrefButtonYellow"><c:out value="${comando.nome}"></c:out></a>
       <form accept-charset="utf-8" id="cliente" action="<c:out value="${comandourl}"></c:out>" onSubmit="return true;" method="post">
       <input type="hidden" name="method_" id="method_" value="<c:out value="${comando.type}"></c:out>"/>  
        <div id="<c:out value="${comando.type}"></c:out>Modal<c:out value="${cliente.id}"></c:out>" class="modalDialog">
	    <div>
		 <a href="#close" title="Close" class="close">X</a>
		   <h2><c:out value="${comando.nome}"></c:out></h2>
		   <TABLE>
		    <c:forEach items="${clienteFields}" var="clienteField"  varStatus="cStatus">
		    <TR><c:choose>
		       <c:when test="${clienteField.name == 'id'}"><input type="hidden" name="${clienteField.name}"  id="${clienteField.name}" value="${cliente.id}"/></c:when>
		       <c:when test="${clienteField.name == 'nome'}"><TD><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><input type="text" name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.nome}"></c:out>" /></TD></c:when>
		       <c:when test="${clienteField.name == 'telefono'}"><TD><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><input type="text" name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.telefono}"></c:out>" /></TD></c:when>
		       <c:when test="${clienteField.name == 'indirizzo'}"><TD><label><c:out value="${clienteField.value}" ></c:out></label></TD><TD><input type="text" name="<c:out value="${clienteField.name}" ></c:out>" id="<c:out value="${clienteField.name}" ></c:out>" value="<c:out value="${cliente.indirizzo}"></c:out>" /></TD></c:when>
		       </c:choose></TR>
		   </c:forEach>
		   </TABLE>
           <p><button type="submit" class="hrefButtonGreen"><c:out value="${comando.conferma}"></c:out></button><BUTTON type="reset" class="hrefButtonGreen">RESET</BUTTON><A HREF="#close" class="hrefButtonGreen"><c:out value="${comando.annulla}"></c:out></A></p>
	     </div>
        </div>
       </form>
        </sec:authorize>
      </c:when>
      </c:choose>
      </div>
      </c:forEach>
      </DIV>
      </DIV>
     </TD>
  </c:forEach>
   <tfoot>
            <tr>
              <c:forEach items="${clientiHeaders}" var="clienteHeader">
                 <TH>
                  <c:out value="${clienteHeader}"></c:out>
                  </TH>
               </c:forEach>
            </tr>
        </tfoot>
 </tbody>
</table>
</body>
</html>