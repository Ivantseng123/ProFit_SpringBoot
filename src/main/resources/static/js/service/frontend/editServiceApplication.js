$(document).ready(function () {

    console.log(currentUser);
    console.log(servicesDTO);
    console.log(serviceApplicationDTO);

    let freelancerHTML = ``;
    let caseownerId = currentUser.userId;


    //如果沒帶委託資料進來，則自動帶入 接案人 及 服務, (可以改變)
    if (servicesDTO != null) {
        console.log("有帶ServicesDTO進來");

        freelancerHTML = `<option value=${servicesDTO.userMajor.user.userId}>${servicesDTO.userMajor.user.userName}</option>`;

        document.getElementById('serviceSelect').innerHTML = `<option value=${servicesDTO.serviceId}>${servicesDTO.serviceTitle}</option>`;

        document.getElementById('to-chatroom').hidden = false;
        document.getElementById('to-chatroom').href = `/ProFit/c/chat/add?serviceId=${servicesDTO.serviceId}&freelancerId=${servicesDTO.userMajor.user.userId}`;
    }


    // 如果帶著委託資料進來, 自動填入資料, 且不能改變接案人及服務
    if (serviceApplicationDTO != null) {
        // console.log("有帶serviceApplicationDTO進來");

        servicesDTO = serviceApplicationDTO.service;

        // 表單按鈕變更
        document.getElementById('submit').innerText = '儲存變更';
        document.getElementById('to-chatroom').hidden = false;
        if (serviceApplicationDTO.status == 1) {
            document.getElementById('pageTitle').innerText = '委託洽談中';
            document.getElementById('to-chatroom').href = `/ProFit/c/chat/add?serviceId=${servicesDTO.serviceId}&freelancerId=${servicesDTO.userMajor.user.userId}`;
        } else if (serviceApplicationDTO.status == 2) {
            document.getElementById('pageTitle').innerText = '委託已接受(待案主付款成立)';
            document.getElementById('create-order-page').hidden = false;
            document.getElementById('submit').hidden = true;
        } else if (serviceApplicationDTO.status == 3) {
            document.getElementById('pageTitle').innerText = '委託已婉拒';
        } else if (serviceApplicationDTO.status == 4) {
            document.getElementById('pageTitle').innerText = '委託已關閉';
        } else if (serviceApplicationDTO.status == 5) {
            document.getElementById('pageTitle').innerText = '委託已成立';
        } else if (serviceApplicationDTO.status == 6) {
            document.getElementById('pageTitle').innerText = '委託已結案';
        }


        // 將數據填入表單
        // 填入接案人
        document.getElementById('freelancer').innerHTML = `<option selected value=${servicesDTO.userMajor.user.userId}>${servicesDTO.userMajor.user.userName}</option>`;
        // 填入合作名稱
        document.getElementById('serviceApplicationTitle').value = serviceApplicationDTO.serviceApplicationTitle;
        // 填入服務選項
        document.getElementById('serviceSelect').innerHTML = `<option selected value=${servicesDTO.serviceId}>${servicesDTO.serviceTitle}</option>`;

        // 填入合同資料
        document.getElementsByName('serviceApplicationSubitem')[0].value = serviceApplicationDTO.serviceApplicationSubitem;
        document.getElementsByName('serviceApplicationPrice')[0].value = serviceApplicationDTO.serviceApplicationPrice;
        document.getElementsByName('serviceApplicationAmount')[0].value = serviceApplicationDTO.serviceApplicationAmount;
        document.getElementsByName('serviceApplicationUnit')[0].value = serviceApplicationDTO.serviceApplicationUnit;
        document.getElementsByName('serviceApplicationContent')[0].value = serviceApplicationDTO.serviceApplicationContent;

        // 填入附件URL
        document.getElementsByName('appendixUrl')[0].href = serviceApplicationDTO.appendixUrl;
        document.getElementsByName('appendixUrl2')[0].innerText = serviceApplicationDTO.appendixUrl;

        // 填入合約里程碑
        document.getElementsByName('serviceApplicationMission')[0].value = serviceApplicationDTO.serviceApplicationMission;

        // 填入交付日期，並轉換為 input date 格式
        let serviceDoneDate = new Date(serviceApplicationDTO.serviceApplicationDoneDate).toISOString().split('T')[0];
        document.getElementById('serviceApplicationDate').value = serviceDoneDate;

        document.getElementsByName('serviceApplicationTotalPrice')[0].value = serviceApplicationDTO.serviceApplicationPrice * serviceApplicationDTO.serviceApplicationAmount;


        // 將欄位設置為只讀
        document.getElementById('freelancer').setAttribute('readonly', true);
        document.getElementById('serviceApplicationTitle').setAttribute('readonly', true);
        document.getElementById('serviceSelect').setAttribute('readonly', true);
    }

    

    // 如果是接案人進入頁面，則全都不能用，且只有接受跟婉拒按鈕
    if (serviceApplicationDTO!=null && currentUser.userId == serviceApplicationDTO.freelancerId) {
        console.log(serviceApplicationDTO)

        caseownerId = serviceApplicationDTO.caseownerId;
        // 填入案主
        document.getElementById('freelancer-top').innerText = "委託申請人";
        document.getElementById('serviceSelect-top').innerText = "合作的服務";
        document.getElementById('freelancer').innerHTML = `<option selected value=${serviceApplicationDTO.service.userMajor.user.userId}>${serviceApplicationDTO.caseowner.userName}</option>`;
        // 設置欄位只讀
        document.getElementsByName('serviceApplicationSubitem')[0].setAttribute('readonly', true);
        document.getElementsByName('serviceApplicationPrice')[0].setAttribute('readonly', true);
        document.getElementsByName('serviceApplicationAmount')[0].setAttribute('readonly', true);
        document.getElementsByName('serviceApplicationUnit')[0].setAttribute('readonly', true);
        document.getElementsByName('serviceApplicationContent')[0].setAttribute('readonly', true);
        document.getElementsByName('appendixFile')[0].setAttribute('hidden', true);
        document.getElementsByName('serviceApplicationMission')[0].setAttribute('readonly', true);
        document.getElementById('serviceApplicationDate').setAttribute('readonly', true);
        document.getElementsByName('serviceApplicationTotalPrice')[0].setAttribute('readonly', true);

        document.getElementById('freelancer').setAttribute('disabled', true);
        document.getElementById('serviceApplicationTitle').setAttribute('disabled', true);
        document.getElementById('serviceSelect').setAttribute('disabled', true);

        

        // 生成帶入的服務詳情
        displayServiceDetails(serviceApplicationDTO.service);

        // 更改按鈕選項及連結
        document.getElementById('submit').hidden = true
        document.getElementById('create-order-page').hidden = true;
        document.getElementById('to-chatroom').href = '/ProFit/c/chat';
        if (serviceApplicationDTO.status == 1) { //洽談中
            // 接受按鈕
            const acceptButton = document.getElementById('accept-button');
            acceptButton.hidden = false;
            // 婉拒按鈕
        } else if (serviceApplicationDTO.status == 2) { // 已接受
            
        } else if (serviceApplicationDTO.status == 3) { // 已婉拒
        } else if (serviceApplicationDTO.status == 4) { // 已關閉
        } else if (serviceApplicationDTO.status == 5) { // 已成立
        } else if (serviceApplicationDTO.status == 6) { // 已結案
        }


    } else {
        // 生成帶入的服務詳情
        displayServiceDetails(servicesDTO);

    }




    // 動態生成 要合作的接案人 選項

    // let freelancerId = servicesDTO.userMajor.user.userId;

    axios({
        method: 'get',
        url: `/ProFit/c/serviceApplication/api/userChatList/${caseownerId}`
    })
        .then(response => {
            // console.log(response.data);

            let freelancerSelect = document.getElementById('freelancer');  // 獲取 select 元素
            response.data.forEach(user => {
                freelancerHTML += `<option data- value=${user.userId}>${user.userName}</option>`
            });

            // 將動態生成的選項添加到 select 元素中
            freelancerSelect.innerHTML += freelancerHTML;
        })
        .catch(error => {
            console.error('Error fetching user list:', error);
        })

    // 當接案客改變時加載其服務
    document.getElementById('freelancer').addEventListener('change', function (e) {

        // 清空服務詳情
        document.getElementById('serviceDetails').innerHTML = '';

        const freelancerId = this.value; // 獲取選中的 freelancerId
        // console.log(freelancerId);
        showServices(freelancerId);
    });


})


