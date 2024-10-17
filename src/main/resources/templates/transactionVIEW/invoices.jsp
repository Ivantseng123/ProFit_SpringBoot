<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>發票管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/model/model.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- 包含header和sidebar -->
    <jsp:include page="../model/header&sidebar.jsp" />
    <main>
        <div class="course-header">
            <h2>發票管理</h2>
        </div>

        <!-- 查詢表單 -->
        <div class="dashboard-header">
            <form method="get" action="${pageContext.request.contextPath}/invoices/search">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="invoice_number">發票號碼:</label>
                        <input type="text" id="invoice_number" name="invoice_number" class="form-control">
                    </div>
                    <div class="col-md-6">
                        <label for="invoice_status">發票狀態:</label>
                        <select id="invoice_status" name="invoice_status" class="form-select">
                            <option value="">全部</option>
                            <option value="open">開立</option>
                            <option value="canceled">取消</option>
                        </select>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="id_type">選擇ID類型:</label>
                        <select id="id_type" name="id_type" class="form-select" onchange="toggleIdInputFields()">
                            <option value="">選擇ID類型</option>
                            <option value="transaction_id">交易ID</option>
                            <option value="job_order_id">職缺訂單ID</option>
                            <option value="course_order_id">課程訂單ID</option>
                            <option value="event_order_id">活動訂單ID</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label for="id_value" id="id_value_label">輸入ID:</label>
                        <input type="text" id="id_value" name="id_value" class="form-control">
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-12 text-center">
                        <input type="submit" value="查詢" class="btn btn-primary">
                    </div>
                </div>
            </form>
        </div>

        <!-- 新增發票按鈕 -->
        <div align="center" class="mb-3">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addInvoiceModal">新增發票</button>
        </div>

        <!-- 發票表格 -->
        <div class="table-container">
            <table id="invoicesTable" class="table table-bordered table-striped table-hover">
                <thead>
                    <tr>
                        <th>發票號碼</th>
                        <th>發票金額</th>
                        <th>發票日期</th>
                        <th>發票狀態</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="invoice" items="${invoices}">
                        <tr>
                            <td>${invoice.invoiceNumber}</td>
                            <td>${invoice.invoiceAmount}</td>
                            <td><fmt:formatDate value="${invoice.issuedDate}" pattern="yyyy-MM-dd" /></td>
                            <td>${invoice.invoiceStatus}</td>
                            <td>
                                <!-- 更新按鈕 -->
                                <button type="button" class="btn btn-primary" 
                                    data-bs-toggle="modal" 
                                    data-bs-target="#updateInvoiceModal" 
                                    onclick="populateUpdateForm('${invoice.invoiceNumber}', '${invoice.invoiceAmount}', '${invoice.invoiceStatus}', '${invoice.orderType}', '${invoice.orderId}')">
                                    更新
                                </button>

                                <!-- 刪除按鈕 -->
                                <form method="post" action="${pageContext.request.contextPath}/invoices/delete" style="display:inline;" onsubmit="return confirm('確定要刪除這張發票嗎？');">
                                    <input type="hidden" name="invoice_number" value="${invoice.invoiceNumber}">
                                    <button type="submit" class="btn btn-danger">刪除</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </main>

    <!-- 新增發票的模態框 -->
    <div class="modal fade" id="addInvoiceModal" tabindex="-1" aria-labelledby="addInvoiceModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form method="post" action="${pageContext.request.contextPath}/invoices/insert" id="addInvoiceForm" onsubmit="return confirm('確定要新增這張發票嗎？');">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addInvoiceModalLabel">新增發票</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- 發票號碼 -->
                        <label for="addInvoiceNumber">發票號碼:</label>
                        <input type="text" id="addInvoiceNumber" name="invoice_number" required class="form-control"><br>

                        <!-- 發票金額 -->
                        <label for="addInvoiceAmount">發票金額:</label>
                        <input type="text" id="addInvoiceAmount" name="invoice_amount" required class="form-control"><br>

                        <!-- 發票狀態 -->
                        <label for="addInvoiceStatus">發票狀態:</label>
                        <select id="addInvoiceStatus" name="invoice_status" required class="form-select">
                            <option value="open">開立</option>
                            <option value="canceled">取消</option>
                        </select><br>

                        <!-- 訂單類型 -->
                        <label for="addOrderType">訂單類型:</label>
                        <select id="addOrderType" name="order_type" class="form-select" onchange="toggleOrderInputFields()" required>
                            <option value="">選擇訂單類型</option>
                            <option value="transaction_id">交易ID</option>
                            <option value="job_order_id">職缺訂單ID</option>
                            <option value="course_order_id">課程訂單ID</option>
                            <option value="event_order_id">活動訂單ID</option>
                        </select><br>

                        <!-- 訂單ID -->
                        <label for="addOrderId" id="orderIdLabel">訂單ID:</label>
                        <input type="text" id="addOrderId" name="order_id" class="form-control" placeholder="輸入訂單ID" required><br>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                        <button type="submit" class="btn btn-primary">新增</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- 更新發票的模態框 -->
    <div class="modal fade" id="updateInvoiceModal" tabindex="-1" aria-labelledby="updateInvoiceModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form method="post" action="${pageContext.request.contextPath}/invoices/update" id="updateInvoiceForm" onsubmit="return confirm('確定要更新這張發票嗎？');">
                    <div class="modal-header">
                        <h5 class="modal-title" id="updateInvoiceModalLabel">更新發票</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- 發票號碼 (不可更改) -->
                        <input type="hidden" id="updateInvoiceNumber" name="invoice_number" class="form-control" readonly>

                        <!-- 發票金額 -->
                        <label for="updateInvoiceAmount">發票金額:</label>
                        <input type="text" id="updateInvoiceAmount" name="invoice_amount" required class="form-control"><br>

                        <!-- 發票狀態 -->
                        <label for="updateInvoiceStatus">發票狀態:</label>
                        <select id="updateInvoiceStatus" name="invoice_status" required class="form-select">
                            <option value="open">開立</option>
                            <option value="canceled">取消</option>
                        </select><br>

                        <!-- 訂單類型 (不可更改) -->
                        <label for="updateOrderType">訂單類型:</label>
                        <input type="text" id="updateOrderType" name="order_type" class="form-control" readonly><br>

                        <!-- 訂單ID (不可更改) -->
                        <label for="updateOrderId">訂單ID:</label>
                        <input type="text" id="updateOrderId" name="order_id" class="form-control" readonly><br>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                        <button type="submit" class="btn btn-primary">更新</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // 切換ID類型的輸入提示
        function toggleIdInputFields() {
            const idType = document.getElementById('id_type').value;
            const idValueLabel = document.getElementById('id_value_label');
            const idValueInput = document.getElementById('id_value');

            switch(idType) {
                case 'transaction_id':
                    idValueLabel.textContent = '輸入交易ID:';
                    idValueInput.placeholder = '輸入交易ID';
                    break;
                case 'job_order_id':
                    idValueLabel.textContent = '輸入職缺訂單ID:';
                    idValueInput.placeholder = '輸入職缺訂單ID';
                    break;
                case 'course_order_id':
                    idValueLabel.textContent = '輸入課程訂單ID:';
                    idValueInput.placeholder = '輸入課程訂單ID';
                    break;
                case 'event_order_id':
                    idValueLabel.textContent = '輸入活動訂單ID:';
                    idValueInput.placeholder = '輸入活動訂單ID';
                    break;
                default:
                    idValueLabel.textContent = '輸入ID:';
                    idValueInput.placeholder = '選擇ID類型後輸入';
            }
        }

        // 填充更新表單的數據
        function populateUpdateForm(invoiceNumber, invoiceAmount, invoiceStatus, orderType, orderId) {
            document.getElementById('updateInvoiceNumber').value = invoiceNumber;
            document.getElementById('updateInvoiceAmount').value = invoiceAmount;
            document.getElementById('updateInvoiceStatus').value = invoiceStatus;
            document.getElementById('updateOrderType').value = orderType;
            document.getElementById('updateOrderId').value = orderId;
        }
    </script>
</body>
</html>
