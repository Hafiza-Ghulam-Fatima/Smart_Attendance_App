# Attendance App - QR Code Based Attendance System

A modern Android application that simplifies attendance tracking in educational institutions using QR code technology. The app features separate interfaces for teachers and students, making attendance management efficient and paperless.

## Features

### For Teachers 👨‍🏫
- **Generate QR Codes**: Create unique QR codes for each class session
- **Real-time Attendance Tracking**: View who has marked attendance in real-time
- **Session Management**: Create and manage multiple attendance sessions
- **Statistics Dashboard**: Track total sessions and present students
- **Attendance History**: View complete list of attendees for each session

### For Students 👨‍🎓
- **Scan QR Code**: Mark attendance by scanning teacher's QR code
- **Instant Confirmation**: Receive immediate feedback about attendance status
- **Simple Interface**: User-friendly design for quick access

## Technical Stack

- **Language**: Java
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Database**: SQLite (local storage)
- **QR Code Library**: 
  - ZXing for QR generation
  - Code Scanner for QR scanning
- **Architecture**: MVC pattern with SQLiteOpenHelper for database management

## Key Components

### Database Schema
- **Sessions Table**: Stores attendance sessions with timestamps
- **Attendance Table**: Records student emails for each session

### Core Classes
- `TeacherActivity`: QR code generation and attendance monitoring
- `StudentActivity`: QR code scanning interface
- `ScannerActivity`: Camera handling and QR processing
- `DatabaseHelper`: SQLite database management
- `AttendanceAdapter`: RecyclerView adapter for attendee list

## How It Works

1. **Teacher creates a session** → Generates unique QR code
2. **Students scan the QR** → System records their attendance
3. **Real-time updates** → Teacher sees growing attendee list
4. **Data persistence** → All records saved locally in SQLite

## Use Cases

- Classroom attendance tracking
- Workshop/seminar registration
- Event check-ins
- Meeting attendance records

## Future Enhancements

- [ ] Cloud synchronization
- [ ] Export attendance as CSV/Excel
- [ ] Student authentication
- [ ] Multiple session types
- [ ] Reports and analytics

## Installation

1. Clone the repository
2. Open in Android Studio
3. Build and run on device/emulator
4. Ensure camera permissions are granted

## Requirements

- Android 5.0 (Lollipop) or higher
- Camera access for QR scanning
- Internet permission (for future cloud features)

## Author

Hafiza Ghulam Fatima
