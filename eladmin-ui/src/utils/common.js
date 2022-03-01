// 下载文件
export function downloadFile(obj, name,suffix){
  const url = window. URL. create0bjectURL(new Blob([obj]))
  const link = document.createElement ('a')
  link.style.display = 'none'
  link.href = url
  const fileName = parseTime(new Date()) +'-'+ name + '.' + suffix
  link.setAttribute('download', fileName)
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

// 金额大写转化函数   add by zhangbg 2018-11-12 start
//money 数字
//currencyunit 金额单位
export function changeMoneyToChinese(money, currencyunit) {
  if (currencyunit == "万元" || currencyunit == "万元/亩") {
    // 修改万元金额精度默认保留6位小数
    money = (money * 10000).toFixed(6);
    // end
  }
  var cnNums = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); // 汉字的数字
  var cnIntRadice = new Array("", "拾", "佰", "仟"); // 基本单位
  var cnIntUnits = new Array("", "万", "亿", "兆"); // 对应整数部分扩展单位
  var cnDecUnits = new Array("角", "分", "毫", "厘"); // 对应小数部分单位
  var cnInteger = "整"; // 整数金额时后面跟的字符
  var cnIntLast = "元"; // 整型完以后的单位
  var cnIntFirst = "负"; // 整型完以后的负数前缀
  var cnIntFirstFlag = false; // 负数flag
  var IntegerNum; // 金额整数部分
  var DecimalNum; // 金额小数部分
  // 新增金额小数部分字符串 
  var DecimalStr = ""; // 金额小数部分字符串
  // end
  var ChineseStr = ""; // 输出的中文金额字符串
  
  var parts; // 分离金额后用的数组，预定义

  // if (money == "") {
  //   console.log(money);
  //   return "";
  // }
  if (money < 0) {
    cnIntFirstFlag = true;
  }
  money = Math.abs(parseFloat(money));
  if (money == 0) {
    ChineseStr = cnNums[0] + cnIntLast + cnInteger;
    // ChineseStr = cnNums[0] + cnIntLast
    //单位是万元每亩时在末尾添加每亩
    if (currencyunit == "万元/亩") {
      ChineseStr = ChineseStr + "每亩";
    }
    return ChineseStr;
  }
  money = money.toString(); // 转换为字符串
  if (money.indexOf(".") == -1) {
    IntegerNum = money;
    DecimalNum = '';
  } else {
    parts = money.split(".");
    IntegerNum = parts[0];
    DecimalNum = parts[1].substr(0, 4);
  }
  if (parseInt(IntegerNum, 10) > 0) { // 获取整型部分转换
    let zeroCount = 0;
    let IntLen = IntegerNum.length;
    for (var i = 0; i < IntLen; i++) {
      let n = IntegerNum.substr(i, 1);
      let p = IntLen - i - 1;
      let q = p / 4;
      let m = p % 4;
      if (n == "0") {
        zeroCount++;

      } else {
        if (zeroCount > 0) {
          ChineseStr += cnNums[0];
        }
        zeroCount = 0; // 归零
        ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
      }
      if (m == 0 && zeroCount < 4) {
        ChineseStr += cnIntUnits[q];
      }
    }
    ChineseStr += cnIntLast;
    // 整型部分处理完毕
  }

  if (DecimalNum != '') { // 小数部分
    // [start]
    decLen = DecimalNum.length;
    for (var i = 0; i < decLen; i++) {
      let n = DecimalNum.substr(i, 1);
      if (n != '0') {
        DecimalStr += cnNums[Number(n)] + cnDecUnits[i];
      }
    }
  }

  // 无小数部分时显示xx元整 
  if (DecimalStr == '') {
    ChineseStr += cnInteger;
  } else {
    ChineseStr += DecimalStr;
  }
  // end

  if (ChineseStr == '') {
    // 空字符串显示零元整
    ChineseStr += cnNums[0] + cnIntLast + cnInteger;
    // end
  }
  //单位是万元每亩时在末尾添加每亩
  if (currencyunit == "万元/亩") {
    ChineseStr = ChineseStr + "每亩";
  }
  if (cnIntFirstFlag) {
    ChineseStr = cnIntFirst + ChineseStr;
  }
  return ChineseStr;
}

