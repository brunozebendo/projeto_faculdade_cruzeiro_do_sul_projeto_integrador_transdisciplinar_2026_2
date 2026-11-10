<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="base" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head><meta charset="UTF-8"><title>${empty item ? 'Novo item' : 'Editar item'}</title></head>
<body>
<h1>${empty item ? 'Novo item' : 'Editar item'}</h1>

<form action="${base}/itens" method="post">
    <input type="hidden" name="id" value="${item.id}">

    <p>Tipo:<br>
        <select name="tipo" required>
            <option value="LIVRO" ${item.tipo == 'LIVRO' ? 'selected' : ''}>Livro</option>
            <option value="FILME" ${item.tipo == 'FILME' ? 'selected' : ''}>Filme</option>
            <option value="SERIE" ${item.tipo == 'SERIE' ? 'selected' : ''}>Série</option>
        </select></p>

    <p>Título:<br>
        <input type="text" name="titulo" maxlength="200" required value="<c:out value='${item.titulo}'/>"></p>

    <p>Autor/Diretor:<br>
        <input type="text" name="autorDiretor" maxlength="150" required value="<c:out value='${item.autorDiretor}'/>"></p>

    <p>Ano:<br>
        <input type="number" name="ano" min="0" max="2100" value="${item.ano}"></p>

    <p>Gênero:<br>
        <input type="text" name="genero" maxlength="50" value="<c:out value='${item.genero}'/>"></p>

    <p>Sinopse:<br>
        <textarea name="sinopse" rows="5" cols="50"><c:out value="${item.sinopse}"/></textarea></p>

    <button type="submit">Salvar</button>
    <a href="${base}/itens">Cancelar</a>
</form>
</body>
</html>