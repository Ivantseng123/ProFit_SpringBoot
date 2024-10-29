$(document).ready(() => {

    $('#serviceApplicationCenter').click(function () {
  
      $('.col-lg-8').eq(0).empty();
  
      // 拿到當前用戶的所有專業
  
  
      // 拿到當前用戶的 所有委託 並渲染, 用caseownerId查
      // 可以 查看 編輯 刪除
      getServiceApplicationsAndRender();

      // 拿到當前用戶的 所有委託申請 並渲染, 用freelancerId查
      // 可以 同意申請(更改狀態), 拒絕(更改狀態)
      getServiceApplicationsAndRender();
  
    })
  
  
  })