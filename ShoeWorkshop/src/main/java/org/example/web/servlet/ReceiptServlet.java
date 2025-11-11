package org.example.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.entities.Client;
import org.example.entities.OrderStatus;
import org.example.entities.Receipt;
import org.example.service.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/receipts")
public class ReceiptServlet extends HttpServlet {

    private final ReceiptService receiptService = new ReceiptServiceImpl();
    private final OrderStatusService orderStatusService = new OrderStatusServiceImpl();
    private final ClientService clientService = new ClientServiceImpl();

    // ===== helpers =====
    private void ensureCsrf(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String token = (String) session.getAttribute("CSRF_TOKEN");
        if (token == null) {
            token = UUID.randomUUID().toString();
            session.setAttribute("CSRF_TOKEN", token);
        }
        req.setAttribute("csrfToken", token);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "getById"    -> getById(req, resp);
                case "byClient"   -> byClient(req, resp);
                default           -> listAll(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create"       -> create(req, resp);
                case "addItem"      -> addItem(req, resp);
                case "changeStatus" -> changeStatus(req, resp);
                case "finalize"     -> finalizeReceipt(req, resp);
                case "delete"       -> deleteReceipt(req, resp);
                default             -> resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    // ===== GET actions =====

    private void listAll(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ensureCsrf(req);
        List<Receipt> receipts = receiptService.getAll();
        req.setAttribute("receipts", receipts);
        req.getRequestDispatcher("/WEB-INF/views/receipts.jsp").forward(req, resp);
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ensureCsrf(req);

        int id = Integer.parseInt(req.getParameter("id"));

        Receipt r = receiptService.getById(id);
        if (r == null) {
            throw new IllegalArgumentException("Receipt not found: " + id);
        }

        // Прокидываем сам объект квитанции
        req.setAttribute("receipt", r);

        // Клиент квитанции: отдадим и объект, и строку ФИО (на выбор в JSP)
        Client client = clientService.getById(r.getClientId());
        req.setAttribute("receiptClient", client);
        req.setAttribute("clientName", (client != null ? client.getFullName() : null));

        // Список статусов для выпадающего списка
        req.setAttribute("statuses", orderStatusService.getAll());

        // Имя текущего статуса
        OrderStatus current = orderStatusService.getById(r.getStatusId());
        req.setAttribute("currentStatusName",
                current != null ? current.getStatusName() : "—");

        // Позиции и итог
        req.setAttribute("items", receiptService.getItemsByReceiptId(id));
        req.setAttribute("total", receiptService.calculateTotal(id));

        // Переход на страницу деталей
        req.getRequestDispatcher("/WEB-INF/views/receipt-detail.jsp").forward(req, resp);
    }


    private void byClient(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ensureCsrf(req);
        int clientId = Integer.parseInt(req.getParameter("clientId"));
        List<Receipt> receipts = receiptService.getByClientId(clientId);
        req.setAttribute("receipts", receipts);
        req.setAttribute("clientId", clientId);
        req.getRequestDispatcher("/WEB-INF/views/receipts.jsp").forward(req, resp);
    }

    // ===== POST actions =====

    private void create(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int clientId = Integer.parseInt(req.getParameter("clientId"));
        int statusId = Integer.parseInt(req.getParameter("statusId")); // напр. “Принят”

        Receipt r = new Receipt();
        r.setClientId(clientId);
        r.setStatusId(statusId);
        // дата выставится в репозитории как now(), если null — см. фиксы выше
        receiptService.add(r);

        resp.sendRedirect(req.getContextPath() + "/receipts");
    }

    private void addItem(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int receiptId = Integer.parseInt(req.getParameter("receiptId"));
        int favourId  = Integer.parseInt(req.getParameter("favourId"));
        int quantity  = Integer.parseInt(req.getParameter("quantity"));

        String discStr = req.getParameter("discount");
        Double discount = null;
        if (discStr != null && !discStr.isBlank()) {
            try { discount = Double.valueOf(discStr.replace(',', '.')); } catch (NumberFormatException ignored) { }
        }

        receiptService.addItem(receiptId, favourId, quantity, discount);
        resp.sendRedirect(req.getContextPath() + "/receipts?action=getById&id=" + receiptId);
    }

    private void changeStatus(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int receiptId = Integer.parseInt(req.getParameter("receiptId"));
        int newStatus = Integer.parseInt(req.getParameter("statusId"));
        receiptService.changeStatus(receiptId, newStatus);
        resp.sendRedirect(req.getContextPath() + "/receipts?action=getById&id=" + receiptId);
    }

    private void finalizeReceipt(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int receiptId = Integer.parseInt(req.getParameter("receiptId"));
        receiptService.finalizeReceipt(receiptId);
        resp.sendRedirect(req.getContextPath() + "/receipts?action=getById&id=" + receiptId);
    }

    private void deleteReceipt(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        receiptService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/receipts");
    }
}