let servicesData = []; // 全局變量，用來存儲服務列表

function displayServiceDetails() {

    axios({
        method: 'get',
        url: `/ProFit/c/service/api/${serviceId}`,
        data: {
            id: requestUrl
        }
    })
        .then(
            (response) => console.log(response)
        )
        .catch(
            (error) => console.log(error)
        )
}


// 選擇接案客後，渲染要合作的服務選項
function showServices(freelancerId) {
    // console.log(freelancerId);
    axios({
        method: 'get',
        url: `/ProFit/c/serviceApplication/api/ServiceList/${freelancerId}`,
    })
        .then(response => {
            // console.log(response.data.content);
            servicesData = response.data.content; // 存儲獲取的服務數據到全局變量
            let servicesArray = response.data.content;
            let serviceSelect = $('#serviceSelect');
            serviceSelect.empty();  // 清空選項
            let HTML = '<option >選擇服務</option>';
            servicesArray.forEach(service => {
                HTML += `<option value=${service.serviceId}>${service.serviceTitle}</option>`;
            });

            serviceSelect.append(HTML);
        })
        .catch(
            (error) => console.log(error)
        )
}

// 當服務選項改變時，顯示選擇的服務詳情
document.getElementById('serviceSelect').addEventListener('change', function () {
    const serviceId = this.value; // 獲取選中的服務 ID
    const selectedService = servicesData.find(service => service.serviceId == serviceId); // 查找對應的服務
    if (selectedService) {
        displayServiceDetails(selectedService); // 渲染服務詳情
    }
});


