// createService.js

document.addEventListener('DOMContentLoaded', function () {

    // 初始化頁面元素和事件
    initializeForm();

    // 載入使用者專業選項
    loadUserMajors();

    // 監聽圖片上傳預覽
    initializePicturePreview();

    // 一鍵填入按鈕(demo用)
    document.getElementById('generateTestDataBtn')?.addEventListener('click', generateTestData);

    console.log(serviceDTO);
    // 若有帶serviceDTO進入頁面
    if (serviceDTO != null) {
        // 填入資料並設定欄位readonly
        document.getElementById('serviceTitle').value = serviceDTO.serviceTitle;
        document.getElementById('serviceContent').value = serviceDTO.serviceContent;
        document.getElementById('servicePrice').value = serviceDTO.servicePrice;
        document.getElementById('serviceUnitName').value = serviceDTO.serviceUnitName;
        document.getElementById('serviceDuration').value = serviceDTO.serviceDuration;
        document.getElementById('serviceCreateDate').value = new Date(serviceDTO.serviceCreateDate).toISOString().split('T')[0]; // 格式化日期
        document.getElementById('serviceStatus').value = serviceDTO.serviceStatus;

        // 設置圖片預覽
        if (serviceDTO.servicePictureURL1) {
            document.getElementById('preview1').src = serviceDTO.servicePictureURL1;
            document.getElementById('preview1').hidden = false;
            document.getElementById('preview1').style.width = '200px';
        }
        if (serviceDTO.servicePictureURL2) {
            document.getElementById('preview2').src = serviceDTO.servicePictureURL2;
            document.getElementById('preview2').hidden = false;
            document.getElementById('preview2').style.width = '200px';
        }
        if (serviceDTO.servicePictureURL3) {
            document.getElementById('preview3').src = serviceDTO.servicePictureURL3;
            document.getElementById('preview3').hidden = false;
            document.getElementById('preview3').style.width = '200px';
        }

        // 設定欄位為 readonly
        // document.getElementById('serviceTitle').readOnly = true;
        // document.getElementById('serviceContent').readOnly = true;
        // document.getElementById('servicePrice').readOnly = true;
        // document.getElementById('serviceUnitName').readOnly = true;
        // document.getElementById('serviceDuration').readOnly = true;
        // document.getElementById('serviceStatus').disabled = true; // 使用 `disabled` 設置下拉選單不可修改

        // 隱藏或禁用提交按鈕
        const smBtn = document.getElementById('submitService');
        smBtn.innerText = '確認修改';
        smBtn.setAttribute('disabled', true);

        const sf = document.getElementById('serviceForm');
        sf.removeAttribute('id');
        sf.setAttribute('id', 'serviceEditForm');

        document.getElementById('servicePictureURL1').removeAttribute('required');

        document.getElementById('title').innerText = '編輯服務';

        document.getElementById('generateTestDataBtn').hidden = true;

    }


    // 監聽表單提交
    document.getElementById('serviceForm').addEventListener('submit', handleSubmit);

});

/**
* 初始化表單
*/
function initializeForm() {
    // 設置當前日期
    const now = new Date();
    const dateString = now.getFullYear() + '-' +
        (now.getMonth() + 1).toString().padStart(2, '0') + '-' +
        now.getDate().toString().padStart(2, '0');
    document.getElementById('serviceCreateDate').value = dateString;

    // 取消按鈕事件
    document.getElementById('cancelCreate').addEventListener('click', function () {
        if (confirm('確定要取消新增服務嗎？所有輸入的資料將會清除。')) {
            window.location.href = '/ProFit/user/profile';
        }
    });
}

