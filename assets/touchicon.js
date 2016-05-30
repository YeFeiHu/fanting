var touchIcons = [];
function gatherTouchIcons(elements) {
	var normalTouchIconLength = elements.length;
	var currentElement;
	for (var i =0; i < normalTouchIconLength;i++) {
		currentElement = elements[i];
		var size;
		if (currentElement.hasAttribute('sizes')) {
			size = currentElement.sizes[0];
		} else {
			size = '';
		}
		var info = {'sizes':size, 'rel': currentElement.rel, 'href': currentElement.href};
		touchIcons.push(info);
	}
}

function obtainTouchIcons() {
	normalElements = document.querySelectorAll("link[rel='apple-touch-icon']");
	precomposedElements = document.querySelectorAll("link[rel='apple-touch-icon-precomposed']");
	gatherTouchIcons(normalElements);
	gatherTouchIcons(precomposedElements);
	var info = JSON.stringify(touchIcons);
	window.app_native.onReceivedTouchIcons(document.URL, info);

}


function selectText(element) {
  var doc = document,
      text = doc.getElementById(element),
      range,
      selection;

  if (doc.body.createTextRange) {
      range = document.body.createTextRange();
      range.moveToElementText(text);
      range.select();
  } else if (window.getSelection) {
      selection = window.getSelection();
      range = document.createRange();
      range.selectNodeContents(text);
      selection.removeAllRanges();
      selection.addRange(range);
      /*if(selection.setBaseAndExtent){
          selection.setBaseAndExtent(text, 0, text, 1);
      }*/
  }else{
      alert("none");
  }
}

obtainTouchIcons();
