<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="service.TicketService"%>
<%@ page import ="java.util.List"%>  <%-- Удаляем лишний import --%>
<%@ page import ="dto.TicketDto"%>  <%-- Удаляем лишний import --%>


<html>
<head>
    <meta charset="UTF-8">
    <title>Пример JSP с Русским Заголовком</title>
</head>
<body>
<h1> Купленные билеты:</h1>
<ul>
    <%
        // 1. Получаем flightId и обрабатываем ошибки
        Long flightId = null;
        String flightIdParam = request.getParameter("flightId");
        if (flightIdParam != null && !flightIdParam.trim().isEmpty()) { // Проверяем на null и пустую строку
            try {
                flightId = Long.valueOf(flightIdParam);
            } catch (NumberFormatException e) {
                // Обработка ошибки: неверный формат flightId
                out.println("<p>Неверный формат Flight ID.</p>");
                //  Можно также перенаправить на другую страницу об ошибке
            }
        } else {
            // Обработка ошибки: flightId не передан
            out.println("<p>Flight ID не указан.</p>");
        }

        // 2. Получаем билеты, если flightId валиден
        if (flightId != null) {
            List<TicketDto> tickets = TicketService.getINSTANCE().findAllByFlightId(flightId);

            // 3. Отображаем билеты, если они есть
            if (tickets != null && !tickets.isEmpty()) {
                for (TicketDto ticket : tickets) {
                    out.write(String.format("<li>%s</li>", ticket.getSeat_no())); // Исправляем опечатку и форматирование
                }
            } else {
                out.println("<p>No tickets found for flight ID: " + flightId + "</p>"); // Сообщение, если билетов нет
            }
        }
    %>
</ul>
</body>
</html>