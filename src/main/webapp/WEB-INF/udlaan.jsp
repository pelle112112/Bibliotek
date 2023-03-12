<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
              Udlån
    </jsp:attribute>

    <jsp:attribute name="footer">
            Footertekst
    </jsp:attribute>

    <jsp:body>

        <table  class="table table-striped">
            <thead>
            <tr>
                <th>Bog Id</th>
                <th>Låner Id</th>
                <th>Navn</th>
                <th>Adresse</th>
                <th>Postnr</th>
                <th>Bogtitel</th>
                <th>Udgivelsesaar</th>
                <th>Forfatter Id</th>
                <th>Forfatter navn</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="udlaan" items="${requestScope.udlaanliste}">
                <tr>
                    <td>${udlaan.bogId}</td>
                    <td>${udlaan.laanerId}</td>
                    <td>${udlaan.laanerNavn}</td>
                    <td>${udlaan.adresse}</td>
                    <td>${udlaan.postnr}</td>
                    <td>${udlaan.titel}</td>
                    <td>${udlaan.udgivelsesaar}</td>
                    <td>${udlaan.forfatter_id}</td>
                    <td>${udlaan.forfatterNavn}</td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

    </jsp:body>
</t:pagetemplate>