document.addEventListener("DOMContentLoaded", function () {

    const currentURL = window.location.pathname;
    const titleElement = document.getElementById('eventFormTitle');
    const isEventActiveSelect = document.getElementById('isEventActive');
    const eventCategorySelect = document.getElementById('eventCategory');
    const eventMajorSelect = document.getElementById('eventMajor');
    const inputs = document.querySelectorAll('#eventForm input, #eventForm select, #eventForm textarea');
    const saveBtn = document.getElementById("saveBtn");
    const cancelBtn = document.getElementById("cancelBtn");

    //填入選項
    Object.keys(statusMapping).forEach(key => {
        if (key !== 'default') {
            const option = document.createElement('option');
            option.value = key;
            option.text = statusMapping[key];
            //初始選項
            if (key === isEventActiveSelect.dataset.value) {
                option.selected = true;
            }
            isEventActiveSelect.appendChild(option);
        }
    });

    Object.keys(categoryMapping).forEach(key => {
        if (key !== 'default') {
            const option = document.createElement('option');
            option.value = key;
            option.text = categoryMapping[key];
            //初始選項
            if (key === eventCategorySelect.dataset.value) {
                option.selected = true;
            }
            eventCategorySelect.appendChild(option);
        }
    });

    Object.keys(majorMapping).forEach(key => {
        if (key !== 'default') {
            const option = document.createElement('option');
            option.value = key;
            option.text = majorMapping[key];
            //初始選項
            if (key === eventMajorSelect.dataset.value) {
                option.selected = true;
            }
            eventMajorSelect.appendChild(option);
        }
    });

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

    //取消
    cancelBtn.addEventListener("click", () => {
        window.location.href = '/ProFit/events';
    });
    
    //送出表單
    function submitForm() {
        const eventData = {
            eventId: document.getElementById("eventId").value || 'new',
            eventName: document.getElementById("eventName").value,
            isEventActive: document.getElementById("isEventActive").value,
            eventCategory: document.getElementById("eventCategory").value,
            eventMajorId: document.getElementById("eventMajor").value,
            eventPublishDate: document.getElementById("eventPublishDate").value,
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

        console.log(eventData);

        axios.post('/ProFit/events/save', eventData)
            .then(function (response) {
                window.location.href = response.data;
            })
            .catch(function (error) {
                console.error('There was an error!', error);
            });
    }

});
