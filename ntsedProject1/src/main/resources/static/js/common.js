
/*
   DBからコードマスターの情報を取得し、セレクトボックスに設定
   param1: kbnコード区分、A01(性別情報),A02(部門情報)
   param2: id、セレクトタグのid属性にbindする
*/
export function showSelectOption(kbn, id) {
	$.ajax({
		type: "GET",
		url: "/code",
		data: `codeKbn=${kbn}`,
		dataType: "JSON",
		success: function(json) {
			if (json.status == 200) {
				let codeList = json.data;
				for (let i = 0; i < codeList.length; i++) {

					//optionタグ組み合わせ
					let option = "<option value='" + codeList[i].codeId + "'>" + codeList[i].codeName + "</option>";

					//selectにoption追加する
					$(`#${id}`).append(option);
				}
			}
		},
		error: function(xhr) {
			alert("失敗" + xhr.status);
		}
	});
}


export function blankCheck(elementId,elementname,errorId) {
	if ($(`#${elementId}`).val().trim() === "") {
		$(`#${errorId}`).text(`${elementname}を入力してください`);
		return false;
	}else{
		$(`#${errorId}`).text("");
		return true;
	}
	
}

export function minLengthCheck(elementId,elementName,errorId,length) {
	if ($(`#${elementId}`).val().trim().length < length) {
		$(`#${errorId}`).text(`${elementName}は少なくとも${length}文字以上である必要があります`);
		return false;
	}else{
		$(`#${errorId}`).text("");
		return true;
	}

}

export function maxLengthCheck(elementId,elementName,errorId,length) {
	if ($(`#${elementId}`).val().trim().length > length) {
		$(`#${errorId}`).text(`${elementName}は${length}文字を超えてはいけません`);
		return false;
	}else{
		$(`#${errorId}`).text("");
		return true;
	}

}

export function dateCheck(elementId,elementName,errorId,minDate,maxDate) {
	var min = new Date(minDate);
	var max = new Date(maxDate);
	var selectedDate = new Date($(`#${elementId}`).val());
	
	if(selectedDate < min || selectedDate > max){
		$(`#${errorId}`).text(`正しい${elementName}を入力してください。${minDate}から${maxDate}まで`);
		return false;
	}else{
		$(`#${errorId}`).text("");
		return true;
	}

}

export function emailFormatCheck(elementId,errorId){
	var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	
	if(!emailRegex.test($(`#${elementId}`).val().trim())){
		$(`#${errorId}`).text("正しいメールアドレスの形式ではありません");
		return false;
	}else{
		$(`#${errorId}`).text("");
		return true;
	}


}
