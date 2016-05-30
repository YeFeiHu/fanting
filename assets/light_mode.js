function startLightMode() {
    var head = document.getElementsByTagName('head')[0];
    var body = document.getElementsByTagName('body')[0];
    var nightMode = document.getElementById('browser_night_mode');
    var shadow = document.getElementById('browser_night_shadow');
    if(nightMode) {
        try{
            document.head.removeChild(nightMode);
            document.body.removeChild(shadow);
        }catch(e){
            nightMode.remove();
            shadow.remove();
        }
        window.light_mode.onStart(window.URL, 'js start light_mode succeed');
    }
}

startLightMode();
