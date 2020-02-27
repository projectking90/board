/**
*	입력양식이 비어 있거나 미체크 시 true 리턴하는 함수 선언
*/
function is_empty(selector){
	try{
		var flag = true;
		var obj = $(selector);
		
		if(obj.is(":checkbox") || obj.is(":radio")){
			if(obj.filter(":checked").length>0){ flag=false; }
		} else{
			var tmp = obj.val();
			
			tmp = $.trim(tmp);
			obj.val(tmp);
			
			tmp = tmp.split(" ").join("");
			if(tmp != ""){
				flag = false;
			}
		}
		
		return flag;
	} catch(e){
		alert("is_empty('" + selector + "') 함수에서 에러 발생!");
		return false;
	}
}

/**
* 검색화면에서 검색 결과물의 페이징 번호 출력 소스 리턴
*/
function getPagingNumber(
	totRowCnt               // 검색 결과 총 행 개수
	, selectPageNo_str         // 선택된 현재 페이지 번호
	, rowCntPerPage_str     // 페이지 당 출력행의 개수
	, pageNoCntPerPage_str  // 페이지 당 출력번호 개수
	, jsCodeAfterClick      // 페이지 번호 클릭후 실행할 자스 코드
) {
	if( $('[name=selectPageNo]').length==0 ){ 	// name=nowPage을 가진 hidden 태그없으면 경고하고 중지하는 자바스크립트 소스 생성해 저장
		alert("name=selectPageNo 을 가진 hidden 태그가 있어야 getPagingNumber(~) 함수 호출이 가능함.');" );
		return;
	}
	
	var arr = [];
	try{
		if( totRowCnt==0 ){	return ""; }	
		if( jsCodeAfterClick==null || jsCodeAfterClick.length==0){
			alert("getPagingNumber(~) 함수의 5번째 인자는 존재하는 함수명이 와야 합니다");
			return "";
		}
		
		/**
		 * 페이징 처리 관련 데이터 얻기
		 */
		if( selectPageNo_str==null || selectPageNo_str.length==0 ) { 
			selectPageNo_str="1";  // 선택한 현재 페이지 번호 저장
		} 
		if( rowCntPerPage_str==null || rowCntPerPage_str.length==0 ) { 
			rowCntPerPage_str="10";  // 선택한 현재 페이지 번호 저장
		}
		if( pageNoCntPerPage_str==null || pageNoCntPerPage_str.length==0 ) { 
			pageNoCntPerPage_str="10";  // 선택한 현재 페이지 번호 저장
		}
		
		var selectPageNo = parseInt(selectPageNo_str, 10);
		var rowCntPerPage = parseInt(rowCntPerPage_str,10);
		var pageNoCntPerPage = parseInt(pageNoCntPerPage_str,10);
		
		if( rowCntPerPage<=0 || pageNoCntPerPage<=0 ) {
			return;
		}
		
		var maxPageNo=Math.ceil( totRowCnt/rowCntPerPage );	// 최대 페이지 번호 얻기
			if( maxPageNo<selectPageNo ) { selectPageNo = 1; }

		//--------------------------------------------------------------
		// 선택된 페이지번호에 따라 출력할 [시작 페이지 번호], [끝 페이지 번호] 얻기
		//--------------------------------------------------------------
		var startPageNo = Math.floor((selectPageNo-1)/pageNoCntPerPage)*pageNoCntPerPage+1;  // 시작 페이지 번호
		var endPageNo = startPageNo+pageNoCntPerPage-1;                                      // 끝 페이지 번호
			if( endPageNo>maxPageNo ) { endPageNo=maxPageNo; }
			/*//--------------------------------------------------------------
			// <참고>위 코딩은 아래 코딩으로 대체 가능
			//--------------------------------------------------------------
			var startPageNo = 1;
			var endPageNo = pageNoCntPerPage;
			while( true ){
				if( selectPageNo <= endPageNo ){ startPageNo = endPageNo - pageNoCntPerPage + 1; break; }
				endPageNo = endPageNo + pageNoCntPerPage;
			}*/

		//---
		var cursor = " style='cursor:pointer' ";
		//arr.push( "<table border=0 cellpadding=3 style='font-size:13'  align=center> <tr>" );
		//--------------------------------------------------------------
		// [처음] [이전] 출력하는 자바스크립트 소스 생성해 저장
		//--------------------------------------------------------------
		//arr.push( "<td align=right width=110> " );
		if( startPageNo>pageNoCntPerPage ) {
			arr.push( "<span "+cursor+" onclick=\"$('[name=selectPageNo]').val('1');"
							+jsCodeAfterClick+";\">[처음]</span>" );
			arr.push( "<span "+cursor+" onclick=\"$('[name=selectPageNo]').val('"
				+(startPageNo-1)+"');"+jsCodeAfterClick+";\">[이전]</span>   " );
		}
		//--------------------------------------------------------------
		// 페이지 번호 출력하는 자바스크립트 소스 생성해 저장
		//--------------------------------------------------------------
		//arr.push( "<td align=center>  " );
		for( var i=startPageNo ; i<=endPageNo; ++i ){
			if(i>maxPageNo) {break;}
			if(i==selectPageNo || maxPageNo==1 ) {
				arr.push( "<b>"+i +"</b> " );
			}else{
				arr.push( "<span "+cursor+" onclick=\"$('[name=selectPageNo]').val('"
							+(i)+"');"+jsCodeAfterClick+";\">["+i+"]</span> " );
			}
		}
		//--------------------------------------------------------------
		// [다음] [마지막] 출력하는 자바스크립트 소스 생성해 저장
		//--------------------------------------------------------------
		//arr.push( "<td align=left width=110>  " );
		if( endPageNo<maxPageNo ) {
			arr.push( "   <span "+cursor+" onclick=\"$('[name=selectPageNo]').val('"
						+(endPageNo+1)+"');"+jsCodeAfterClick+";\">[다음]</span>" );
			arr.push( "<span "+cursor+" onclick=\"$('[name=selectPageNo]').val('"
						+(maxPageNo)+"');"+jsCodeAfterClick+";\">[마지막]</span>" );
		}
		//arr.push( "</table>" );
		return arr.join( "" );
	} catch(ex){
		alert("getPagingNumber(~) 메소드 호출 시 예외발생!");
		return "";
	}
}