// 選擇要合作的服務選項後, 渲染服務詳情
function displayServiceDetails(service) {
    let serviceDetailsDiv = document.getElementById('serviceDetails');
    // 將服務詳情渲染到對應的區域
    let detailsHTML = `
        <a href="/ProFit/c/service/${service.serviceId}" target="_blank"><h4>服務標題: ${service.serviceTitle}</h4></a>
        <hr>
        <p><strong>價格:</strong> $${service.servicePrice} / ${service.serviceUnitName}</p>
        <p><strong>服務描述:</strong> ${service.serviceContent}</p>
    `;
    serviceDetailsDiv.innerHTML = detailsHTML;  // 將詳情插入到 div 中
}

// 填價錢算總價
document.getElementsByName('serviceApplicationPrice')[0].addEventListener('change', function () {
    document.getElementsByName('serviceApplicationTotalPrice')[0].value = document.getElementsByName('serviceApplicationPrice')[0].value * document.getElementsByName('serviceApplicationAmount')[0].value;
})
document.getElementsByName('serviceApplicationAmount')[0].addEventListener('change', function () {
    document.getElementsByName('serviceApplicationTotalPrice')[0].value = document.getElementsByName('serviceApplicationPrice')[0].value * document.getElementsByName('serviceApplicationAmount')[0].value;
})



// 表單提交
if (serviceApplicationDTO != null) {
    document.getElementById('serviceApplicationForm').addEventListener('submit', function (event) {
        event.preventDefault();

        //轉換date到localdatetime
        const date = document.getElementById('serviceApplicationDate').value;
        let localDateTime = `${date}T00:00`
        document.getElementById('localDateTimeInput').value = localDateTime;

        let form = document.getElementById('serviceApplicationForm');
        let formData = new FormData(form); // 創建FormData物件，將表單的所有資料包含在內
        console.log(formData);

        console.log(serviceApplicationDTO.serviceApplicationId);

        // 發送Ajax請求
        axios.put(`/ProFit/c/serviceApplication/api/${serviceApplicationDTO.serviceApplicationId}`,
            formData, {
            headers: {
                'Content-Type': 'multipart/form-data' // 確保正確的Content-Type
            }
        })
            .then(function (response) {
                console.log(response.data);
                alert("服務委託更新成功！");

                // 頁面跳轉,跳回相同頁面,但是帶著serviceApplicationDTO
                // 自動填入serviceApplicationDTO到欄位中
                // 可以編輯合約, 但是 freelancer、title、service 變為readonly
                // 跳轉回同一頁面，帶著 serviceApplicationId
                window.location.href = `/ProFit/c/serviceApplication/edit?serviceApplicationId=${response.data.serviceApplicationId}`;

            })
            .catch(function (error) {
                console.error('提交失敗:', error);
                alert("提交失敗，請稍後再試。");
            });

    })

} else {
    // 表單提交監聽器
    document.getElementById('serviceApplicationForm').addEventListener('submit', function (event) {
        event.preventDefault(); // 防止表單自動提交


        //轉換date到localdatetime
        const date = document.getElementById('serviceApplicationDate').value;
        let localDateTime = `${date}T00:00`
        document.getElementById('localDateTimeInput').value = localDateTime;

        let form = document.getElementById('serviceApplicationForm');
        let formData = new FormData(form); // 創建FormData物件，將表單的所有資料包含在內

        // 發送Ajax請求
        axios.post('/ProFit/c/serviceApplication/api', formData, {
            headers: {
                'Content-Type': 'multipart/form-data' // 確保正確的Content-Type
            }
        })
            .then(function (response) {
                console.log(response.data);
                alert("服務委託創建成功！");

                // 頁面跳轉,跳回相同頁面,但是帶著serviceApplicationDTO
                // 自動填入serviceApplicationDTO到欄位中
                // 可以編輯合約, 但是 freelancer、title、service 變為readonly
                // 跳轉回同一頁面，帶著 serviceApplicationId
                window.location.href = `/ProFit/c/serviceApplication/edit?serviceApplicationId=${response.data.serviceApplicationId}`;



            })
            .catch(function (error) {
                console.error('提交失敗:', error);
                alert("提交失敗，請稍後再試。");
            });
    });
}


