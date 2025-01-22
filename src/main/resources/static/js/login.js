const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#password');

togglePassword.addEventListener('click', function() {
	// パスワード表示/非表示を切り替え
	const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
	password.setAttribute('type', type);
	// アイコンを切り替え
	this.classList.toggle('fa-eye-slash');
});