const paginationElement = document.getElementById('pagination');

// function createPagination(currentPage, totalPages, onPageChange) {
//   paginationElement.innerHTML = '';

//   const ul = document.createElement('ul');
//   ul.className = 'pagination';

//   // 上一頁按鈕
//   const prevLi = document.createElement('li');
//   prevLi.className = `page-item ${currentPage === 1 ? 'disabled' : ''}`;
//   const prevA = document.createElement('a');
//   prevA.className = 'page-link';
//   prevA.href = '#';
//   prevA.setAttribute('aria-label', 'Previous');
//   prevA.innerHTML = '<span aria-hidden="true">&laquo;</span>';
//   prevA.onclick = (e) => {
//     e.preventDefault();
//     if (currentPage > 1) onPageChange(currentPage - 1);
//   };
//   prevLi.appendChild(prevA);
//   ul.appendChild(prevLi);

//   // 頁碼按鈕
//   for (let i = 1; i <= totalPages; i++) {
//     const li = document.createElement('li');
//     li.className = `page-item ${i === currentPage ? 'active' : ''}`;
//     const a = document.createElement('a');
//     a.className = 'page-link';
//     a.href = '#';
//     a.textContent = i;
//     a.onclick = (e) => {
//       e.preventDefault();
//       onPageChange(i);
//     };
//     li.appendChild(a);
//     ul.appendChild(li);
//   }

//   // 下一頁按鈕
//   const nextLi = document.createElement('li');
//   nextLi.className = `page-item ${currentPage === totalPages ? 'disabled' : ''}`;
//   const nextA = document.createElement('a');
//   nextA.className = 'page-link';
//   nextA.href = '#';
//   nextA.setAttribute('aria-label', 'Next');
//   nextA.innerHTML = '<span aria-hidden="true">&raquo;</span>';
//   nextA.onclick = (e) => {
//     e.preventDefault();
//     if (currentPage < totalPages) onPageChange(currentPage + 1);
//   };
//   nextLi.appendChild(nextA);
//   ul.appendChild(nextLi);

//   paginationElement.appendChild(ul);
// }