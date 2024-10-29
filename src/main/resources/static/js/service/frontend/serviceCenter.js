$(document).ready(() => {

  $('#serviceCenter').click(function () {

    $('.col-lg-8').eq(0).empty();

    // 拿到當前用戶的所有專業


    // 拿到當前用戶的所有服務並渲染
    getServicesAndRender();

  })


})


function getServicesAndRender() {
  $('.col-lg-8').eq(0).empty();
  // 拿到當前用戶的所有服務
  $.ajax({
    url: `/ProFit/c/service/api/userId`,
    dataType: 'JSON',
    type: 'GET',
    success: function (searchServicesByUserId) {

      console.log(searchServicesByUserId);

      // 寫入類別
      htmlMakerForServices(searchServicesByUserId.content);

    },
    error: function (jqXHR, textStatus, errorThrown) {
      // 處理錯誤
      console.error('查詢失敗:', textStatus, errorThrown);
      alert('查詢失敗，請重試。');
    }
  });
}



function htmlMakerForServices(searchServicesByUserId) {
  console.log(searchServicesByUserId)

  $('.col-lg-8').eq(0).append(`
        <div class="inner-content">
        <div class="card">
            <div class="card-body">
                <!-- Job List Header -->
                <div class="row border-bottom pb-3 mb-3">
                    <div class="col-md-12 d-flex  justify-content-between">
                        <div>
                          <h5>我的服務</h5>
                        </div>
                        <div>
                          <a target="_blank" href="/ProFit/c/service/add" class="btn btn-warning btn-sm">新增服務</a>
                        </div>
                    </div>
                </div>

                <!-- Job Item List -->
                <div class="orderListSpace"></div>
            </div>
        </div>
    </div>
    `)

  searchServicesByUserId.forEach(function (servicesDTO) {
    $('.orderListSpace').append(`
                <div class="row align-items-center border-bottom py-3 px-3">
                    <div class="col-md-2 p-0 d-flex align-items-center">
                        <img src="${servicesDTO.servicePictureURL1}" alt="Conzio logo" style="max-width: 120px; max-height:80px;">
                    </div>
                    <div class="col-md-3 text-center d-flex flex-column align-items-center justify-content-center">
                        <div>
                          <a href="/ProFit/c/service/${servicesDTO.serviceId}" target="_blank" class="mb-0">
                            <h5 class="fw-bold mb-0">${servicesDTO.serviceTitle}</h5>
                          </a>
                        </div>
                        <div>
                          <h4 class="badge rounded-pill bg-primary">${servicesDTO.userMajor.major.majorName}</h4>
                        </div>
                    </div>
                    <div class="col-md-3 text-center d-flex flex-column align-items-center justify-content-center">
                        <div>
                          <h6 class="mb-0">${servicesDTO.servicePrice} / ${servicesDTO.serviceUnitName}</h6>
                        </div>
                        <div>
                          <span class="mb-0">${formatDate(servicesDTO.serviceUpdateDate)}</span>
                        </div>
                    </div>
                    <div class="col-md-2 text-center d-flex align-items-center justify-content-center">
                        <span class="mb-0">狀態: ${getStatusText(servicesDTO.serviceStatus)}</span>
                    </div>
                    <div class="col-md-2 text-center d-flex flex-column align-items-center justify-content-center">
                        <a href="/ProFit/c/service/edit/${servicesDTO.serviceId}" target="_blank" class="btn btn-success btn-sm mb-2">修改服務</a>
                        <a href="#" onclick="deleteService(event, ${servicesDTO.serviceId})" class="btn btn-danger btn-sm">刪除服務</a>
                    </div>
                </div>
            `)
  })

}


function deleteService(event, serviceId) {
  event.preventDefault(); // 阻止默認的 a 標籤行為
  if (window.confirm("確定刪除該服務?")) {
    fetch(`/ProFit/c/service/api/${serviceId}`, {
      method: 'DELETE',
    })
      .then(response => {
        if (response.ok) {
          alert('服務已刪除');
          // 拿到當前用戶的所有服務並渲染
          getServicesAndRender();
        } else {
          alert('刪除失敗');
        }
      })
      .catch(error => console.error('Error:', error));
  }
}




function formatDate(data) {
  let date = new Date(data);
  let formattedDate = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
  return formattedDate;
}

// 狀態轉換成文字
function getStatusText(status) {
  switch (status) {
    case 0: return '審核中';
    case 1: return '啟用';
    case 2: return '已關閉';
    default: return '未知';
  }
}