/**
*	게시판 목록을 보여주는 table의 헤더행, 짝수행, 홀수행, 마우스over 일 때 배경색 설정하기
*/
function setTableTrBgColor(
	tableClassV	// 테이블 class 값
	, headerColor	// 헤더 tr 배경색
	, oddBgColor	// 홀수행 배경색
	, evendBgColor	// 짝수행 배경색
	, mouseOnBgColor	// 마우스 over 시 배경색
	){
	try{
		var firstTrObj = $("." + tableClassV + " tr:eq(0)");	// 첫 번째 tr - 헤더가 되는 tr 태그를 관리하는 jQuery 객체
		var trObjs = firstTrObj.siblings("tr");
		
		firstTrObj.css("background", headerColor);	// 첫 번째 tr 배경색 지정
		trObjs.filter(":odd").css("background", evendBgColor);	// 짝수 tr 배경색 지정
		trObjs.filter(":even").css("background", oddBgColor);	// 홀수 tr 배경색 지정
		
		trObjs.hover(
			function(){
				$(this).css("background", mouseOnBgColor);
			},
			function(){
				if($(this).index()%2==0){
					$(this).css("background", evendBgColor);
				} else{
					$(this).css("background", oddBgColor);
				}
			}
		);
	} catch(ex){
		alert("setTableTrBgColor(~) 메소드 호출 시 예외발생!");
		return "";
	}
}

/**
* 문자열의 패턴을 검사하여 true 또는 false를 리턴하는 함수
* @param selector : 입력양식의 이름
* @param regExpObj : 문자열 패턴을 가진 RegExp 객체
* @return regExpObj.test 메소드 결과 혹은 ""
*/
function is_valid_pattern(selector, regExpObj){
	try{
		var obj = $(selector);	// 선택자가 가르키는 입력양식을 관리하는 jQuery 객체
		
		if(obj.is(":checkbox") || obj.is(":radio") || obj.is("select")){
			alert("checkbox 또는 radio 또는 select는 is_valid_pattern 함수의 호출 대상이 아님");
			return "";
		}
		
		var value = obj.val();	// 입력양식의 value값 얻기
		
		return regExpObj.test(value);	// 입력양식의 value값이 패턴에서 벗어나면 false 리턴, 아니면 true 리턴
	} catch(ex){
		alert("is_valid_pattern(" + selector + ", ~) 메소드 호출 시 예외발생!");
		return "";
	}
	
}

/**
* 이메일의 패턴을 검사하여 true 또는 false를 리턴하는 함수
* @param selector : 이메일 입력양식의 이름
* @return is_valid_pattern 메소드 결과
*/
function is_valid_email(selector){
	return is_valid_pattern(
				selector
				, /^([0-9a-zA-Z_-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/);
}

/**
 * 키워드 검색 시 앞뒤 공백을 제거한 후 리턴하는 함수
 * @param selector : 키워드 입력양식의 이름
 * @return 앞뒤 공백이 제거된 데이터
 */
function removeSpace(selector){
	try{
		var obj = $(selector);
		var tmp = obj.val();
			
		tmp = $.trim(tmp);
		
		return tmp;
	} catch(e){
		alert("removeSpace('" + selector + "') 함수에서 에러 발생!");
		return;
	}
}

/**
 * 입력양식에 value값을 삽입하거나 체크해주는 함수 선언
 * @param selector : 선택자
 * @param date : 입력 데이터
 * @return
 */
function inputData(selector, data){
	try{
		var obj = $(selector);	// selector가 가르키는 입력 양식을 관리하는 jQuery 객체
		
		if(obj.length==0){	// selector가 가르키는 입력 양식이 없으면
			alert("inputData('" + selector + ", " + data + "') 호출 시 " + selector + "란 선택자를 가르키는 입력양식이 없음");	// 경고하고
			return;	// 함수 중단
		}

		if(obj.is(":checkbox") || obj.is(":radio")){	// 입력 양식이 checkbox 또는 radio면
			obj.filter("[value='" + data + "']").prop("checked", true);	// value 값으로 data 안의 데이터로 체크
		} else{	// 입력 양식이 checkbox 또는 radio가 아니면
			obj.val(data);	// value 값으로 data 안의 데이터를 삽입
		}
	} catch(e){
		alert("inputData('" + selector + ", " + data + "') 함수에서 에러 발생!");
		return;
	}
}