/**
 * 判断一个可split字符的是否存在有一个split结果在另一个可split的字符中(默认split字符串为“,”)
 */
function splitStrIsIntoString(strBig, strSmall) {
  var flag = false;
  if (strSmall && strSmall.length > 0) {
    var qxSplit = strSmall.split(",");
    if (qxSplit && qxSplit.length > 0) {
      for (var i = 0; i < qxSplit.length; i++) {
        if (strIsIntoString(strBig, qxSplit[i])) {
          flag = true;
          break;
        }
      }
    }
  }
  return flag;
}

/**
 * 判断一个字符是否在另一个可split的字符中(默认split字符串为“,”)
 */
function strIsIntoString(strBig, strSmall) {
  var flag = false;
  if (strBig && strBig.length > 0) {
    var qxSplit = strBig.split(",");
    if (qxSplit && qxSplit.length > 0) {
      for (var i = 0; i < qxSplit.length; i++) {
        if (strSmall == qxSplit[i]) {
          flag = true;
          break;
        }
      }
    }
  }
  return flag;
}

//动态加载JS或CSS文件
function loadjscssfile(filename, filetype) {
  if (filetype == "js") { //判断文件类型 
    $("html").append('<script src="' + filename + '" type="text/javascript"></script>');
  } else if (filetype == "css") { //判断文件类型 
    $("html").append('<link href="' + filename + '" rel="stylesheet" type="text/css" />');
  }
}

/**
 * 获取字符串的字节长度
 */
function getByteLength(str) {
  return str.replace(/[^\x00-\xff]/gi, "--").length;
}

/**
 * 获取字符串固定字节长度的内容，超长则用...替换
 */
function getStringByLength(str, subLength) {
  //设计思想：步步累加，超过则停
  var strRet = "";
  if (str != null && str.length > 0) {
    var strLength = 0;
    for (var i = 0; i < str.length; i++) {
      var strTemp = str.substring(i, i + 1);
      strLength = strLength + getByteLength(strTemp);
      if (strLength <= subLength) {
        strRet = strRet + strTemp;
      } else {
        strRet = strRet + "...";
        break;
      }
    }
  }
  return strRet;
}

//禁用本页所有下拉列表
function disenableAllSelect() {
  $("select").attr("disabled", "disabled");
}
//启用本页所有下拉列表
function enableAllSelect() {
  $("select").removeAttr("disabled");
}

//获取url参数
function request(paras) {
  var url = location.href;
  var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
  var paraObj = {}
  for (i = 0; j = paraString[i]; i++) {
    paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
  }
  var returnValue = paraObj[paras.toLowerCase()];
  if (typeof (returnValue) == "undefined") {
    return "";
  } else {
    return returnValue;
  }
}

//获取url参数（转换&amp;为&）
function requestAmp(paras) {
  var url = location.href;
  url = myReplaceAll(url, "&amp;", "&");
  var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
  var paraObj = {}
  for (i = 0; j = paraString[i]; i++) {
    paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
  }
  var returnValue = paraObj[paras.toLowerCase()];
  if (typeof (returnValue) == "undefined") {
    return "";
  } else {
    return returnValue;
  }
}

//获取url参数(解决中文乱码)
function decodRequestURL(paras) {
  var u = location.href;
  // 浏览器(除IE外)默认使用 encodeURI() 进行URL的编码， 当参数有中文时，直接取值会出现乱码，
  // 解决办法 使用   decodeURI() 进行URL解码，再取值
  var url = decodeURI(u);
  var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
  var paraObj = {}
  for (i = 0; j = paraString[i]; i++) {
    paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
  }
  var returnValue = paraObj[paras.toLowerCase()];
  if (typeof (returnValue) == "undefined") {
    return "";
  } else {
    return returnValue;
  }
}

