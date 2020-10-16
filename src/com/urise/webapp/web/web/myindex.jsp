<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table>
    <tr>
        <c:forEach var="resumes" items="${resume}">
            <td>${resumes.uuid}</td>
            <td>${resumes.fullName}</td>
        </c:forEach>
    </tr>
</table>

</body>
</html>