# Hướng dẫn chạy ứng dụng UTH SmartTasks

## Các bước cần thực hiện:

### 1. Cấu hình Firebase (QUAN TRỌNG)
- Mở file `FIREBASE_SETUP.md` để xem hướng dẫn chi tiết
- Thay thế `YOUR_WEB_CLIENT_ID` trong file `GoogleSignInActivity.kt` (dòng 30)
- Đảm bảo file `google-services.json` đã được cập nhật đúng

### 2. Build và chạy
```bash
./gradlew build
./gradlew installDebug
```

### 3. Test ứng dụng
- Mở ứng dụng UTH SmartTasks
- Nhấn "SIGN IN WITH GOOGLE"
- Đăng nhập bằng tài khoản Google
- Kiểm tra màn hình Profile hiển thị thông tin đúng

## Cấu trúc code đã tạo:

✅ **AuthViewModel** - Quản lý trạng thái đăng nhập Firebase
✅ **LoginScreen** - Màn hình đăng nhập với UI đẹp
✅ **ProfileScreen** - Màn hình hiển thị thông tin người dùng
✅ **GoogleSignInActivity** - Activity chính xử lý flow
✅ **Dependencies** - Đã thêm Firebase Auth, Google Sign-In, Coil

## Lưu ý:
- Ứng dụng cần kết nối internet
- Cần cấu hình Firebase Console trước khi chạy
- SHA-1 fingerprint phải khớp với Firebase

