# NetActivity (Java / Classic)

A lightweight Android network utility for quick server and domain availability checks. Written in pure Java using the standard Android SDK without heavy third-party dependencies.

With a minimal footprint (**~2 MB APK**) and low memory overhead, the app runs smoothly even on older devices (Android 7.0 – 10).

## Features
- **DNS Lookup:** Automatic IP address resolution from domain names via `InetAddress`.
- **ICMP Ping:** Packet transmission using the native `/system/bin/ping` binary with customizable packet count (1 to 50).
- **Smart Input Sanitization:** Paste full URLs (`https://example.com/page`) — the app automatically extracts the clean hostname.
- **Background Execution:** Network tasks run on a separate worker thread with thread-safe, real-time console updates.
- **Minimalist Dark UI:** Terminal-style output log displaying latency statistics (`min/avg/max`) with auto-scroll.

## Project Editions
- **NetActivity (Java)** *(this repository)* — Lightweight classic edition (~2 MB) supporting Android 7.0+ (API 24).
- **NetActivity Kotlin** — Feature-rich edition with Material You (Material Design 3), TCP socket ping (works over VPN), and a multi-threaded port scanner for Android 11+.

## Installation
Download the signed `app-release.apk` from the **[Releases](../../releases)** page and install it on your device (requires Android 7.0 or higher).

## Tech Stack
- **Language:** Java
- **UI:** Android XML (`AppCompatActivity`, `LinearLayout`, `ScrollView`)
- **Min SDK:** API 24 (Android 7.0 Nougat)
- Built entirely on a smartphone using **Code on the Go**.