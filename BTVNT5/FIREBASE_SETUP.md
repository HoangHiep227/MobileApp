# Hướng dẫn cấu hình Firebase cho UTH SmartTasks

## Bước 1: Cấu hình Firebase Console

1. Truy cập [Firebase Console](https://console.firebase.google.com/)
2. Tạo một dự án mới hoặc chọn dự án hiện có
3. Thêm ứng dụng Android với package name: `com.example.myapplication`

## Bước 2: Tải file google-services.json

1. Tải file `google-services.json` từ Firebase Console
2. Thay thế file `google-services.json` hiện tại trong thư mục `app/`

## Bước 3: Cấu hình Google Sign-In

1. Trong Firebase Console, vào Authentication > Sign-in method
2. Bật Google Sign-In
3. Lấy Web Client ID từ phần OAuth 2.0 client IDs
4. Thay thế `YOUR_WEB_CLIENT_ID` trong các file sau:
   - `app/src/main/java/com/example/myapplication/GoogleSignInActivity.kt` (dòng 30)
   - `app/src/main/java/com/example/myapplication/ui/screens/LoginScreen.kt` (nếu có)

## Bước 4: Cấu hình SHA-1 fingerprint

1. Lấy SHA-1 fingerprint của debug keystore:
   ```bash
   keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
   ```
2. Thêm SHA-1 fingerprint vào Firebase Console > Project Settings > Your apps

## Bước 5: Build và chạy ứng dụng

1. Sync project với Gradle
2. Build và chạy ứng dụng
3. Test chức năng đăng nhập Google

## Lưu ý quan trọng

- Đảm bảo file `google-services.json` được cập nhật đúng
- Kiểm tra Web Client ID đã được thay thế đúng
- SHA-1 fingerprint phải khớp với Firebase Console
- Ứng dụng cần kết nối internet để hoạt động

## Cấu trúc ứng dụng

- `AuthViewModel`: Quản lý trạng thái đăng nhập
- `LoginScreen`: Màn hình đăng nhập với Google Sign-In
- `ProfileScreen`: Màn hình hiển thị thông tin người dùng
- `GoogleSignInActivity`: Activity chính xử lý flow đăng nhập

