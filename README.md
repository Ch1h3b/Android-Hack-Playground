# Android-Hack-Playground

## Overview

**Free Ram Installer** is an Android application designed as a CTF challenge for BSides Algiers 2023. This app is intentionally vulnerable and offers a hands-on experience for security enthusiasts to practice and enhance their Android hacking skills.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/Android-Hack-Playground.git
   ```
2. Open the project in Android Studio.
3. Build and run the app on your device or emulator.

### Challenge Description

The beginning of the challenge requires basic reversing skills, open the app with jadx, do not peek at the code from the repository unless you are stuck.

When you reach the point where you need the AES key for decryption, you will have to extract it from the native library. There are two primary solutions to this challenge:

1. **Using Frida**: Utilize Frida, a dynamic instrumentation toolkit, to extract the key from the running app.
2. **Manual Extraction**: Create a new Android Studio project, manually add the necessary libraries, and call the `getKey` function from class `A`.

### Decryption Instructions

Once you have the AES key, use CyberChef to decrypt the data using the AES ECB mode.

## Flag

The flag for this challenge is:

`shellmates{android_&_foren$ics=<3}`
