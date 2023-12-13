//Các xử lý kịch bản cho loginv3.html

function checkValidLogin(){
	//Lấy thông tin
	let name = document.getElementById('inputName').value;
	let pass = document.getElementById('inputPass').value;
	
	//Tham chiếu vị trí báo lỗi
	let viewErrName = document.getElementById('errName');
	let viewErrPass = document.getElementById('errPass');
	
	//Biến xác nhận sự hợp lệ
	var validName = true;
	var validPass = true;
	
	//Biến ghi nhận thông báo
	var message = '';
	
	///Kiểm tra
	name = name.trim();
	if(name==''){
		validName = false;
		message = 'Please input username for login.';
	}else{
		if((name.length<5) || (name.length>50)){
			validName = false;
			message = 'Username length have from 5 to 50 characters';
		}else{
			if(name.indexOf(' ')!=-1){
				validName = false;
				message = 'Username should not contain space character';
			}else{
				if(name.indexOf('@')!=-1){
					var pattern = /\w+@\w+[.]\w/;
					if(!name.match(pattern)){
						validName = false;
						message = 'Username is incorrect email structure';
					}
				}
			}
		}
	}
	
	//Xuất thông báo name
	viewErrName.style.paddingTop = '5px';
	viewErrName.style.paddingBottom = '5px';
	viewErrName.style.marginTop = '5px';
	viewErrName.style.fontSize = '12px';
	if(validName){
		viewErrName.innerHTML='<i class="fa-solid fa-circle-check"></i>';
		viewErrName.style.backgroundColor = 'transparent';
		viewErrName.style.color = 'blue';
	}else{
		viewErrName.innerHTML=message;
		viewErrName.style.backgroundColor = 'red';
		viewErrName.style.color = 'yellow';
		
	}
	
	//Kiểm tra pass
	pass = pass.trim();
	if(pass==''){
		validPass = false;
		message = 'Please input password for login';
	}else{
		if(pass.length<6){
			validPass = false;
			message = 'Length of password is greater than or equal to 6 characters';
		}
		
	}
	
	//Xuất thông báo pass
	viewErrPass.style.paddingTop = '5px';
	viewErrPass.style.paddingBottom = '5px';
	viewErrPass.style.marginTop = '5px';
	viewErrPass.style.fontSize = '12px';
	if(validName){
		viewErrPass.innerHTML='<i class="fa-solid fa-circle-check"></i>';
		viewErrPass.style.backgroundColor = 'transparent';
		viewErrPass.style.color = 'blue';
	}else{
		viewErrPass.innerHTML=message;
		viewErrPass.style.backgroundColor = 'red';
		viewErrPass.style.color = 'yellow';	
	}
	
	return validName && validPass;
}

function login(fn){
	if(this.checkValidLogin()){
		fn.method = "post";
		fn.action = "/adv/user/login";
		fn.submit();
	}
}
