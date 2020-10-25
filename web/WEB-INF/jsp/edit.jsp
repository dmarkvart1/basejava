<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.storage.SqlStorage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Просмотр резюме: ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=70 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionEntry" items="<%=resume.getSections()%>">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, java.lang.String>"/>
            <dl>
                <dt>${sectionEntry.getKey().title}</dt>
                <dd><input type="text" name="${sectionEntry.getKey().name()}" size="30"
                           value="${SqlStorage.removeElement(sectionEntry.getValue())}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отмена</button>
    </form>
</section>
</java.lang.String>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
