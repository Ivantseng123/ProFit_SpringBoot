document.addEventListener('DOMContentLoaded', function () {
  const submitBtn = document.getElementById('submitBtn');
  submitBtn.addEventListener('click', function (event) {
    event.preventDefault(); // 防止表單默認提交

    // 獲取必要的表單數據
    let serviceApplicationId = document.querySelector('[data-service-application-id]').getAttribute('data-service-application-id');
    let invoiceType = document.querySelector('input[name="invoiceType"]:checked')?.value;
    let taxID = document.getElementById('courseOrderTaxID').value || null;
    let orderRemark = document.getElementById('orderRemark').value;
    let serviceOrderAmount = parseInt(document.getElementById('subtotal').innerText, 10);
    let caseOwnerId = document.getElementById("studentId").getAttribute("data-caseownerid");
    console.log(caseOwnerId);

    if (!invoiceType) {
      alert("請選擇發票類型");
      return;
    }



    // 構建發送的數據
    let data = {
      serviceApplicationId: serviceApplicationId,
      serviceOrderPayById: caseOwnerId,
      invoiceType: invoiceType,
      taxID: taxID,
      orderRemark: orderRemark,
      paymentMethod: "綠界", // 新增付款方式
      serviceOrderAmount: serviceOrderAmount, // 設定訂單金額
      status: 'Pending' // 初始化狀態
    };

    // 發送 AJAX 請求
    fetch(`/ProFit/c/serviceApplication/api/order`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('訂單創建失敗');
        }
      })
      .then(success => {
        if (success) {
          alert("訂單創建成功");
          // 重定向到其他頁面或執行成功後的操作
          window.location.href = "/ProFit/allOrder";
        } else {
          alert("訂單創建失敗，請重試");
        }
      })
      .catch(error => {
        console.error('發生錯誤:', error);
        alert('發生錯誤，請稍後再試');
      });
  });

  // 切換顯示統編輸入框
  document.getElementById('businessInvoice').addEventListener('change', function () {
    document.getElementById('businessInputField').style.display = 'block';
  });
  document.getElementById('personalInvoice').addEventListener('change', function () {
    document.getElementById('businessInputField').style.display = 'none';
  });
});