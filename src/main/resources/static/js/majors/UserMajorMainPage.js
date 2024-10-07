// 全局變量
let currentSort = {
  sortBy: 'id',
  ascending: true
};

// 當文檔加載完成時執行
document.addEventListener('DOMContentLoaded', () => {
  // 初始化頁面
  fetchData(1);

  // 綁定搜索按鈕事件
  document.querySelector('button:contains("送出查詢")').addEventListener('click', handleSearch);

  // 綁定新增按鈕事件
  document.querySelector('button:contains("新增會員-專業關聯")').addEventListener('click', () => {
    $('#staticBackdrop-insert').modal('show');
  });

  // 綁定新增表單提交事件
  document.querySelector('#staticBackdrop-insert .modal-footer button:contains("送出")').addEventListener('click', handleAdd);
});

// 獲取數據並更新頁面
function fetchData(page, userId = null, majorId = null) {
  const size = 10; // 每頁顯示的數量
  let url = `/Profit/userMajors/api/list?page=${page - 1}&size=${size}&sortBy=${currentSort.sortBy}&ascending=${currentSort.ascending}`;

  if (userId) url += `&userId=${userId}`;
  if (majorId) url += `&majorId=${majorId}`;

  fetch(url)
    .then(response => response.json())
    .then(data => {
      updateContent(data.content);
      createPagination(data.pageNo + 1, data.totalPages, page => fetchData(page, userId, majorId));
    })
    .catch(error => console.error('Error:', error));
}

// 更新內容區域
function updateContent(content) {
  const contentElement = document.getElementById('search-results');
  contentElement.innerHTML = `
        <table class="table">
            <thead>
                <tr>
                    <th>使用者ID</th>
                    <th>使用者名稱</th>
                    <th>專業ID</th>
                    <th>專業名稱</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                ${content.map(item => `
                    <tr>
                        <td>${item.userId}</td>
                        <td>${item.user.userName}</td>
                        <td>${item.majorId}</td>
                        <td>${item.major.majorName}</td>
                        <td>
                            <button onclick="deleteUserMajor(${item.userId}, ${item.majorId})">刪除</button>
                        </td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

// 處理搜索
function handleSearch() {
  const userId = document.querySelector('input[placeholder="使用者ID"]').value;
  const majorId = document.querySelector('input[placeholder="專業ID"]').value;
  fetchData(1, userId, majorId);
}

// 處理新增
function handleAdd() {
  const userId = document.querySelector('#staticBackdrop-insert input[placeholder="使用者"]').value;
  const majorId = document.querySelector('#staticBackdrop-insert input[placeholder="專業"]').value;

  fetch(`/Profit/userMajors/api/${userId}/${majorId}`, {
    method: 'POST',
  })
    .then(response => response.json())
    .then(data => {
      alert('新增成功');
      $('#staticBackdrop-insert').modal('hide');
      fetchData(1);
    })
    .catch(error => console.error('Error:', error));
}

// 刪除使用者-專業關聯
function deleteUserMajor(userId, majorId) {
  if (confirm('確定要刪除這個關聯嗎？')) {
    fetch(`/Profit/userMajors/api/${userId}/${majorId}`, {
      method: 'DELETE',
    })
      .then(response => {
        if (response.ok) {
          alert('刪除成功');
          fetchData(1);
        } else {
          alert('刪除失敗');
        }
      })
      .catch(error => console.error('Error:', error));
  }
}

// 分頁功能（使用之前提供的 createPagination 函數）
function createPagination(currentPage, totalPages, onPageChange) {
  paginationElement.innerHTML = '';

  const ul = document.createElement('ul');
  ul.className = 'pagination';

  // 上一頁按鈕
  const prevLi = document.createElement('li');
  prevLi.className = `page-item ${currentPage === 1 ? 'disabled' : ''}`;
  const prevA = document.createElement('a');
  prevA.className = 'page-link';
  prevA.href = '#';
  prevA.setAttribute('aria-label', 'Previous');
  prevA.innerHTML = '<span aria-hidden="true">&laquo;</span>';
  prevA.onclick = (e) => {
    e.preventDefault();
    if (currentPage > 1) onPageChange(currentPage - 1);
  };
  prevLi.appendChild(prevA);
  ul.appendChild(prevLi);

  // 頁碼按鈕
  for (let i = 1; i <= totalPages; i++) {
    const li = document.createElement('li');
    li.className = `page-item ${i === currentPage ? 'active' : ''}`;
    const a = document.createElement('a');
    a.className = 'page-link';
    a.href = '#';
    a.textContent = i;
    a.onclick = (e) => {
      e.preventDefault();
      onPageChange(i);
    };
    li.appendChild(a);
    ul.appendChild(li);
  }

  // 下一頁按鈕
  const nextLi = document.createElement('li');
  nextLi.className = `page-item ${currentPage === totalPages ? 'disabled' : ''}`;
  const nextA = document.createElement('a');
  nextA.className = 'page-link';
  nextA.href = '#';
  nextA.setAttribute('aria-label', 'Next');
  nextA.innerHTML = '<span aria-hidden="true">&raquo;</span>';
  nextA.onclick = (e) => {
    e.preventDefault();
    if (currentPage < totalPages) onPageChange(currentPage + 1);
  };
  nextLi.appendChild(nextA);
  ul.appendChild(nextLi);

  paginationElement.appendChild(ul);
}