// 格式化日期
export function formatJsonDate(date) {
  if (date == null || date == "") {
    return "";
  }
  var date = new Date(date);
  var seperator = "-"; //年月日间的间隔
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var strDate = date.getDate();

  var hour = date.getHours();
  var minute = date.getMinutes();
  var second = date.getSeconds();

  if (month >= 1 && month <= 9) {
    month = "0" + month; //如果月份位于1至9月内，则前面加0
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate; //如果日为1至9日内，则前面加0
  }
  if (hour >= 0 && hour <= 9) {
    hour = "0" + hour
  }
  if (minute >= 0 && minute <= 9) {
    minute = "0" + minute
  }
  if (second >= 0 && second <= 9) {
    second = "0" + second
  }
  var currentdate = year + seperator + month + seperator + strDate + " " + hour + ":" + minute + ":" + second;
  return currentdate;
}

// 格式化日期
export function formatJsonChinaDate(date) {
  if (date == null || date == "") {
    return "";
  }
  var date = new Date(date);
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var strDate = date.getDate();

  var hour = date.getHours();
  var minute = date.getMinutes();
  var second = date.getSeconds();

  if (month >= 1 && month <= 9) {
    month = "0" + month; //如果月份位于1至9月内，则前面加0
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate; //如果日为1至9日内，则前面加0
  }
  if (hour >= 0 && hour <= 9) {
    hour = "0" + hour
  }
  if (minute >= 0 && minute <= 9) {
    minute = "0" + minute
  }
  if (second >= 0 && second <= 9) {
    second = "0" + second
  }
  var currentdate = year + '年' + month + '月' + strDate + "日";
  return currentdate;
}


// 格式化日期(年月日)
export function formatJsonDateYMD(date) {
  if (date != null && date != "") {
    var month = (date.month + 1) + "";
    var day = date.date + "";
    if (month.length == 1) {
      month = "0" + month;
    }
    if (day.length == 1) {
      day = "0" + day;
    }
    return (date.year + 1900) + "-" + month + "-" + day;
  }
  return "";
}

//把时间戳转为普通的日期格式（yy-MM-dd）
export function getFormatDate(dates) {
  if (dates) {
    if (dates == "null") {
      return "";
    } else {
      var date = new Date(dates);
      var seperator = "-"; //年月日间的间隔
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();

      if (month >= 1 && month <= 9) {
        month = "0" + month; //如果月份位于1至9月内，则前面加0
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate; //如果日为1至9日内，则前面加0
      }
      var currentdate = year + seperator + month + seperator + strDate;
      return currentdate;
    }
  } else {
    return "";
  }
}


//得到不为空的字符串
function getStrNoNull(str) {
  if (str) {
    if (str == "null") {
      str = "";
    }
    return str;
  } else {
    return "";
  }
}

//检查数组中的记录是否重复
function checkSzRepeatVal(strSz) {
  var retFlag = false;
  if (strSz && strSz.length > 0) {
    var currVal;
    for (var i = 0; i < strSz.length; i++) {
      currVal = strSz[i];
      for (var j = i + 1; j < strSz.length; j++) {
        if (currVal == strSz[j]) {
          retFlag = true;
          break;
        }
      }
      if (retFlag) {
        break;
      }
    }
  }
  return retFlag;
}

//获取当前月第一天
function getCurrMonthFirst() {
  var date = new Date();
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  if (month < 10) {
    month = "0" + month;
  }
  return date.getFullYear() + "-" + month + "-01";
}

//获取当前月最后一天
function getCurrMonthLast() {
  var date = new Date();
  var year = date.getFullYear();
  var month = date.getMonth();
  var nextMonth = month + 1;
  if (nextMonth == 12) {
    nextMonth = 0;
    year = year + 1;
  }
  var nextMonthFirstDay = new Date(year, nextMonth, 1);
  var minusDate = 1000 * 60 * 60 * 24;
  var dateTmp = new Date(nextMonthFirstDay.getTime() - minusDate);
  var year2 = dateTmp.getFullYear();
  var month2 = dateTmp.getMonth() + 1;
  var day2 = dateTmp.getDate();
  if (month2 < 10) {
    month2 = "0" + month2;
  }
  if (day2 < 10) {
    day2 = "0" + day2;
  }
  return year2 + "-" + month2 + "-" + day2;
}

