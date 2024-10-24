document.addEventListener("DOMContentLoaded", function () {

    const saveBtn = document.getElementById("saveBtn");
    const cancelBtn = document.getElementById("cancelBtn");

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
        const eventHostData = {
            eventId: document.getElementById("eventId").value,
            eventName: 'new',
            eventHostId: document.getElementById("eventHostId").value,
            eventHostName: 'new'
        };

        console.log(eventHostData);

        axios.post('/ProFit/events/host/save', eventHostData)
            .then(function (response) {
                window.history.back();
            })
            .catch(function (error) {
                console.error('There was an error!', error);
            });
    }

});