/**
* 載入使用者專業選項
*/
function loadUserMajors() {
    fetch(`/ProFit/c/service/api/user/${currentUser.userId}`)
        .then(response => response.json())
        .then(data => {
            console.log(data.content);
            const select = document.getElementById('userMajor');
            select.innerHTML = '<option value="">請選擇專業</option>';

            data.content.forEach(item => {
                const option = document.createElement('option');
                option.value = item.major.majorId;
                option.setAttribute('data-user-id', item.user.userId);
                option.setAttribute('data-major-id', item.major.majorId);
                if ( serviceDTO!=null && serviceDTO.userMajor.major.majorId == item.major.majorId) {
                    option.setAttribute('selected', true);
                }

                option.textContent = `${item.major.majorName}`;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error loading user majors:', error);
            alert('加載會員-專業選項時出錯，請稍後再試。');
        });
}

/**
* 初始化圖片預覽功能
*/
function initializePicturePreview() {
    const fileInputs = document.querySelectorAll('.form-control-file');
    fileInputs.forEach(input => {
        input.addEventListener('change', previewPicture);
    });
}

/**
* 預覽圖片
*/
function previewPicture(event) {
    const fileInput = event.target;
    const parentElement = fileInput.parentElement;
    const previewImage = parentElement.querySelector('img');

    if (previewImage) {
        previewImage.hidden = false;

        if (fileInput.files && fileInput.files[0]) {
            const reader = new FileReader();
            reader.onload = (e) => {
                previewImage.src = e.target.result;
                previewImage.style.width = '200px';
            };
            reader.readAsDataURL(fileInput.files[0]);
        }
    }
}

/**
* 處理表單提交
*/
function handleSubmit(event) {
    event.preventDefault();

    // 獲取選中的專業選項value
    const selectedValue = document.querySelector('#userMajor').value;

    // 創建 FormData 物件
    const formData = new FormData();

    // 添加表單數據
    formData.append('serviceTitle', document.getElementById('serviceTitle').value);
    formData.append('serviceContent', document.getElementById('serviceContent').value);
    formData.append('servicePrice', document.getElementById('servicePrice').value);
    formData.append('serviceUnitName', document.getElementById('serviceUnitName').value);
    formData.append('serviceDuration', document.getElementById('serviceDuration').value);
    formData.append('serviceStatus', document.getElementById('serviceStatus').value);
    formData.append('userId', currentUser.userId);
    formData.append('majorId', selectedValue);

    // 添加圖片文件
    const pictureInputs = ['servicePictureURL1', 'servicePictureURL2', 'servicePictureURL3'];
    pictureInputs.forEach(inputId => {
        const fileInput = document.getElementById(inputId);
        if (fileInput.files[0]) {
            formData.append(inputId, fileInput.files[0]);
        }
    });

    // 發送請求
    fetch('/ProFit/c/service/api', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('服務創建失敗');
            }
            return response.json();
        })
        .then(serviceDTO => {
            alert('服務創建成功！');
            // console.log(serviceDTO);
            window.location.href = `/ProFit/c/service/edit/${serviceDTO.serviceId}`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('創建服務時發生錯誤: ' + error.message);
        });
}

/**
* 一鍵填入測試數據(demo用)
*/
function generateTestData() {
    document.getElementById('serviceTitle').value = 'java 網站建置' + Math.floor(Math.random() * 100);
    document.getElementById('servicePrice').value = Math.floor(Math.random() * 10000) + 1000;
    document.getElementById('serviceUnitName').value = '件';
    document.getElementById('serviceDuration').value = 30;
    document.getElementById('serviceContent').value = '本網站建置服務專為企業及個人客戶量身打造，提供完整的 Java 網站開發解決方案。我們的服務包括需求分析、系統設計、後端開發、前端整合及測試部署。網站採用 Java 技術，並結合 Spring Boot 框架，以確保穩定性及高效性。數據庫可選擇 SQL Server 或 MySQL，並使用 Hibernate 進行 ORM 處理，確保數據持久性與安全性。此外，透過 AJAX 提供即時數據交互，讓網站擁有更佳的使用者體驗。我們重視客戶需求，並提供量身定制的解決方案及持續技術支援，協助您的網站順利上線並穩定運營。';
}

/**
* 驗證表單數據
*/
function validateForm() {
    const requiredFields = {
        'serviceTitle': '服務名稱',
        'serviceContent': '服務內容',
        'servicePrice': '服務報價',
        'serviceUnitName': '單位',
        'serviceDuration': '執行時間'
    };

    for (const [fieldId, fieldName] of Object.entries(requiredFields)) {
        const value = document.getElementById(fieldId).value.trim();
        if (!value) {
            alert(`請填寫${fieldName}`);
            return false;
        }
    }

    const price = parseInt(document.getElementById('servicePrice').value);
    if (isNaN(price) || price <= 0) {
        alert('請填寫有效的服務報價');
        return false;
    }

    const duration = parseFloat(document.getElementById('serviceDuration').value);
    if (isNaN(duration) || duration <= 0) {
        alert('請填寫有效的執行時間');
        return false;
    }

    return true;
}