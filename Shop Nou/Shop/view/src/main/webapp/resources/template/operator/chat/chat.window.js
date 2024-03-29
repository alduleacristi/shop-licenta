/**
 * @author Semika Siriwardana(semika.siriwardana@gmail.com)
 */
function ChatWindow(config) {
	
    var _self = this;
    var _peerUserName;
    var _loginUserName;
    var _config;
    this._windowWidth  = 230;
    this._windowHeight = 230;
    this.lastUser      = null;
    this.windowArray   = [];
   
    this.getWindowLeftPosition = function() {
    	return this.windowArray.length*this._windowWidth;
    },
    
    this.getPeerUserName = function() {
    	return this._peerUserName;
    };
    
    this.getLoginUserName = function() {
    	return this._loginUserName;
    };
    
    this.getMessageContainerID = function() {
    	return this.getLoginUserName() + "_" + this.getPeerUserName();
    };
    
    this.getTextInputID = function() {
    	return this.getLoginUserName() + "_" + this.getPeerUserName() + "_chatInput";
    };
    
    this.getWindowID = function() {
    	return this.getLoginUserName() + "_" + this.getPeerUserName() + "_window";
    };
    
    this.hide = function(_self) {
    	$("#" + _self.getWindowID()).css("display", "none");
    };
    
    this.show = function() {
    	$("#" + this.getWindowID()).css("display", "block");
    };
    
    /**
     * Returns whether the chat window is currently visible or not
     */
    this.isVisible = function() {
    	return $("#" + this.getWindowID()).css("display") == "none"?false:true;
    };
    
    this.addOnClickListener = function(el, fnHandler, context) {
        $(el).bind("click", context, function(evt) {
            if(context != undefined) {
                fnHandler(context);
            } else {
                fnHandler();
            }
            return false;
        });
    };
    
    this.appendMessage = function(fromUser, text, loginUser) {
    	
    	var userNameCssClass    = "";
    	var textMessageCssClass = "";
    	
    	if (fromUser == loginUser) {
    		userNameCssClass    = "fromUserName";
        	textMessageCssClass = "fromMessage";
    	} else {
    		userNameCssClass    = "toUserName";
        	textMessageCssClass = "toMessage";
    	}
    	
    	if (this.lastUser == fromUser) {
    		fromUser = "...";
    	} else {
    		this.lastUser = fromUser;
    		fromUser += ':';
    	}
    	var chatContainer = $("#" + this.getMessageContainerID());
    	var sb = [];
    	sb[sb.length] = '<span class="' + userNameCssClass + '">' + fromUser + '</span>';
    	sb[sb.length] = '<span class="' + textMessageCssClass + '">' + text + '</span><br/>';
    	chatContainer.append(sb.join(""));  
    	chatContainer[0].scrollTop = chatContainer[0].scrollHeight - chatContainer.outerHeight();
    };
    
    this.focusTextInput = function() {
    	$("#" + this.getTextInputID()).focus();
    },
    
    this.getWindowBody = function() {
    	
    	var bodyDIV = document.createElement("div");
    	bodyDIV.setAttribute("id", this.getMessageContainerID()); 
    	bodyDIV.style.width     = this._windowWidth + "px";
    	bodyDIV.style.height    = "140px";
    	bodyDIV.style.position  = 'absolute';
    	bodyDIV.style.left      = 0;
    	bodyDIV.style.bottom    = "30px";
    	bodyDIV.style.overflowY = 'auto';
    	return bodyDIV;
    };
    
    this.getWindowFooter = function() {
    	
    	var footerDIV = document.createElement("div");
    	footerDIV.style.width  = this._windowWidth + "px";
    	footerDIV.style.height = "30px";
    	footerDIV.style.backgroundColor = '#8b0000'; 
    	footerDIV.style.position = 'absolute';
    	footerDIV.style.left     = 0;
    	footerDIV.style.bottom   = 0;
    	
    	//create text input
    	var textInput = document.createElement("input");
    	textInput.setAttribute("id", this.getTextInputID());
    	textInput.setAttribute("type", "text");
    	textInput.setAttribute("name", "chatInput");
    	textInput.setAttribute("class", "chatInput");
    	
    	$(textInput).attr('autocomplete', 'off');
        $(textInput).keyup(function(e) {
            if (e.keyCode == 13) {
            	$.cometChat.send($(textInput).val(), _self.getPeerUserName());
            	$(textInput).val('');
            	$(textInput).focus();
            }
        });
        
    	footerDIV.appendChild(textInput);
    	
    	return footerDIV;
    };
    
    this.getWindowHeader = function() {
    	
    	var headerDIV = document.createElement("div");
    	headerDIV.style.width  = this._windowWidth + "px";
    	headerDIV.style.height = "30px";
    //	headerDIV.style.backgroundColor = '#31B404'; 
    	headerDIV.style.backgroundColor ='#8b0000';
    	headerDIV.style.position = 'relative';
    	headerDIV.style.top      = 0;
    	headerDIV.style.left     = 0;
    	
    	var textUserName = document.createElement("span");
    	textUserName.setAttribute("class", "windowTitle");
    	textUserName.innerHTML = this.getPeerUserName();
    	
    	var textClose = document.createElement("span");
    	textClose.setAttribute("class", "windowClose");
    	textClose.innerHTML = "[X]";
    	this.addOnClickListener(textClose, this.hide, this);
    	
    	headerDIV.appendChild(textUserName);
    	headerDIV.appendChild(textClose);
    	
    	return headerDIV;
    };
    
    this.getWindowHTML = function() {
    	
    	var windowDIV = document.createElement("div");
    	windowDIV.setAttribute("id", this.getWindowID());
    	windowDIV.style.width  = this._windowWidth + "px"; 
    	windowDIV.style.height = this._windowHeight +"px";
    	windowDIV.style.backgroundColor = '#FFFFFF'; 
    	windowDIV.style.position = 'absolute';
    	windowDIV.style.bottom   = 0;
    	windowDIV.style.right    = this.getWindowLeftPosition() + "px"; 
    	windowDIV.style.zIndex   = 100;
    	windowDIV.style.border   = '1px solid #8b0000'; 
    	
    	windowDIV.appendChild(this.getWindowHeader()); 
    	windowDIV.appendChild(this.getWindowBody());
    	windowDIV.appendChild(this.getWindowFooter()); 
    	
    	return windowDIV;
    };
    
    this.initWindow = function(config) {
    	this._config = config;
    	this._peerUserName    = config.peerUserName;
    	this._loginUserName   = config.loginUserName;
    	this.windowArray      = config.windowArray;
    	
    	var body = document.getElementsByTagName('body')[0];
    	body.appendChild(this.getWindowHTML()); 
    	//focus text input just after opening window
    	this.focusTextInput();
    };
}