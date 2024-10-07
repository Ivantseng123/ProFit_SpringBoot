$(function () {
    var dialog, form,
        name = $("#name"),
        tips = $(".validateTips");

    function updateTips(t) {
        tips.text(t).addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }

    function checkLength(o, n, min, max) {
        if (o.val().length > max || o.val().length < min) {
            o.addClass("ui-state-error");
            updateTips(n + " 的長度必須在 " + min + " 到 " + max + " 個字元之間。");
            return false;
        } else {
            return true;
        }
    }

    function addModuleName() {
        var valid = true;
        name.removeClass("ui-state-error");

        valid = valid && checkLength(name, "模組名稱", 3, 16);

        if (valid) {
            var rowCount = $("#users tbody tr").length + 1;

            var newRow = $("<tr>")
                .append("<td style='text-align: center'>" + rowCount + "</td>")
                .append("<td class='courseModuleName'>" + name.val() + "</td>")
                .append("<td class='action-buttons'>" +
                    "<button class='edit btn btn-primary'>編輯</button> " +
                    "<button class='save btn btn-success' style='display:none;'>確認</button> " +
                    "<button class='delete btn btn-danger'>刪除</button>" +
                    "</td>");

            $("#users tbody").append(newRow);

            // 設定編輯和刪除按鈕事件
            newRow.find(".edit").on("click", editModule);
            newRow.find(".save").on("click", saveModule);
            newRow.find(".delete").on("click", deleteModule);

            dialog.dialog("close");
        }
        return valid;
    }

    function editModule() {
        var row = $(this).closest("tr");
        var courseModuleName = row.find(".courseModuleName");

        // 變為可編輯的 input 欄位
        courseModuleName.html("<input type='text' class='editInput' value='" + courseModuleName.text() + "' />");

        // 顯示「確認」按鈕，隱藏「編輯」按鈕
        row.find(".edit").hide();
        row.find(".save").show();
    }

    function saveModule() {
        var row = $(this).closest("tr");
        var courseModuleName = row.find(".courseModuleName");

        // 將輸入框的值保存回表格
        var newValue = row.find(".editInput").val();
        courseModuleName.html(newValue);

        // 隱藏「確認」按鈕，顯示「編輯」按鈕
        row.find(".save").hide();
        row.find(".edit").show();
    }

    function deleteModule() {
        $(this).closest("tr").remove();

        // 更新行號
        $("#users tbody tr").each(function (index) {
            $(this).find("td:first").text(index + 1);
        });
    }

    dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 300,
        width: 500,
        modal: true,
        buttons: {
            "Create Module": addModuleName,
            Cancel: function () {
                dialog.dialog("close");
            }
        },
        close: function () {
            form[0].reset();
            name.removeClass("ui-state-error");
        }
    });

    form = $("#dialog-form form").on("submit", function (event) {
        event.preventDefault();
        addModuleName();
    });

    $("#create-Module").button().on("click", function () {
        dialog.dialog("open");
    });
});