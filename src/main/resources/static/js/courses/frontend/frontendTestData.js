$('#insertCourseBtn').click(function(){

    $("#courseName").val("Java基礎入門");
    $("#courseCategory").val("100"); 
    $("#courseInformation").val(`Java基礎入門課程旨在帶領初學者深入了解Java的核心概念和實作技巧。

從基本語法、控制流程到面向對象設計，學生將學習到構建Java應用的必備知識，為後續進階學習和開發奠定堅實基礎。

課程內容循序漸進，適合零基礎學員或希望鞏固Java基礎的學員。`);
    $("#courseStartDate").val("2024-11-01T09:00");
    $("#courseEndDate").val("2024-12-31T17:00");
    $("#courseDescription").val(`Java基礎入門課程將帶領學員從零開始學習Java編程，涵蓋Java語言的基本概念和實作技巧，幫助學員掌握編程的核心要素。

課程首先介紹Java的基本語法，包括數據類型、變量、運算符和控制結構等，並深入講解如何在不同情境下正確運用。

隨後，課程將深入面向對象編程的核心觀念，如類別與物件、封裝、繼承與多態等，幫助學員建立良好的編程思維，並能夠應用於真實項目。

在此基礎上，我們將講解Java集合框架，學習如何有效率地存儲和操作數據。接著介紹例外處理機制，以便學員能夠編寫出更加健壯和容錯的代碼。

課程最後涵蓋基本的I/O操作，幫助學員了解文件讀寫的實作方法，並在此過程中逐步累積實踐經驗。

本課程設計為循序漸進的學習體系，每節課程結合範例與練習，確保學員能夠從理論學習中獲得實際的編程技能。

適合想從事軟體開發的初學者，或有編程基礎但想深入學習Java的學員。通過本課程，學員將具備構建Java基礎應用的能力，為進階技術打下堅實基礎。`);
    $("#coursePrice").val("16888");

});

$('#insertModuleBtn').click(function(){
    $('#moduleTableBody').html(`
    <tr>
       <td>
           <input type="text" class="form-control text-center no-spinner"
               value="1" readonly>
       </td>
       <td>
           <input type="text" class="form-control" value="Java基礎語法">
       </td>
       <td class="text-center">
           <a type="button" class="btn btn-primary btn-sm saveBtn">保存</a>
           <a type="button" class="btn btn-danger btn-sm deleteBtn">刪除</a>
       </td>
    /tr>
    <tr>
       <td>
           <input type="text" class="form-control text-center no-spinner"
               value="2" readonly>
       </td>
       <td>
           <input type="text" class="form-control" value="物件導向">
       </td>
       <td class="text-center">
           <a type="button" class="btn btn-primary btn-sm saveBtn">保存</a>
           <a type="button" class="btn btn-danger btn-sm deleteBtn">刪除</a>
       </td>
    /tr>
    <tr>
       <td>
           <input type="text" class="form-control text-center no-spinner"
               value="3" readonly>
       </td>
       <td>
           <input type="text" class="form-control" value="Java集合框架">
       </td>
       <td class="text-center">
           <a type="button" class="btn btn-primary btn-sm saveBtn">保存</a>
           <a type="button" class="btn btn-danger btn-sm deleteBtn">刪除</a>
       </td>
    /tr>
    <tr>
       <td>
           <input type="text" class="form-control text-center no-spinner"
               value="4" readonly>
       </td>
       <td>
           <input type="text" class="form-control" value="例外處理機制">
       </td>
       <td class="text-center">
           <a type="button" class="btn btn-primary btn-sm saveBtn">保存</a>
           <a type="button" class="btn btn-danger btn-sm deleteBtn">刪除</a>
       </td>
    /tr>
    <tr>
       <td>
           <input type="text" class="form-control text-center no-spinner"
               value="5" readonly>
       </td>
       <td>
           <input type="text" class="form-control" value="文件輸入/輸出(I/O)操作">
       </td>
       <td class="text-center">
           <a type="button" class="btn btn-primary btn-sm saveBtn">保存</a>
           <a type="button" class="btn btn-danger btn-sm deleteBtn">刪除</a>
       </td>
    /tr>
        `);

    // 為測試資料添加事件監聽
    $('#moduleTableBody tr').each(function() {
        addEventListenersToButtons(this);
    });
});

