<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="base" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head><meta charset="UTF-8"><title>Detalhes</title></head>
<body>
<c:choose>
    <c:when test="${empty item}">
        <p>Item não encontrado.</p>
    </c:when>
    <c:otherwise>
        <h1><c:out value="${item.titulo}"/></h1>
        <p><b>Tipo:</b> <c:out value="${item.tipo}"/></p>
        <p><b>Autor/Diretor:</b> <c:out value="${item.autorDiretor}"/></p>
        <p><b>Ano:</b> <c:out value="${item.ano}"/></p>
        <p><b>Gênero:</b> <c:out value="${item.genero}"/></p>
        <p><b>Sinopse:</b><br><c:out value="${item.sinopse}"/></p>
        <a href="${base}/itens?acao=editar&id=${item.id}">Editar</a> |
    </c:otherwise>
</c:choose>
<a href="${base}/itens">Voltar à lista</a>
</body>
</html>