//获取当前年月日
function getCurrDate() {
  var d = new Date();
  var year = d.getFullYear();
  var month = d.getMonth() + 1;
  var day = d.getDate();
  if (month < 10) {
    month = "0" + month;
  }
  if (day < 10) {
    day = "0" + day;
  }
  return year + "-" + month + "-" + day;
}

//字符串全部替换方法
function myReplaceAll(s1, s2, s3) {
  var r = new RegExp(s2.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g, "\\$1"), "ig");
  return s1.replace(r, s3);
}

//下拉列表当前选中的数据项向上移动一行
function listMoveUp(listId) {
  var val = $("#" + listId).val();
  var size = $("#" + listId + " option").size();
  var nowIndex = $("#" + listId + " :selected").index();
  if (val && val.length > 0 && size > 1 && nowIndex > 0) {
    $("#" + listId + " option:selected").insertBefore($("#" + listId + " option:selected").prev("option"));
  }
}

//下拉列表当前选中的数据项向下移动一行
function listMoveDown(listId) {
  var val = $("#" + listId).val();
  var size = $("#" + listId + " option").size();
  var optionLength = $("#" + listId)[0].options.length;
  var nowIndex = $("#" + listId + " :selected").index();
  if (val && val.length > 0 && size > 1 && nowIndex < (optionLength - 1)) {
    $("#" + listId + " option:selected").insertAfter($("#" + listId + " option:selected").next("option"));
  }
}

//精度减法函数 
function numSub(num1, num2) {
  var baseNum,
    baseNum1,
    baseNum2;
  try {
    baseNum1 = num1.toString().split(".")[1].length;
  } catch (e) {
    baseNum1 = 0;
  }
  try {
    baseNum2 = num2.toString().split(".")[1].length;
  } catch (e) {
    baseNum2 = 0;
  }
  baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
  var precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
  return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
}

//金额原始格式（去逗号）
function jeCdh(node) {
  node.value = node.value.toString().replace(/,/g, "");
}

//规范金额格式
function formatJine(number, node) {
  if (number == null || number == "") {
    return "";
  } else {
    var num = number.toString().replace(/,/g, "");
    if (cNumber(num)) {
      var arr = num.toString().split('.');
      num = parseFloat(arr[0]).toLocaleString();
      if (node != null && node != "") {
        if (num[0] == "-") {
          node.style.color = "red";
        } else {
          node.style.color = "#0000FF";
        }
        node.value = (num + '.' + ((arr[1] || '00') + '0').substring(0, 2));
      }
      return (num + '.' + ((arr[1] || '00') + '0').substring(0, 2));
    } else {
      window.top.alert('消息', '请输入数字', 'warning');
      node.value = "0.00";
    }
  }
}

/**
 * 验证数字
 */
function cNumber(val) {
  var regPos = /^\d+(\.\d+)?$/; //非负浮点数
  var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
  if (regPos.test(val) || regNeg.test(val)) {
    return true;
  } else {
    return false;
  }
}

/** 
 * 为数字加上单位：万或亿 
 * 例如： 
 * 1000.01 => 1000.01 
 * 10000 => 1万 
 * 99000 => 9.9万 
 * 566000 => 56.6万 
 * 5660000 => 566万 
 * 44440000 => 4444万 
 * 11111000 => 1111.1万 
 * 444400000 => 4.44亿 
 * 40000000,00000000,00000000 => 4000万亿亿 
 * 4,00000000,00000000,00000000 => 4亿亿亿 
 * 
 * @param {number} number 输入数字. 
 * @param {number} decimalDigit 小数点后最多位数，默认为2 
 * @return {string} 加上单位后的数字 
 */
