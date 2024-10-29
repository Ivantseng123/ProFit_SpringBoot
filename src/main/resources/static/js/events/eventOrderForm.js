document.addEventListener("DOMContentLoaded", function () {

    const currentURL = window.location.pathname;
    const titleElement = document.getElementById('eventOrderFormTitle');
    const inputs = document.querySelectorAll('#eventOrderForm input, #eventOrderForm select, #eventOrderForm textarea');
    const saveBtn = document.getElementById("saveBtn");
    const cancelBtn = document.getElementById("cancelBtn");

    //設定標題文字
    if (currentURL.includes('/edit')) {
        titleElement.textContent = '編輯參加者';
    } else if (currentURL.includes('/new')) {
        titleElement.textContent = '新增參加者';
    } else if (currentURL.includes('/view')) {
        titleElement.textContent = '檢視參加者';
        saveBtn.style.display = 'none';
        inputs.forEach(input => {
            input.setAttribute('disabled', true);
        });
    }

    //保存
    saveBtn.addEventListener("click", () => {
        const confirmation = confirm("確定保存?");
        if (!confirmation) {
            console.log("保存已取消");
            return;
        }
        submitForm();
    });

    //取消
    cancelBtn.addEventListener("click", () => {
        const confirmation = confirm("確定回到上一頁?");
        if (!confirmation) {
            console.log("已取消");
            return;
        }
        window.history.back();
    });
    
    //送出表單
    function submitForm() {
        const eventOrderData = {
                eventOrderId: document.getElementById("eventOrderId").value || 'new',
                eventId: document.getElementById("eventId").value,
                participantId: document.getElementById("participantId").value,
                eventOrderAmount: document.getElementById("eventOrderAmount").value,
                isEventOrderActive: document.getElementById("isEventOrderActive").value,
                eventParticipantDate: document.getElementById("eventParticipantDate").value,
                eventParticipantNote: document.getElementById("eventParticipantNote").value
            };
        };

        console.log(eventOrderData);

        axios.post('/ProFit/events/order/save', eventOrderData)
            .then(function (response) {
                window.location.href = response.data;
            })
            .catch(function (error) {
                console.error('There was an error!', error);
            });
    }

});
