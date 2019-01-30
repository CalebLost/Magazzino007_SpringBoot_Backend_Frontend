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

.googlebutton {
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    padding: 5px 17px;
    border: 1px solid #4569e0;
    border-radius: 0px;
    background: #ffffff;
    background: -webkit-gradient(linear, left top, left bottom, from(#ffffff), to(#ffffff));
    background: -moz-linear-gradient(top, #ffffff, #ffffff);
    background: linear-gradient(to bottom, #ffffff, #ffffff);
    font: normal normal normal 20px arial;
    color: #000000;
    text-decoration: none;
}
.googlebutton:hover,
.googlebutton:focus {
    color: #000000;
    text-decoration: none;
}
.googlebutton:active {
    background: #999999;
    background: -webkit-gradient(linear, left top, left bottom, from(#999999), to(#ffffff));
    background: -moz-linear-gradient(top, #999999, #ffffff);
    background: linear-gradient(to bottom, #999999, #ffffff);
}
.googlebutton:before{
    content:  "\0000a0";
    display: inline-block;
    height: 24px;
    width: 24px;
    line-height: 24px;
    margin: 0 4px -6px -4px;
    position: relative;
    top: 0px;
    left: -4px;
    background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAACXBIWXMAAA7EAAAOxAGVKw4bAAAEPElEQVRIiZWVa2gcVRTH/+fOY7f7iJtNstY2NNEGjbaIUtSiSKgVJSli1FihxMRWSknb+GgpQVRQJFg/mA+NtmJTtNRqY2mSfjDRCkJFgo0N6kpLjNjGEjXZbDZxyWN3duYeP+xrstk09MDAmZk7/3PO795zhgAILGPRQwdhBH/1Cbe7FFLqbMRDVHxzyN/aZiz3LaUCyHyBwo3PVPLU5E5OJGpgWRWwTMEACASo6jRUtZ8czhNKeUWPv+2IkaMjAQh7gLSJyaYGnzV67X3Mz9WDWWVwUhQAM0AEMKdCEQBNG6ICX1PJl73f27QEALkoQLi+9m45ETrLplEOttVp9/OhICGp0L+r5PTXHfbnqi0awvW1lXJi/FuYZoBAC8WWEM4qaSPkvek75KDOVBBpecllBgcHYRiVbNNjZpDDOURO5wkoykVyOGIci63heLwa8dhWSKlC00eU1aWbio51XstFlK4A1h+X3mQjXomUOgMgUgxR6GtxPvHkIW/j7gX7BOCzcGNdK09H3hMlgeaijlMjudlnKkhceNBv/nnl6mzPKo+cVNJQJRUVP1vS2duzDJzrmgAAjl2pUwNhj7fhd2h3zCexuD2HU+LSVvYN+yoAkIxXMwPkiMP91DDUn8pMY3RdKxYeXwkAu47NrrQk9OUyVxUyPtrhGlPl6Acwh19bT5TZVjg2jv/ofeR4CHmYjkxwX8LCPcsFcKg8BGCdkLN/6cTSDzA4c9iV4aXLT65hTjdbft+U8H94LqYKpDJPS2eDZLEs9Ckjdj2fCLpkFqrwlhsmKRGS5Eu9A8O8PR+edArJnBZ0yyKfGTMepzCEuGU3QGoQxGACLAh0zN218flvDhTnQ+TSaY9Lp+rkheq0rykYSQoTmAFNwej2KkfqFCnuPraitVPswtsz9+NCzKt6tLHXAbyci6h7n6c/t6aGIzO3/T2F8vQABABFYABpDOxe3/WbLIu+OF2FgXgBiAhz5vzex7p31KbWLHk1fzKjTkTRnp6yRAwihtuBbgBCAIC+oS/cMvPw4TEzOTmYGcwspuLRLx7temHvG/1t9ioyuJ7r2+e7+l/kZNzkmuT+JhHpKgXvu1X8APu4fvX8u66BUHAwZqXmkc10RbvsUPSTBLroUPRY3DJWJmSiKiHNbcIq9LnG90DOrs0cgEABPX6q2XMOyPmjPf1Vc+W/s6HzCWkW4wZMQINnahsQ2QSvk9rO7vccyL7Lmuza0n55lTuwWRfaiK0x7E1iayjO+BYbiBYeh3vNmY/vLVNa7BgXnfUzW9qDdxau3eDV3Z8KkMmpDmfOdrqdICcRhkpW+Lc3PRBoeqtuhZkWX4QoN9jW3lcqw7HIzriVqLGkVWGylVmjCTWqCe0Xp6J3lhWs/vzo5nei+fDl/enb7gUAefTSadH/z88FLtVRajE7LbZCRU5f+OBD++fyrbf7/wMDBABx3cdRGwAAAABJRU5ErkJggg==") no-repeat left center transparent;
    background-size: 100% 100%;
}

</style>
</head>
<body>
<jsp:include page="parts/login-operations.jsp"></jsp:include>
   


<c:url var="resurl"   value="/resources/" />

<form action="login" method="post">
  <div class="imgcontainer">
  <h2>${titolo}</h2>
    <img src="${resurl}${avatar}" alt="Avatar" class="avatar">
  </div>


  
		
	<c:choose>
    <c:when test="${pageContext.request.userPrincipal == null}">
      <div class="container">
      	<c:if test="${param.error ne null}">
			<div style="color: red">${errormessage}</div>
		</c:if>
		
     <label class ="leftLabel" for="username"><b>${labeluser}</b></label>
    <input type="text" placeholder="${labelmissingusername}" id="username" name="username" required>

    <label class ="leftLabel" for="password"><b>${labelpassword}</b></label>
    <input type="password" placeholder="${labelmissingpassword}" id="password" name="password" required>
        
    <button type="submit">${buttonsubmit}</button>
    <label class ="leftLabel">
      <input type="checkbox" checked="checked" name="remember-me">${labelrememberme}
    </label>
       
  </div>
    <div class="container" style="background-color:#f1f1f1">
    <button type="reset" class="cancelbtn">${cancel}</button>
      <BR>
         <span class="psw"><img src="${resurl}android.png"/><a href="${resurl}${apk}">${appname}</a></span>
    <!--  
    <span class="psw"><a href="${linkforgotpassword}">${labelforgotpassword}</a></span>
    -->
  </div>
    <center><a href = "${googleLoginUrl}" class="googlebutton">${googleLoginText}</a></center>
    </c:when>    
    <c:otherwise>
      <sec:authorize access="isAuthenticated()"> 
      <sec:authentication var="roles" property="principal.authorities" />
        <div class="container" style="background-color:#f1f1f1">
       <c:forEach items="${roles}" var="role" varStatus="vs">
         <p>${role.authority}</p>
       </c:forEach>
       
         <span class="psw"><img src="${resurl}android.png"/><a href="${resurl}${apk}">${appname}</a></span>
       </div>
      </sec:authorize>
    </c:otherwise>
</c:choose>



  <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
</form>

</body>
</html>
