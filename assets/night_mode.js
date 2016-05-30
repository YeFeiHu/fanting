var intervale = 1000;
var exeNum = 0;
var timer = null;
var isEnd = false;
function crateLinkNightCSS() {

    if(document.getElementById('browser_night_mode')) {
        if(exeNum > 6) {
            if(!isEnd){
                window.clearInterval(timer);
                isEnd = true;
            }

        }else {
            removeShaow();
            addShaow();

            ++exeNum;
            window.night_mode.onReceivedMsg('head', 'js night mode get html');
        }

    }else {
        var head = document.getElementsByTagName('head')[0];

        var fileref = document.createElement('link');

        fileref.setAttribute('rel','stylesheet');

        fileref.setAttribute('href','http://www.yefeihu.com?file:///android_asset/night.css');

        fileref.setAttribute('id','browser_night_mode');
        fileref.setAttribute('type','text/css');
        head.appendChild(fileref);

        addShaow();


        window.night_mode.onReceivedMsg('head', 'js start night mode get html');
    }
}

function addShaow() {
    var shadow = document.getElementById('browser_night_shadow');
    if(shadow) {

    }else {
       shadow = document.createElement('div');
       shadow.setAttribute('class','browser_night_shadow');
       shadow.setAttribute('id','browser_night_shadow');
       shadow.setAttribute('style','width: 100% !important;height: 100% !important;z-index:9999; !important;position: fixed !important;display: block !important;top: 0px   !important;left: 00px !important;right:000px !important;button:00px !important;pointer-events: none !important;background-color : rgba(0,0,0,0.5) !important');
       var body = document.getElementsByTagName('body')[0];
       body.appendChild(shadow);
    }

}

function removeShaow(){
    var shadow = document.getElementById('browser_night_shadow');
    if(shadow) {
        try{
            document.body.removeChild(shadow);
        }catch(e){
            shadow.remove();
            return;
        }
    }

}

function reloadNight() {
     timer = window.setInterval(crateLinkNightCSS,intervale);

}


crateLinkNightCSS();

//reloadNight();

