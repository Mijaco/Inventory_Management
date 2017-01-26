

function Encrypt(str) {
    if (!str) str = "";
    str = (str == "undefined" || str == "null") ? "" : str;
    try {
        var key = 146;
        var pos = 0;
        ostr = '';
        while (pos < str.length) {
            ostr = ostr + String.fromCharCode(str.charCodeAt(pos) ^ key);
            pos += 1;
        }

        return ostr;
    } catch (ex) {
        return '';
    }
}

function Decrypt(str) {
    if (!str) str = "";
    str = (str == "undefined" || str == "null") ? "" : str;
    try {
        var key = 146;
        var pos = 0;
        ostr = '';
        while (pos < str.length) {
            ostr = ostr + String.fromCharCode(key ^ str.charCodeAt(pos));
            pos += 1;
        }

        return ostr;
    } catch (ex) {
        return '';
    }
}

function  decrypt(){
    var salt = CryptoJS.enc.Hex.parse("4acfedc7dc72a9003a0dd721d7642bde");
    var iv = CryptoJS.enc.Hex.parse("69135769514102d0eded589ff874cacd");
    var encrypted = "PU7jfTmkyvD71ZtISKFcUQ==";
    var key = CryptoJS.PBKDF2("Secret Passphrase", salt, { keySize: 128/32, iterations: 100 });
    console.log( 'key '+ key);
    var decrypt = CryptoJS.AES.decrypt(encrypted, key, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
    var ddd = decrypt.toString(CryptoJS.enc.Utf8); 
    console.log('ddd '+ddd);
}
function encryptString(str, encryptionKey, iv) {
    var cipher = crypto.createCipheriv('aes-128-cbc', encryptionKey, iv);
    var cipherText = cipher.update(str, 'binary', 'base64');
    var cipherTextRemaining = cipher.final('base64');
    return cipherText + cipherTextRemaining;
}
function getTransformerList1(reqNoVal){
	/*var contextPath=$("#contextPath").val();
	var crypto = require(contextPath+'/resources/assets/js/workshop/aes.js');*/
	//var login = 'ABCD';
	var key = crypto.CryptoJS.enc.Hex.parse('0123456789012345');
	var ive  = crypto.CryptoJS.enc.Hex.parse('0123456789012345');
	//var key = crypto.CryptoJS.enc.Utf8.parse('0123456789012345');
	//var ive  = crypto.CryptoJS.enc.Utf8.parse('0123456789012345');
	//var encrypted = crypto.CryptoJS.AES.encrypt(reqNoVal, key, {iv: ive});
	//console.log('encrypted msg = ' + encrypted)
	
	 var cipher = crypto.createCipheriv('aes-128-cbc', key, ive);
	    var cipherText = cipher.update(reqNoVal, 'binary', 'base64');
	    var cipherTextRemaining = cipher.final('base64');
	    var r= cipherText + cipherTextRemaining;
	
	alert(r);
	window.location.href = 'getTransformerList.do?reqNo='
		+ r;
	
 }
/*$(document).ready(function() {
	$(function() {

		$("#reqNo").onChange({
			source : function(request, response) {
				 alert(request.term);
				$.ajax({
					url : 'getTransformerList.do',
					type : "POST",
					data : {
						reqNo : request.term
					},

					dataType : "json",

					success : function(data) {
						response($.map(data, function(v, i) {
							alert(v);
							return {

								label : v.itemName,
								value : v.itemName
							};

						}));
					}
				});
			}
		});
	});
});*/