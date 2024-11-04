document.addEventListener("DOMContentLoaded", function () {

    const eventCategorySelect = document.getElementById('eventCategory');
    const eventMajorSelect = document.getElementById('eventMajor');
    const saveBtn = document.getElementById("saveBtn");
    const cancelBtn = document.getElementById("cancelBtn");

    //取得使用者資料
    axios.get("/ProFit/events/get/user")
        .then(function(response) {
            if (response.data !== "") {
                console.log(response.data);
                document.getElementById("hostId").value = response.data.userId;
            } else {
                window.location.href = "/ProFit/user/profile";
            }
        })
        .catch(function (error) {
            console.error('There was an error!', error);
        });

    //填入選項
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

    fetchMajors().then((majors) => {
        if (majors) {
            Object.keys(majors).forEach(category => {
                if (category !== 'default') {
                    const optgroup = document.createElement('optgroup');
                    optgroup.label = majors[category][0];
            
                    majors[category].forEach(major => {
                        if (major.majorId !== undefined) {
                            const option = document.createElement('option');
                            option.value = major.majorId;
                            option.text = major.majorName;
                            if (major.majorId == eventMajorSelect.dataset.value) {
                                option.selected = true;
                            }
                            optgroup.appendChild(option);
                        }
                    });
                    eventMajorSelect.appendChild(optgroup);
                }
            });
        }
    });

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
        const eventData = {
            eventId: 'new',
            eventName: document.getElementById("eventName").value,
            isEventActive: 2,
            eventCategory: document.getElementById("eventCategory").value,
            eventMajorId: document.getElementById("eventMajor").value,
            eventStartDate: document.getElementById("eventStartDate").value,
            eventEndDate: document.getElementById("eventEndDate").value,
            eventPartStartDate: document.getElementById("eventPartStartDate").value,
            eventPartEndDate: document.getElementById("eventPartEndDate").value,
            eventAmount: document.getElementById("eventAmount").value,
            eventLocation: document.getElementById("eventLocation").value,
            eventParticipantMaximum: document.getElementById("eventParticipantMaximum").value,
            eventDescription: document.getElementById("eventDescription").value,
            // eventNote: document.getElementById("eventNote").value
            hostId: document.getElementById("hostId").value
        };


        console.log(eventData);

        axios.post('/ProFit/f/events/save', eventData)
            .then(function (response) {
                window.location.href = response.data;
            })
            .catch(function (error) {
                console.error('There was an error!', error);
            });
    }

    // function submitForm() {
    //     //使用formdata封裝資料
    //     let formData = new FormData();

    //     //文件
    //     const file = document.getElementById("eventFile");
    //     if (file) {
    //         formData.append("eventFile", file.files[0]);
    //     } else {
    //         formData.append("eventNote", document.getElementById("eventNote").value);
    //     }

    //     formData.append('eventId', document.getElementById("eventId").value || 'new');
    //     formData.append('eventName', document.getElementById("eventName").value);
    //     formData.append('isEventActive', document.getElementById("isEventActive").value);
    //     formData.append('eventCategory', document.getElementById("eventCategory").value);
    //     formData.append('eventMajorId', document.getElementById("eventMajor").value);
    //     formData.append('eventPublishDate', document.getElementById("eventPublishDate").value);
    //     formData.append('eventStartDate', document.getElementById("eventStartDate").value);
    //     formData.append('eventEndDate', document.getElementById("eventEndDate").value);
    //     formData.append('eventPartStartDate', document.getElementById("eventPartStartDate").value);
    //     formData.append('eventPartEndDate', document.getElementById("eventPartEndDate").value);
    //     formData.append('eventAmount', document.getElementById("eventAmount").value);
    //     formData.append('eventLocation', document.getElementById("eventLocation").value);
    //     formData.append('eventParticipantMaximum', document.getElementById("eventParticipantMaximum").value);
    //     formData.append('eventDescription', document.getElementById("eventDescription").value);

    //     console.log(formData);

    //     axios.post('/ProFit/events/save', formData)
    //         .then(function (response) {
    //             // window.location.href = response.data;
    //         })
    //         .catch(function (error) {
    //             console.error('There was an error!', error);
    //         });
    // }

});
