<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body 
    {
  
    font-family: Arial, Helvetica, sans-serif;
    }
form 
{
margin: 0 auto; 
 width:  60%;
 height: 60%;
}

input[type=text], input[type=password]
 {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

button
 {
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
}

button:hover {
  opacity: 0.8;
}

.cancelbtn {
  width: auto;
  padding: 10px 18px;
  background-color: #f44336;
}
.leftLabel 
{
  text-align: left;
}
.imgcontainer {
  text-align: center;
  margin: 24px 0 12px 0;
}

img.avatar {
  width: 30%;
  border-radius: 50%;
}

.container {
  padding: 16px;
}

span.password {
  float: right;
  padding-top: 16px;
}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) 
{
  span.password {
     display: block;
     float: none;
  }
  .cancelbtn {
     width: 100%;
  }
}
</style>
</head>
<body>



<c:url var="resurl"   value="/resources/" />
<form action="login" method="post">
  <div class="imgcontainer">
  <h2>${message}</h2>
    <img src="${resurl}${avatar}" alt="Avatar" class="avatar">
  </div>


  
		
	<c:choose>
    <c:when test="${pageContext.request.userPrincipal == null}">
      <div class="container">
      	<c:if test="${param.error ne null}">
			<div style="color: red">${errormessage}</div>
		</c:if>
		
     <label class ="leftLabel" for="username"><b>${labeluser}</b></label>
    <input type="text" placeholder="Enter Username" id="username" name="username" required>

    <label class ="leftLabel" for="password"><b>${labelpassword}</b></label>
    <input type="password" placeholder="Enter Password" id="password" name="password" required>
        
    <button type="submit">${buttonsubmit}</button>
    <label class ="leftLabel">
      <input type="checkbox" checked="checked" name="remember-me">${labelrememberme}
    </label>
       
  </div>
    <div class="container" style="background-color:#f1f1f1">
    <button type="reset" class="cancelbtn">${cancel}</button>
      <BR>
         <span class="psw"><a href="${resurl}${apk}">${appname}</a></span>
    <!--  
    <span class="psw"><a href="${linkforgotpassword}">${labelforgotpassword}</a></span>
    -->
  </div>
    </c:when>    
    <c:otherwise>
      <sec:authorize access="isAuthenticated()"> 
      <sec:authentication var="user" property="principal.username" />
      <sec:authentication var="roles" property="principal.authorities" />
        <div class="container" style="background-color:#f1f1f1">
       <p>${user}</p>
       <c:forEach items="${roles}" var="role" varStatus="vs">
         <p>${role.authority}</p>
       </c:forEach>
         <c:url value="${linklogout}" var = "linklogouturl"></c:url>
          <c:url value="${linkclienti}" var = "linkclientiurl"></c:url>
           <c:url value="${linkprodotti}" var = "linkprodottiurl"></c:url>
         <span class="psw"><a href="${linklogouturl}">${labellogout}</a></span>
         <span class="psw"><a href="${linkclientiurl}">${labelclienti}</a></span>
         <span class="psw"><a href="${linkprodottiurl}">${labelprodotti}</a></span>
         <BR>
         <span class="psw"><a href="${resurl}${apk}">${appname}</a></span>
       </div>
      </sec:authorize>
    </c:otherwise>
</c:choose>



  <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
</form>

</body>
</html>