var numberFormats = {
  addWan: function (integer, number, mutiple, decimalDigit) {
    var me = this;
    var digit = me.getDigit(integer);
    if (digit > 3) {
      var remainder = digit % 8;
      if (remainder >= 5) { // ‘十万’、‘百万’、‘千万’显示为‘万’ 
        remainder = 4;
      }
      return Math.round(number / Math.pow(10, remainder + mutiple - decimalDigit)) / Math.pow(10, decimalDigit) + '万';
    } else {
      return Math.round(number / Math.pow(10, mutiple - decimalDigit)) / Math.pow(10, decimalDigit);
    }
  },
  getDigit: function (integer) {
    var digit = -1;
    while (integer >= 1) {
      digit++;
      integer = integer / 10;
    }
    return digit;
  },
  addChineseUnit: function (number, decimalDigit) {
    var me = this;
    decimalDigit = decimalDigit == null ? 2 : decimalDigit;
    var integer = Math.floor(number);
    var digit = me.getDigit(integer);
    // ['个', '十', '百', '千', '万', '十万', '百万', '千万']; 
    var unit = [];
    if (digit > 3) {
      var multiple = Math.floor(digit / 8);
      if (multiple >= 1) {
        var tmp = Math.round(integer / Math.pow(10, 8 * multiple));
        unit.push(me.addWan(tmp, number, 8 * multiple, decimalDigit));
        for (var i = 0; i < multiple; i++) {
          unit.push('亿');
        }
        return unit.join('');
      } else {
        return me.addWan(integer, number, 0, decimalDigit);
      }
    } else {
      return number;
    }
  }
};

var unitCN = ["京", "", "", "", "万兆", "", "", "", "兆", "", "", "", "万亿", "", "", "", "亿", "", "", "", "万", "", "", "", ""];

/**
 * 把一个double数值转换为带有中文单位的字符串
 * @author 雷日初
 * @date 2020年1月17日 上午09:56:52
 * @param num 待转换的double数值
 * @param digit 添加计量单位后要保留的最长小数位数（注：小于一万的数值，原小数部分将全部保留）
 * 如：10000.1234转换得“1万”，9999.1234转换得“9999.1234”
 * @return 带中文“兆/亿/万”计量单位的字符串,在不超过指定保留的最长小数位数的情况下，再截去结尾连续为0的小数位
 */
function formartNumByCnUnit(num, digit) {
  if (!num) {
    return "0";
  }
  if (typeof (num) == "string") {
    num = Number(num);
    if (isNaN(num)) {
      return "0";
    }
  }
  if (!digit || digit < 0) {
    digit = 2;
  }
  var numberStr = (num + "").split(".");
  var ends = numberStr.length > 1 ? numberStr[1].split("") : []; //原始数值的小数部分
  var splits = numberStr[0].split(""); //原始数值的整数部分
  for (var i = ends.length - 1; i >= 0; i--) { //清除连续为0的小数位
    if (ends[i] != "0") {
      break;
    }
    ends[i] = "";
  }
  ends = ends.join("");
  var retStr = [];
  var unit = "";
  for (var i = 0; i < splits.length; i++) {
    if (splits.length > 4 && (unitCN.length - splits.length + i) % 4 == 0 && unit.length == 0) {
      unit = unitCN[unitCN.length - splits.length + i];
      retStr.push(splits[i]);
      var others = (splits.slice(i + 1)).join("").substr(0, digit + 1);
      if (Number(others) > 0) {
        if (others.length > 0) {
          if (others.length > digit) {
            var tmps = (Math.round(Number(others) / 10)) * Math.pow(10, -digit);
            others = (tmps + "").split(".")[1] ? (tmps + "").split(".")[1] : "";
            if (tmps >= 1) {
              var tmpInt = Number(retStr.join("")) + 1;
              if (tmpInt == 10000) {
                retStr = ["1"];
                unit = unitCN[unitCN.length - splits.length + i - 4];
              } else {
                retStr = [tmpInt + ""];
              }
            }
          }
        }
        while (others.endsWith("0")) {
          others = others.substring(0, others.length - 1);
        }
        others = others.length == 0 ? "" : "." + others;
      } else {
        others = "";
      }
      retStr.push(others + unit);
      break;
    } else {
      retStr.push(splits[i]);
    }
  }
  if (unit.length == 0 && ends.length > 0) {
    retStr.push("." + ends);
  }
  return retStr.join("");
}
