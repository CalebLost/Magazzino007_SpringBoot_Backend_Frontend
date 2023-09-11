   <%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
   <c:url var="modalsandbuttonsurl"   value="/resources/modalsandbuttons/" />
   <link rel="stylesheet" type="text/css" href="${modalsandbuttonsurl}modalsandbuttons.css"/>

<sec:authorize access="isAuthenticated()"> 
  <c:url value="${linklogout}" var = "linklogouturl"></c:url>
  <c:set var="linklogOutByAuthType" value="${hosturl}${linklogouturl}"/>
<script>
	
 
  
  function checkGoogle()
  {
	  var g = false;

	  <sec:authorize access="hasRole('ROLE_GOOGLE_USER')">
      g = true;
      <c:set var="linklogOutByAuthType" value="${googlelogouturl}${hosturl}${linklogouturl}"/>
	  </sec:authorize>

	  return g;
  }
  
</script>
      <sec:authentication var="user" property="principal.username" />
        <div style="background-color:#f1f1f1">
        
         <c:url value="${linkclienti}" var = "linkclientiurl"></c:url>
         <c:url value="${linkvendite}" var = "linkvenditeurl"></c:url>
         <c:url value="${linkprodotti}" var = "linkprodottiurl"></c:url>
         <span>${user}</span>
         <span><a href="${linklogOutByAuthType}">${labellogout}</a></span>
         <span><a href="${linkclientiurl}" class="hrefButtonYellow">${labelclienti}</a></span>
         <span><a href="${linkvenditeurl}" class="hrefButtonGreen">${labelvendite}</a></span>
         <span><a href="${linkprodottiurl}" class="hrefButtonBlue">${labelprodotti}</a></span>
       </div>
      </sec:authorize>