document.getElementById("saveBtn").addEventListener("click", submitForm);

function submitForm() {
    const eventData = {
        eventId: document.getElementById("eventId").value || 'new',
        eventName: document.getElementById("eventName").value,
        isEventActive: document.getElementById("isEventActive").checked ? 1 : 0,
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

    console.log(eventData);
    axios.post('/ProFit/events', eventData)
        .then(function (response) {
            window.location.href = response.data;
        })
        .catch(function (error) {
            console.error('There was an error!', error);
        });
}