function addEventListenersToButtons(row) {
    const saveBtn = row.querySelector(".saveBtn");
    saveBtn.addEventListener("click", function () {
        const chapterInput = row.querySelector("td:nth-child(2) input");

        if (saveBtn.textContent.trim() === "保存") {
            chapterInput.readOnly = true;
            saveBtn.textContent = "編輯";
            // 移除刪除按鈕
            const deleteBtn = row.querySelector(".deleteBtn");
            if (deleteBtn) {
                deleteBtn.remove();
            }
        } else {
            chapterInput.readOnly = false;
            saveBtn.textContent = "保存";
            // 恢復刪除按鈕
            if (!row.querySelector(".deleteBtn")) {
                const deleteBtn = document.createElement("a");
                deleteBtn.type = "button";
                deleteBtn.className = "btn btn-danger btn-sm deleteBtn";
                deleteBtn.textContent = "刪除";
                deleteBtn.addEventListener("click", function () {
                    row.remove();
                    updateChapterNumbers();
                });
                row.querySelector("td.text-center").appendChild(deleteBtn);
            }
        }
    });

    const deleteBtn = row.querySelector(".deleteBtn");
    if (deleteBtn) {
        deleteBtn.addEventListener("click", function () {
            row.remove();
            updateChapterNumbers();
        });
    }
}

$('#insertLessonBtn').click(function(){
    $('#lessonTableBody').html(`
            <tr>
                <td>
                    <input type="text" class="form-control" id="courseLessonSort"
                        value="1">
                </td>
                <td>
                    <input type="text" class="form-control" id="courseLessonName"
                    value="Java開發環境設置與Hello World程序"  required>
                </td>
                <td>
                    <input type="file" class="form-control" id="lessonMedia"></input>
                </td>
                <td>
                    <select class="form-control" id="lessonMediaType" value="Video" required>
                        <option value="" disabled>請選擇</option>
                        <option value="Video" selected>影片</option>
                        <option value="File">檔案</option>
                        <option value="HomeWork">作業</option>
                    </select>
                </td>
                <td>
                    <input class="form-control" type="number" value="15" id="mediaDuration">
                </td>
                <td class="text-center">
                    <a type="button" class="btn btn-primary btn-sm saveLessonBtn">保存</a>
                    <a type="button"
                        class="btn btn-danger btn-sm deleteLessonBtn">刪除</a>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="text" class="form-control" id="courseLessonSort"
                        value="2">
                </td>
                <td>
                    <input type="text" class="form-control" id="courseLessonName"
                    value="數據類型、變量與運算符"  required>
                </td>
                <td>
                    <input type="file" class="form-control" id="lessonMedia"></input>
                </td>
                <td>
                    <select class="form-control" id="lessonMediaType" value="Video" required>
                        <option value="" disabled>請選擇</option>
                        <option value="Video" selected>影片</option>
                        <option value="File">檔案</option>
                        <option value="HomeWork">作業</option>
                    </select>
                </td>
                <td>
                    <input class="form-control" type="number" value="30" id="mediaDuration">
                </td>
                <td class="text-center">
                    <a type="button" class="btn btn-primary btn-sm saveLessonBtn">保存</a>
                    <a type="button"
                        class="btn btn-danger btn-sm deleteLessonBtn">刪除</a>
                </td>
            </tr>        
        `);
        // 添加一個新的空行，準備輸入新單元
        const newRow = `
        <tr>
            <td>
                <input type="text" class="form-control" id="courseLessonSort" name="courseLessonSort" required>
            </td>
            <td>
                <input type="text" class="form-control" id="courseLessonName" name="courseLessonName" required>
            </td>
            <td>
                <input type="file" class="form-control" id="lessonMedia" name="lessonMedia"></input>
            </td>
            <td>
                <select class="form-control" id="lessonMediaType" name="lessonMediaType" required>
                    <option value="" disabled selected>請選擇</option>
                    <option value="Video">影片</option>
                    <option value="File">檔案</option>
                    <option value="HomeWork">作業</option>
                </select>
            </td>
            <td>
                <input class="form-control" type="number" id="mediaDuration" name="mediaDuration">
            </td>
            <td class="text-center">
                <a type="button" class="btn btn-primary btn-sm saveLessonBtn">保存</a>
                <a type="button" class="btn btn-danger btn-sm deleteLessonBtn">刪除</a>
            </td>
        </tr>
    `;
        $('#lessonTableBody').append(newRow);
});