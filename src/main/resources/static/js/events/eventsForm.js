document.addEventListener("DOMContentLoaded", function () {

    const currentURL = window.location.pathname;
    const titleElement = document.getElementById('event-form-title');
    const inputs = document.querySelectorAll('#eventForm input, #eventForm select, #eventForm textarea');
    const saveBtn = document.getElementById("saveBtn");

    //設定標題文字
    if (currentURL.includes('/edit')) {
        titleElement.textContent = '編輯活動';
    } else if (currentURL.includes('/new')) {
        titleElement.textContent = '新增活動';
    } else if (currentURL.includes('/view')) {
        titleElement.textContent = '檢視活動';
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

    function submitForm() {
        const eventData = {
            eventId: document.getElementById("eventId").value || 'new',
            eventName: document.getElementById("eventName").value,
            isEventActive: document.getElementById("isEventActive").value,
            eventMajorId: document.getElementById("eventMajor").value,
            eventStartDate: document.getElementById("eventStartDate").value,
            eventEndDate: document.getElementById("eventEndDate").value,
            eventPartStartDate: document.getElementById("eventPartStartDate").value,
            eventPartEndDate: document.getElementById("eventPartEndDate").value,
            eventAmount: document.getElementById("eventAmount").value,
            eventLocation: document.getElementById("eventLocation").value,
            eventParticipantMaximum: document.getElementById("eventParticipantMaximum").value,
            eventDescription: document.getElementById("eventDescription").value,
            eventNote: document.getElementById("eventNote").value
        };

        axios.post('/ProFit/events', eventData)
            .then(function (response) {
                window.location.href = response.data;
            })
            .catch(function (error) {
                console.error('There was an error!', error);
            });
    }

});
