//活動狀態映射
const statusMapping = {
    0: '已關閉',
    1: '已啟用',
    2: '審核中',
    default: '錯誤'
};

//活動類別映射
const categoryMapping = {
    0: '其他',
    1: '講座',
    2: '工作坊',
    3: '訓練營',
    4: '研討會',
    5: '展覽',
    6: '競賽',
    default: '錯誤'
}

//活動專業映射
const majorMapping = [];
const majors = document.getElementById('majors-data').querySelectorAll('div');
majors.forEach(major => {
    const majorId = major.getAttribute('value');
    const majorName = major.getAttribute('data-name');
    majorMapping[majorId] = majorName;
});
majorMapping['default'] = '錯誤';

