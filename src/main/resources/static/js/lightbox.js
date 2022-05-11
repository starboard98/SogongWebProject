// 라이트 박스
var size=3;         ////////size는 select시 검색된 사이즈////////
function LightBoxAct(num){
  var dark="#darken-background:nth-child("+num+")";
  var p=".paper:nth-child("+num+")";
  var li="#lightbox:nth-child("+num+")";
    function showLightBox() {
        // 라이트박스를 보이게 합니다.
        $(dark).show();
        $(dark).css('top', $(window).scrollTop());
        // 스크롤을 못하게 합니다.
        $('body').css('overflow', 'hidden');
    }
    function hideLightBox() {
        // 라이트박스를 안 보이게 합니다.
        $(dark).hide();
        // 스크롤을 하게 합니다.
        $('body').css('overflow', '');
    }
    // 라이트박스 제거 이벤트
    $(dark).click(function () {
        hideLightBox();
    });
      // 클릭 이벤트 연결
    $(p).click(function () {
        showLightBox();
    });
    // 라이트박스 제거 이벤트 보조
    $(li).click(function (event) {
        event.stopPropagation()
    });
}

$(document).ready(function(){
  for(var i=1; i<=size; i++){
    LightBoxAct(i)
  }
});
/*
$(document).ready(function () {
  var num=1;
  var dark="#darken-background:nth-child("+num+")";
  var p=".paper:nth-child("+num+")";
  var li="#lightbox:nth-child("+num+")";
    function showLightBox() {
        // 라이트박스를 보이게 합니다.
        $(dark).show();
        $(dark).css('top', $(window).scrollTop());
        // 스크롤을 못하게 합니다.
        $('body').css('overflow', 'hidden');
    }
    function hideLightBox() {
        // 라이트박스를 안 보이게 합니다.
        $(dark).hide();
        // 스크롤을 하게 합니다.
        $('body').css('overflow', '');
    }
    // 라이트박스 제거 이벤트
    $(dark).click(function () {
        hideLightBox();
    });
      // 클릭 이벤트 연결
    $(p).click(function () {
        showLightBox();
    });
    // 라이트박스 제거 이벤트 보조
    $(li).click(function (event) {
        event.stopPropagation()
    });
});
*/
