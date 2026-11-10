<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="base" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head><meta charset="UTF-8"><title>Catálogo</title></head>
<body>
<h1>Catálogo de Livros, Séries e Filmes</h1>

<form action="${base}/itens" method="get">
    <input type="hidden" name="acao" value="buscar">
    <input type="text" name="termo" value="<c:out value='${termo}'/>" placeholder="Título ou autor/diretor">
    <button type="submit">Buscar</button>
    <a href="${base}/itens">Limpar</a>
</form>

<p><a href="${base}/itens?acao=novo">+ Novo item</a></p>

<table border="1" cellpadding="6">
    <tr><th>Tipo</th><th>Título</th><th>Autor/Diretor</th><th>Ano</th><th>Gênero</th><th>Ações</th></tr>
    <c:forEach var="i" items="${itens}">
        <tr>
            <td><c:out value="${i.tipo}"/></td>
            <td><c:out value="${i.titulo}"/></td>
            <td><c:out value="${i.autorDiretor}"/></td>
            <td><c:out value="${i.ano}"/></td>
            <td><c:out value="${i.genero}"/></td>
            <td>
                <a href="${base}/itens?acao=detalhes&id=${i.id}">Ver</a> |
                <a href="${base}/itens?acao=editar&id=${i.id}">Editar</a> |
                <form action="${base}/itens" method="post" style="display:inline"
                      onsubmit="return confirm('Excluir este item?');">
                    <input type="hidden" name="acao" value="excluir">
                    <input type="hidden" name="id" value="${i.id}">
                    <button type="submit">Excluir</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty itens}">
        <tr><td colspan="6">Nenhum item encontrado.</td></tr>
    </c:if>
</table>
</body>
</html>