# Vocal Scribe

**VoiceScribe** is a voice transcription Android app that accurately and efficiently converts speech into text. It enables users to save and manage their transcriptions for easy access and future reference. The app currently supports **US English** and **German** and operates fully offline.

https://github.com/user-attachments/assets/ecd6b516-2444-4d93-8bcd-fc3ca66bdd26

## Features

- **Accurate Transcription** of English and German speech
- **Offline Functionality** – No internet connection required for speech recognition
- **Transcription Management** – Save, view, and delete prior transcriptions
- **User-Friendly Interface** – Intuitive UI for seamless interaction
- **Multi-Language Support** – Expandable to additional languages in the future
- **Dark Mode**
- **Punctuation Support** *(Coming Soon!)*
- **Export Options** *(Planned Feature: Export transcripts as TXT, PDF, or share via email/messaging apps)*

## Technical Breakdown

VoiceScribe is a **multi-module Clean-architected** Android app built with modern development practices. It utilizes the following technologies and libraries:

### Programming Language
- [Kotlin](https://kotlinlang.org/) – Modern language for Android development, optimized for concurrency and multi-threading

### Speech Recognition
- [Vosk](https://alphacephei.com/vosk/) – Offline, open-source speech recognition toolkit

### Asynchronous Data Handling
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html) – Handles real-time data streams efficiently

### UI Development
- [Jetpack Compose](https://developer.android.com/compose) – Android’s modern UI toolkit for building reactive interfaces

### Data Persistence
- [Room](https://developer.android.com/jetpack/androidx/releases/room) – Simplifies local database management
- [Datastore Preference](https://developer.android.com/topic/libraries/architecture/datastore) – Stores user preferences in key-value pairs

### Dependency Injection
- [Hilt](https://dagger.dev/hilt/) – Simplifies dependency injection and modular architecture

### Permissions Handling
- [Accompanist Permissions](https://github.com/google/accompanist/tree/main/permissions) – Manages system permissions in Jetpack Compose

### Unit Testing
- [Mockk](https://mockk.io/) – Mocking library for unit testing
- [Turbine](https://github.com/cashapp/turbine) – Handles unit testing for Flow-based concurrency

## Architecture

VoiceScribe follows **Clean Architecture** principles to ensure maintainability and scalability. The app is designed using a **modular architecture**, enabling independent feature development and easier testing.

### Modules Overview

![Vocal Scribe Architecture Diagram](https://github.com/user-attachments/assets/133ef6de-b151-4ef3-96e5-11386f499bfd)


- `:app` – The main Android application
- `:core-ui` – Base module containing common UI components, styling, and themes
- `:core-ui` – Base module containing common business logic
- `:feature-home` – Manages real-time transcription on the home screen
- `:feature-home-domain` – Contains business logic for home feature
- `:feature-transcript` – Handles viewing, updating, and deleting saved transcripts
- `:feature-transcript-domain` – Contains business logic for transcript feature
- `:data` – Manages data persistence and repository pattern
- `:speechrecognizer` – Speech recognition logic, exposing the `SpeechRecognizer` interface
- `:models` – Storage for AI-based speech recognition models

## Future Enhancements
- **Real-time Translation** – Translate transcriptions into other languages
- **Cloud Syncing** – Securely back up and restore transcriptions across devices
- **Voice Commands** – Control the app hands-free with predefined voice commands
- **Custom Vocabulary** – Add user-defined words for better recognition accuracy
- **Wear OS & Tablet Support** – Optimize the app for smartwatches and tablets

---

With **VoiceScribe**, users can enjoy a seamless, offline, and efficient voice-to-text experience tailored for productivity and accessibility.

